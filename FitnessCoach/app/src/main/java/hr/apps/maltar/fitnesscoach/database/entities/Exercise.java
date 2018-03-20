package hr.apps.maltar.fitnesscoach.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

import java.sql.Date;

/**
 * Created by Maltar on 16.3.2018..
 */
@Parcel
@DatabaseTable(tableName = "Exercises")
public class Exercise {
    private static final String TRAINING_ID_FIELD_NAME = "trainingId";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true)
    private ExerciseType exerciseType;

    @DatabaseField
    private int numberOfSeries;

    @DatabaseField
    private int numberOfRepetitions;

    @DatabaseField
    private double weight;

    @DatabaseField
    private String time;

    @DatabaseField
    private boolean done;

    @DatabaseField
    private Date date;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = TRAINING_ID_FIELD_NAME)
    private Training training;

    public Exercise() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public int getNumberOfSeries() {
        return numberOfSeries;
    }

    public void setNumberOfSeries(int numberOfSeries) {
        this.numberOfSeries = numberOfSeries;
    }

    public int getNumberOfRepetitions() {
        return numberOfRepetitions;
    }

    public void setNumberOfRepetitions(int numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }
}
