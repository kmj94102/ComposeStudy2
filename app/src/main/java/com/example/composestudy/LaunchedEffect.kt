package com.example.composestudy

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun LaunchedEffectTest() {
    val context = LocalContext.current
    val number = remember {
        mutableStateOf(0)
    }
    Box {
        Text(
            text = "${number.value} click",
            modifier = Modifier
                .align(Alignment.Center)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 20.dp)
                .align(Alignment.BottomCenter)
        ) {
            Button(
                onClick = { number.value = number.value + 1 },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "+1")
            }

            Button(
                onClick = { number.value = number.value + 0 },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "+0")
            }
        }
    }

    if (number.value == 2) {
        LaunchedEffect(number.value) {
            Toast.makeText(context, "+1을 두번 째 눌렀습니다.", Toast.LENGTH_SHORT)
                .show()
        }
    }
}