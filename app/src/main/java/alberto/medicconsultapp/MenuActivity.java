package alberto.medicconsultapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    TextView text;
    private String dni = "";
    private String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();//Preparamos el objeto para obtener los datos compartidos
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior

        text = (TextView)findViewById(R.id.textView);

        if(extras != null){ //Editar data para que sea del estilo yyyy-MM-dd HH:mm y quitar el text.setText(dni);
            dni = (String)extras.get("DNI");
            data = (String)extras.get("DATA");
            if(data == ""){
                text.setText(dni);
            }
            else{
                text.setText(data);
            }
        }
    }

    public void PedirCita(View view){
        Intent intent;
        intent = new Intent(this,CitaActivity.class);
        intent.putExtra("dni",dni);
        startActivity(intent);
    }
    public void Historial(View view){
        /*Intent intent;
        intent = new Intent(this,HistorialActivity.class);
        intent.putExtra("dni",dni);
        intent.putExtra("data",data);
        startActivity(intent);*/
    }
}