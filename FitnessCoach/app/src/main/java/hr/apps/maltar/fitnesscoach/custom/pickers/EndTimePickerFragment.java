package hr.apps.maltar.fitnesscoach.custom.pickers;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import hr.apps.maltar.fitnesscoach.R;

/**
 * Created by Maltar on 20.3.2018..
 */

public class EndTimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public EndTimePickerFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        TextView textView = getActivity().findViewById(R.id.add_training_end_time_text);
        textView.setText(textView.getText().toString() + hourOfDay + ":" + minute);
    }
}
