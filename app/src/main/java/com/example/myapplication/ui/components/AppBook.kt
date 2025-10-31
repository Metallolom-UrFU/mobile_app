package com.example.myapplication.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun BookItemCard(
    item: BookItemUi,
    modifier: Modifier = Modifier,
    onClickOrder: () -> Unit = {},
    canOrder: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(155.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(10.dp, 17.dp),
        verticalAlignment = Alignment.Top
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(width = 121.dp, height = 121.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = item.author,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.Top,
            ) {
                item.tags.forEach { tag ->
                    Surface(
                        modifier = Modifier
                            .height(20.dp),
                        color = Color(0xFFD9D9D9),
                        shape = RoundedCornerShape(40)
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 10.dp),
                            contentAlignment = Alignment.Center

                        ) {
                            Text(
                                text = tag,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xAA000000)
                            )

                        }
                    }
                }
            }
            if (canOrder) {
                Spacer(Modifier.height(11.dp))
                Surface(
                    modifier = Modifier
                        .height(20.dp),
                    color = Color(0xFFD9D9D9),
                    shape = RoundedCornerShape(40)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable {onClickOrder()},
                        contentAlignment = Alignment.Center

                    ) {
                        Text(
                            text = "Заказать здесь",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black
                        )

                    }
                }
            }
        }
    }
}

data class BookItemUi(
    val title: String,
    val author: String,
    val tags: List<String>,
    val imageRes: Int
)

@Preview(showBackground = true)
@Composable
fun BookItemCardPreview() {
    val sample = BookItemUi(
        title = "Название книги",
        author = "Автор",
        tags = listOf("Тег", "Тег"),
        imageRes = R.drawable.ic_launcher_foreground,
    )

    BookItemCard(item = sample, canOrder = true)
}
