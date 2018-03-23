package hr.apps.maltar.fitnesscoach.listAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hr.apps.maltar.fitnesscoach.R;
import hr.apps.maltar.fitnesscoach.database.entities.ExerciseType;
import hr.apps.maltar.fitnesscoach.database.entities.ExerciseTypeCategory;

/**
 * Created by Maltar on 23.3.2018..
 */

public class ExerciseTypeAdapter extends ArrayAdapter<ExerciseType> {
    public ExerciseTypeAdapter(@NonNull Context context, ArrayList<ExerciseType> exerciseTypes) {
        super(context, 0, exerciseTypes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.choose_exercise_type_item, parent, false);
        }

        ExerciseType exerciseType = getItem(position);
        TextView nameTextView = listItemView.findViewById(R.id.exercise_type_item_name);
        nameTextView.setText(exerciseType.getName());
        TextView categoryTextView = listItemView.findViewById(R.id.exercise_type_item_category);
        switch (exerciseType.getCategory()) {
            case ExerciseTypeCategory.TIME:
                categoryTextView.setText("Time");
                break;
            case ExerciseTypeCategory.REPETITION:
                categoryTextView.setText("Repetition");
        }
        return listItemView;
    }
}
