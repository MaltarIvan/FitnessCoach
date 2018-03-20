package hr.apps.maltar.fitnesscoach.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.parceler.Parcels;

import java.sql.SQLException;
import java.util.ArrayList;

import hr.apps.maltar.fitnesscoach.database.DBHelper;
import hr.apps.maltar.fitnesscoach.database.entities.Exercise;
import hr.apps.maltar.fitnesscoach.database.entities.ExerciseType;
import hr.apps.maltar.fitnesscoach.database.entities.Training;
import hr.apps.maltar.fitnesscoach.params.IntentExtrasParams;
import hr.apps.maltar.fitnesscoach.params.IntentFilterParams;

/**
 * Created by Maltar on 16.3.2018..
 */

public class DataIntentService extends IntentService {

    private static final String SERVICE_NAME = "Data Intent Service";
    private static final String LOG_TAG = "DataIntentService";

    private Training receivedTraining;
    private Exercise receivedExercise;
    private ExerciseType receivedExerciseType;

    private int receivedTrainingId;
    private int receivedExerciseId;
    private int receivedExerciseTypeId;

    private int receivedExerciseTypeCategory;

    private DBHelper dbHelper;

    public DataIntentService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        dbHelper = new DBHelper(getApplicationContext());

        receivedTraining = Parcels.unwrap(intent.getParcelableExtra(IntentExtrasParams.TRAINING_EXTRA));
        receivedExercise = Parcels.unwrap(intent.getParcelableExtra(IntentExtrasParams.EXERCISE_EXTRA));
        receivedExerciseType = Parcels.unwrap(intent.getParcelableExtra(IntentExtrasParams.EXERCISE_TYPE_EXTRA));

        receivedTrainingId = intent.getIntExtra(IntentExtrasParams.TRAINING_ID_EXTRA, -1);
        receivedExerciseId = intent.getIntExtra(IntentExtrasParams.EXERCISE_ID_EXTRA, -1);
        receivedExerciseTypeId = intent.getIntExtra(IntentExtrasParams.EXERCISE_TYPE_ID_EXTRA, -1);

        receivedExerciseTypeCategory = intent.getIntExtra(IntentExtrasParams.EXERCISE_TYPE_ID_EXTRA, -1);

        handleAction(intent.getStringExtra(IntentExtrasParams.ACTION_EXTRA));
    }

    private void handleAction(String action) {
        switch (action) {
            case IntentFilterParams.ADD_EXERCISE_TYPE_ACTION:
                addExerciseType();
                break;
            case IntentFilterParams.LOAD_ALL_EXERCISE_TYPES_ACTION:
                loadAllExerciseType();
                break;
            case IntentFilterParams.LOAD_ALL_TRAININGS_ACTION:
                loadAllTrainings();
                break;
        }
    }

    private void loadAllTrainings() {
        try {
            ArrayList<Training> trainings = (ArrayList<Training>) dbHelper.getAllTrainings(Training.class);
            Intent intent = new Intent(IntentFilterParams.LOAD_ALL_TRAININGS_ACTION);
            intent.putExtra(IntentExtrasParams.TRAINING_EXTRA, trainings);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAllExerciseType() {
        try {
            ArrayList<ExerciseType> exerciseTypes = (ArrayList<ExerciseType>) dbHelper.getAllExerciseTypes(ExerciseType.class);
            Intent intent = new Intent(IntentFilterParams.LOAD_ALL_EXERCISE_TYPES_ACTION);
            intent.putExtra(IntentExtrasParams.EXERCISE_TYPE_EXTRA, Parcels.wrap(exerciseTypes));
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addExerciseType() {
        if (receivedExerciseType == null) {
            Log.e(LOG_TAG, "No Exercise Type send to Data Service Intent.");
        } else {
            try {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                dbHelper.createOrUpdate(receivedExerciseType);
                Intent intent = new Intent(IntentFilterParams.ADD_EXERCISE_TYPE_ACTION);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
