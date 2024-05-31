package at.htl.todo;

import static at.htl.todo.TodoApplication.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.ComponentActivity;
import javax.inject.Inject;
import at.htl.todo.ui.layout.MainView;
import dagger.hilt.android.AndroidEntryPoint;
import at.htl.todo.util.Config;
@AndroidEntryPoint
public class MainActivity extends ComponentActivity {

    @Inject
    MainView mainView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Config.load(this);
        super.onCreate(savedInstanceState);
        var base_url = Config.getProperty("json.placeholder.baseurl");
        Log.i(TAG, "onCreate: " + base_url);
        mainView.buildContent(this);
    }
}

