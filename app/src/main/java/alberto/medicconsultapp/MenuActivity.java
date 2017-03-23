package alberto.medicconsultapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import android.app.DatePickerDialog;
import android.app.Dialog;

public class MenuActivity extends AppCompatActivity {

    TextView text;
    protected String dni = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();//Preparamos el objeto para obtener los datos compartidos
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior

        text = (TextView)findViewById(R.id.textView);

        if(extras != null){
            dni = (String)extras.get("DNI");
            text.setText(dni);
        }

    }

    public void PedirCita(View view){
        Intent intent;
        intent = new Intent(this,CitaActivity.class);
        intent.putExtra("dni",dni);
        startActivity(intent);
    }


}
