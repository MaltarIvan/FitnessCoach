package hr.apps.maltar.fitnesscoach.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.parceler.Parcel;

/**
 * Created by Maltar on 16.3.2018..
 */
@Parcel
@DatabaseTable(tableName = "ExerciseType")
public class ExerciseType {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String name;

    @DatabaseField
    private int category;

    public ExerciseType(String name, int category) {
        this.name = name;
        this.category = category;
    }

    public ExerciseType() {
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
}
