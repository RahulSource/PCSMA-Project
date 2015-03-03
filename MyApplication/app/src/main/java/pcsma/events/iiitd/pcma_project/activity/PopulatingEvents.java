package pcsma.events.iiitd.pcma_project.activity;

import android.app.Activity;
//import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

import java.util.ArrayList;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.adapter.EventsAdapter;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;


public class PopulatingEvents extends Activity {



    ArrayList<EventsList> eventsPopulate = new ArrayList<EventsList>();

    ListView eventsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_lv_page);

        eventsListView=(ListView) findViewById(R.id.events_lv1);
        populateData();
        eventsListView.setAdapter(new EventsAdapter(eventsPopulate, this));



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

        eventsPopulate.add(new EventsList("title1","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title2","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title3","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title4","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title5","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title16","description1","date1","time1","imageUrl1"));


        new Request(
                 session,
                "/me/events",
                null,
                HttpMethod.GET,
                new Request.Callback() {
                    public void onCompleted(Response response) {
            /* handle the result */
                       String res=response.toString();

                        System.out.println("123 : "+res);


                    }
                }
        ).executeAsync();


    }
}
