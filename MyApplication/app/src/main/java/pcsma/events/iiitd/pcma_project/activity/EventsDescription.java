package pcsma.events.iiitd.pcma_project.activity;

import android.content.Context;
import android.content.Intent;
import android.provider.AlarmClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.Calendar;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;

public class EventsDescription extends ActionBarActivity implements View.OnClickListener {


    LinearLayout llBt1, llBt2, llBt3;

    EventsList currentEvent;  //Async one event.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_description);


        llBt1=(LinearLayout ) findViewById(R.id.fl_button1);
        llBt2=(LinearLayout ) findViewById(R.id.fl_button2);
        llBt3=(LinearLayout ) findViewById(R.id.fl_button3);
        llBt1.setOnClickListener(this);
        llBt2.setOnClickListener(this);
        llBt3.setOnClickListener(this);

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

        Intent intent;
        switch(v.getId()){

            case R.id.fl_button1:
                intent = new Intent(this,DescussionPage.class);
                //intent.putExtra("event_id", currentEvent.getId());
                startActivity(intent);

                break;

            case R.id.fl_button2:
                Intent openClockIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
                openClockIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                this.startActivity(openClockIntent);


                /*
                Calendar cal = Calendar.getInstance();
                intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("beginTime", cal.getTimeInMillis());
                intent.putExtra("allDay", true);
                intent.putExtra("rrule", "FREQ=YEARLY");
                intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
                intent.putExtra("title", "Test Event");
                intent.putExtra("description", "This is a sample description");
                startActivity(intent);
                break;*/

        }
    }
}
