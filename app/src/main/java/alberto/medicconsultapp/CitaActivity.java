package alberto.medicconsultapp;


import android.app.Dialog; //Añadido junto al fragmento de código del DialogFragment
import android.widget.TimePicker;
import android.widget.DatePicker;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.os.AsyncTask; //Tareas async
import android.widget.Toast;

import java.util.Calendar;

public class CitaActivity extends AppCompatActivity {

    CitaClass cita;
    String dni;
    Boolean completo = false;
    Toast toast2;

    private EditText dateView,hourView;
    private int year, month, day,hour,minute;
    private Calendar calendar;
    private String data; //Fecha(Dia + Hora) seleccionada por el usuario
    private boolean validate = false;

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

        Intent intent = getIntent();//Preparamos el objeto para obtener los datos compartidos
        Bundle extras = intent.getExtras();//Recibimos los datos del activity anterior
        if(extras != null){
            dni = (String)extras.get("dni");
        }
        dateView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    setDate(v);
                }
            }
        });

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
        if(id == 998){
            return new TimePickerDialog(this,myTimeListener,hour,minute,true);
        }
        return null;
    }
    private TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker arg0,int arg1,int arg2){
            // TODO Auto-generated method stub
            // arg1 = hour
            // arg2 = minute
            showTime(arg1,arg2);
        }
    };
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
    private void showTime(int hour,int minute){
        String hora = String.format("%02d:%02d",hour,minute);
        data = data.concat(" ").concat(hora).concat(":00.000000000");
        hourView.setText(hora);
    }

    private void showDate(int year, int month, int day) {
        String dia = Integer.toString(year).concat("-0").concat(Integer.toString(month)).concat("-").concat(Integer.toString(day));
        //Mostrar la fecha seleccionada en el campo fecha
        data = dia;
        dateView.setText(dia);
    }

    public void Enviar(View view){
        //Configuración del siguiente Activity que se abrira
        Intent intentMenu;
        intentMenu = new Intent(this, MenuActivity.class);

        cita = new CitaClass(dni,data);
        new  DbASync().execute(""); //Ejecutamos la consulta en 2o plano

        if (validate) {
            intentMenu.putExtra("DNI", dni);
            intentMenu.putExtra("DATA",cita.getdata());
            startActivity(intentMenu);
        }
    }

    private class DbASync extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String ... params){
            cita.searchMedico(cita.getDni_Paciente()); //Obtenemos el dni del médico asignado
            if(cita.searchCita(data)){
                completo = true;
                //Si la fecha esta disponible
                cita.setCita(cita);
            }
            else{ //Fecha no disponible buscará la más cercana //Comrpobar este caso
                if(cita.searchNearestCita(data)){
                    completo = true;
                    //Mostrar ventana aviso con el cambio de fecha
                    cita.setCita(cita);
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            if(completo == true){
                toast2 = Toast.makeText(getApplicationContext(),"Fecha reservada",Toast.LENGTH_LONG);
                validate = true; //Activamos la señal para pasar al siguiente activity
                toast2.show();
            }
            else{
                toast2 = Toast.makeText(getApplicationContext(),"Error en la reserva",Toast.LENGTH_LONG);
                toast2.show();
            }
        }
        @Override
        protected void onPreExecute(){
        }
    }
}
