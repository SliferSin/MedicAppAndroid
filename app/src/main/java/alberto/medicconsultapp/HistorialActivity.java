package alberto.medicconsultapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HistorialActivity extends AppCompatActivity {
    TextView username,data,nom,cognoms,edat,enfermetat,observacions;

    private String dni;
    private String fecha;

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

        if(extras != null){ //Editar data para que sea del estilo yyyy-MM-dd HH:mm y quitar el text.setText(dni);
            dni = (String)extras.get("DNI");
            fecha = (String)extras.get("DATA");

            historial = new HistorialClass(dni,fecha);

            historial.fillHistorial(historial);

            username.setText(historial.getNom());
            data.setText(historial.getDataHistorial());
            nom.setText(historial.getNom());
            cognoms.setText(historial.getCognoms());
            edat.setText(historial.getEdat());
            enfermetat.setText(historial.getEnfermetat());
            observacions.setText(historial.getObservacions());
        }
    }
}

