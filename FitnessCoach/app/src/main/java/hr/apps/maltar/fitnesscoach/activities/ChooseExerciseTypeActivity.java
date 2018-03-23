package hr.apps.maltar.fitnesscoach.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import org.parceler.Parcels;

import java.util.ArrayList;

import hr.apps.maltar.fitnesscoach.R;
import hr.apps.maltar.fitnesscoach.database.entities.ExerciseType;
import hr.apps.maltar.fitnesscoach.listAdapters.ExerciseTypeAdapter;
import hr.apps.maltar.fitnesscoach.params.IntentExtrasParams;
import hr.apps.maltar.fitnesscoach.params.IntentFilterParams;
import hr.apps.maltar.fitnesscoach.services.DataIntentService;

public class ChooseExerciseTypeActivity extends AppCompatActivity {
    private ListView exerciseTypeList;
    private Button cancelButton;

    private ExerciseTypeAdapter exerciseTypeAdapter;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercise_type);

        cancelButton = findViewById(R.id.choose_exercise_type_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        exerciseTypeList = findViewById(R.id.choose_exercise_type_list);
        exerciseTypeAdapter = new ExerciseTypeAdapter(getApplicationContext(), new ArrayList<ExerciseType>());
        exerciseTypeList.setAdapter(exerciseTypeAdapter);
        exerciseTypeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ExerciseType exerciseType = (ExerciseType) adapterView.getItemAtPosition(position);
                Intent intent = new Intent();
                intent.putExtra(IntentExtrasParams.EXERCISE_TYPE_EXTRA, Parcels.wrap(exerciseType));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        registerBroadcastManagerReceiver();

        Intent intent = new Intent(getApplicationContext(), DataIntentService.class);
        intent.putExtra(IntentExtrasParams.ACTION_EXTRA, IntentFilterParams.LOAD_ALL_EXERCISE_TYPES_ACTION);
        startService(intent);
    }

    private void registerBroadcastManagerReceiver() {
        broadcastReceiver = new ChooseExerciseTypeActivity.DataBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(IntentFilterParams.LOAD_ALL_EXERCISE_TYPES_ACTION);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    private class DataBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(IntentFilterParams.LOAD_ALL_EXERCISE_TYPES_ACTION)) {
                ArrayList<ExerciseType> exerciseTypes = new ArrayList<>();
                ArrayList<Parcelable> parcelables = intent.getParcelableArrayListExtra(IntentExtrasParams.EXERCISE_TYPE_EXTRA);
                for (Parcelable parcelable : parcelables) {
                    exerciseTypes.add((ExerciseType) Parcels.unwrap(parcelable));
                }
                exerciseTypeAdapter.addAll(exerciseTypes);
            }
        }
    }
}
