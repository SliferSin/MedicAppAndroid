package alberto.medicconsultapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


public class ListHistorial extends AppCompatActivity {

    ListView listView;
    String data;
    String dni;
    ArrayList<String>values = new ArrayList<String>();
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

    //public String[] BuscarFecha(String dni){
    public String[] BuscarFecha(String dni, String data){
        String driverDB = "org.postgresql.Driver";
        String urlDB = "jdbc:postgresql://192.168.1.10:5432/db_TFG";
        String userDB = "postgres";
        String passDB = "password";

        String stsql = "SELECT data::date from tbl_historial WHERE id_pacient = ? and date_part('year',data) = ? " +
                       "and date_part('month',data) = ?";
        PreparedStatement st;
        ResultSet rs;

        try{
            Class.forName(driverDB);
            Connection conn = DriverManager.getConnection(urlDB, userDB, passDB);
            st = conn.prepareStatement(stsql);
            st.setString(1, dni);
            st.setTimestamp(2, Timestamp.valueOf(data));
            st.setTimestamp(3, Timestamp.valueOf(data));
            rs = st.executeQuery();

            if(rs.next()){
                values.add(rs.getString("data"));
            }
            else{
                while(rs.next()){
                    values.add(rs.getString("data"));
                }
            }
            st.close();
            conn.close();
        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
        return null;
    }

    private class ConsultaASync extends AsyncTask<String,String,String[]> {
        @Override
        protected String[] doInBackground(String... params) {
            String[] fechas = new String[3];

            //BuscarFecha(dni);
            BuscarFecha(dni,data);
            done = true;

            return fechas;
        }

        @Override
        protected void onPostExecute(String[] result) {
           adapter = new ArrayAdapter<String>(getApplicationContext(),
                           android.R.layout.simple_list_item_1,android.R.id.text1, values);

            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemPosition = position;
                    String itemValue = (String) listView.getItemAtPosition(position);

                    Intent intentMenu;
                    intentMenu = new Intent(getApplicationContext(),HistorialActivity.class);

                    intentMenu.putExtra("DNI",dni);
                    intentMenu.putExtra("DATA",itemValue);
                    startActivity(intentMenu);

                    //Toast.makeText(getApplicationContext(), "Position:" + itemPosition + " ListItem:" + itemValue, Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        protected void onPreExecute() {
        }
    }
}