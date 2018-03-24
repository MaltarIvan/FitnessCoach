package hr.apps.maltar.fitnesscoach.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.parceler.Parcels;

import hr.apps.maltar.fitnesscoach.R;
import hr.apps.maltar.fitnesscoach.database.entities.Training;
import hr.apps.maltar.fitnesscoach.entities.Day;
import hr.apps.maltar.fitnesscoach.listAdapters.TrainingAdapter;
import hr.apps.maltar.fitnesscoach.params.IntentExtrasParams;

public class DayActivity extends AppCompatActivity {
    private static final int ADD_TRAINING_REQUEST = 1;

    private TrainingAdapter trainingAdapter;

    private Button addTrainingButton;
    private TextView dateTextView;
    private ListView trainingListView;

    private Day day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Intent intent = getIntent();
        day = Parcels.unwrap(intent.getParcelableExtra(IntentExtrasParams.DAY_EXTRA));

        trainingAdapter = new TrainingAdapter(getApplicationContext(), day.getTrainings());

        setViews();
    }

    private void setViews() {
        dateTextView = findViewById(R.id.day_date_text);
        dateTextView.setText(day.getDate().toString());

        addTrainingButton = findViewById(R.id.add_training_button);
        addTrainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTrainingActivity.class);
                intent.putExtra(IntentExtrasParams.DATE_EXTRA, day.getDate().getTime());
                startActivityForResult(intent, ADD_TRAINING_REQUEST);
            }
        });

        trainingListView = findViewById(R.id.day_training_list);
        trainingListView.setAdapter(trainingAdapter);
        trainingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TRAINING_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Training training = Parcels.unwrap(data.getParcelableExtra(IntentExtrasParams.TRAINING_EXTRA));
                trainingAdapter.add(training);
                trainingAdapter.notifyDataSetChanged();
            }
        }
    }
}
