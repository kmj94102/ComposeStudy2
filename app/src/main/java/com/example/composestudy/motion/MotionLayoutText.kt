package com.example.composestudy.motion

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.composestudy.R

@OptIn(ExperimentalMotionApi::class)
@Composable
fun MotionLayoutTest() {
    var state by remember {
        mutableStateOf(false)
    }
    val progress by animateFloatAsState(
        targetValue = if (state) 1f else 0f,
        animationSpec = tween(500)
    )
    val context = LocalContext.current
    val motionSceneContent = remember {
        context.resources
            .openRawResource(R.raw.my_motion)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        motionScene = MotionScene(motionSceneContent),
        progress = progress
    ) {
        val boxProperties = motionProperties(id = "box")

        Box(
            modifier = Modifier
                .layoutId("box")
                .size(50.dp)
                .background(boxProperties.value.color("background"))
        )

        Button(
            onClick = { state = !state },
            modifier = Modifier.layoutId("button")
        ) {
            Text(text = if (state) "초기화" else "시작")
        }
    }

}

private fun startConstraintSet(): ConstraintSet = ConstraintSet(
    """
        {
            box: {
                top: ['parent', 'top', 30],
                bottom: ['parent', 'bottom'],
                start: ['parent' , 'start', 20],
                custom: {
                  background: "#84FFFF"
                }
            },
            button: {
                width: "parent", 
                start: ['parent', 'start', 20],
                end: ['parent', 'end', 20],
                bottom: ['parent', 'bottom', 30]
            }
        }
    """
)

private fun endConstraintSet(): ConstraintSet = ConstraintSet(
    """
        {
            box: {
                top: ['parent', 'top', 30],
                bottom: ['parent', 'bottom'],
                end: ['parent' , 'end', 20],
                custom: {
                  background: "#FF9E80"
                }
            },
            button: {
                width: "parent",
                start: ['parent', 'start', 20],
                end: ['parent', 'end', 20],
                bottom: ['parent', 'bottom', 30]
            }
        }
    """
)

@Composable
private fun myMotionScene() = MotionScene(
    """
    {
      ConstraintSets: {
        start: {
            box: {
                top: ['parent', 'top', 30],
                bottom: ['parent', 'bottom'],
                start: ['parent' , 'start', 20],
                custom: {
                  background: "#84FFFF"
                }
            },
            button: {
                width: "parent", 
                start: ['parent', 'start', 20],
                end: ['parent', 'end', 20],
                bottom: ['parent', 'bottom', 30]
            }
        },
        end: {
          box: {
                top: ['parent', 'top', 30],
                bottom: ['parent', 'bottom'],
                end: ['parent' , 'end', 20],
                custom: {
                  background: "#FF9E80"
                }
            },
            button: {
                width: "parent",
                start: ['parent', 'start', 20],
                end: ['parent', 'end', 20],
                bottom: ['parent', 'bottom', 30]
            }
        }
      }
    }
    """
)