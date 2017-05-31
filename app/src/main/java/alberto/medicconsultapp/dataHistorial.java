package alberto.medicconsultapp;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.os.AsyncTask; //Tareas async
import android.widget.Toast;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class dataHistorial extends AppCompatActivity {


    HistorialClass historial;
    String dni;
    String data;
    private Button button;
    private EditText dataHistorial;
    private TextView data1,data2,data3;
    Toast toast2;
    private int year,month,day;
    private Calendar calendar;
    private boolean validate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_historial);

        button = (Button)findViewById(R.id.Consultar);
        dataHistorial = (EditText)findViewById(R.id.DataHistorial);
        data1 = (TextView)findViewById(R.id.data1);
        data2 = (TextView)findViewById(R.id.data2);
        data3 = (TextView)findViewById(R.id.data3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Intent intent = getIntent();//Preparamos el objeto para obtener los datos compartidos
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior
        if (extras != null) {
            dni = (String) extras.get("DNI");
        }
        //new  ConsultaASync().execute(""); //Ejecutamos la consulta en 2o plano

        dataHistorial.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    setDate(v);
                }
            }
        });
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view){
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
    // TODO Auto-generated method stub
        return new DatePickerDialog(this,myDateListener, year, month, day);
    }
        private DatePickerDialog.OnDateSetListener myDateListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0,
                                          int arg1, int arg2, int arg3) {
                        // TODO Auto-generated method stub
                        // arg1 = year
                        // arg2 = month
                        // arg3 = day
                        showDate(arg1, arg2+1, arg3);
                    }
                };
    private void showDate(int year, int month, int day) {
        String dia = Integer.toString(day);
        String mes = Integer.toString(month);
        String año = Integer.toString(year);
        String fecha;

        if(month<10){
            mes = "0" + mes;
        }
        if(day<10){
            dia = "0" + dia;
        }
        fecha = año + "-" + mes + "-" + dia;
        data = fecha;
        //Mostrar la fecha seleccionada en el campo fecha
        dataHistorial.setText(fecha);
    }

    public void Consultar (View view){
        //Configuración del siguiente Activity que se abrira
        Intent intentMenu;
        intentMenu = new Intent(this, ListHistorial.class);

        historial = new HistorialClass(dni,data);

        //new  DbASync().execute(""); //Ejecutamos la consulta en 2o plano

        if (validate) {
            intentMenu.putExtra("DNI", dni);
            intentMenu.putExtra("DATA",data);
            startActivity(intentMenu);
        }
    }

    private class DbASync extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String ... params){
            if(historial.searchHistorial(dni,data)!= false){
                validate = true;
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            if(validate == false){
                toast2 = Toast.makeText(getApplicationContext(),"Fecha sin historial" ,Toast.LENGTH_LONG);
                toast2.show();
            }
        }
        @Override
        protected void onPreExecute(){
        }
    }

    /*public String[] BuscarFecha(String dni){
        String driverDB= "org.postgresql.Driver";
        String urlDB = "jdbc:postgresql://192.168.1.10:5432/db_TFG";
        String userDB = "postgres";
        String passDB = "password";

        String[] fechas = new String[3];
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
                fechas[i] = rs.getString("data");
                i++;
            }
            st.close();
            conn.close();
        }catch(SQLException se){
            System.out.println("No se puede conectar. Error: "+ se.toString());
        }catch (ClassNotFoundException e){
            System.out.println("No se encuentra la classe. Error: "+ e.getMessage());
        }
        return fechas;
    }*/

    /**
     * Consulta para mostrar las 3 últimas fechas para el historial
     */
    /*private class ConsultaASync extends AsyncTask<String,String,String[]> {
        @Override
        protected String[] doInBackground(String ... params){
            String[] fechas;

            fechas = BuscarFecha(dni);

            return fechas;
        }
        @Override
        protected void onPostExecute(String[] result){
            if(result[0] != null)
                data1.setText(result[0].substring(0,10));
            else data1.setVisibility(View.GONE);
            if(result[1] != null)
                data2.setText(result[1].substring(0,10));
            else data2.setVisibility(View.GONE);
            if(result[2] != null)
                data3.setText(result[2].substring(0,10));
            else data3.setVisibility(View.GONE);
        }
        @Override
        protected void onPreExecute(){
        }
    }*/
}