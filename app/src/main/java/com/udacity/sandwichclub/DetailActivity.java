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

import java.util.ArrayList;
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
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }


        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        populateUI(sandwich);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(sandwich.getMainName());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView description = findViewById(R.id.value_description);
        description.setText(sandwich.getDescription());

        TextView origin = findViewById(R.id.value_origin);
        origin.setText(sandwich.getPlaceOfOrigin());

        TextView ingredients = findViewById(R.id.value_ingredients);
        String ingredientString = joinString(sandwich.getIngredients(),", ");
        ingredients.setText(ingredientString);

        TextView alsoKnownAs = findViewById(R.id.value_alias);
        String aliasString = joinString(sandwich.getAlsoKnownAs(),", ");
        if(aliasString.length()>1)
            alsoKnownAs.setText(aliasString.substring(0,aliasString.length()-2));
        else {
            findViewById(R.id.label_alias).setVisibility(View.GONE);
            alsoKnownAs.setVisibility(View.GONE);
        }

    }

    private String joinString(List<String> list, String seperator){
        StringBuilder joinedString = new StringBuilder();
        if(list == null || list.size()==0){
            return "";
        }
        for (String alias : list) {
            joinedString.append(alias).append(seperator);
        }
        return joinedString.substring(0,joinedString.length()-seperator.length());
    }
}
