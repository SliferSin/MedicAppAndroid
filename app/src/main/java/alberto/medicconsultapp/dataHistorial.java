package alberto.medicconsultapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class dataHistorial extends AppCompatActivity {


    HistorialClass historial;
    String dni,data;
    private Button button;
    private EditText dataHistorial;
    private TextView data1,data2,data3;
    private int year,month,day;
    private Calendar calendar;


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
                DatePickerDialog dialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_DARK,
                new DatePickerDialog.OnDateSetListener() {

                    @Override //Mes lo coge mal
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String mes = Integer.toString(monthOfYear+1);
                        String año = Integer.toString(year);
                        String fecha;

                        if(month<10){
                            mes = "0" + mes;
                        }
                        fecha = mes + "-" + año;
                        data = fecha;
                        //Mostrar la fecha seleccionada en el campo fecha
                        dataHistorial.setText(fecha);

                    }

                }, year, month, day);
        //Ocultamos el campo "dia" del DatePickerDialog
        ((ViewGroup) dialog.getDatePicker()).findViewById(Resources.getSystem().getIdentifier("day", "id", "android")).setVisibility(View.GONE);
        return dialog;
    }

    public void Consultar (View view){
        //Configuración del siguiente Activity que se abrira
        Intent intentMenu;
        intentMenu = new Intent(this, ListHistorial.class);

        historial = new HistorialClass(dni,data);

        intentMenu.putExtra("DNI", dni);
        intentMenu.putExtra("DATA",data);
        startActivity(intentMenu);

    }
}
