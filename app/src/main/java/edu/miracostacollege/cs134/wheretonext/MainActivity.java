package edu.miracostacollege.cs134.wheretonext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import edu.miracostacollege.cs134.wheretonext.model.College;
import edu.miracostacollege.cs134.wheretonext.model.JSONLoader;

public class MainActivity extends AppCompatActivity {

    // private DBHelper db;
    private List<College> collegesList;
    private CollegeListAdapter collegesListAdapter;
    private ListView collegesListView;

    private EditText nameEditText;
    private EditText populationEditText;
    private EditText tuitionEditText;
    private RatingBar collegeRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        populationEditText = findViewById(R.id.populationEditText);
        tuitionEditText = findViewById(R.id.tuitionEditText);
        collegeRatingBar = findViewById(R.id.collegeRatingBar);

        //this.deleteDatabase(DBHelper.DATABASE_NAME);
        // db = new DBHelper(this);

        // DONE: Comment this section out once the colleges below have been added to the database,
        // DONE: so they are not added multiple times (prevent duplicate entries)
/*
        db.addCollege(new College("UC Berkeley", 42082, 14068, 4.7, "ucb.png"));
        db.addCollege(new College("UC Irvine", 31551, 15026.47, 4.3, "uci.png"));
        db.addCollege(new College("UC Los Angeles", 43301, 25308, 4.5, "ucla.png"));
        db.addCollege(new College("UC San Diego", 33735, 20212, 4.4, "ucsd.png"));
        db.addCollege(new College("CSU Fullerton", 38948, 6437, 4.5, "csuf.png"));
        db.addCollege(new College("CSU Long Beach", 37430, 6452, 4.4, "csulb.png"));
        */

        // DONE:  Fill the collegesList with all Colleges from the database
        try {
            collegesList = JSONLoader.loadJSONFromAsset(this);
        } catch (IOException e) {
            Log.e("WhereToNext", "Error loading from JSON.", e);
        }

        collegesListView = findViewById(R.id.collegeListView);
        // DONE:  Connect the list adapter with the list
        collegesListAdapter = new CollegeListAdapter(this, R.layout.college_list_item, collegesList);
        // DONE:  Set the list view to use the list adapter
        collegesListView.setAdapter(collegesListAdapter);
    }

    public void viewCollegeDetails(View view) {

        // DONE: Implement the view college details using an Intent
        College clickedCollege = (College) view.getTag();
        Intent intent = new Intent(this, CollegeDetailsActivity.class);

        intent.putExtra("Name", clickedCollege.getName());
        intent.putExtra("Population", clickedCollege.getPopulation());
        intent.putExtra("Tuition", (float) clickedCollege.getTuition());
        intent.putExtra("Rating", (float) clickedCollege.getRating());
        intent.putExtra("ImageName", clickedCollege.getImageName());

        startActivity(intent);

    }

    public void addCollege(View view) {

        // TODO: Implement the code for when the user clicks on the addCollegeButton
        String collegeName = nameEditText.getText().toString();
        String collegePopulation = populationEditText.getText().toString();
        String collegeTuition = tuitionEditText.getText().toString();
        float collegeRating = collegeRatingBar.getRating();

        if(collegeName.isEmpty()
                || collegePopulation.isEmpty()
                || collegeTuition.isEmpty())
        {
            Toast.makeText(this, "Please enter all college information.",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            College newCollege = new College(collegeName,
                    Integer.parseInt(collegePopulation),
                    Float.parseFloat(collegeTuition),
                    collegeRating);

            collegesList.add(newCollege);
            collegesListAdapter.notifyDataSetChanged();

            nameEditText.setText("");
            populationEditText.setText("");
            tuitionEditText.setText("");
            collegeRatingBar.setRating(0.0f);
        }
    }

}
