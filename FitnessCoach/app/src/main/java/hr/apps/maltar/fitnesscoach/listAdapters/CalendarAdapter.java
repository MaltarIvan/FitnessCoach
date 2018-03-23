package hr.apps.maltar.fitnesscoach.listAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hr.apps.maltar.fitnesscoach.R;
import hr.apps.maltar.fitnesscoach.database.entities.Training;
import hr.apps.maltar.fitnesscoach.entities.Day;

/**
 * Created by Maltar on 20.3.2018..
 */

public class CalendarAdapter extends BaseAdapter {
    private int count;
    private int daysDiff;
    private Context context;
    private ArrayList<Day> days;

    public CalendarAdapter(Context context, int count, int daysDiff, ArrayList<Day> days) {
        this.count = count;
        this.daysDiff = daysDiff;
        this.context = context;
        this.days = days;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View calendarGridView = view;
        if (calendarGridView == null) {
            calendarGridView = LayoutInflater.from(context).inflate(R.layout.calendar_day_grid_item, viewGroup, false);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -daysDiff + position);
        Date date = calendar.getTime();

        TextView dateText = calendarGridView.findViewById(R.id.calendar_day_date);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
        String strDate = sdf.format(date);

        dateText.setText(strDate);

        Day day = days.get(position);
        if (day.getTrainings() != null) {
            for (int i = 0; i < 2 && i < day.getTrainings().size(); i++) {
                Training training = day.getTrainings().get(i);
                if (i == 0) {
                    TextView textView = calendarGridView.findViewById(R.id.calendar_day_item_1_text);
                    textView.setText("Id:" + String.valueOf(training.getId()) + " ");
                    TextView timeTextView = calendarGridView.findViewById(R.id.calendar_day_item_1_time);
                    timeTextView.setText(String.valueOf(training.getTimeStart()));
                }
                if (i == 1) {
                    TextView textView = calendarGridView.findViewById(R.id.calendar_day_item_2_text);
                    textView.setText(String.valueOf(training.getId()));
                    TextView timeTextView = calendarGridView.findViewById(R.id.calendar_day_item_2_time);
                    timeTextView.setText(String.valueOf(training.getTimeStart()));
                }
            }
        }
        return calendarGridView;
    }
}
