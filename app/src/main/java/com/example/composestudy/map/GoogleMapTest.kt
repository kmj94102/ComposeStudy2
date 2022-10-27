package com.example.composestudy.map

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.google.accompanist.pager.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.*
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue


@OptIn(ExperimentalPagerApi::class)
@Composable
fun GoogleMapTest() {
    val list = getMapItemList()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(list[0].latLng, 14f)
    }
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false
            )
        )
    }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            cameraPositionState = cameraPositionState,
            uiSettings = uiSettings
        ) {
            list.forEachIndexed { index, mapItem ->
                MarkerInfoWindow(
                    state = MarkerState(position = mapItem.latLng),
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                        true
                    }
                )
            }
        }

        MapViewPager(
            list = list,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 10.dp, max = 200.dp)
                .align(Alignment.BottomCenter)
        )
        
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                cameraPositionState.move(CameraUpdateFactory.newLatLng(list[page].latLng))
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MapViewPager(
    list: List<MapItem>,
    state: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        count = list.size,
        state = state,
        contentPadding = PaddingValues(start = 25.dp, end = 50.dp),
        modifier = modifier
    ) { index ->
        MapCard(
            item = list[index],
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(index).absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                }
        )
    }
}

@Composable
fun MapCard(
    item: MapItem,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(end = 10.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                model = item.image,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = item.title, fontSize = 16.sp)
        }
    }
}