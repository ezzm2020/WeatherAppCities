package com.example.weatherappcities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.weatherappcities.adapter.RecAdapters;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SEarchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    List<String> citylisy;
    RecAdapters recAdapters;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_search);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        citylisy = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.reccities);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        citylisy = Arrays.asList(getResources().getStringArray(R.array.cityslist));
         recAdapters = new RecAdapters(citylisy,getBaseContext());
        recyclerView.setAdapter(recAdapters);

    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.toolbar, menu);
////        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        MenuItem menuItem = menu.findItem(R.id.srechs);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String inputst=newText.toLowerCase();
        List<String>stringList=new ArrayList<>();
        for (String text :citylisy)
        {
            if(text.toLowerCase().contains(inputst))
                stringList.add(text);
        }
        recAdapters.updateList(stringList);
        return true;
    }
}
