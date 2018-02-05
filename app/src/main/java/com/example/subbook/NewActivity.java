package com.example.subbook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by heesoopark on 2018-02-04.
 */

public class NewActivity extends AppCompatActivity {

    private EditText edit_name;
    private EditText edit_date;
    private EditText edit_charge;
    private EditText edit_comment;

    private ArrayList<Subscription> subscriptionList;
    private ArrayAdapter<Subscription> adapter;
    //private ListView oldSubscriptionList;

    private static final String FILENAME = "subscription.sav";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_new);
        //oldSubscriptionList = (ListView) findViewById(R.id.mySubscriptionList_main);

        edit_name = (EditText) findViewById(R.id.enter_name);
        edit_date = (EditText) findViewById(R.id.enter_date);
        edit_charge = (EditText) findViewById(R.id.enter_charge);
        edit_comment = (EditText) findViewById(R.id.enter_comment);


        Button saveButton = (Button) findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);

                String name_text = edit_name.getText().toString();
                String date_text = edit_date.getText().toString();
                double charge_text = Double.parseDouble(edit_charge.getText().toString());
                String comment_text = edit_comment.getText().toString();


                Subscription subscription = new NormalSubscription(name_text, date_text, charge_text, comment_text);

                subscriptionList.add(subscription);
                adapter.notifyDataSetChanged();

                saveInFile();


                finish();

            }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        adapter = new ArrayAdapter<Subscription>(this, R.layout.list_item, subscriptionList);

        //oldSubscriptionList.setAdapter(adapter);
    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();


            Type listType = new TypeToken<ArrayList<NormalSubscription>>(){}.getType();
            subscriptionList = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subscriptionList = new ArrayList<Subscription>();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();

            gson.toJson(subscriptionList, out);
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
