package alberto.medicconsultapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    protected Boolean validate = false;
    protected String dni = "";
    protected String pass = "";

    EditText DNI;
    EditText Password;
    Button sendButton;
    Toast toast2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DNI = (EditText) findViewById(R.id.editDNI);
        Password = (EditText) findViewById(R.id.editPass);
        sendButton = (Button) findViewById(R.id.button);
    }

    public void Loguear(View view) {
        dni = DNI.getText().toString();
        pass = Password.getText().toString();

        Intent intentMenu;
        intentMenu = new Intent(this, MenuActivity.class);

        new HttpASync().execute("");

        if (validate) {
            intentMenu.putExtra("DNI", dni);
            startActivity(intentMenu);
        }
    }

    private class HttpASync extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            UserClass usuario = new UserClass(dni, pass);
            validate = usuario.consultarLogin();

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (validate == false) {
                toast2 = Toast.makeText(getApplicationContext(), "Login erroneo", Toast.LENGTH_LONG);
                toast2.show();
            }
        }

        @Override
        protected void onPreExecute() {
        }
    }
}