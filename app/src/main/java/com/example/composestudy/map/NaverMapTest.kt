package com.example.composestudy.map

import android.Manifest
import android.view.Gravity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import com.example.composestudy.R
import com.google.accompanist.pager.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.*
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun NaverMapTest(modifier: Modifier = Modifier) {

    val permission = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    LaunchedEffect(key1 = Unit) {
        permission.launchPermissionRequest()
    }

    val latLng = LatLng(37.740118399999744, 127.04342009999966)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(latLng, 15.0)
    }
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(
                maxZoom = 20.0,
                minZoom = 5.0,
                isBicycleLayerGroupEnabled = true,
                isIndoorEnabled = true
            )
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isLocationButtonEnabled = false,
                isZoomControlEnabled = false,
                logoGravity = Gravity.TOP or Gravity.END,
            )
        )
    }
    val scope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        NaverMap(
            properties = mapProperties,
            uiSettings = mapUiSettings,
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(LatLng(37.740111, 127.0475821)),
                captionText = "스타벅스 의정부공원점",
                subCaptionText = "카페",
                captionOffset = 10.dp,
                captionColor = Color.Magenta,
                captionRequestedWidth = 10.dp
            )
            Marker(
                state = MarkerState(LatLng(37.733071, 127.0388841)),
                captionText = "스타벅스 의정부예술의전당DT점",
                subCaptionText = "카페",
                captionOffset = 10.dp,
                captionColor = Color.White,
                captionHaloColor = Color.Black,
                captionRequestedWidth = 10.dp,
                icon = OverlayImage.fromResource(R.drawable.ic_round_star_24),
                height = 40.dp,
                width = 40.dp
            )
            ArrowheadPathOverlay(
                coords = listOf(
                    LatLng(37.740111, 127.0475821),
                    LatLng(37.7378009, 127.0461568),
                    LatLng(37.733071, 127.0388841),
                ),
                width = 5.dp
            )
            CircleOverlay(
                center = LatLng(37.7378009, 127.0461568),
                color = Color(0x4DFFEA00),
                outlineColor = Color.White,
                outlineWidth = 10.dp
            )
            val builder = LatLngBounds.Builder()
            builder.include(LatLng(37.7378009, 127.0461568))
            builder.include(LatLng(37.733071, 127.0388841))

            GroundOverlay(
                bounds = builder.build(),
                image = OverlayImage.fromResource(R.drawable.ic_launcher_background),
                alpha = .5f
            )

            MultipartPathOverlay(
                coordParts = listOf(
                    listOf(
                        LatLng(37.740111, 127.0475821),
                        LatLng(37.7378009, 127.0461568),
                        LatLng(37.733071, 127.0388841),
                    ),
                    listOf(
                        LatLng(37.7378009, 127.0461568),
                        LatLng(37.733071, 127.0388841),
                    )
                ),
                colorParts = listOf(
                    ColorPart(
                        color = Color(0xFFFF1744),
                        passedColor = Color(0xFFFFEA00)
                    ),
                ),
                progress = 0.8,
            )

            PathOverlay(
                coords = listOf(
                    LatLng(37.740111, 127.0475821),
                    LatLng(37.7378009, 127.0461568),
                    LatLng(37.733071, 127.0388841),
                ),
                progress = 0.4,
                color = Color(0xFFFF1744),
                passedColor = Color(0xFFFFEA00),
                outlineWidth = 10.dp
            )

            PolygonOverlay(
                coords = listOf(
                    LatLng(37.740111, 127.0475821),
                    LatLng(37.7378009, 127.0461568),
                    LatLng(37.733071, 127.0388841),
                ),
            )

        }

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Button(onClick = {
                mapProperties = mapProperties.copy(
                    isBuildingLayerGroupEnabled = !mapProperties.isBuildingLayerGroupEnabled
                )
                scope.launch {
                    cameraPositionState.animate(
                        update = CameraUpdate.scrollTo(LatLng(37.740111, 127.0475821)),
                        animation = CameraAnimation.Fly,
                    )
                }
            }) {
                Text(text = "1")
            }
            Button(onClick = {
                mapUiSettings = mapUiSettings.copy(
                    isLocationButtonEnabled = !mapUiSettings.isLocationButtonEnabled
                )
            }) {
                Text(text = "2")
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPagerApi::class)
@Composable
fun NaverMapTest2() {
    val list = getNaverMapItemList()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(list[1].latLng, 14.0)
    }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(
            cameraPositionState = cameraPositionState
        ) {
            list.forEachIndexed { index, item ->
                Marker(
                    state = MarkerState(position = item.latLng),
                    onClick = {
                        scope.launch {
                            cameraPositionState.animate(update = CameraUpdate.scrollTo(list[index].latLng))
                            pagerState.animateScrollToPage(index)
                        }
                        true
                    }
                )
            }
        }

        NaverMapViewPager(
            list = list,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 10.dp, max = 200.dp)
                .align(Alignment.BottomCenter)
        )

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                cameraPositionState.animate(update = CameraUpdate.scrollTo(list[page].latLng))
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NaverMapViewPager(
    list: List<NaverMapItem>,
    state: PagerState,
    modifier: Modifier = Modifier
) {
    HorizontalPager(
        count = list.size,
        state = state,
        contentPadding = PaddingValues(start = 25.dp, end = 50.dp),
        modifier = modifier
    ) { index ->
        NaverMapCard(
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
fun NaverMapCard(
    item: NaverMapItem,
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