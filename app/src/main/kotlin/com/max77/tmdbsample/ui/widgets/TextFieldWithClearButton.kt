import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldWithClearButton(
    modifier: Modifier = Modifier,
    label: String = "Enter text",
    onTextChange: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            onTextChange(newText) // Notify the caller about text changes
        },
        label = { Text(label) },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = {
                    text = ""
                    onTextChange("") // Notify the caller that the text was cleared
                }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
                }
            }
        },
        modifier = modifier
    )
//    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTextFieldWithClearButton() {
    TextFieldWithClearButton(onTextChange = { println("Text changed: $it") })
}