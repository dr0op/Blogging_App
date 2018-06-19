package com.dailyraven.tradebrains;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Objects;

public class FeedbackActivity extends AppCompatActivity {

    private EditText feed,mail;
    ProgressDialog loading;
    Button btnSend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_feedback);

        actionbar();

        feed = findViewById(R.id.feed);
        mail=findViewById(R.id.email);
        btnSend = findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //checking if field are filled or not
                String var = feed.getText().toString();
                 String var1=mail.getText().toString();
                if(Objects.equals(var, "")){
                    Toast.makeText(FeedbackActivity.this, "Please Enter Required Information", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Send the feedback
                    uploadfeed(var1,var);
                    Toast.makeText(FeedbackActivity.this, "Sent Successfully!!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });
    }

    public void actionbar(){
        //for back button in Action Bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //for Title in Action Bar
        getSupportActionBar().setTitle("Feedback");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    } private void uploadfeed(final String feedemail, final String feedback) {

        class Uploadfeed extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
           //     loading = ProgressDialog.show(FeedbackActivity.this, "Sending...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
             //   loading.dismiss();
            //    Toast.makeText(FeedbackActivity.this, s, Toast.LENGTH_LONG).show();
                feed.setText("Thank you for your feedback");
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> data = new HashMap<>();

                data.put("email", feedemail);
                data.put("feed", feedback);
                RequestHandler rh = new RequestHandler();
                String result = rh.sendPostRequest("http://stockpkr.in/feed.php", data);

                return result;
            }
        }

        Uploadfeed ui = new Uploadfeed();
        ui.execute();
    }
}
