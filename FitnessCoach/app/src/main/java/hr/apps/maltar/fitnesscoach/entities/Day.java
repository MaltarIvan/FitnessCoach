package hr.apps.maltar.fitnesscoach.entities;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;

import hr.apps.maltar.fitnesscoach.database.entities.Training;

/**
 * Created by Maltar on 20.3.2018..
 */
@Parcel
public class Day {
    private Date date;
    private ArrayList<Training> trainings;

    public Day(Date date, ArrayList<Training> trainings) {
        this.date = date;
        this.trainings = trainings;
    }

    public Day() {
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(ArrayList<Training> trainings) {
        this.trainings = trainings;
    }
}
