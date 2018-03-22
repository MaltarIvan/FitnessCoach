package hr.apps.maltar.fitnesscoach.database;

import android.provider.BaseColumns;

/**
 * Created by Maltar on 22.3.2018..
 */

public class FitnessCoachContract {
    private FitnessCoachContract() {}

    public static class TrainingEntry implements BaseColumns {
        public static final String TABLE_NAME = "Trainings";
        public static final String ID = BaseColumns._ID;
        public static final String DATE = "Date";
        public static final String TIME_START = "time_start";
        public static final String TIME_END = "time_end";
        public static final String DONE = "done";
    }

    public static class ExerciseEntry implements BaseColumns {
        public static final String TABLE_NAME = "Exercises";
        public static final String ID = BaseColumns._ID;
        public static final String TRAINING_ID = "training_id";
        public static final String EXERCISE_TYPE_ID = "exercise_type_id";
        public static final String NUMBER_OF_SERIES = "number_of_series";
        public static final String NUMBER_OF_REPETITIONS = "number_of_repetitions";
        public static final String WEIGHT = "weight";
        public static final String TIME = "time";
        public static final String DONE = "done";
        public static final String DATE = "date";
    }

    public static class ExerciseTypeEntry implements BaseColumns {
        public static final String TABLE_NAME = "Exercise_types";
        public static final String ID = BaseColumns._ID;
        public static final String NAME = "name";
        public static final String CATEGORY = "category";
    }
}
