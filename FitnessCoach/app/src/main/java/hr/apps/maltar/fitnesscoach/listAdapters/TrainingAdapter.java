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
import hr.apps.maltar.fitnesscoach.database.entities.Training;

/**
 * Created by Maltar on 22.3.2018..
 */

public class TrainingAdapter extends ArrayAdapter<Training> {
    public TrainingAdapter(@NonNull Context context, ArrayList<Training> trainings) {
        super(context, 0, trainings);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.day_training_list_item, parent, false);
        }

        Training training = getItem(position);

        TextView dateTextView = listItemView.findViewById(R.id.day_training_list_item_date);
        dateTextView.setText(training.getDate().toString());

        return listItemView;
    }
}
