package ir.atitec.signalgoApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    boolean b = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Main.start(this);

    }

    public void onButtonClick(View v) {
        Main.doHello();
    }

    public void onButtonClick2(View v) {
        Main.stop();
        b = false;
    }

    public void onButtonClick3(View v) {
        Main.start(this);
    }
}
