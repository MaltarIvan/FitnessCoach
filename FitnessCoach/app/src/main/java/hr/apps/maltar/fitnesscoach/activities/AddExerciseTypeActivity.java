package hr.apps.maltar.fitnesscoach.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.parceler.Parcels;

import hr.apps.maltar.fitnesscoach.R;
import hr.apps.maltar.fitnesscoach.database.entities.ExerciseType;
import hr.apps.maltar.fitnesscoach.params.IntentExtrasParams;
import hr.apps.maltar.fitnesscoach.params.IntentFilterParams;
import hr.apps.maltar.fitnesscoach.services.DataIntentService;

public class AddExerciseTypeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String LOG_TAG = "AddExerciseTypeActivity";

    private BroadcastReceiver broadcastReceiver;

    private Spinner exerciseTypeCategorySpinner;
    private EditText exerciseTypeNameEditText;
    private TextView exerciseCategoryTextView;
    private Button saveButton;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_type);

        registerLocalBroadcastManagerReceiver();

        exerciseTypeNameEditText = findViewById(R.id.add_exercise_type_name_text_edit);
        exerciseTypeCategorySpinner = findViewById(R.id.add_exercise_type_category_spinner);
        exerciseCategoryTextView = findViewById(R.id.add_exercise_type_category_text);

        exerciseTypeCategorySpinner = findViewById(R.id.add_exercise_type_category_spinner);
        exerciseTypeCategorySpinner.setOnItemSelectedListener(this);

        saveButton = findViewById(R.id.add_exercise_type_add_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = exerciseTypeNameEditText.getText().toString();
                if (category == null || name == "") {
                    return;
                } else {
                    int categoryInt = category.equals("Time") ? 0 : 1;
                    ExerciseType exerciseType = new ExerciseType(name, categoryInt);
                    Intent intent = new Intent(getApplicationContext(), DataIntentService.class);
                    intent.putExtra(IntentExtrasParams.EXERCISE_TYPE_EXTRA, Parcels.wrap(exerciseType));
                    intent.putExtra(IntentExtrasParams.ACTION_EXTRA, IntentFilterParams.ADD_EXERCISE_TYPE_ACTION);
                    startService(intent);
                }
            }
        });

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.exercise_type_category, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseTypeCategorySpinner.setAdapter(spinnerAdapter);
    }

    private void registerLocalBroadcastManagerReceiver() {
        broadcastReceiver = new DataBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(IntentFilterParams.ADD_EXERCISE_TYPE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        category = adapterView.getItemAtPosition(position).toString();
        exerciseCategoryTextView.setText(category);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class DataBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (IntentFilterParams.ADD_EXERCISE_TYPE_ACTION.equals(intent.getAction())) {
                finish();
            }
        }
    }
}
