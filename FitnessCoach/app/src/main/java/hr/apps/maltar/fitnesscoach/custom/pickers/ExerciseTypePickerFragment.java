package hr.apps.maltar.fitnesscoach.custom.pickers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import hr.apps.maltar.fitnesscoach.database.entities.ExerciseType;

/**
 * Created by Maltar on 21.3.2018..
 */

public class ExerciseTypePickerFragment extends DialogFragment {
    private static final String LOG_TAG = "ExerciseTypePicker";
    private static final String ARG_SELECTED_INDEX = "arg_selected_index";

    private ArrayList<ExerciseType> exerciseTypes;
    private int selectedIndex;

    public interface OnItemSelectedListener {
        void onItemSelected(ExerciseTypePickerFragment fragment, ExerciseType exercise, int index);
    }

    public ExerciseTypePickerFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_SELECTED_INDEX, selectedIndex);
    }

    public void setExerciseTypes(ArrayList<ExerciseType> exerciseTypes) {
        this.exerciseTypes = exerciseTypes;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pick Exercise Type").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Activity activity = getActivity();
                if (activity instanceof OnItemSelectedListener) {
                    if (0 <= selectedIndex && selectedIndex < exerciseTypes.size()) {
                        ExerciseType exerciseType = exerciseTypes.get(selectedIndex);
                        OnItemSelectedListener listener = (OnItemSelectedListener)activity;
                        listener.onItemSelected(ExerciseTypePickerFragment.this, exerciseType, selectedIndex);
                    }
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(LOG_TAG, "Cancel button clicked");
                if (dialogInterface != null) {
                    dialogInterface.dismiss();
                }
            }
        }).setSingleChoiceItems(getExerciseTypesTitles(), selectedIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index) {
                Log.d(LOG_TAG, "User clicked item with index " + index);
                selectedIndex = index;
            }
        });

        return builder.create();
    }

    private String[] getExerciseTypesTitles() {
        int count = exerciseTypes.size();
        String[] titles = new String[count];
        for (int i = 0; i < count; i++) {
            titles[i] = exerciseTypes.get(i).getName();
        }
        return titles;
    }
}
