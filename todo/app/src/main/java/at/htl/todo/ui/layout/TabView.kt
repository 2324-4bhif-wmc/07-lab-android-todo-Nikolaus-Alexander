package at.htl.todo.ui.layout
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import at.htl.todo.model.Model
import at.htl.todo.model.ModelStore
import at.htl.todo.model.UiState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TabView @Inject constructor() {
    @Inject
    lateinit var store: ModelStore

    @Inject
    lateinit var homeScreenView: HomeView

    @Inject
    lateinit var createView: CreateView

    @Inject
    lateinit var cardView: CardView

    @Composable
    fun TabViewLayout() {
        val model = store.pipe
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeAsState(initial = Model())
            .value

        val numberOfVehicles = model.vehicles.size
        val tab = model.uiState.selectedTab
        val tabIndex = tab.index()
        val selectedTab = remember { mutableIntStateOf(tabIndex) }
        selectedTab.intValue = tabIndex
        val tabs = listOf("Home", "Create/Update","Cards")
        Column(modifier = Modifier.fillMaxWidth()) {
            TabRow(selectedTabIndex = selectedTab.intValue) {
                tabs.forEachIndexed { index, title ->
                    Tab(text = { Text(title) },
                        selected = selectedTab.intValue == index,
                        onClick = {
                            selectedTab.intValue = index
                            selectTabByIndex(index)
                        },
                        icon = {
                            when (index) {
                                0 -> BadgedBox(badge = { Badge { Text("$numberOfVehicles") } }) {
                                    Icon(Icons.Filled.Favorite, contentDescription = "ToDos")
                                }

                                1 -> Icon(
                                    imageVector = Icons.Default.Create,
                                    contentDescription = null
                                )

                                2 -> Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = null
                                )
                            }
                        }
                    )
                }
            }
            ContentArea(selectedTab.intValue)
        }
    }

    fun selectTabByIndex(index: Int) {
        val tabs: Array<UiState.Tab> = UiState.Tab.entries.toTypedArray()
        val tab: UiState.Tab = tabs.filter { t -> t.index() === index }.first()
        store.apply { model -> model.uiState.selectedTab = tab }
    }

    @Composable
    fun ContentArea(selectedTab: Int) {
        if (LocalInspectionMode.current) {
            PreviewContentArea()
        } else {
            when (selectedTab) {
                0 -> homeScreenView.HomeScreen(model = store.pipe.value!!)
                1 -> createView.CreateScreen(model = store.pipe.value!!,Modifier)
                2 -> cardView.CardScreen(model = store.pipe.value!!)
            }
        }
    }

    @Composable
    fun PreviewContentArea() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
            ) {
                Text(text = "Content area of the selected tab", softWrap = true)
            }
        }
    }
}