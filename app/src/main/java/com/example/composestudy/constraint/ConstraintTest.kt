package com.example.composestudy.constraint

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

@Composable
fun ConstraintTest() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val button1 = createRef()
        val (button2, text1, text2, text3, text4, text5, box) = createRefs()

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(text = "button1")
        }

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(button1.bottom, 10.dp)
                start.linkTo(parent.start, 20.dp)
            }
        ) {
            Text(text = "button2")
        }
        val line = createGuidelineFromStart(fraction = 0.5f)
        Box(modifier = Modifier
            .background(Color(0xFF1DE9B6))
            .constrainAs(box) {
                start.linkTo(line)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }
        )

        Text(
            text = "text1 Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            color = Color.Red,
            modifier = Modifier
                .background(Color.Black)
                .constrainAs(text1) {
                    top.linkTo(parent.top)
                    start.linkTo(line)
                    end.linkTo(parent.end)
                    width = Dimension.preferredWrapContent
                }
        )

        Text(
            text = "text2 Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            color = Color.Blue,
            modifier = Modifier
                .background(Color.Black)
                .constrainAs(text2) {
                    top.linkTo(text1.bottom, 10.dp)
                    start.linkTo(line)
                    end.linkTo(parent.end)
                    width = Dimension.wrapContent
                }
        )

        Text(
            text = "text3 Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            color = Color.Green,
            modifier = Modifier
                .background(Color.Black)
                .constrainAs(text3) {
                    top.linkTo(text2.bottom, 10.dp)
                    start.linkTo(line)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = "text4 Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            color = Color.White,
            modifier = Modifier
                .background(Color.Black)
                .constrainAs(text4) {
                    top.linkTo(text3.bottom, 10.dp)
                    start.linkTo(line)
                    end.linkTo(parent.end)
                    width = Dimension.preferredValue(100.dp)
                }
        )

        Text(
            text = "text5 Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
            color = Color.Yellow,
            modifier = Modifier
                .background(Color.Black)
                .constrainAs(text5) {
                    top.linkTo(text4.bottom, 10.dp)
                    start.linkTo(line)
                    end.linkTo(parent.end)
                    width = Dimension.value(100.dp)
                }
        )
    }
}

@Composable
fun ConstraintTest2() {
//    ConstraintLayout(decoupledConstraints()) {
//        Box(
//            modifier = Modifier
//                .layoutId("box1")
//                .background(Color(0xFFFF8A80))
//        )
//        Box(
//            modifier = Modifier
//                .layoutId("box2")
//                .background(Color(0xFF80D8FF))
//        )
//    }

    ConstraintLayout {
        val (text1, text2, text3) = createRefs()
        createHorizontalChain(text1, text2, text3, chainStyle = ChainStyle.Spread)

        Text(
            text = "text1",
            modifier = Modifier.constrainAs(text1) {
                top.linkTo(parent.top, 20.dp)
            }
        )

        Text(
            text = "text2",
            modifier = Modifier.constrainAs(text2) {
                top.linkTo(text1.top)
            }
        )

        Text(
            text = "text3",
            modifier = Modifier.constrainAs(text3) {
                top.linkTo(text1.top)
            }
        )
    }
}

private fun decoupledConstraints(): ConstraintSet =
    ConstraintSet {
        val box1 = createRefFor("box1")
        val box2 = createRefFor("box2")

        constrain(box1) {
            top.linkTo(parent.top)
            bottom.linkTo(box2.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }

        constrain(box2) {
            top.linkTo(box1.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

//            width = Dimension.fillToConstraints
//            height = Dimension.fillToConstraints
        }
    }