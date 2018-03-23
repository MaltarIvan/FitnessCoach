package hr.apps.maltar.fitnesscoach.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import hr.apps.maltar.fitnesscoach.R;
import hr.apps.maltar.fitnesscoach.database.entities.ExerciseType;
import hr.apps.maltar.fitnesscoach.database.entities.Training;
import hr.apps.maltar.fitnesscoach.entities.Day;
import hr.apps.maltar.fitnesscoach.listAdapters.CalendarAdapter;
import hr.apps.maltar.fitnesscoach.params.IntentExtrasParams;
import hr.apps.maltar.fitnesscoach.params.IntentFilterParams;
import hr.apps.maltar.fitnesscoach.params.SharedPreferencesParams;
import hr.apps.maltar.fitnesscoach.services.DataIntentService;

/**
 * Created by Maltar on 16.3.2018..
 */

public class MainActivity extends AppCompatActivity {
    private Button addExerciseTypeButton;
    private Button getAllExerciseTypesButton;
    private GridView calendarGridView;

    private BroadcastReceiver broadcastReceiver;

    private ArrayList<Day> daysList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerBroadcastManagerReceiver();

        setViews();
    }

    private void registerBroadcastManagerReceiver() {
        broadcastReceiver = new DataBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(IntentFilterParams.LOAD_ALL_TRAININGS_ACTION);
        intentFilter.addAction(IntentFilterParams.LOAD_ALL_EXERCISE_TYPES_ACTION);
        intentFilter.addAction(IntentFilterParams.ADD_TRAINING_ACTION);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
    }

    private void setViews() {
        getAllExerciseTypesButton = findViewById(R.id.get_all_exercise_types);
        getAllExerciseTypesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DataIntentService.class);
                intent.putExtra(IntentExtrasParams.ACTION_EXTRA, IntentFilterParams.LOAD_ALL_EXERCISE_TYPES_ACTION);
                startService(intent);
            }
        });

        addExerciseTypeButton = findViewById(R.id.add_exercise_type);
        addExerciseTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddExerciseTypeActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = new Intent(this, DataIntentService.class);
        intent.putExtra(IntentExtrasParams.ACTION_EXTRA, IntentFilterParams.LOAD_ALL_TRAININGS_ACTION);
        startService(intent);
    }

    private void setCalendar(final ArrayList<Training> trainings) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String firstDate = sharedPreferences.getString(SharedPreferencesParams.FIRST_DATE_PREFERENCE, "");
        if (firstDate == "") {
            Date date = new Date();
            firstDate = date.toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(SharedPreferencesParams.FIRST_DATE_PREFERENCE, firstDate);
            editor.commit();
        } else {
            Toast.makeText(this, firstDate, Toast.LENGTH_SHORT).show();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy");
        Date date = null;
        try {
            date = sdf.parse(firstDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }

        Date today = new Date();
        long diff = today.getTime() - date.getTime();
        long daysDiff = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
        daysDiff = Math.min(Math.max(daysDiff, 1), 365);
        int count = (int) daysDiff + 100;

        daysList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, -(int) daysDiff + i);

            Day day = new Day(calendar.getTime(), new ArrayList<Training>());

            for (Training training : trainings) {
                Calendar trainingCalendar = Calendar.getInstance();
                trainingCalendar.setTime(training.getDate());
                int calendarDay = calendar.get(Calendar.DAY_OF_MONTH);
                int calendarMonth = calendar.get(Calendar.MONTH);
                int calendarYear = calendar.get(Calendar.YEAR);
                int trainingDay = trainingCalendar.get(Calendar.DAY_OF_MONTH);
                int trainingMonth = trainingCalendar.get(Calendar.MONTH);
                int trainingYear = trainingCalendar.get(Calendar.YEAR);
                if (calendarDay == trainingDay
                        && calendarMonth == trainingMonth
                        && calendarYear == trainingYear) {
                    day.getTrainings().add(training);
                }
            }

            daysList.add(day);
        }

        calendarGridView = findViewById(R.id.calendar_grid_view);
        calendarGridView.setAdapter(new CalendarAdapter(this, count, (int) daysDiff, daysList));
        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Day day = (Day) adapterView.getItemAtPosition(position);
                Day day = daysList.get(position);
                Intent intent = new Intent(getApplicationContext(), DayActivity.class);
                intent.putExtra(IntentExtrasParams.DAY_EXTRA, Parcels.wrap(day));
                startActivity(intent);
            }
        });

        calendarGridView.smoothScrollToPosition((int) daysDiff + 8);
    }

    private class DataBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (IntentFilterParams.LOAD_ALL_TRAININGS_ACTION.equals(intent.getAction())) {
                ArrayList<Training> trainings = new ArrayList<>();
                ArrayList<Parcelable> parcelables = intent.getParcelableArrayListExtra(IntentExtrasParams.TRAINING_EXTRA);
                for (Parcelable parcelable : parcelables) {
                    trainings.add((Training) Parcels.unwrap(parcelable));
                }
                setCalendar(trainings);
            }
            if (IntentFilterParams.LOAD_ALL_EXERCISE_TYPES_ACTION.equals(intent.getAction())) {
                ArrayList<ExerciseType> exerciseTypes = new ArrayList<>();
                ArrayList<Parcelable> parcelables = intent.getParcelableArrayListExtra(IntentExtrasParams.EXERCISE_TYPE_EXTRA);
                for (Parcelable parcelable : parcelables) {
                    exerciseTypes.add((ExerciseType) Parcels.unwrap(parcelable));
                }
            }
            if (IntentFilterParams.ADD_TRAINING_ACTION.equals(intent.getAction())) {
                Training training = Parcels.unwrap(intent.getParcelableExtra(IntentExtrasParams.TRAINING_EXTRA));
                for (Day day : daysList) {
                    Calendar dayCalendar = Calendar.getInstance();
                    dayCalendar.setTime(day.getDate());

                    Calendar trainingCalendar = Calendar.getInstance();
                    trainingCalendar.setTime(training.getDate());

                    if (dayCalendar.get(Calendar.DAY_OF_MONTH) == trainingCalendar.get(Calendar.DAY_OF_MONTH)
                            && dayCalendar.get(Calendar.MONTH) == trainingCalendar.get(Calendar.MONTH)
                            && dayCalendar.get(Calendar.YEAR) == trainingCalendar.get(Calendar.YEAR)) {
                        day.getTrainings().add(training);
                        ((CalendarAdapter) calendarGridView.getAdapter()).notifyDataSetChanged();
                    }
                }
            }
        }
    }
}