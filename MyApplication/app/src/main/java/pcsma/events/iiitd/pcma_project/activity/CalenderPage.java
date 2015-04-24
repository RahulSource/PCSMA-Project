package pcsma.events.iiitd.pcma_project.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.adapter.EventsAdapter;
import pcsma.events.iiitd.pcma_project.populatingClass.Events;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

public class CalenderPage extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    ArrayList<Events> eventsPopulate = new ArrayList<Events>();
    int mYear, mMonth, mDay;
    Button bt1;
    String date;
    PublicEvents publicEvents;
    EventsAdapter adapter;

    SharedPreferences pref;
    public SharedPreferences.Editor edit;

    ListView eventsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_page);




        eventsListView=(ListView) findViewById(R.id.calender_lv);

        adapter=new EventsAdapter(eventsPopulate, this);
        eventsListView.setAdapter(adapter);
        eventsListView.setOnItemClickListener(this);

    }

  protected  void onResume(){
        super.onResume();

      bt1=(Button) findViewById(R.id.calender_button);
      bt1.setOnClickListener(this);


    }


    public void populateData(){
/*

        eventsPopulate.add(new Events("title1","If Nothing to show Default message will be shared.","date1","time1","imageUrl1"));
        eventsPopulate.add(new Events("title2","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new Events("title3","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new Events("title4","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new Events("title5","description1","date1","time1","imageUrl1"));
        eventsPopulate.add(new Events("title16","description1","date1","time1","imageUrl1"));

*/



        pref = CalenderPage.this.getSharedPreferences("IIITD_Events", 0);
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
        //user_id=pref.getString("FB_USER_ID", "user_1");

        ServiceClass srv=new ServiceClass();
        srv.execute();


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
System.out.println("1234 : " + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);



                                populateData();
                                if(monthOfYear <9){
                                    date=year+ "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }
                                else{
                                    date=year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                }
                                System.out.println(date);


                            }
                        }, mYear, mMonth, mDay);
                dpd.show();

                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Intent intent = new Intent(this,EventsDescription.class);
        intent.putExtra("event_id", eventsPopulate.get(position).getEvent_id());
        startActivity(intent);

    }



    private class ServiceClass extends AsyncTask<String, Void, String> {




        ArrayList<Events> eventspop = new ArrayList<Events>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            eventsPopulate.clear();
        }

        @Override
        protected String doInBackground(String... params) {

           // eventspop = publicEvents.fetchProfile(user_id);

            eventspop = publicEvents.calendarEvents(date);

            if(eventspop.size()==0){


            }
            else {
                for (Events object : eventspop) {
                    eventsPopulate.add(object);
                    System.out.println("something 786:");
                }
            }
            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(eventspop.size()==0){

                Toast.makeText(getApplication(),"Nothing to show sorry. Try an other date.",Toast.LENGTH_LONG).show();
            }


            //

            else{
                adapter.notifyDataSetChanged();
                System.out.println("check 1245" + eventsPopulate.get(0).getDescription());
            }
            adapter.notifyDataSetChanged();


        }
    }




}
