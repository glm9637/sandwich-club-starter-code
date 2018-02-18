package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich result = new Sandwich();
        try {
            JSONObject jsBase= new JSONObject(json);
            JSONObject jsName = jsBase.getJSONObject("name");
            JSONArray jsAlias = jsName.getJSONArray("alsoKnownAs");
            JSONArray jsIngredient = jsBase.getJSONArray("ingredients");
            result.setMainName(jsName.getString("mainName"));
            ArrayList<String> aliasList = new ArrayList<>();
            for(int i = 0;i<jsAlias.length();i++){
                aliasList.add(jsAlias.getString(i));
            }
            result.setAlsoKnownAs(aliasList);
            result.setPlaceOfOrigin(jsBase.getString("placeOfOrigin"));
            result.setDescription(jsBase.getString("description"));
            result.setImage(jsBase.getString("image"));
            ArrayList<String> ingredientList = new ArrayList<>();
            for(int i=0;i<jsIngredient.length();i++){
                ingredientList.add(jsIngredient.getString(i));
            }
            result.setIngredients(ingredientList);
        } catch (JSONException e) {
            return null;
        }

        return result;
    }
}
