package com.example.vipin.recipepuppy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ScrollView scrollView;
    String dish;
    final int[] i = {0};
    int count=0;
    EditText disheditText;
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<String> ingredientlist = new ArrayList<>(100);
        scrollView = (ScrollView) findViewById(R.id.scv);
        generate_views(i[0],ingredientlist);
        count++;
        disheditText = (EditText) findViewById(R.id.dish_ed);

    }
    @TargetApi(Build.VERSION_CODES.M)
    private void generate_views(final int i, final ArrayList<String> ingredientlist) {

        LinearLayout layout= (LinearLayout) findViewById(R.id.llv);
        final LinearLayout linearLayout = new LinearLayout(MainActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT , LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setLayoutParams(params);
        layout.addView(linearLayout);
        final EditText editText = new EditText(MainActivity.this);
        editText.setSingleLine(true);
        LinearLayout.LayoutParams params1= new LinearLayout.LayoutParams(700 , LinearLayout.LayoutParams.WRAP_CONTENT);
        params1.setMargins(0,0,0,20);
        editText.setLayoutParams(params1);
        editText.setTextSize(18);
        editText.setPadding(50,0,0,30);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        linearLayout.addView(editText);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT , LinearLayout.LayoutParams.WRAP_CONTENT);

        params2.setMargins(60,0,0,20);
        final FloatingActionButton floatingActionButton = new FloatingActionButton(MainActivity.this);
        floatingActionButton.setForeground(getResources().getDrawable(R.drawable.add));
        floatingActionButton.setTag("add");
        floatingActionButton.setLayoutParams(params2);
        floatingActionButton.setSize(FloatingActionButton.SIZE_MINI);
        floatingActionButton.setId(i);
        final int[] j = {i};

        Button searchbtn = (Button) findViewById(R.id.searchbtn);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count == 5) {
                   ingredientlist.add(editText.getText().toString());
                }
                if (ingredientlist != null) {
                    for (int j = 0; j < ingredientlist.size(); j++) {
                        if (!ingredientlist.get(j).matches("") && !disheditText.getText().toString().matches("")) {
                            Intent intent =new Intent(MainActivity.this,Recipes.class);
                            dish=disheditText.getText().toString();
                            String url = create_base_url(ingredientlist,dish);
                            intent.putStringArrayListExtra("list",ingredientlist);
                            intent.putExtra("dish",dish);
                            intent.putExtra("url",url);
                            startActivityForResult(intent,1);

                        }
                    }
                }

             }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(floatingActionButton.getTag().toString().matches("add") && !(editText.getText().toString().matches(""))){
                    if(count<5){
                        floatingActionButton.setForeground(getResources().getDrawable(R.drawable.remove));
                        floatingActionButton.setTag("remove");
                        ingredientlist.add(editText.getText().toString());
                        editText.setFocusable(false);
                        editText.setClickable(false);
                        j[0]++;
                        generate_views(j[0], ingredientlist);
                        count++;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"You can only add 5 ingredients",Toast.LENGTH_SHORT).show();
                    }
                    }
                else if(floatingActionButton.getTag().toString().matches("remove")){
                    floatingActionButton.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                    ingredientlist.remove(editText.getText().toString());
                    count--;
                }

            }
        });

        linearLayout.addView(floatingActionButton);
    }

    private String create_base_url(ArrayList<String> ingredientlist, String dish) {
        String baseurl="http://www.recipepuppy.com/";
        String encoded_url = null;
        for(int i=0 ; i<ingredientlist.size(); i++){
            if(i==0){
                encoded_url= baseurl+ "/api/?i="+ingredientlist.get(i);

            }
            else {
                encoded_url =encoded_url +"," +ingredientlist.get(i);
            }

            if(i==4){
                encoded_url = encoded_url + "&q=" + dish;
            }
        }
        return encoded_url;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode==2){
            Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
        }
    }
}
