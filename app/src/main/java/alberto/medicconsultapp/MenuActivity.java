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

        if(extras != null){
            dni = (String)extras.get("DNI");
            data = (String)extras.get("DATA");
            if(data==null){
                text.setText(dni);
            }
            else{
                //text.setText(data);
                text.setText(data.substring(0,16));
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
        Intent intent;
        intent = new Intent(this,dataHistorial.class);
        intent.putExtra("DNI",dni);
        //intent.putExtra("DATA",data);
        startActivity(intent);
    }
}