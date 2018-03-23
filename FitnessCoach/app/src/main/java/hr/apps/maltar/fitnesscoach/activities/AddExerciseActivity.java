package hr.apps.maltar.fitnesscoach.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.parceler.Parcels;

import hr.apps.maltar.fitnesscoach.R;
import hr.apps.maltar.fitnesscoach.database.entities.Exercise;
import hr.apps.maltar.fitnesscoach.database.entities.ExerciseType;
import hr.apps.maltar.fitnesscoach.database.entities.ExerciseTypeCategory;
import hr.apps.maltar.fitnesscoach.params.IntentExtrasParams;

public class AddExerciseActivity extends AppCompatActivity {
    private static final String LOG_TAG = "AddExerciseActivity";
    private static final int CHOOSE_EXERCISE_TYPE_REQUEST = 1;

    private TextView exerciseTypeTextView;
    private Button chooseExerciseTypeButton;
    private Button doneButton;
    private Button cancelButton;

    private LinearLayout detailsTimeLayout;
    private EditText timeSeriesEditText;
    private EditText timeWeightEditText;
    private NumberPicker minutesNumberPicker;
    private NumberPicker secondsNumberPicker;
    private CheckBox timeDoneCheckBox;

    private LinearLayout detailsRepetitionLayout;
    private EditText repetitionSeriesEditText;
    private EditText repetitionsEditText;
    private EditText repetitionWeightEditText;
    private CheckBox repetitionDoneCheckBox;

    private ExerciseType exerciseType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        setMainViews();
    }

    private void setMainViews() {
        exerciseTypeTextView = findViewById(R.id.add_exercise_type_text);
        chooseExerciseTypeButton = findViewById(R.id.add_exercise_choose_type_button);
        chooseExerciseTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChooseExerciseTypeActivity.class);
                startActivityForResult(intent, CHOOSE_EXERCISE_TYPE_REQUEST);
            }
        });
        cancelButton = findViewById(R.id.add_exercise_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        detailsTimeLayout = findViewById(R.id.add_exercise_details_time);
        detailsRepetitionLayout = findViewById(R.id.add_exercise_details_repetition);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_EXERCISE_TYPE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                exerciseType = Parcels.unwrap(data.getParcelableExtra(IntentExtrasParams.EXERCISE_TYPE_EXTRA));
                if (exerciseType == null) {
                    Log.d(LOG_TAG, "Error: no Exercise Type returned from ChooseExerciseTypeActivity");
                } else {
                    exerciseTypeTextView.setText(exerciseType.getName());
                    doneButton = findViewById(R.id.add_exercise_done_button);
                    doneButton.setVisibility(View.VISIBLE);
                    doneButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            returnExercise();
                        }
                    });
                    switch (exerciseType.getCategory()) {
                        case ExerciseTypeCategory.REPETITION:
                            setRepetitionLayout();
                            break;
                        case ExerciseTypeCategory.TIME:
                            setTimeLayout();
                            break;
                    }
                }
            }
        }
    }

    private void returnExercise() {
        Exercise exercise = new Exercise();
        if (exerciseType.getCategory() == ExerciseTypeCategory.TIME) {
            long time = minutesNumberPicker.getValue() * 60 + secondsNumberPicker.getValue();
            int numberOfSeries = Integer.parseInt(timeSeriesEditText.getText().toString());
            double weight = 0;
            try {
                weight = Double.parseDouble(timeWeightEditText.getText().toString());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            boolean done = timeDoneCheckBox.isChecked();
            exercise = new Exercise(numberOfSeries, weight, time, done);
        } else {
            int numberOfSeries = 0;
            try {
                numberOfSeries  = Integer.parseInt(repetitionSeriesEditText.getText().toString());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            int numberOfRepetitions = 0;
            try {
                numberOfRepetitions = Integer.parseInt(repetitionsEditText.getText().toString());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            double weight = 0;
            try {
                weight = Double.parseDouble(repetitionWeightEditText.getText().toString());
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            boolean done = repetitionDoneCheckBox.isChecked();
            exercise = new Exercise(numberOfSeries, numberOfRepetitions, weight, done);
        }
        exercise.setExerciseType(exerciseType);
        Intent intent = new Intent();
        intent.putExtra(IntentExtrasParams.EXERCISE_EXTRA, Parcels.wrap(exercise));
        // intent.putExtra(IntentExtrasParams.EXERCISE_TYPE_EXTRA, Parcels.wrap(exerciseType));
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void setTimeLayout() {
        detailsRepetitionLayout.setVisibility(View.GONE);
        detailsTimeLayout.setVisibility(View.VISIBLE);

        timeSeriesEditText = findViewById(R.id.add_exercise_time_series_edit);
        timeWeightEditText = findViewById(R.id.add_exercise_time_weight_edit);
        minutesNumberPicker = findViewById(R.id.add_exercise_minutes_picker);
        minutesNumberPicker.setMinValue(0);
        minutesNumberPicker.setMaxValue(60);
        //minutesNumberPicker.setWrapSelectorWheel(true);
        secondsNumberPicker = findViewById(R.id.add_exercise_seconds_picker);
        secondsNumberPicker.setMinValue(0);
        secondsNumberPicker.setMaxValue(60);
        //secondsNumberPicker.setWrapSelectorWheel(true);
        timeDoneCheckBox = findViewById(R.id.add_exercise_time_done_check_box);
    }

    private void setRepetitionLayout() {
        detailsRepetitionLayout.setVisibility(View.VISIBLE);
        detailsTimeLayout.setVisibility(View.GONE);

        repetitionSeriesEditText = findViewById(R.id.add_exercise_repetition_series_edit);
        repetitionsEditText = findViewById(R.id.add_exercise_repetitions_edit);
        repetitionWeightEditText = findViewById(R.id.add_exercise_repetition_weight_edit);
        repetitionDoneCheckBox = findViewById(R.id.add_exercise_repetition_done_check_box);
    }
}
