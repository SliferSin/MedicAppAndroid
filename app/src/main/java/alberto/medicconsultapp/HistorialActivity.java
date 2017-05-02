package alberto.medicconsultapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HistorialActivity extends AppCompatActivity {
    TextView username;
    private String dni = "";
    private String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        Intent intent = getIntent();//Preparamos el objeto para obtener los datos compartidos
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior

        username = (TextView)findViewById(R.id.username);

        if(extras != null){ //Editar data para que sea del estilo yyyy-MM-dd HH:mm y quitar el text.setText(dni);
            dni = (String)extras.get("DNI");
            data = (String)extras.get("DATA");
        }
    }

}

