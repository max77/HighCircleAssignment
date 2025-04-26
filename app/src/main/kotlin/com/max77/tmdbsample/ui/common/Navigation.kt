package com.max77.tmdbsample.ui.common

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.koin.compose.koinInject
import kotlin.reflect.typeOf

class Router() {
    private val scope = CoroutineScope(Dispatchers.Default)
    private val _navigationEvent = MutableSharedFlow<Any>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun navigateBack() {
        navigate(BackEvent)
    }

    fun navigate(route: Any) {
        scope.launch {
            _navigationEvent.emit(route)
        }
    }

    companion object {
        const val BackEvent = "back_event"
    }
}

@Composable
fun NavHost(
    modifier: Modifier,
    startDestination: Any,
    builder: NavGraphBuilder.(Router) -> Unit
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val router = koinInject<Router>()

    LaunchedEffect(key1 = router.navigationEvent, key2 = router) {
        scope.launch {
            router.navigationEvent.collectLatest { event ->
                when (event) {
                    Router.BackEvent -> navController.navigateUp()
                    else -> navController.navigate(event)
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        builder(router)
    }
}


@Serializable
data class Route(val payload: String?)

inline fun <reified T : Any> serializableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        bundle.getString(key)?.let<String, T>(json::decodeFromString)

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, json.encodeToString(value))
    }
}

inline fun <reified T : Any, reified P : Any> NavGraphBuilder.composable(
    noinline content: @Composable (T?) -> Unit,
) {
    composable<T>(typeMap = mapOf(typeOf<P>() to serializableType<P>())) {
        val arg = it.toRoute<T>()
        content(arg)
    }
}


