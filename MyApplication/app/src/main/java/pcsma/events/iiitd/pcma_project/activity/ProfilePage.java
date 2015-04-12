package pcsma.events.iiitd.pcma_project.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.adapter.ProfilePageAdapter;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;
import android.view.View.OnClickListener;

import com.facebook.Session;


public class ProfilePage extends ActionBarActivity {



    ArrayList<EventsList> eventsPopulate = new ArrayList<EventsList>();

    ListView profileListView;

    RelativeLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        profileListView=(ListView) findViewById(R.id.profile_page_lv);
        populateData();

        logout=(RelativeLayout)  findViewById(R.id.profile_page_rl);
        logout.setOnClickListener(new OnClickListener() {

            @Override

            public void onClick(View view) {

              //  Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();

                Session session = Session.getActiveSession();
                if (session != null) {
                    if (!session.isClosed()) {
                        session.closeAndClearTokenInformation();

                    }
                } else {

                    session = new Session(getBaseContext());
                    Session.setActiveSession(session);
                    session.closeAndClearTokenInformation();

                }

                Intent intent = new Intent(ProfilePage.this, MainActivity.class);
              /*  edit.putString("FB_USER_ID","0");
                edit.commit();*/
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });

        profileListView.setAdapter(new ProfilePageAdapter(eventsPopulate, this));




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_page, menu);
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


    public void populateData(){

        eventsPopulate.add(new EventsList("title1","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title2","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title3","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title4","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title5","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title16","description1","date1","time1","imageUrl1"));

/*
search?type=event&q=IIIT%20Delhi
        new Request(
                 session,
                "/me/events",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {
            */
/* handle the result *//*

                       String res=response.toString();

                        System.out.println("123 : "+res);


                    }
                }
        ).executeAsync();

*/

    }

}
