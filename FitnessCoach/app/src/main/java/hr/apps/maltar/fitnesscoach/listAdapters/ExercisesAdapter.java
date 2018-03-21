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
import hr.apps.maltar.fitnesscoach.database.entities.Exercise;

/**
 * Created by Maltar on 21.3.2018..
 */

public class ExercisesAdapter extends ArrayAdapter<Exercise> {
    public ExercisesAdapter(Context context, ArrayList<Exercise> exercises) {
        super(context, 0, exercises);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_list_item, parent, false);
        }

        Exercise exercise = getItem(position);

        TextView exerciseTypeTextView = listItemView.findViewById(R.id.exercise_list_item_exercise_type_text_view);
        exerciseTypeTextView.setText(exercise.getExerciseType().getName());

        return listItemView;
    }
}
