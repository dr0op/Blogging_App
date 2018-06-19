package com.dailyraven.tradebrains;
/**
 * Created by Shubham.
 */
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "random";
    private RecyclerView recyclerView;
    private BlogAdapter adapter;
    private List<blogData>blogList;
    private int requestCount = 1;
    private FirebaseAuth auth;

    //Volley Request Queue
    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth=FirebaseAuth.getInstance();
        FirebaseUser a=FirebaseAuth.getInstance().getCurrentUser();
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        requestQueue = Volley.newRequestQueue(this);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set the header of the navigation bar
        View hView =  navigationView.getHeaderView(0);
        TextView profileid= hView.findViewById(R.id.profile_id);
        TextView profileName= hView.findViewById(R.id.profile_name);
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        boolean isNotSkippedUser = wmbPreference.getBoolean("SKIPLOGIN", true);
        if (isNotSkippedUser)
        {
            profileid.setText(a.getEmail());
            profileName.setText(a.getDisplayName());
        }
        else{
            profileid.setText("Login");
            profileid.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,FirstActivity.class));
                }
            });
        }

        getFunction();
    }
    public void getFunction(){
        blogList=new ArrayList<>();
        recyclerView = findViewById(R.id.recycleView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new BlogAdapter(this,blogList);
        getData();
        recyclerView.setAdapter(adapter);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
//Ifscrolled at last then
                    if (isLastItemDisplaying(recyclerView)) {
                        // q=string;
//Calling the method getdata again
                        getData();
                    }
                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                recyclerView.setOnScrollChangeListener( new RecyclerView.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                        //Ifscrolled at last then
                        if (isLastItemDisplaying(recyclerView)) {
                            //Calling the method getdata again
                            //   q=string;
                            getData();
                        }
                    }
                });
            }
        }
    }
    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1;
        }
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id ==R.id.homes) {
            // Handle the camera action
        } else if (id == R.id.bookmarks) {
            Intent i = new Intent(this,BookmarksActivity.class);
            startActivity(i);

        } else if (id ==R.id.reqtopic) {
            Intent i = new Intent(this,RequestActivity.class);
            startActivity(i);

        } else if (id ==R.id.investing_quotes) {
            Toast.makeText(MainActivity.this,"Under Construction Will be Updated Soon!!",Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_share) {
            final String appPackageName = getPackageName();
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "TradeBrains");
                String sAux = "Trade brains is a financial educational blog focused to teach stock market investing and personal finance\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id="+appPackageName;
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        } else if (id == R.id.feedback) {
            startActivity(new Intent(MainActivity.this,FeedbackActivity.class));
        }
        else if (id == R.id.rateus) {
            rateApp();
        }
        else if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this,AboutActivity.class));
        }
        else if(id== R.id.logout){
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(MainActivity.this,"Log out Sucessfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this,FirstActivity.class));
            finish();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //This method will get data from the web api
    private void getData() {
        //Adding the method to the queue by calling the method getDataFromServer
        requestQueue.add(getDataFromServer(requestCount));
        //Incrementing the request counter
        requestCount++;
    }
    private JsonArrayRequest getDataFromServer(int requestCount) {
        //Initializing ProgressBar
        final ProgressBar progressBar = findViewById(R.id.progressBar1);
        //Displaying Progressbar
        progressBar.setVisibility(View.VISIBLE);
        setProgressBarIndeterminateVisibility(true);
        //JsonArrayRequest of volley
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.DATA_URL + String.valueOf(requestCount),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Calling method parseData to parse the json response
                        parseData(response);
                        //Hiding the progressbar
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        //If an error occurs that means end of the list has reached
                        Toast.makeText(MainActivity.this, "No More Items Available", Toast.LENGTH_SHORT).show();
                    }
                });
        //Returning the request
        return jsonArrayRequest;
    }
    private void parseData(JSONArray response) {
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);

                blogData blog = new blogData();
                blog.setBlogId(jsonObject.getInt("id"));
                blog.setBlogTitle(jsonObject.getJSONObject("title").getString("rendered"));
                blog.setBlogtime(jsonObject.getString("date"));
                blog.setBlogContent(jsonObject.getJSONObject("content").getString("rendered"));
                blog.setBlogImage(jsonObject.getJSONObject("better_featured_image").getString("source_url"));
//add for image
                blogList.add(blog);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }
    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

}
