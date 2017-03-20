package alberto.medicconsultapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ProgressBar;

import android.content.Intent; //Pasar datos a otras activities
import android.os.AsyncTask; //Tareas async


public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    protected Boolean validate = false;
    protected String dni = "";
    protected  String pass = "";

    int mostrar = 0;
    ProgressBar Pbar;
    EditText DNI;
    EditText Password;
    Button sendButton;
    Toast toast2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DNI = (EditText)findViewById(R.id.editDNI);
        Password = (EditText)findViewById(R.id.editPass);
        sendButton = (Button)findViewById(R.id.button);
        Pbar = (ProgressBar)findViewById(R.id.progressBar);
    }
    public void Loguear(View view) {
        dni = DNI.getText().toString();
        pass = Password.getText().toString();

        Intent intentMenu;
        intentMenu = new Intent(this,MenuActivity.class);

        Pbar.setMax(100);
        Pbar.setProgress(0);

        new  HttpASync().execute("");

        if (mostrar == 1)
            Pbar.setVisibility(View.VISIBLE);
        if(validate) {
            intentMenu.putExtra("DNI", dni);
            startActivity(intentMenu);
        }
    }
    private class HttpASync extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String ... params){
            UserClass usuario = new UserClass(dni,pass);
            mostrar = 1;
            validate = usuario.consultarLogin();

            return null;
        }
        @Override
        protected void onPostExecute(String result){
            Pbar.setProgress(100);
            if(validate == false){
                toast2 = Toast.makeText(getApplicationContext(),"Login erroneo",Toast.LENGTH_LONG);
                toast2.show();
            }
        }
        @Override
        protected void onPreExecute(){
        }
    }
}