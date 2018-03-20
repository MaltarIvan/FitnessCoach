package hr.apps.maltar.fitnesscoach.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.parceler.Parcels;

import hr.apps.maltar.fitnesscoach.R;
import hr.apps.maltar.fitnesscoach.entities.Day;
import hr.apps.maltar.fitnesscoach.params.IntentExtrasParams;

public class DayActivity extends AppCompatActivity {
    private Button addTrainingButton;
    private TextView dateTextView;

    private Day day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Intent intent = getIntent();
        day = Parcels.unwrap(intent.getParcelableExtra(IntentExtrasParams.DAY_EXTRA));
        if (day == null) {

        }

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
                intent.putExtra(IntentExtrasParams.DATE_EXTRA, day.getDate().toString());
                startActivity(intent);
            }
        });
    }
}
