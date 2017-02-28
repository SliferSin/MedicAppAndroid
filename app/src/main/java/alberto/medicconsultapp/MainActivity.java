package alberto.medicconsultapp;

import android.os.AsyncTask; //Tareas async
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Collections;

//Http Libraries
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    EditText DNI;
    EditText Password;
    Button sendButton;
    TextView msgMostra;
    Toast toast2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DNI = (EditText)findViewById(R.id.editDNI);
        Password = (EditText)findViewById(R.id.editPass);
        sendButton = (Button)findViewById(R.id.button);
        msgMostra = (TextView) findViewById(R.id.TextMuestra);
    }
    public void Loguear(View view) {
        String formDatos = DNI.getText().toString();
        //AESCrypt passEnc = AESCrypt.encrypt("sadsd");
        new  HttpASync().execute("");
    }

    /**
     * Converts the contents of an InputStream to a String.
     */
    private String readStream(InputStream stream, int maxLength) throws IOException {
        String result = null;
        // Read InputStream using the UTF-8 charset.
        InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
        // Create temporary buffer to hold Stream data with specified max length.
        char[] buffer = new char[maxLength];
        // Populate temporary buffer with Stream data.
        int numChars = 0;
        int readSize = 0;
        while (numChars < maxLength && readSize != -1) {
            numChars += readSize;
            int pct = (100 * numChars) / maxLength;
            readSize = reader.read(buffer, numChars, buffer.length - numChars);
        }
        if (numChars != -1) {
            // The stream was not empty.
            // Create String that is actual length of response body if actual length was less than
            // max length.
            numChars = Math.min(numChars, maxLength);
            result = new String(buffer, 0, numChars);
        }
        return result;
    }
    private class HttpASync extends AsyncTask<String,Void,String> {
        String msg = null;

        @Override
        protected String doInBackground(String ... params){
            //try {

                JSONParser js = new JSONParser();
                String jsonStr = js.getJSONFromUrl("http://192.168.1.11:155");

                if (jsonStr != null){
                    try{
                        //JSONObject jsonObj = new JSONObject(jsonStr);
                        JSONObject c = new JSONObject(jsonStr);
                        //Getting JSON Array node
                        //JSONArray users = jsonObj.getJSONArray("users");

                        //looping through All Users
                        //for(int i = 0;i <users.length();i++){
                            //JSONObject c = users.getJSONObject(i);

                            String nom = c.getString("Nom");
                            //String cognom = c.getString("cognom");
                            String edat = c.getString("Edat");
                        //}
                    }catch (JSONException e){
                        Log.e(TAG,"JSON parsing error: " + e.getMessage());
                    }
                }
                else{
                    Log.e(TAG,"Couldn't get JSON from server");
                }
                return null;
                //URL url = new URL("http://192.168.1.11:155");
                //HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

                /*InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while((inputString = bufferedReader.readLine()) != null){
                    builder.append(inputString);
                }
                JSONObject toplevel = new JSONObject(builder.toString());
                JSONObject main = toplevel.getJSONObject("main");

                urlConnection.disconnect();*/



                /*urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.connect();
                msg = readStream(urlConnection.getInputStream(),500);
                urlConnection.disconnect();*/

            /*}
            catch(MalformedURLException ex){

                System.out.println(ex);
            }
            catch(IOException | JSONException ex){
                System.out.println(ex);*/
            //}
            //return msg;
        }
        @Override
        protected void onPostExecute(String result){
            TextView msgMostra = (TextView)findViewById(R.id.TextMuestra);
            msgMostra.setText(result);
        }
        @Override
        protected void onPreExecute(){
        }
    }
}

