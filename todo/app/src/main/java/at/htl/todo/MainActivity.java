package at.htl.todo;

import static at.htl.todo.TodoApplication.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import androidx.compose.ui.platform.ComposeView;

import javax.inject.Inject;

import at.htl.todo.model.VehicleService;
import at.htl.todo.ui.layout.HomeView;
import dagger.hilt.android.AndroidEntryPoint;
import at.htl.todo.util.Config;
@AndroidEntryPoint
public class MainActivity extends ComponentActivity {
    @Inject
    VehicleService vehicleService;

    @Inject
    MainViewRenderer mainViewRenderer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Config.load(this);
        super.onCreate(savedInstanceState);

        vehicleService.getAll();

        var view = new ComposeView(this);
        mainViewRenderer.setContent(view);
        setContentView(view);
    }
}