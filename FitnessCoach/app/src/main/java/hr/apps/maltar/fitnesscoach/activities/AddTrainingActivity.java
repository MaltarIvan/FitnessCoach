package hr.apps.maltar.fitnesscoach.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

import hr.apps.maltar.fitnesscoach.R;
import hr.apps.maltar.fitnesscoach.custom.pickers.EndTimePickerFragment;
import hr.apps.maltar.fitnesscoach.custom.pickers.StartTimePickerFragment;
import hr.apps.maltar.fitnesscoach.database.entities.Exercise;
import hr.apps.maltar.fitnesscoach.database.entities.Training;
import hr.apps.maltar.fitnesscoach.listAdapters.ExercisesAdapter;

public class AddTrainingActivity extends AppCompatActivity {
    private Button setStartTimeButton;
    private Button setEndTimeButton;
    private Button saveTrainingButton;
    private Button cancelButton;
    private TextView startTimeTextView;
    private TextView endTimeTextView;

    private Button addExerciseButton;
    private CheckBox isDoneCheckBox;
    private ListView exercisesListView;

    private ExercisesAdapter exercisesAdapter;

    private boolean isDone = false;
    private int startTimeHours;
    private int startTimeMinutes;
    private int endTimeHours;
    private int endTimeMinutes;
    private ArrayList<Exercise> exercises;

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
                finish();
            }
        });

        startTimeTextView = findViewById(R.id.add_training_start_time_text);
        endTimeTextView = findViewById(R.id.add_training_end_time_text);

        addExerciseButton = findViewById(R.id.add_training_add_exercise_button);
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExercise();
            }
        });

        isDoneCheckBox = findViewById(R.id.add_training_done_check_box);
        isDoneCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDone = isDoneCheckBox.isChecked();
            }
        });

        exercisesListView = findViewById(R.id.add_training_exercise_list);
        exercisesAdapter = new ExercisesAdapter(getApplicationContext(), new ArrayList<Exercise>());
    }

    private void addExercise() {
        Training training = new Training();
        training.setDate(new Date());
        training.setTimeStart(startTimeHours * 3600000 + startTimeMinutes * 60000);
        training.setTimeEnd(endTimeHours * 3600000 + endTimeMinutes * 60000);
        training.setDone(isDone);
    }

    public void setStartTime(int hours, int minutes) {
        startTimeHours = hours;
        startTimeMinutes = minutes;
    }

    public void setEndTime(int hours, int minutes) {
        endTimeHours = hours;
        endTimeMinutes = minutes;
    }
}
