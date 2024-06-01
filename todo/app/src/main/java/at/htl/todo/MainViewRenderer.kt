package at.htl.todo

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import at.htl.todo.model.ModelStore
import at.htl.todo.model.UiState
import at.htl.todo.ui.layout.TabView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainViewRenderer @Inject constructor() {
    @Inject
    lateinit var tabScreenView: TabView

    @Inject
    lateinit var store: ModelStore

    fun setContent(view: ComposeView) {
        view.setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                var orientation by remember { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }
                val configuration = LocalConfiguration.current
                LaunchedEffect(configuration) {
                    orientation = configuration.orientation
                    val currentOrientation = orientationFromConfiguration(configuration)
                    store.apply { it.uiState.orientation = currentOrientation }
                }
                tabScreenView.TabViewLayout()
            }
        }
    }

    private fun orientationFromConfiguration(configuration: Configuration): UiState.Orientation {
        return when (configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> UiState.Orientation.portrait
            Configuration.ORIENTATION_LANDSCAPE -> UiState.Orientation.landscape
            else -> UiState.Orientation.undefined
        }
    }
}
