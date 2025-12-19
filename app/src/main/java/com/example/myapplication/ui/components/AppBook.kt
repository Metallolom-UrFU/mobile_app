package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.bookList.domain.entity.BookShortEntity
import com.example.myapplication.profile.presentation.state.ReservationUiStatus
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun BookItemCard(
    item: BookShortEntity,
    onClickOpenBook: () -> Unit = {},
    onClickReserve: () -> Unit = {},
    onClickCancel: () -> Unit = {},
    onClickPickup: () -> Unit = {},
    canOpenDetails: Boolean,
    reservationStatus: ReservationUiStatus = ReservationUiStatus.NONE,
    reservedUntil: String? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .then(
                if (canOpenDetails) Modifier.clickable { onClickOpenBook() } else Modifier
            ),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = item.imageRes,
                contentDescription = item.title,
                modifier = Modifier
                    .size(width = 96.dp, height = 128.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.ic_launcher_foreground)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = item.author,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (item.showTags && item.tags.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            item.tags.take(3).forEach {
                                TagChip(it)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                when (reservationStatus) {
                    ReservationUiStatus.PENDING -> {
                        reservedUntil?.let {
                            Text(
                                text = "Зарезервировано до ${it.toDisplayDate()}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(Modifier.height(6.dp))
                        }
                        Button(
                            onClick = onClickPickup,
                            modifier = Modifier.fillMaxWidth().height(30.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Показать QR код", style = MaterialTheme.typography.bodySmall)
                        }
                        Spacer(Modifier.height(6.dp))
                        Button(
                            onClick = onClickCancel,
                            modifier = Modifier.fillMaxWidth().height(30.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                "Отменить бронь",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onError
                            )
                        }
                    }

                    ReservationUiStatus.CONFIRMED -> {
                        reservedUntil?.let {
                            Text(
                                text = "Книга взята до ${it.toDisplayDate()}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    ReservationUiStatus.CANCELLED -> {
                        Text(
                            text = "Бронь отменена",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    ReservationUiStatus.COMPLETED -> {
                        Text(
                            text = "Книга возвращена",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    ReservationUiStatus.AVAILABLE -> {
                        Button(
                            onClick = onClickReserve,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text("Зарезервировать")
                        }
                    }

                    ReservationUiStatus.NONE -> {
                    }
                }
            }
        }
    }
}

fun String.toDisplayDate(): String {
    return try {
        val inputFormatter = DateTimeFormatter.ISO_DATE_TIME
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yy")
        LocalDate.parse(this, inputFormatter).format(outputFormatter)
    } catch (e: Exception) {
        this
    }
}
