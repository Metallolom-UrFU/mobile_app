import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.container.presentation.AppTabs

@Composable
fun MenuBar(
    selectedTab: Int,
    onTabClick: (Int) -> Unit,
) {
    BottomAppBar {
        for ((pos, tab) in AppTabs.entries.withIndex()) {
            IconButton(
                onClick = { onTabClick(pos) },
                modifier = Modifier.weight(1f),
            ) {
                Image(
                    painter = painterResource(tab.iconRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                )
            }
        }
    }
}