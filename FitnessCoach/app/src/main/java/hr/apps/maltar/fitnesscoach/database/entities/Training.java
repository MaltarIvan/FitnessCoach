package hr.apps.maltar.fitnesscoach.database.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Maltar on 16.3.2018..
 */
@DatabaseTable(tableName = "Training")
public class Training implements Parcelable {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private Date date;

    @DatabaseField
    private Date timeStart;

    @DatabaseField
    private Date timeEnd;

    /*
    @ForeignCollectionField
    private ForeignCollection<Exercise> exercises;
    */

    @ForeignCollectionField
    private ForeignCollection<Exercise> exercises;

    // todo : Parceler ne može parsirati .. odg ?? => https://groups.google.com/forum/#!topic/ormlite-user/C1kt6XbJD6w

    @DatabaseField
    private Boolean done;

    public Training() {
    }

    protected Training(Parcel in) {
        id = in.readInt();
        byte tmpDone = in.readByte();
        done = tmpDone == 0 ? null : tmpDone == 1;

        timeStart = new Date(in.readLong());
        timeEnd = new Date(in.readLong());

        ArrayList<Exercise> ex = in.readArrayList(null);
        exercises = (ForeignCollection<Exercise>) new HashSet<Exercise>(ex);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (done == null ? 0 : done ? 1 : 2));

        dest.writeLong(timeStart.getTime());
        dest.writeLong(timeEnd.getTime());

        ArrayList<Exercise> ex = new ArrayList<>(exercises);
        dest.writeList(ex);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Training> CREATOR = new Creator<Training>() {
        @Override
        public Training createFromParcel(Parcel in) {
            return new Training(in);
        }

        @Override
        public Training[] newArray(int size) {
            return new Training[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Collection<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(ForeignCollection<Exercise> exercises) {
        this.exercises = exercises;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
