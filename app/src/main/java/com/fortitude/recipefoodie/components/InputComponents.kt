package com.fortitude.recipefoodie.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InputWithHint(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    singleLine: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = singleLine,
            modifier = modifier
                .padding(16.dp),
            textStyle = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
        )

        if (isHintVisible) {
            Text(
                text = hint,
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                ),
                color = Color.DarkGray,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}