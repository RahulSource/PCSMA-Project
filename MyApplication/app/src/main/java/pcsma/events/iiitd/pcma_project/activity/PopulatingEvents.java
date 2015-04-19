package pcsma.events.iiitd.pcma_project.activity;

import android.app.Activity;
//import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.adapter.EventsAdapter;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;


public class PopulatingEvents extends Activity implements AdapterView.OnItemClickListener {



    ArrayList<EventsList> eventsPopulate = new ArrayList<EventsList>();

    ListView eventsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_lv_page);

        eventsListView=(ListView) findViewById(R.id.events_lv1);
        populateData();
        eventsListView.setAdapter(new EventsAdapter(eventsPopulate, this));

        eventsListView.setOnItemClickListener(this);



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

        eventsPopulate.add(new EventsList("title1","description1","date1","time1","imageUrl1","123"));
        eventsPopulate.add(new EventsList("title2","description1","date1","time1","imageUrl1","123"));
        eventsPopulate.add(new EventsList("title3","description1","date1","time1","imageUrl1","123"));
        eventsPopulate.add(new EventsList("title4","description1","date1","time1","imageUrl1","123"));
        eventsPopulate.add(new EventsList("title5","description1","date1","time1","imageUrl1","123"));
        eventsPopulate.add(new EventsList("title16","description1","date1","time1","imageUrl1","123"));

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
        intent.putExtra("event_id", eventsPopulate.get(position).getId());
        startActivity(intent);

    }
}
