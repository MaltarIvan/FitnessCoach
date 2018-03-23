package hr.apps.maltar.fitnesscoach.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;

import hr.apps.maltar.fitnesscoach.R;
import hr.apps.maltar.fitnesscoach.custom.pickers.EndTimePickerFragment;
import hr.apps.maltar.fitnesscoach.custom.pickers.StartTimePickerFragment;
import hr.apps.maltar.fitnesscoach.database.entities.Exercise;
import hr.apps.maltar.fitnesscoach.database.entities.Training;
import hr.apps.maltar.fitnesscoach.listAdapters.ExercisesAdapter;
import hr.apps.maltar.fitnesscoach.params.IntentExtrasParams;
import hr.apps.maltar.fitnesscoach.params.IntentFilterParams;
import hr.apps.maltar.fitnesscoach.services.DataIntentService;

public class AddTrainingActivity extends AppCompatActivity {
    private static final int ADD_EXERCISE_REQUEST = 1;

    private BroadcastReceiver broadcastReceiver;

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

    private int startTimeHours;
    private int startTimeMinutes;
    private int endTimeHours;
    private int endTimeMinutes;
    private ArrayList<Exercise> exercises;

    private Date daysDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_training);

        exercises = new ArrayList<>();
        exercisesAdapter = new ExercisesAdapter(getApplicationContext(), exercises);

        registerLocalBroadcastManagerReceiver();

        daysDate = new Date(getIntent().getLongExtra(IntentExtrasParams.DATE_EXTRA, -1));

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
                int startTime = startTimeHours * 60 + startTimeMinutes;
                int endTime = endTimeHours * 60 + endTimeMinutes;
                boolean done = isDoneCheckBox.isChecked();
                for (Exercise exercise : exercises) {
                    exercise.setDate(daysDate);
                }
                Training training = new Training(startTime, endTime, done, daysDate, exercises);
                Intent intent = new Intent(getApplicationContext(), DataIntentService.class);
                intent.putExtra(IntentExtrasParams.ACTION_EXTRA, IntentFilterParams.ADD_TRAINING_ACTION);
                intent.putExtra(IntentExtrasParams.TRAINING_EXTRA, Parcels.wrap(training));
                startService(intent);
            }
        });

        cancelButton = findViewById(R.id.add_training_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
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

        exercisesListView = findViewById(R.id.add_training_exercise_list);
        exercisesListView.setAdapter(exercisesAdapter);
    }

    private void addExercise() {
        Intent intent = new Intent(getApplicationContext(), AddExerciseActivity.class);
        startActivityForResult(intent, ADD_EXERCISE_REQUEST);
    }

    public void setStartTime(int hours, int minutes) {
        startTimeHours = hours;
        startTimeMinutes = minutes;
    }

    public void setEndTime(int hours, int minutes) {
        endTimeHours = hours;
        endTimeMinutes = minutes;
    }

    private void registerLocalBroadcastManagerReceiver() {
        broadcastReceiver = new AddTrainingActivity.DataBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentFilterParams.ADD_TRAINING_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_EXERCISE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Exercise exercise = Parcels.unwrap(data.getParcelableExtra(IntentExtrasParams.EXERCISE_EXTRA));
                exercises.add(exercise);
                //exercisesAdapter.add(exercise);
                exercisesAdapter.notifyDataSetChanged();
            }
        }
    }

    private class DataBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (IntentFilterParams.ADD_TRAINING_ACTION.equals(intent.getAction())) {
                sendResult(intent);
            }
        }
    }

    private void sendResult(Intent intent) {
        Training training = Parcels.unwrap(intent.getParcelableExtra(IntentExtrasParams.TRAINING_EXTRA));
        Intent resultIntent = new Intent();
        resultIntent.putExtra(IntentExtrasParams.TRAINING_EXTRA, Parcels.wrap(training));
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}
