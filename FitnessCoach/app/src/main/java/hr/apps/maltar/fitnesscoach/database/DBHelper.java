package hr.apps.maltar.fitnesscoach.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import hr.apps.maltar.fitnesscoach.database.entities.Exercise;
import hr.apps.maltar.fitnesscoach.database.entities.ExerciseType;
import hr.apps.maltar.fitnesscoach.database.entities.Training;

/**
 * Created by Maltar on 16.3.2018..
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "fitness_coach.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Training.class);
            TableUtils.createTable(connectionSource, Exercise.class);
            TableUtils.createTable(connectionSource, ExerciseType.class);
        } catch (SQLException exe) {
            exe.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    // training

    public List<Training> getAllTrainings(Class clazz) throws SQLException {
        Dao<Training, ?> dao = getDao(clazz);
        return dao.queryForAll();
    }

    public Training getTrainingById(Class clazz, Object aId) throws SQLException {
        Dao<Training, Object> dao = getDao(clazz);
        return dao.queryForId(aId);
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(Training obj) throws SQLException {
        Dao<Training, ?> dao = (Dao<Training, ?>) getDao(obj.getClass());
        return dao.createOrUpdate(obj);
    }

    public int deleteTrainingById(Class clazz, Object aId) throws SQLException {
        Dao<Training, Object> dao = getDao(clazz);
        return dao.deleteById(aId);
    }

    // exercise

    public List<Exercise> getAllExercises(Class clazz) throws SQLException {
        Dao<Exercise, ?> dao = getDao(clazz);
        return dao.queryForAll();
    }

    public Exercise getExerciseById(Class clazz, Object aId) throws SQLException {
        Dao<Exercise, Object> dao = getDao(clazz);
        return dao.queryForId(aId);
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(Exercise obj) throws SQLException {
        Dao<Exercise, ?> dao = (Dao<Exercise, ?>) getDao(obj.getClass());
        return dao.createOrUpdate(obj);
    }

    public int deleteExerciseById(Class clazz, Object aId) throws SQLException {
        Dao<Exercise, Object> dao = getDao(clazz);
        return dao.deleteById(aId);
    }

    // exercise type

    public List<ExerciseType> getAllExerciseTypes(Class clazz) throws SQLException {
        Dao<ExerciseType, ?> dao = getDao(clazz);
        return dao.queryForAll();
    }

    public ExerciseType getExerciseTypeById(Class clazz, Object aId) throws SQLException {
        Dao<ExerciseType, Object> dao = getDao(clazz);
        return dao.queryForId(aId);
    }

    public Dao.CreateOrUpdateStatus createOrUpdate(ExerciseType obj) throws SQLException {
        Dao<ExerciseType, ?> dao = (Dao<ExerciseType, ?>) getDao(obj.getClass());
        return dao.createOrUpdate(obj);
    }

    public int deleteExerciseTypeById(Class clazz, Object aId) throws SQLException {
        Dao<ExerciseType, Object> dao = getDao(clazz);
        return dao.deleteById(aId);
    }
}
