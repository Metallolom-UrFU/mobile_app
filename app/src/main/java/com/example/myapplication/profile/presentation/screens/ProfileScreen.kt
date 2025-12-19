package com.example.myapplication.profile.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.myapplication.profile.presentation.state.toReservationUiStatus
import com.example.myapplication.profile.presentation.viewModel.ProfileViewModel
import com.example.myapplication.ui.components.BookItemCard
import com.example.myapplication.ui.components.FullscreenLoading
import com.example.myapplication.ui.components.FullscreenMessage
import com.github.terrakok.modo.Screen
import com.github.terrakok.modo.ScreenKey
import com.github.terrakok.modo.generateScreenKey
import com.github.terrakok.modo.stack.LocalStackNavigation
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Parcelize
class ProfileScreen(
    override val screenKey: ScreenKey = generateScreenKey()
) : Screen {

    @Composable
    override fun Content(modifier: Modifier) {
        val navigation = LocalStackNavigation.current
        val viewModel = koinViewModel<ProfileViewModel> { parametersOf(navigation) }

        val state by viewModel.viewState.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.refresh()
        }

        ProfileScreenContent(
            state = state,
            onOpenQr = viewModel::openQr,
            onCloseQr = viewModel::closeQr,
            onCancelReservation = viewModel::cancelReservation,
            onRefresh = viewModel::refresh
        )
    }
}

@Composable
private fun ProfileScreenContent(
    state: com.example.myapplication.profile.presentation.viewModel.ProfileState,
    onOpenQr: (com.example.myapplication.profile.domain.entity.ReservationEntity) -> Unit,
    onCloseQr: () -> Unit,
    onCancelReservation: (String) -> Unit,
    onRefresh: () -> Unit
) {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {

            if (state.isLoading) {
                FullscreenLoading()
                return@Box
            }

            state.error?.let { error ->
                FullscreenMessage(
                    msg = error,
                )
                return@Box
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                item {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Fotaxis@mail.ru",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF0F0F0), RoundedCornerShape(16.dp))
                            .padding(vertical = 16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Counter(state.reservedCount, "Зарезервировано")
                            Counter(state.activeCount, "Взято")
                            Counter(state.returnedCount, "Возвращено")
                        }
                    }
                    Spacer(Modifier.height(24.dp))
                    Text("Мои заказы", style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(8.dp))
                }


                items(state.reservations) { reservation ->
                    BookItemCard(
                        item = reservation.book,
                        canOpenDetails = false,
                        reservationStatus = reservation.status.toReservationUiStatus(),
                        reservedUntil = reservation.expDate,
                        onClickPickup = { onOpenQr(reservation) },
                        onClickCancel = { onCancelReservation(reservation.id) }
                    )
                }


                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { /* Редактировать профиль */ },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp)
                    ) { Text("Редактировать профиль") }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick = { /* Выйти */ },
                            modifier = Modifier.align(Alignment.Center)
                        ) { Text("Выйти", color = Color.Red) }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            state.selectedReservation?.let { reservation ->
                if (reservation.status == "pending") {
                    AlertDialog(
                        onDismissRequest = onCloseQr,
                        title = { Text(reservation.book.title) },
                        text = {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = reservation.qrCodeUrl,
                                    contentDescription = "QR Code",
                                    modifier = Modifier.size(220.dp)
                                )
                                Spacer(Modifier.height(16.dp))
                                Text("Код получения")
                                Text(
                                    reservation.pickupCode,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        },
                        confirmButton = {},
                        dismissButton = {
                            TextButton(onClick = onCloseQr) {
                                Text("Закрыть")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun Counter(value: Int, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value.toString(), style = MaterialTheme.typography.titleMedium)
        Text(label, style = MaterialTheme.typography.bodySmall)
    }
}