package com.example.myapplication.profile.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.profile.presentation.viewModel.ProfileViewModel
import com.example.myapplication.ui.components.BookItemCard
import com.example.myapplication.ui.components.BookItemUi
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

        val orders = listOf(
            BookItemUi(
                title = "Книга 1",
                author = "Автор 1",
                tags = listOf("Возврат до: 12.11.2025"),
                imageRes = com.example.myapplication.R.drawable.ic_launcher_foreground
            ),
            BookItemUi(
                title = "Книга 2",
                author = "Автор 2",
                tags = listOf("Возвращено"),
                imageRes = com.example.myapplication.R.drawable.ic_launcher_foreground
            ),
        )

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Почта")
                }

                Spacer(modifier = Modifier.height(16.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF0F0F0), RoundedCornerShape(16.dp))
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("2", style = MaterialTheme.typography.titleMedium)
                            Text("Книг взято", style = MaterialTheme.typography.bodySmall)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("1", style = MaterialTheme.typography.titleMedium)
                            Text("Активных", style = MaterialTheme.typography.bodySmall)
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("1", style = MaterialTheme.typography.titleMedium)
                            Text("Возвращено", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Мои заказы", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(orders) { book ->
                        BookItemCard(
                            item = book,
                            canOrder = false,
                        )
                    }
                }

                Button(
                    onClick = { /* Редактировать профиль */ },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Редактировать профиль")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    onClick = { /* Выйти */ },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Выйти", color = Color.Red)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}