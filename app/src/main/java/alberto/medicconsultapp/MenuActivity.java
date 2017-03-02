package alberto.medicconsultapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    protected String nom = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();//Preparamo el objeto para obtener los datos compartidos
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior

        if(extras != null){
            nom = (String)extras.get("name");
        }

    }

    public void PedirCita(View view){

    }
}
