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
import java.util.Calendar;

public class dataHistorial extends AppCompatActivity {


    HistorialClass historial;
    String dni;
    String data;
    private Button button;
    private EditText dataHistorial;
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
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Intent intent = getIntent();//Preparamos el objeto para obtener los datos compartidos
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior
        if (extras != null) {
            dni = (String) extras.get("DNI");
        }
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
        intentMenu = new Intent(this, HistorialActivity.class);

        historial = new HistorialClass(dni,data);

        new  DbASync().execute(""); //Ejecutamos la consulta en 2o plano

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
}