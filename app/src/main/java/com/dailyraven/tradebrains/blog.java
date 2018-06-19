package com.dailyraven.tradebrains;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/*
 * Created by Shubham.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.Map;

public class blog extends AppCompatActivity {
    TextView title;
    WebView content;
    ImageView imageView;
    ProgressDialog progressDialog;
    // Gson gson;
    Map<String, Object> mapPost;
    Map<String, Object> mapTitle;
    Map<String, Object> mapContent;
    Map<String,Object> mapImage;
    Button share;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String title1=getIntent().getExtras().getString("title");
        final String content1=getIntent().getExtras().getString("content");
        final String Image=getIntent().getExtras().getString("image");
        final String id1 = getIntent().getExtras().getString("id");
        setTitle(title1);
        setContentView(R.layout.activity_blog);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        share= findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName();
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "TradeBrains");
                    String sAux = content1.replaceAll("\\<.*?\\>","");
                    sAux  =sAux+"\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id="+appPackageName;
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch(Exception e) {
                    e.toString();
                }
            }
        });

        content = findViewById(R.id.content1);
        imageView= findViewById(R.id.expandedImage);
        content.getSettings().setBuiltInZoomControls(true);
        content.loadData(content1,"text/html","UTF-8");
        Glide.with(blog.this).load(Image).into(imageView);
     /*    FloatingActionButton fab = findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}

