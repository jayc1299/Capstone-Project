package com.nanodegree.newyorktravel.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nanodegree.newyorktravel.R;
import com.nanodegree.newyorktravel.holders.Attraction;
import com.nanodegree.newyorktravel.holders.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityAddReview extends AppCompatActivity {

    private static final String TAG = ActivityAddReview.class.getSimpleName();

    private TextInputEditText title;
    private TextInputEditText content;
    private TextInputLayout titleLayout;
    private TextInputLayout contentLayout;
    private Spinner attractionSpinner;
    private RatingBar starRating;

    private FirebaseDatabase database;
    private ArrayAdapter<String> dataAdapter;
    private ArrayList<Attraction> attractions;
    private Attraction selectedAttraction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        titleLayout = findViewById(R.id.activity_add_review_title_layout);
        contentLayout = findViewById(R.id.activity_add_review_content_layout);
        title = findViewById(R.id.activity_add_review_title);
        content = findViewById(R.id.activity_add_review_content);
        attractionSpinner = findViewById(R.id.fragment_reviews_spinner);
        starRating = findViewById(R.id.add_review_star_rating);

        database = FirebaseDatabase.getInstance();

        setupAttractionSpinner();
        setupUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupUI() {
        findViewById(R.id.activity_add_review_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    submit();
                }
            }
        });

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                titleLayout.setError("");
            }
        });

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                contentLayout.setError("");
            }
        });
    }

    private void setupAttractionSpinner() {
        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attractionSpinner.setAdapter(dataAdapter);

        DatabaseReference attractionRef = database.getReference("attractions");

        attractionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> simpleAttractions = new ArrayList<>();
                attractions = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Attraction attraction = postSnapshot.getValue(Attraction.class);
                    if (attraction != null) {
                        attraction.setId(postSnapshot.getKey());
                        simpleAttractions.add(attraction.getName());
                        attractions.add(attraction);
                    }
                }

                //Reload adapter data
                dataAdapter.clear();
                dataAdapter.addAll(simpleAttractions);
                dataAdapter.notifyDataSetChanged();
                //Set the selected attraction to first attraction in list.
                if (attractions.size() > 0) {
                    selectedAttraction = attractions.get(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "onCancelled: ", databaseError.toException());
            }
        });

        //Set selected attraction
        attractionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                selectedAttraction = attractions.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Do nothing
            }
        });
    }

    /**
     * validate user input
     *
     * @return true if valid, false if not.
     */
    private boolean validate() {
        boolean isValid = true;

        if (TextUtils.isEmpty(title.getText().toString().trim())) {
            isValid = false;
            titleLayout.setError(getString(R.string.review_no_title));
        }

        if (TextUtils.isEmpty(content.getText().toString().trim())) {
            isValid = false;
            contentLayout.setError(getString(R.string.review_no_content));
        }

        if (selectedAttraction == null) {
            isValid = false;
        }

        return isValid;
    }

    private void submit() {
        String titleText = title.getText().toString().trim();
        String titleContent = content.getText().toString().trim();

        DatabaseReference reviewRef = database.getReference("reviews");

        Review review = new Review();
        review.setTitle(titleText);
        review.setContent(titleContent);
        review.setAttractionId(selectedAttraction.getId());
        review.setReviewScore(starRating.getRating());

        String key = reviewRef.push().getKey();
        Map<String, Object> postValues = review.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + key, postValues);
        reviewRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    title.setText("");
                    content.setText("");
                    starRating.setRating(0);

                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityAddReview.this)
                            .setTitle(getString(R.string.review_add_success_title))
                            .setMessage(getString(R.string.review_add_success_content))
                            .setPositiveButton(getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityAddReview.this.finish();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
}