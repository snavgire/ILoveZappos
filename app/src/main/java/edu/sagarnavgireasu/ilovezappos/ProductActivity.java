package edu.sagarnavgireasu.ilovezappos;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductActivity extends AppCompatActivity
{
    TextView textView;
    Menu menu;
    Adapter adapter;
    String imgURL;
    RecyclerView recyclerView;
    public static final String BASE_URL = "https://api.zappos.com/";
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_product);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView= (TextView) findViewById(R.id.textViewSearch);
        String query=getIntent().getStringExtra("query");

        adapter=new Adapter();
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(this.adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        ZAPI ZAPIinstance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ZAPI.class);

        ZAPIinstance.getResponse(query,"b743e26728e16b81da139182bb2094357c31d331").enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                if(product.getTotalResultCount().contentEquals("0")){
                    textView.setText("Sorry! No results.");
                    textView.setTextColor(Color.RED);
                    new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            finish();
                        }
                    }.start();

                }
                else{
                    Log.d("Response", product.getResults().get(0).getBrandName());
                    adapter.addItem(product.getResults().get(0));
                }
            }



            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            boolean flag = false;

            @Override
            public void onClick(View view) {

                if(flag == false){
                    flag = true;
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.done_final));
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.menu_cart_loaded_final));
                    Snackbar.make(view, "The product has been added to cart!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else{
                    flag = false;
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.cart4));
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.menu_cart_grey));
                    Snackbar.make(view, "The product has been removed!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

            }
        });
    }


    public void gotoweb(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(v.getTag()+""));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        this.menu=menu;
        return super.onCreateOptionsMenu(menu);
    }
}
