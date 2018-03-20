package hr.apps.maltar.fitnesscoach.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hr.apps.maltar.fitnesscoach.R;
import hr.apps.maltar.fitnesscoach.pickers.EndTimePickerFragment;
import hr.apps.maltar.fitnesscoach.pickers.StartTimePickerFragment;

public class AddTrainingActivity extends AppCompatActivity {
    private Button setStartTimeButton;
    private Button setEndTimeButton;
    private Button saveTrainingButton;
    private Button cancelButton;
    private TextView startTimeTextView;
    private TextView endTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        setViews();
    }

    private void setViews() {
        setStartTimeButton = findViewById(R.id.add_training_start_time_button);
        setStartTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new StartTimePickerFragment();
                dialogFragment.show(getFragmentManager(), "TimePicker");
            }
        });

        setEndTimeButton = findViewById(R.id.add_training_end_time_button);
        setEndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new EndTimePickerFragment();
                dialogFragment.show(getFragmentManager(), "TimePicker");
            }
        });

        saveTrainingButton = findViewById(R.id.add_training_save_button);
        saveTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cancelButton = findViewById(R.id.add_training_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        startTimeTextView = findViewById(R.id.add_training_start_time_text);
        endTimeTextView = findViewById(R.id.add_training_end_time_text);
    }
}
