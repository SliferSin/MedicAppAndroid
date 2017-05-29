package alberto.medicconsultapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ListHistorial extends AppCompatActivity {

    ListView listView;
    String data;
    String dni;
    String[] values = new String[4];
    /*String[] values = new String[] { "Android List View",
            "Adapter implementation",
            "Simple List View In Android",
            "Create List View Android",
            "Android Example",
            "List View Source Code",
            "List View Array Adapter",
            "Android Example List View"
    };*/

    boolean done = false;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_historial);

        listView = (ListView) findViewById(R.id.list);

        Intent intent = getIntent();//Preparamos el objeto para obtener los datos compartidos
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior

        if (extras != null) {
            data = (String) extras.get("DATA");
            dni = (String) extras.get("DNI");
        }

        new ConsultaASync().execute("");
    }

    public String[] BuscarFecha(String dni){
        String driverDB= "org.postgresql.Driver";
        String urlDB = "jdbc:postgresql://192.168.1.10:5432/db_TFG";
        String userDB = "postgres";
        String passDB = "password";

        int i = 0;

        String stsql = "SELECT data from tbl_historial WHERE id_pacient = ?";
        PreparedStatement st;
        ResultSet rs;

        try{
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB, userDB, passDB);
            st = conn.prepareStatement(stsql);
            st.setString(1, dni);
            rs = st.executeQuery();

            if(rs.next()){
                values[i] = rs.getString("data").substring(0,10);//Quitamos la hora de la fecha (YYYY/MM/dd)
                i++;
            }
           /* while(rs.next()){
                values[i] = rs.getString("data").substring(0,10);//Quitamos la hora de la fecha (YYYY/MM/dd)
                i++;
            }*/
            st.close();
            conn.close();
        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
        return values;
    }
    private class ConsultaASync extends AsyncTask<String,String,String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            String[] fechas = new String[3];

            BuscarFecha(dni);
            done = true;

            return fechas;
        }

        @Override
        protected void onPostExecute(String[] result) {
           adapter = new ArrayAdapter<String>(getApplicationContext(),
                           android.R.layout.simple_list_item_1, android.R.id.text1, values);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemPosition = position;
                    String itemValue = (String) listView.getItemAtPosition(position);

                    Toast.makeText(getApplicationContext(), "Position:" + itemPosition + " ListItem:" + itemValue, Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        protected void onPreExecute() {
        }
    }
}
