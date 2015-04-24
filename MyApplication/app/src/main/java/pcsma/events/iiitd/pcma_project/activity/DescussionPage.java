package pcsma.events.iiitd.pcma_project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.adapter.DescussionPageAdapter;
import pcsma.events.iiitd.pcma_project.populatingClass.DescussionsList;
import pcsma.events.iiitd.pcma_project.populatingClass.Discussion;
import pcsma.events.iiitd.pcma_project.populatingClass.Events;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DescussionPage extends ActionBarActivity {


    ImageView ivImage;
    EditText etDescussion;
    Button btSubmit;
    String finalText="";


    SharedPreferences pref;
    SharedPreferences.Editor edit;


    String  stvTitle, stvDate, stvTime, stvDescription,surl;


    PublicEvents publicEvents;
    TextView tvTitle, tvDescription, tvDate, tvTime;

    ListView  lvDescussion;

    String event_id="123", user_id;

    String user_name;


    EventsList currentEvent;
    ArrayList<Discussion> descussions=new ArrayList<Discussion>();
    DescussionPageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descussion_page);


        pref = DescussionPage.this.getSharedPreferences("IIITD_Events", 0);
        edit = pref.edit();
        user_id=pref.getString("FB_USER_ID", "user_1");
        Intent intent = getIntent();
        event_id = intent.getStringExtra("event_id");



        stvTitle= intent.getStringExtra("tvTitle");
        stvDate= intent.getStringExtra("tvDate");
        stvTime= intent.getStringExtra("tvTime");
        stvDescription=  intent.getStringExtra("tvDescription");
        surl=  intent.getStringExtra("cover_url");
        ivImage=(ImageView) findViewById(R.id.descussion_page_iv_image);

        Picasso.with(getApplicationContext())
                .load(surl)
                .placeholder(R.drawable.com_facebook_button_like) // optional
                .error(R.drawable.com_facebook_button_like)         // optional
                .into(ivImage);

        //get Extra


        etDescussion=(EditText) findViewById(R.id.descussion_page_et_descussion);
        btSubmit=(Button) findViewById(R.id.descussion_page_button);

        lvDescussion=(ListView) findViewById(R.id.descussion_page_lv);

        populateData();

        adapter= new DescussionPageAdapter(descussions,this);

        lvDescussion.setAdapter(adapter);

        tvTitle=(TextView) findViewById(R.id.descussion_page_title);
        tvDate=(TextView) findViewById(R.id.descussion_page_date);
        tvTime=(TextView) findViewById(R.id.descussion_page_time);
        tvDescription=(TextView) findViewById(R.id.descussion_page_description);


        tvTitle.setText(stvTitle);
        tvDate.setText(stvDate);
        tvTime.setText(stvTime);
        tvDescription.setText(stvDescription);

        btSubmit.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {

                    //Async to update the discussion

                        //get current user name and date =today, time system time.
                        finalText=etDescussion.getText().toString();
                       // descussions.add(new DescussionsList("Current User","today","now",text));


                        SendMessage srv= new SendMessage();
                        srv.execute();

                        adapter.notifyDataSetChanged();


                    }
                                   }
        );


    }

    public void populateData(){


        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Accept", "application/json");
            }
        };

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://192.168.48.2:8080")
                .setRequestInterceptor(requestInterceptor)
                .build();

        publicEvents = restAdapter.create(PublicEvents.class);

        ServiceClass srv=new ServiceClass();
        srv.execute();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_descusion_page, menu);
        return true;
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



    private class ServiceClass extends AsyncTask<String, Void, String> {

        ArrayList<Discussion> descu=new ArrayList<Discussion>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {

try {

    descu = publicEvents.eventDiscussion(event_id);


    for (Discussion object : descu) {
        descussions.add(object);
    }
}
catch(Exception e){

}
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            if(descussions.size()==0) {

//                System.out.println("check 124"+descussions.get(0).getDescription());
            }

            else{

                System.out.println("check 1249"+descussions.get(0).getDescription());

                adapter.notifyDataSetChanged();

            }


        }
    }





    private class SendMessage extends AsyncTask<String, Void, String> {

        Discussion descu=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


          /*  DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateobj = new Date();*/
//            System.out.println(df.format(dateobj));
            user_name= pref.getString("FB_USER_NAME", "Rahul Kohli");
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

          //  DateTime
            String te= " ";

            descu=new  Discussion(event_id, user_name, finalText, te);
            descussions.add(descu);
        }

        @Override
        protected String doInBackground(String... params) {

try{
            Date dateobj = new Date();
          //  descu=new  Discussion( event_id,  user_name, finalText,  dateobj);
          //  descu = publicEvents.eventDiscussion(event_id);


            publicEvents.postDiscussion(descu, new Callback<Discussion>(){
                public void failure(RetrofitError arg0) {
                    // TODO Auto-generated method stub

                    System.out.println("not added in desc");
                }
                public void success(Discussion arg0, Response arg1) {
                    // TODO Auto-generated method stub
                    System.out.println("added in desc");

                }
            });

        }
        catch(Exception e){

        }

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            adapter.notifyDataSetChanged();

        }
    }


}

