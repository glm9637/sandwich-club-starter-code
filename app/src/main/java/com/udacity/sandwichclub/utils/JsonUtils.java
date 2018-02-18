package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich result = null;
        try {
            JSONObject jsBase = new JSONObject(json);
            JSONObject jsName = jsBase.getJSONObject("name");
            JSONArray jsAlias = jsName.getJSONArray("alsoKnownAs");
            JSONArray jsIngredient = jsBase.getJSONArray("ingredients");

            String mainName = jsName.getString("mainName");
            String placeOfOrigin = jsBase.getString("placeOfOrigin");
            String description = jsBase.getString("description");
            String image = jsBase.getString("image");

            ArrayList<String> aliasList = new ArrayList<>();
            for (int i = 0; i < jsAlias.length(); i++) {
                aliasList.add(jsAlias.getString(i));
            }

            ArrayList<String> ingredientList = new ArrayList<>();
            for (int i = 0; i < jsIngredient.length(); i++) {
                ingredientList.add(jsIngredient.getString(i));
            }

            result = new Sandwich(mainName, aliasList, placeOfOrigin, description, image, ingredientList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
