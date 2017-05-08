package alberto.medicconsultapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;

public class dataHistorial extends AppCompatActivity {

    private Button button;
    private EditText dataHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_historial);

        button = (Button)findViewById(R.id.Consultar);
        dataHistorial = (EditText)findViewById(R.id.DataHistorial);
    }
}
