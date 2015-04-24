package pcsma.events.iiitd.pcma_project.activity;

import android.app.Activity;
//import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.adapter.EventsAdapter;
import pcsma.events.iiitd.pcma_project.populatingClass.Events;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;


public class PopulatingEvents extends Activity implements AdapterView.OnItemClickListener {

    SharedPreferences pref;
    public SharedPreferences.Editor edit;
    String json;
    EventsAdapter adapter;
    int netn=1;



    private boolean isOnline() {
        ConnectivityManager cm =   (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }


    String user_id;
    ArrayList<Events> eventsPopulate = new ArrayList<Events>();
    PublicEvents publicEvents;
    ListView eventsListView;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_lv_page);

        eventsListView=(ListView) findViewById(R.id.events_lv1);
        populateData();




        pref = PopulatingEvents.this.getSharedPreferences("IIITD_Events", 0);
        edit = pref.edit();
        gson = new Gson();
        eventsListView.setOnItemClickListener(this);


        adapter=new EventsAdapter(eventsPopulate, getApplicationContext());

        eventsListView.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_populating_events, menu);
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



        pref = PopulatingEvents.this.getSharedPreferences("IIITD_Events", 0);
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

        if(isOnline()) {
            ServiceClass srv = new ServiceClass();
            srv.execute();
        }
        else {
            Toast.makeText(getApplication(), "This is the cache version.", Toast.LENGTH_LONG).show();
            ArrayList<Events> eventspop = new ArrayList<Events>();
            json = pref.getString("json", " ");
            eventsPopulate = gson.fromJson(json, new TypeToken<ArrayList<Events>>() {
            }.getType());

try {
    for (Events object : eventspop) {
        eventsPopulate.add(object);
    }
    adapter.notifyDataSetChanged();
}
catch(Exception e) {

}

        }
/*
        eventsPopulate.add(new Events("title1","description1","date1","imageUrl1","123"));
        eventsPopulate.add(new Events("title2","description1","date1","imageUrl1","123"));
        eventsPopulate.add(new Events("title3","description1","date1","imageUrl1","123"));
        eventsPopulate.add(new Events("title4","description1","date1","imageUrl1","123"));
        eventsPopulate.add(new Events("title5","description1","date1","imageUrl1","123"));
        eventsPopulate.add(new Events("title16", "description1", "date1", "imageUrl1", "123"));*/

/*

        Events events=new Events();
        PublicEvents demoEventsService= new RestAdapter.Builder().setEndpoint("http:\\").build().create(PublicEvents.class);
        demoEventsService.rsvp(1, 1, events, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {

                System.out.println("1237 :" + response.toString());

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        ArrayList<Events> list = demoEventsService.abc(1, new Callback<String>() {
            @Override
            public void success(String s, retrofit.client.Response response) {
                System.out.println(response.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error.toString());
            }
        });

*/

/*
        Session session=Session.getActiveSession();
//search?type=event&q=IIIT%20Delhi
        new Request(
                 session,
                "/me/events",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {


/*
                   String res=response.toString();

                        System.out.println("123 : "+res);
                        int st=res.indexOf( '[' );
                        int ed=res.indexOf( ']' )+1;
                        res=res.substring(st, ed);
                        JSONArray jarray = null;

                        try{
                         jarray =  new JSONArray(res);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for(int i = 0; i < jarray.length(); i++){
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = jarray.getJSONObject(i);


                                   System.out.println( jsonObject.getString("id")); // this will return you the album's name.
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                    }

        ).executeAsync();
*/


    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this,EventsDescription.class);
        intent.putExtra("event_id", eventsPopulate.get(position).getEvent_id());
        startActivity(intent);



    }





     class ServiceClass extends AsyncTask<String, Void, String> {

        ArrayList<Events> eventspop = new ArrayList<Events>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            eventsPopulate.clear();

        }

        @Override
        protected String doInBackground(String... params) {



            eventspop= publicEvents.userSettingEvents(user_id);

            for (Events object : eventspop){
                eventsPopulate.add(object);
            }




            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(eventsPopulate.size()==0 || !isOnline()){

                Toast.makeText(getApplication(), "This is the cache version. of your previous settings as there is nothing to Show in this settings.", Toast.LENGTH_LONG).show();
                json=pref.getString("json"," ");
                eventspop=gson.fromJson(json, new TypeToken<ArrayList<Events> >(){}.getType()  );
                for (Events object : eventspop){
                    eventsPopulate.add(object);
                }
            }

            else{

                json = gson.toJson(eventsPopulate);

                System.out.println("check 124" + json);

                edit.putString("json",json);
                edit.commit();
                System.out.println("check 124" + eventsPopulate.get(0).getDescription());

            adapter.notifyDataSetChanged();

            }




        }
    }





}