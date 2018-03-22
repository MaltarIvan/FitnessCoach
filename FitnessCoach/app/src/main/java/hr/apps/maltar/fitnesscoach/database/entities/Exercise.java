package hr.apps.maltar.fitnesscoach.database.entities;


import org.parceler.Parcel;

import java.util.Date;

/**
 * Created by Maltar on 16.3.2018..
 */
@Parcel
public class Exercise {
    private int id;
    private ExerciseType exerciseType;
    private int numberOfSeries;
    private int numberOfRepetitions;
    private double weight;
    private long time;
    private boolean done;
    private Date date;
    private Training training;

    public Exercise() {
    }

    public Exercise(int id, ExerciseType exerciseType, int numberOfSeries, int numberOfRepetitions, double weight, long time, boolean done, Date date, Training training) {
        this.id = id;
        this.exerciseType = exerciseType;
        this.numberOfSeries = numberOfSeries;
        this.numberOfRepetitions = numberOfRepetitions;
        this.weight = weight;
        this.time = time;
        this.done = done;
        this.date = date;
        this.training = training;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
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
