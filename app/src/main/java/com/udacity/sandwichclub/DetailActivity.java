package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        Sandwich sandwich = loadSandwich(position);

        if (sandwich == null) {
            closeOnError();
            return;
        }

        setupToolbar(sandwich.getMainName());

        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.no_image)
                .into(ingredientsIv);

        populateUI(sandwich);

    }

    private Sandwich loadSandwich(int position) {
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        return JsonUtils.parseSandwichJson(json);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Name
        TextView description = findViewById(R.id.value_description);
        description.setText(sandwich.getDescription());

        //origin
        TextView origin = findViewById(R.id.value_origin);
        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            origin.setText(R.string.unknown_origin);
        } else {
            origin.setText(sandwich.getPlaceOfOrigin());
        }

        //ingredients
        TextView ingredients = findViewById(R.id.value_ingredients);
        String ingredientString = joinString(sandwich.getIngredients());
        if (ingredientString.length() > 1)
            ingredients.setText(ingredientString);
        else {
            findViewById(R.id.label_ingredients).setVisibility(View.GONE);
            ingredients.setVisibility(View.GONE);
        }

        //alias
        TextView alsoKnownAs = findViewById(R.id.value_alias);
        String aliasString = joinString(sandwich.getAlsoKnownAs());
        if (aliasString.length() > 1)
            alsoKnownAs.setText(aliasString);
        else {
            findViewById(R.id.label_alias).setVisibility(View.GONE);
            alsoKnownAs.setVisibility(View.GONE);
        }
    }

    private String joinString(List<String> list) {
        String separator = getString(R.string.detail_list_separator);
        StringBuilder joinedString = new StringBuilder();
        if (list == null || list.size() == 0) {
            return "";
        }
        for (String alias : list) {
            joinedString.append(alias).append(separator);
        }
        return joinedString.substring(0, joinedString.length() - separator.length());
    }

    private void setupToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(title);
    }
}
