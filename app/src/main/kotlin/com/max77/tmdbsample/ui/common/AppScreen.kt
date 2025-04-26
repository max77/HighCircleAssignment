import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.max77.tmdbsample.R
import com.max77.tmdbsample.ui.common.ViewState
import com.max77.tmdbsample.ui.theme.Typography

@Composable
fun <T> AppScreen(
    viewState: ViewState<T>,
    loadingContent: @Composable () -> Unit = { LoadingContent() },
    errorContent: @Composable (message: String?) -> Unit = { ErrorContent(it) },
    content: @Composable (data: T) -> Unit = {}
) {
    when (viewState) {
        is ViewState.Loading -> loadingContent()
        is ViewState.Success -> content(viewState.data)
        is ViewState.Error -> errorContent(viewState.error?.message ?: "Error")
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp), color = Color.Blue)
    }
}

@Composable
private fun ErrorContent(message: String?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message ?: stringResource(R.string.error),
            color = Color.Red,
            style = Typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}


// Preview

@Preview(showBackground = true)
@Composable
fun LoadingContentPreview() {
    LoadingContent()
}

@Preview(showBackground = true)
@Composable
fun SuccessContentPreview() {
    val successData = listOf("Item 1", "Item 2", "Item 3")
    AppScreen(viewState = ViewState.Success(successData)) { data ->
        Column {
            Text(text = "Success", fontSize = 20.sp, color = Color.Green)
            data.forEach { item ->
                Text(text = item, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorContentPreview() {
    ErrorContent(message = "Failed to load data: Network error")
}