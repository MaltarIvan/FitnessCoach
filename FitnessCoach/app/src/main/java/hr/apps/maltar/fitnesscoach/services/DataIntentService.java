package hr.apps.maltar.fitnesscoach.services;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Date;

import hr.apps.maltar.fitnesscoach.database.DBHelper;
import hr.apps.maltar.fitnesscoach.database.FitnessCoachContract;
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
    private SQLiteDatabase database;

    private String[] trainingProjection = {
            FitnessCoachContract.TrainingEntry.ID,
            FitnessCoachContract.TrainingEntry.DATE,
            FitnessCoachContract.TrainingEntry.TIME_START,
            FitnessCoachContract.TrainingEntry.TIME_END,
            FitnessCoachContract.TrainingEntry.DONE
    };
    private String[] exerciseProjection = {
            FitnessCoachContract.ExerciseEntry.ID,
            FitnessCoachContract.ExerciseEntry.EXERCISE_TYPE_ID,
            FitnessCoachContract.ExerciseEntry.NUMBER_OF_SERIES,
            FitnessCoachContract.ExerciseEntry.NUMBER_OF_REPETITIONS,
            FitnessCoachContract.ExerciseEntry.WEIGHT,
            FitnessCoachContract.ExerciseEntry.TIME,
            FitnessCoachContract.ExerciseEntry.DATE,
            FitnessCoachContract.ExerciseEntry.TRAINING_ID
    };
    private String[] exerciseTypeProjection = {
            FitnessCoachContract.ExerciseTypeEntry.ID,
            FitnessCoachContract.ExerciseTypeEntry.NAME,
            FitnessCoachContract.ExerciseTypeEntry.CATEGORY
    };

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
            case IntentFilterParams.ADD_TRAINING_ACTION:
                addTraining();
                break;
        }
    }

    private void addTraining() {
        if (receivedTraining == null) {
            Log.d(LOG_TAG, "Error: no Training send to Data Service Intent");
        } else {
            database = dbHelper.getWritableDatabase();
            for (Exercise exercise : receivedTraining.getExercises()) {
                ContentValues values = new ContentValues();
                values.put(FitnessCoachContract.ExerciseEntry.TRAINING_ID, receivedTraining.getId());
                values.put(FitnessCoachContract.ExerciseEntry.EXERCISE_TYPE_ID, exercise.getExerciseType().getId());
                values.put(FitnessCoachContract.ExerciseEntry.NUMBER_OF_SERIES, exercise.getNumberOfSeries());
                values.put(FitnessCoachContract.ExerciseEntry.NUMBER_OF_REPETITIONS, exercise.getNumberOfRepetitions());
                values.put(FitnessCoachContract.ExerciseEntry.WEIGHT, exercise.getWeight());
                values.put(FitnessCoachContract.ExerciseEntry.TIME, exercise.getTime());
                values.put(FitnessCoachContract.ExerciseEntry.DONE, exercise.isDone() ? 1 : 0);
                values.put(FitnessCoachContract.ExerciseEntry.DATE, exercise.getDate().getTime());
                exercise.setId((int) database.insert(FitnessCoachContract.ExerciseEntry.TABLE_NAME, null, values));
            }
            ContentValues values = new ContentValues();
            values.put(FitnessCoachContract.TrainingEntry.DATE, receivedTraining.getDate().getTime());
            values.put(FitnessCoachContract.TrainingEntry.TIME_START, receivedTraining.getTimeStart());
            values.put(FitnessCoachContract.TrainingEntry.TIME_END, receivedTraining.getTimeEnd());
            values.put(FitnessCoachContract.TrainingEntry.DONE, receivedTraining.isDone() ? 1 : 0);
            receivedTraining.setId((int) database.insert(FitnessCoachContract.TrainingEntry.TABLE_NAME, null,values));

            Intent intent = new Intent(IntentFilterParams.ADD_TRAINING_ACTION);
            intent.putExtra(IntentExtrasParams.TRAINING_EXTRA, Parcels.wrap(receivedTraining));
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }
    }

    private void loadAllTrainings() {
        database = dbHelper.getReadableDatabase();
        Cursor trainingCursor = database.query(
            FitnessCoachContract.TrainingEntry.TABLE_NAME,
                trainingProjection,
                null,
                null,
                null,
                null,
                FitnessCoachContract.TrainingEntry.DATE + " ASC"
        );

        ArrayList<Training> trainings = new ArrayList<>();
        while (trainingCursor.moveToNext()) {
            int trainingIdIndex = trainingCursor.getColumnIndex(FitnessCoachContract.TrainingEntry.ID);
            int trainingDateIndex = trainingCursor.getColumnIndex(FitnessCoachContract.TrainingEntry.DATE);
            int timeStartIndex = trainingCursor.getColumnIndex(FitnessCoachContract.TrainingEntry.TIME_START);
            int timeEndIndex = trainingCursor.getColumnIndex(FitnessCoachContract.TrainingEntry.TIME_END);
            int trainingDoneIndex = trainingCursor.getColumnIndex(FitnessCoachContract.TrainingEntry.DONE);

            int trainingId = trainingCursor.getInt(trainingIdIndex);
            Date trainingDate = new Date(trainingCursor.getLong(trainingDateIndex));
            long timeStart = trainingCursor.getLong(timeStartIndex);
            long timeEnd = trainingCursor.getLong(timeEndIndex);
            boolean trainingDone = trainingCursor.getInt(trainingDoneIndex) == 1;

            Training training = new Training(trainingId, trainingDate, timeStart, timeEnd, trainingDone, new ArrayList<Exercise>());

            Cursor exerciseCursor = database.query(
                    FitnessCoachContract.ExerciseEntry.TABLE_NAME,
                    exerciseProjection,
                    FitnessCoachContract.ExerciseEntry.TRAINING_ID + " = " + trainingId,
                    null,
                    null,
                    null,
                    null
            );

            ArrayList<Exercise> exercises = new ArrayList<>();
            while (exerciseCursor.moveToNext()) {
                int exerciseIdIndex = exerciseCursor.getColumnIndex(FitnessCoachContract.ExerciseEntry.ID);
                int exerciseTypeIdIndex = exerciseCursor.getColumnIndex(FitnessCoachContract.ExerciseEntry.EXERCISE_TYPE_ID);
                int numberOfSeriesIndex = exerciseCursor.getColumnIndex(FitnessCoachContract.ExerciseEntry.NUMBER_OF_SERIES);
                int numberOfRepetitionsIndex = exerciseCursor.getColumnIndex(FitnessCoachContract.ExerciseEntry.NUMBER_OF_REPETITIONS);
                int weightIndex = exerciseCursor.getColumnIndex(FitnessCoachContract.ExerciseEntry.WEIGHT);
                int timeIndex = exerciseCursor.getColumnIndex(FitnessCoachContract.ExerciseEntry.TIME);
                int exerciseDoneIndex = exerciseCursor.getColumnIndex(FitnessCoachContract.ExerciseEntry.DONE);
                int exerciseDateIndex = exerciseCursor.getColumnIndex(FitnessCoachContract.ExerciseEntry.DATE);

                int exerciseId = exerciseCursor.getInt(exerciseIdIndex);
                int exerciseTypeId = exerciseCursor.getInt(exerciseTypeIdIndex);
                int numberOfSeries = exerciseCursor.getInt(numberOfSeriesIndex);
                int numberOfRepetition = exerciseCursor.getInt(numberOfRepetitionsIndex);
                double weight = exerciseCursor.getFloat(weightIndex);
                long time  = exerciseCursor.getLong(timeIndex);
                boolean exerciseDone = exerciseCursor.getInt(exerciseDoneIndex) == 1;
                Date exerciseDate = new Date(exerciseCursor.getLong(exerciseDateIndex));

                String selectExerciseTypeQuery = "SELECT * FROM " + FitnessCoachContract.ExerciseTypeEntry.TABLE_NAME + " WHERE " + FitnessCoachContract.ExerciseTypeEntry.ID + " = " + exerciseTypeId;
                Cursor exerciseTypeCursor = database.rawQuery(selectExerciseTypeQuery, null);

                if (exerciseTypeCursor == null || exerciseTypeCursor.getCount() < 1) {
                    Log.d(LOG_TAG, "Cursor is not valid " + exerciseTypeCursor.getCount());
                }

                exerciseTypeCursor.moveToFirst();

                int typeIdIndex = exerciseTypeCursor.getColumnIndex(FitnessCoachContract.ExerciseTypeEntry.ID);
                int nameIndex = exerciseTypeCursor.getColumnIndex(FitnessCoachContract.ExerciseTypeEntry.NAME);
                int categoryIndex = exerciseTypeCursor.getColumnIndex(FitnessCoachContract.ExerciseTypeEntry.CATEGORY);

                int typeId = exerciseTypeCursor.getInt(typeIdIndex);
                String name = exerciseTypeCursor.getString(nameIndex);
                int category = exerciseTypeCursor.getInt(categoryIndex);

                ExerciseType exerciseType = new ExerciseType(typeId, name, category, new ArrayList<Exercise>());
                Exercise exercise = new Exercise(exerciseId, exerciseType, numberOfSeries, numberOfRepetition, weight, time, exerciseDone, exerciseDate, training);
                exerciseType.getExercises().add(exercise);
                exercises.add(exercise);
            }
            training.setExercises(exercises);
            trainings.add(training);
        }
        Intent intent = new Intent(IntentFilterParams.LOAD_ALL_TRAININGS_ACTION);
        ArrayList<Parcelable> parcelables = new ArrayList<>();
        for (Training training : trainings) {
            parcelables.add(Parcels.wrap(training));
        }
        intent.putExtra(IntentExtrasParams.TRAINING_EXTRA, parcelables);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void loadAllExerciseType() {
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(
                FitnessCoachContract.ExerciseTypeEntry.TABLE_NAME,
                exerciseTypeProjection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<ExerciseType> exerciseTypes = new ArrayList<>();

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(FitnessCoachContract.ExerciseTypeEntry.ID);
            int nameIndex = cursor.getColumnIndex(FitnessCoachContract.ExerciseTypeEntry.NAME);
            int categoryIndex = cursor.getColumnIndex(FitnessCoachContract.ExerciseTypeEntry.CATEGORY);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            int category = cursor.getInt(categoryIndex);

            ExerciseType exerciseType = new ExerciseType(id, name, category);
            exerciseTypes.add(exerciseType);
        }

        Intent intent = new Intent(IntentFilterParams.LOAD_ALL_EXERCISE_TYPES_ACTION);
        ArrayList<Parcelable> parcelables = new ArrayList<>();
        for (ExerciseType exerciseType : exerciseTypes) {
            parcelables.add(Parcels.wrap(exerciseType));
        }
        intent.putExtra(IntentExtrasParams.EXERCISE_TYPE_EXTRA, parcelables);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void addExerciseType() {
        if (receivedExerciseType == null) {
            Log.d(LOG_TAG, "Error: no ExerciseType send to DataIntentService");
        } else {
            database = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(FitnessCoachContract.ExerciseTypeEntry.NAME, receivedExerciseType.getName());
            values.put(FitnessCoachContract.ExerciseTypeEntry.CATEGORY, receivedExerciseType.getCategory());
            int id = (int) database.insert(FitnessCoachContract.ExerciseTypeEntry.TABLE_NAME, null, values);
            receivedExerciseType.setId(id);

            Intent intent = new Intent(IntentFilterParams.ADD_EXERCISE_TYPE_ACTION);
            intent.putExtra(IntentExtrasParams.EXERCISE_TYPE_EXTRA, Parcels.wrap(receivedExerciseType));
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }
    }
}
