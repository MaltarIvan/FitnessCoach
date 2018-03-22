package hr.apps.maltar.fitnesscoach.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maltar on 16.3.2018..
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "fitness_coach.db";
    private static final int DB_VERSION = 1;

    private static final String SQL_CREATE_TRAINING_ENTRIES = "CREATE TABLE " +
            FitnessCoachContract.TrainingEntry.TABLE_NAME + " (" +
            FitnessCoachContract.TrainingEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FitnessCoachContract.TrainingEntry.DATE + " BIGINT NOT NULL," +
            FitnessCoachContract.TrainingEntry.TIME_START + " BIGINT," +
            FitnessCoachContract.TrainingEntry.TIME_END + " BIGINT," +
            FitnessCoachContract.TrainingEntry.DONE + " INT NOT NULL)";

    private static final String SQL_CREATE_EXERCISE_ENTRIES = "CREATE TABLE " +
            FitnessCoachContract.ExerciseEntry.TABLE_NAME + " (" +
            FitnessCoachContract.ExerciseEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FitnessCoachContract.ExerciseEntry.EXERCISE_TYPE_ID + " INTEGER NOT NULL," +
            FitnessCoachContract.ExerciseEntry.NUMBER_OF_SERIES + " INTEGER NOT NULL," +
            FitnessCoachContract.ExerciseEntry.NUMBER_OF_REPETITIONS + " INTEGER NOT NULL," +
            FitnessCoachContract.ExerciseEntry.WEIGHT + " FLOAT," +
            FitnessCoachContract.ExerciseEntry.TIME + " BIGINT," +
            FitnessCoachContract.ExerciseEntry.DONE + " INT NOT NULL," +
            FitnessCoachContract.ExerciseEntry.TRAINING_ID + " INT NOT NULL," +
            FitnessCoachContract.ExerciseEntry.DATE + " BIGINT NOT NULL)";

    private static final String SQL_CREATE_EXERCISE_TYPE_ENTRIES = "CREATE TABLE " +
            FitnessCoachContract.ExerciseTypeEntry.TABLE_NAME + " (" +
            FitnessCoachContract.ExerciseTypeEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            FitnessCoachContract.ExerciseTypeEntry.NAME + " TEXT NOT NULL," +
            FitnessCoachContract.ExerciseTypeEntry.CATEGORY + " INT NOT NULL)";

    private static final String SQL_DELETE_TRAININGS_ENTRIES = "DROP TABLE IF EXISTS " + FitnessCoachContract.TrainingEntry.TABLE_NAME;
    private static final String SQL_DELETE_EXERCISES_ENTRIES = "DROP TABLE IF EXISTS " + FitnessCoachContract.ExerciseEntry.TABLE_NAME;
    private static final String SQL_DELETE_EXERCISE_TYPES_ENTRIES = "DROP TABLE IF EXISTS " +FitnessCoachContract.ExerciseTypeEntry.TABLE_NAME;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TRAINING_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_EXERCISE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_EXERCISE_TYPE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_TRAININGS_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_EXERCISES_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_EXERCISE_TYPES_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
