package pcsma.events.iiitd.pcma_project.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.AlarmClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.populatingClass.Discussion;
import pcsma.events.iiitd.pcma_project.populatingClass.Events;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;
import pcsma.events.iiitd.pcma_project.populatingClass.Rsvp;
import pcsma.events.iiitd.pcma_project.populatingClass.UserResponse;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EventsDescription extends ActionBarActivity implements View.OnClickListener {


    LinearLayout llBt1, llBt2, llBt3;

    TextView title, venue, date, time, description;
    Button going, mayBe, notGoing;



    ArrayList<Rsvp> rsvp = new ArrayList<Rsvp>();

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    String event_id,user_id;
    Events event;
    String responce="";
    RestAdapter restAdapter;
    int flag=0;
    PublicEvents publicEvents;
    ImageView iv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_description);


        //put Extra  event_id

        Intent intent = getIntent();
        event_id = intent.getStringExtra("event_id");


        prepData();



        title=(TextView) findViewById(R.id.events_description_title);
        venue=(TextView) findViewById(R.id.events_description_venu);
        date=(TextView) findViewById(R.id.events_description_date);
        time=(TextView) findViewById(R.id.events_description_time);
        description=(TextView) findViewById(R.id.events_descruption_description);
        iv1=(ImageView) findViewById(R.id.events_description_row_image);


/*


        if(event.getResponse()=="attending"){


            going.setBackgroundColor(Color.GREEN);
            mayBe.setBackgroundColor(Color.WHITE);
            notGoing.setBackgroundColor(Color.WHITE);


        }


        if(event.getResponse()=="unsure"){

            going.setBackgroundColor(Color.WHITE);
            mayBe.setBackgroundColor(Color.GREEN);
            notGoing.setBackgroundColor(Color.WHITE);


        }


        if(event.getResponse()=="notGoing"){

            going.setBackgroundColor(Color.WHITE);
            mayBe.setBackgroundColor(Color.WHITE);
            notGoing.setBackgroundColor(Color.GREEN);


        }
*/


        llBt1=(LinearLayout ) findViewById(R.id.fl_button1);
        llBt2=(LinearLayout ) findViewById(R.id.fl_button2);
        llBt3=(LinearLayout ) findViewById(R.id.fl_button3);

        going=(Button) findViewById(R.id.events_description_going);
        mayBe=(Button) findViewById(R.id.events_description_maybe);
        notGoing=(Button) findViewById(R.id.events_description_not_going);

        going.setOnClickListener(this);
        mayBe.setOnClickListener(this);
        notGoing.setOnClickListener(this);

        llBt1.setOnClickListener(this);
        llBt2.setOnClickListener(this);
        llBt3.setOnClickListener(this);

    }

    private void prepData() {


        SharedPreferences pref;
        SharedPreferences.Editor edit;


        pref = EventsDescription.this.getSharedPreferences("IIITD_Events", 0);
        edit = pref.edit();


        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestInterceptor.RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Accept", "application/json");
            }
        };

        restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://192.168.48.2:8080")
                .setRequestInterceptor(requestInterceptor)
                .build();



         publicEvents = restAdapter.create(PublicEvents.class);

        user_id=pref.getString("FB_USER_ID", "user_1");
        ServiceClass srv=new ServiceClass();
        srv.execute();





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events_description, menu);
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


    @Override
    public void onClick(View v) {





        pref = EventsDescription.this.getSharedPreferences("IIITD_Events", 0);
        edit = pref.edit();





         publicEvents = restAdapter.create(PublicEvents.class);



        String user_id=pref.getString( "FB_USER_ID", "");
        String user_name=pref.getString("FB_USER_NAME","");



        SendMessage srv;

        Intent intent;
        switch(v.getId()){

            case R.id.fl_button1:
                intent = new Intent(this,DescussionPage.class);
                intent.putExtra("event_id", event.getEvent_id());
                intent.putExtra("tvTitle", event.getName());
                intent.putExtra("tvDate", event.getStart_time().substring(0, 10));
                intent.putExtra("tvTime", event.getStart_time().substring(11, 18));
                intent.putExtra("tvDescription", event.getDescription());
                intent.putExtra("cover_url", event.getCover_url());

                startActivity(intent);

                break;

            case R.id.fl_button2:
                Intent openClockIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                openClockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                this.startActivity(openClockIntent);
                break;




            case R.id.fl_button3:


                Calendar cal = Calendar.getInstance();
                intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                //event.getStart_time();

                intent.putExtra("beginTime", event.getStart_time());

                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", event.getName());
                intent.putExtra("description", event.getDescription());

                startActivity(intent);
                break;



            case R.id.events_description_going:

                rsvp.add(new Rsvp(event_id, "attending"));
                 srv=new SendMessage();
                srv.execute();

                event.setResponse("attending");


                going.setBackgroundColor(Color.GREEN);
                mayBe.setBackgroundColor(Color.WHITE);
                notGoing.setBackgroundColor(Color.WHITE);

                break;
            case R.id.events_description_maybe:


                rsvp.add(new Rsvp(event_id, "unsure"));
                srv=new SendMessage();
                srv.execute();
                event.setResponse("unsure");

                going.setBackgroundColor(Color.WHITE);
                mayBe.setBackgroundColor(Color.GREEN);
                notGoing.setBackgroundColor(Color.WHITE);

                break;

            case R.id.events_description_not_going:


                rsvp.add(new Rsvp(event_id, "notGoing"));

                srv=new SendMessage();
                srv.execute();
                event.setResponse("notGoing");
                going.setBackgroundColor(Color.WHITE);
                mayBe.setBackgroundColor(Color.WHITE);
                notGoing.setBackgroundColor(Color.GREEN);

                break;

        }


    }



    private class ServiceClass extends AsyncTask<String, Void, String> {

     Events eventspop;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected String doInBackground(String... params) {




            event = publicEvents.eventDescription(user_id, event_id);



            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);




        //    System.out.println("768 " + event.getName());

         title.setText(event.getName());
            venue.setText("Venue: \n"+event.getVenue());
            date.setText(event.getStart_time().substring(0, 10));
            time.setText(event.getStart_time().substring(11, 18));
            description.setText("About : \n"+event.getDescription());
            responce= (event.getResponse());

            Picasso.with(getApplicationContext())
                    .load(event.getCover_url())
                    .placeholder(R.drawable.com_facebook_button_like)
                    .error(R.drawable.com_facebook_button_like)         // optional
                    .into(iv1);
if(flag==0) {

    if (event.getResponse().equals("") || event.getResponse() == null) {


        going.setBackgroundColor(Color.WHITE);
        mayBe.setBackgroundColor(Color.WHITE);
        notGoing.setBackgroundColor(Color.WHITE);


    } else if (event.getResponse().equals("attending")) {


        going.setBackgroundColor(Color.GREEN);
/*
                mayBe.setBackgroundColor(Color.WHITE);
                notGoing.setBackgroundColor(Color.WHITE);
*/


    } else if (event.getResponse().equals("unsure")) {

        //              going.setBackgroundColor(Color.WHITE);
        mayBe.setBackgroundColor(Color.GREEN);
//                notGoing.setBackgroundColor(Color.WHITE);


    } else if (event.getResponse().equals("notGoing")) {

        //              going.setBackgroundColor(Color.WHITE);
//                mayBe.setBackgroundColor(Color.WHITE);
        notGoing.setBackgroundColor(Color.GREEN);


    } else {


    /*            going.setBackgroundColor(Color.WHITE);
                mayBe.setBackgroundColor(Color.WHITE);
                notGoing.setBackgroundColor(Color.WHITE);
    */
    }
    flag=1;
}

        }
    }




    private class SendMessage extends AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {



            publicEvents.createUserRSVPTable(rsvp, user_id, new Callback<Rsvp>() {
                @Override
                public void success(Rsvp rsvp, retrofit.client.Response response) {

                }

                @Override
                public void failure(RetrofitError error) {

                }


            });


            UserResponse userResponse = new UserResponse(user_id, event_id, event.getResponse());
            publicEvents.updateUserResponse(userResponse, new Callback<UserResponse>() {
                public void failure(RetrofitError arg0) {

                    System.out.println("786 9:  ");
                }

                public void success(UserResponse arg0, Response arg1) {

                    System.out.println("*************User Response callback success**************");

                }
            });



            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            System.out.println("786 9: on post ");

          //  rsvp.clear();

        }
    }



}
