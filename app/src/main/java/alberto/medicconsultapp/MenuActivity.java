package alberto.medicconsultapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.List;

public class MenuActivity extends ListActivity{//AppCompatActivity {

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

        if(extras != null){ //Comprobar el setText del else
            dni = (String)extras.get("DNI");
            data = (String)extras.get("DATA");
            if(data.isEmpty()){
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
        intent = new Intent(this,HistorialActivity.class);
        intent.putExtra("DNI",dni);
        //intent.putExtra("DATA",data);
        //startActivity(intent);
    }
}