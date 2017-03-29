package alberto.medicconsultapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.text.format.DateFormat;
import java.util.Calendar;
import android.app.DialogFragment;
import android.os.Bundle;


/**
 * Created by Ashto on 29/03/2017.
 */

public class TimePickerFragment extends android.app.DialogFragment implements TimePickerDialog.OnTimeSetListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hour,minute, DateFormat.is24HourFormat(getActivity()));
    }
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){

    }
}
