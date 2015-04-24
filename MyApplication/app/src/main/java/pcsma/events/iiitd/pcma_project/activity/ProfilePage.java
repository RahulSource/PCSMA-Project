package pcsma.events.iiitd.pcma_project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.adapter.ProfilePageAdapter;
import pcsma.events.iiitd.pcma_project.populatingClass.Events;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

import android.view.View.OnClickListener;
import android.widget.Toast;

import com.facebook.Session;


public class ProfilePage extends ActionBarActivity  implements AdapterView.OnItemClickListener{



    ArrayList<Events> eventsPopulate = new ArrayList<Events>();

    SharedPreferences pref;
    SharedPreferences.Editor edit;

    String user_id;
    PublicEvents publicEvents;
    ListView profileListView;
    ProfilePageAdapter adapter;
    RelativeLayout logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_page);

        pref = ProfilePage.this.getSharedPreferences("IIITD_Events", 0);
        edit = pref.edit();

        profileListView=(ListView) findViewById(R.id.profile_page_lv);

        populateData();

        adapter= new ProfilePageAdapter(eventsPopulate, this);
        profileListView.setAdapter(adapter);

        profileListView.setOnItemClickListener(this);




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

                    edit.putString("FB_USER_ID", "0");
                    edit.putString("FB_USER_NAME", "0");
                    edit.commit();

                    session = new Session(getBaseContext());
                    Session.setActiveSession(session);
                    session.closeAndClearTokenInformation();

                }

                Intent intent = new Intent(ProfilePage.this, MainActivity.class);
             /* edit.putString("FB_USER_ID","0");
                edit.commit();*/
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);


            }
        });


//        adapter.notifyDataSetChanged();



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

        pref = ProfilePage.this.getSharedPreferences("IIITD_Events", 0);
        edit = pref.edit();


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
        user_id=pref.getString("FB_USER_ID", "user_1");

//        eventsPopulate = publicEvents.fetchProfile(user_id);


System.out.println(user_id+"  124:");
        ServiceClass serv=new ServiceClass();
        serv.execute("","","");


//        adapter.notifyDataSetChanged();

        //eventsPopulate = publicEvents.fetchProfile(user_id);




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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(position!=0){

        Intent intent = new Intent(this,EventsDescription.class);
            System.out.println("768 :"+eventsPopulate.get(position-1).getEvent_id()+ "   "+ (position-1));
        intent.putExtra("event_id", eventsPopulate.get(position-1).getEvent_id());
        startActivity(intent);

        }

    }


    private class ServiceClass extends AsyncTask<String, Void, String>{

        ArrayList<Events> eventspop = new ArrayList<Events>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();



}

        @Override
        protected String doInBackground(String... params) {



            eventspop = publicEvents.fetchProfile(user_id);

            for (Events object : eventspop){
                eventsPopulate.add(object);
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


if(eventsPopulate.size()==0){
    Toast.makeText(getApplication(),"You don't have any events in your history of the app yet.",Toast.LENGTH_LONG).show();

}
            else{


            System.out.println("check 124"+eventsPopulate.get(0).getDescription());

            adapter.notifyDataSetChanged();
}

        }
    }


}