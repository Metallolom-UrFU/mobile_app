package com.example.myapplication.ui.components

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.shelfList.domain.entity.ShelfShortEntity
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectCollection

@SuppressLint("MissingPermission")
@Composable
fun YandexMapComposable(
    shelves: List<ShelfShortEntity>,
    onShelfClick: (ShelfShortEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            MapKitFactory.initialize(context)
            MapView(context).apply {
                map.move(CameraPosition(Point(56.838651, 60.610180), 15f, 0f, 0f))
            }
        },
        update = { mapView ->
            mapView.map.mapObjects.clear()
            val mapObjects: MapObjectCollection = mapView.map.mapObjects.addCollection()

            shelves.forEach { shelf ->
                val placemark = mapObjects.addPlacemark(
                    Point(56.838651, 60.610180)
                )

                placemark.setIconStyle(
                    IconStyle().apply {
                        scale = 3f
                    }
                )
                placemark.userData = shelf

                placemark.addTapListener { mapObject, _ ->
                    val clickedShelf = mapObject.userData as? ShelfShortEntity
                    clickedShelf?.let { onShelfClick(it) }
                    true
                }
            }
        }
    )
}
