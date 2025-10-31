package com.example.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.graphics.Color


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PostItemCard(
    item: PostItemUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(109.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier
                .padding(10.dp, 11.dp),

            ) {
            Text(item.title)
            Spacer(modifier = Modifier.height(15.dp))
            Text(item.address)
        }

        Surface(
            modifier = Modifier
                .padding(top = 15.dp, end = 6.dp)
                .height(27.dp),
            color = Color(0xFFD9D9D9),
            shape = RoundedCornerShape(50)
        ) {
            Box(
                modifier = Modifier.padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${item.booksAmount} книг",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xAA000000)
                )
            }
        }
    }
}

data class PostItemUi(
    val title: String,
    val address: String,
    val booksAmount: Int
)

@Preview(showBackground = true)
@Composable
fun PostItemCardPreview() {
    val sample = PostItemUi(
        title = "Постамат №",
        address = "Адрес",
        booksAmount = 12
    )

    PostItemCard(item = sample)
}
