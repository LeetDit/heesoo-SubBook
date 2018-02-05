package com.example.subbook;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.aware.SubscribeConfig;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Subscription> adapter;
    private ListView newSubscriptionList;
    private ArrayList<Subscription> subscriptionList_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newSubscriptionList = (ListView) findViewById(R.id.mySubscriptionList_done);
        adapter = new ArrayAdapter<Subscription>(this, android.R.layout.simple_list_item_single_choice, subscriptionList_main);


        Button newActivityButton = (Button) findViewById(R.id.newActivity);
        Button deleteButton = (Button) findViewById(R.id.delete);
        Button viewButton = (Button) findViewById(R.id.view);

        newActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewActivity.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = newSubscriptionList.getCheckedItemPosition();
                if (pos != ListView.INVALID_POSITION) {
                    subscriptionList_main.remove(pos);
                    newSubscriptionList.clearChoices();
                    adapter.notifyDataSetChanged();
                    saveInFile();
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = newSubscriptionList.getCheckedItemPosition();
                if (pos != ListView.INVALID_POSITION) {
                    Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
                    startActivity(intent);
                }
            }
        });



        Button clearButton = (Button) findViewById(R.id.clear);

        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                subscriptionList_main.clear();
                adapter.notifyDataSetChanged();
                saveInFile();
                finish();
                startActivity(getIntent());
            }
        });

    }


    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();

        newSubscriptionList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //newSubscriptionList.setAdapter(adapter);
        registerForContextMenu(newSubscriptionList);
        newSubscriptionList.setClickable(true);



        final ArrayAdapter<Subscription> arrayAdapter = new ArrayAdapter<Subscription>(this,
                android.R.layout.simple_list_item_multiple_choice,
                subscriptionList_main) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView,parent);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = 300;
                view.setLayoutParams(params);
                return view;
            }
        };
        newSubscriptionList.setAdapter(arrayAdapter);

    }

    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput("subscription.sav");
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();


            Type listType = new TypeToken<ArrayList<NormalSubscription>>(){}.getType();
            subscriptionList_main = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            subscriptionList_main = new ArrayList<Subscription>();
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput("subscription.sav",
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();

            gson.toJson(subscriptionList_main, out);
            out.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
