package pcsma.events.iiitd.pcma_project.activity;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.adapter.EventsAdapter;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;

public class CalenderPage extends ActionBarActivity implements View.OnClickListener {

    ArrayList<EventsList> eventsPopulate = new ArrayList<EventsList>();
    int mYear, mMonth, mDay;
    Button bt1;
    ListView eventsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_page);




        eventsListView=(ListView) findViewById(R.id.calender_lv);
        populateData();
        eventsListView.setAdapter(new EventsAdapter(eventsPopulate, this));
    }

  protected  void onResume(){
        super.onResume();

      bt1=(Button) findViewById(R.id.calender_button);
      bt1.setOnClickListener(this);


    }


    public void populateData(){

        eventsPopulate.add(new EventsList("title1","If Nothing to show Default message will be shared.","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title2","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title3","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title4","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title5","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new EventsList("title16","description1","date1","time1","imageUrl1"));



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calender_page, menu);
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

        switch(v.getId()){
            case R.id.calender_button:
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                               // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);


                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

                break;

        }

    }
}
