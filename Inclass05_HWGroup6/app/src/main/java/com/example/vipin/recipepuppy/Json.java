package com.example.vipin.recipepuppy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HP on 9/25/2017.
 */

public class Json {
    static public class PersonJSONParser{
        static ArrayList<Person> parsePersons(String in) throws JSONException {
            ArrayList<Person> personList=new ArrayList<Person>();
            JSONObject root= new JSONObject(in);
            JSONArray personJsonArray=root.getJSONArray("results");
            for(int i=0;i<personJsonArray.length();i++){
                JSONObject personsJSONObject=personJsonArray.getJSONObject(i);
                Person person=new Person();
                person.setTitle(personsJSONObject.getString("title"));
                person.setIngredients(personsJSONObject.getString("ingredients"));
                person.setImage(personsJSONObject.getString("thumbnail"));
                person.setUrl(personsJSONObject.getString("href"));
                person.setIndex(i);
                personList.add(person);

            }
            return personList;
        }
    }
}