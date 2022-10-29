package com.example.composestudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composestudy.effect.LaunchedEffectTest
import com.example.composestudy.map.NaverMapTest
import com.example.composestudy.map.NaverMapTest2
import com.example.composestudy.ui.theme.ComposeStudyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeStudyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    // rememberCoroutineScope
                    // rememberUpdateState
                    // DisposableEffect
                    // SideEffect
                    // produceState
                    // derivedStateOf
                    // snapshotFlow
//                    LaunchedEffectTest()
//                    GoogleMapTest()
//                    NaverMapTest()
                    NaverMapTest2()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeStudyTheme {
        Greeting("Android")
    }
}

val LocalSpacing = compositionLocalOf { Spacing() }

data class Spacing(
    val Medium: Dp = 10.dp
)