package edu.sagarnavgireasu.ilovezappos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    Button searchButton;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView= (SearchView) findViewById(R.id.searchView);
        searchButton=(Button) findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String query=searchView.getQuery().toString();
                if(query!=null) {
                    Intent myIntent=new Intent(getApplicationContext(),ProductActivity.class);
                    myIntent.putExtra("query",query);
                    startActivity(myIntent);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView= (SearchView) findViewById(R.id.searchView);
        searchView.setQuery("",false);
        searchView.setIconified(true);

    }
}
