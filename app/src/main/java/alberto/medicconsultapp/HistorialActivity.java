package alberto.medicconsultapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class HistorialActivity extends AppCompatActivity {
    TextView username,data,nom,cognoms,edat,enfermetat,observacions;

    private String dni;
    private String fecha;
    Toast toast2;
    private boolean completado = false;

    HistorialClass historial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);


        Intent intent = getIntent();//Preparamos el objeto para obtener los datos compartidos
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior

        username = (TextView)findViewById(R.id.username);
        data = (TextView)findViewById(R.id.data);
        nom = (TextView)findViewById(R.id.nom);
        cognoms = (TextView)findViewById(R.id.cognoms);
        edat = (TextView)findViewById(R.id.edat);
        enfermetat = (TextView)findViewById(R.id.enfermetat);
        observacions = (TextView)findViewById(R.id.observacions);

        if(extras != null){
            dni = (String)extras.get("DNI");
            fecha = (String)extras.get("DATA");

            historial = new HistorialClass(dni,fecha);

            new DbASync().execute("");
        }

    }

    private class DbASync extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String ... params){
            completado = historial.fillHistorial(historial);
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            if(!completado){
                toast2 = Toast.makeText(getApplicationContext(),"Error con el historial",Toast.LENGTH_LONG);
                toast2.show();
            }
            else{
                username.setText(String.valueOf(historial.getNom()));
                data.setText(String.valueOf(historial.getDataHistorial().substring(0,10)));
                nom.setText(String.valueOf(historial.getNom()));
                cognoms.setText(String.valueOf(historial.getCognoms()));
                edat.setText(String.valueOf(historial.getEdat()));
                enfermetat.setText(String.valueOf(historial.getEnfermetat()));
                observacions.setText(String.valueOf(historial.getObservacions()));
            }
        }
        @Override
        protected void onPreExecute(){
        }
    }
}