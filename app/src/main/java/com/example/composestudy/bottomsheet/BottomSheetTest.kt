package com.example.composestudy.bottomsheet

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.composestudy.R
import com.example.composestudy.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun BottomSheetTest() {

//    BottomSheetScaffoldTest()

    ModalBottomSheetLayoutTest()

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetScaffoldTest() {
    val state = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = state,
        sheetContent = {
            BottomSheetContent(state)
        },
        sheetPeekHeight = 40.dp,
        modifier = Modifier.fillMaxSize()
    ) {
        MainContent {
            scope.launch {
                if (state.bottomSheetState.isCollapsed) {
                    state.bottomSheetState.expand()
                } else {
                    state.bottomSheetState.collapse()
                }
            }
        }
    }
}

@Composable
fun MainContent(
    onClickListener: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 24.dp)
    ) {
        Text(
            text = "Bottom Sheet",
            style = Typography.h4,
            fontWeight = FontWeight.Bold
        )

        Button(onClick = onClickListener) {
            Text(text = "버튼", style = Typography.body1)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetContent(state: BottomSheetScaffoldState) {
    Card(
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            val rotate by animateFloatAsState(
                targetValue = if (state.bottomSheetState.isCollapsed) 0f else 180f
            )

            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_up),
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier
                    .rotate(rotate)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Bottom Sheet Scaffold", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "Is Open")

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheetLayoutTest() {
    val modalState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = modalState,
        sheetContent = {
            ModalBottomSheetContents()
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ModalBottomSheetMain {
                scope.launch {
                    modalState.show()
                }
            }
        }
    }
}

@Composable
fun ModalBottomSheetMain(
    onClickListener: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 20.dp, horizontal = 24.dp)
    ) {
        Text(
            text = "Modal Bottom Sheet",
            style = Typography.h4,
            fontWeight = FontWeight.Bold
        )

        Button(onClick = onClickListener) {
            Text(text = "버튼", style = Typography.body1)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ModalBottomSheetContents() {
    LazyColumn {
        items(50) {
            ListItem(
                text = { Text("Item $it") },
                icon = {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Localized description"
                    )
                }
            )
        }
    }
}