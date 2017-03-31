package alberto.medicconsultapp;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.widget.DatePicker;

import android.widget.TimePicker;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.os.AsyncTask; //Tareas async
import android.widget.ProgressBar;
import android.widget.Toast;

//import java.text.DateFormat;
import java.util.Calendar;

public class CitaActivity extends AppCompatActivity {

    CitaClass cita;
    String dni;
    String[] listhoras;
    ProgressBar Pbar;
    Boolean completo = false;
    int mostrar = 0;
    Toast toast2;
    boolean elegido = false;

    private EditText dateView,hourView;
    private int year, month, day,hour,minute;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita);
        dateView = (EditText)findViewById(R.id.data);
        hourView = (EditText)findViewById(R.id.Hora);

        //Inicializamos los "pickers" con la fecha y hora actual
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        /*Pbar.setMax(100);
        Pbar.setProgress(0);*/

        Intent intent = getIntent();//Preparamos el objeto para obtener los datos compartidos
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior
        if(extras != null){
            dni = (String)extras.get("DNI");
        }
        dateView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setDate(v);
                }
            }
        });
        //while(!elegido){}
        //new  DbASync().execute("");
        hourView.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v,boolean hasFocus){
                if(hasFocus){
                    setTime(v);
                }
            }
        });
    }
    @SuppressWarnings("deprecation")
    public void setTime(View view){ //No muestra el time picker
        showDialog(998);
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view){
        showDialog(999);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
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
        String data = Integer.toString(year).concat("/").concat(Integer.toString(month)).concat("/").concat(Integer.toString(day));
        //Mostrar la fecha seleccionada en el campo fecha
        /*dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));*/
        elegido = true;
        dateView.setText(data);

        //cita = new CitaClass(dni,data);

        //Intent intentCita;//Preparamos el objeto para obtener los datos compartidos
        //intentCita = new Intent(this,ListHours.class);

        //new  DbASync().execute("");


       /* if (mostrar == 1)
            Pbar.setVisibility(View.VISIBLE);
        if(completo){
            System.out.println("Completo");
            /*intentCita.putExtra("Horas",listhoras);
            intentCita.putExtra("Tamaño",listhoras.length);
            startActivity(intentCita);*/
        //}
        //cita.searchMedico(cita.getDni_Paciente()); //Obtenemos el dni del médico asignado
    }
    private class DbASync extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String ... params){
            cita.searchMedico(cita.getDni_Paciente()); //Obtenemos el dni del médico asignado
            listhoras = cita.getHours(cita.getdata());
            if(listhoras != null){
                completo = true;
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            Pbar.setProgress(100);
            if(completo == false){
                toast2 = Toast.makeText(getApplicationContext(),"Login erroneo",Toast.LENGTH_LONG);
                toast2.show();
            }
        }
        @Override
        protected void onPreExecute(){
        }
    }
}