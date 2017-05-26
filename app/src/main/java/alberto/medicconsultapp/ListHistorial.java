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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListHistorial extends AppCompatActivity {

    ListView listView;
    String data;
    String dni;
    String[] values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_historial);

        listView = (ListView) findViewById(R.id.listDays);

        Intent intent = getIntent();//Preparamos el objeto para obtener los datos compartidos
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior
        //String[] values = new String[]{"Prueba1", "Prueba2"};

        if (extras != null) {
            data = (String) extras.get("DATA");
            dni = (String) extras.get("DNI");
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);

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
    public String[] BuscarFecha(String dni){
        String driverDB= "org.postgresql.Driver";
        String urlDB = "jdbc:postgresql://192.168.1.13:5432/db_TFG";
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

            while(rs.next()){
                values[i] = rs.getString("data");
                i++;
            }
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
            String[] fechas;

            fechas = BuscarFecha(dni);

            return fechas;
        }

        @Override
        protected void onPostExecute(String[] result) {
        }

        @Override
        protected void onPreExecute() {
        }
    }
}
