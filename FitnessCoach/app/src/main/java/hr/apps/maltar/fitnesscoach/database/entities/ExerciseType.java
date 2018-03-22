package hr.apps.maltar.fitnesscoach.database.entities;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Maltar on 16.3.2018..
 */
@Parcel
public class ExerciseType {
    private int id;
    private String name;
    private int category;
    private ArrayList<Exercise> exercises;

    public ExerciseType() {
    }

    public ExerciseType(int id, String name, int category, ArrayList<Exercise> exercises) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.exercises = exercises;
    }

    public ExerciseType(String name, int category) {
        this.name = name;
        this.category = category;
    }

    public ExerciseType(int id, String name, int category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public ArrayList<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises = exercises;
    }
}
