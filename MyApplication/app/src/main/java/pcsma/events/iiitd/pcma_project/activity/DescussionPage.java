package pcsma.events.iiitd.pcma_project.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.adapter.DescussionPageAdapter;
import pcsma.events.iiitd.pcma_project.populatingClass.DescussionsList;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;

public class DescussionPage extends ActionBarActivity {


    ImageView ivImage;
    EditText etDescussion;
    Button btSubmit;

    TextView tvTitle, tvDescription, tvDate, tvTime;

    ListView  lvDescussion;

    EventsList currentEvent;
    ArrayList<DescussionsList> descussions=new ArrayList<DescussionsList>();
    ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descussion_page);


        ivImage=(ImageView) findViewById(R.id.descussion_page_iv_image);
        etDescussion=(EditText) findViewById(R.id.descussion_page_et_descussion);
        btSubmit=(Button) findViewById(R.id.descussion_page_button);

        lvDescussion=(ListView) findViewById(R.id.descussion_page_lv);

        populateData();
        lvDescussion.setAdapter(new DescussionPageAdapter(descussions,this));
        adapter=lvDescussion.getAdapter();
        tvTitle=(TextView) findViewById(R.id.descussion_page_title);
        tvDate=(TextView) findViewById(R.id.descussion_page_date);
        tvTime=(TextView) findViewById(R.id.descussion_page_time);
        tvDescription=(TextView) findViewById(R.id.descussion_page_description);


        btSubmit.setOnClickListener(new View.OnClickListener() {

                    @Override

                    public void onClick(View view) {

                    //Async to update the discussion

                        //get current user name and date =today, time system time.
                        String text=etDescussion.getText().toString();
                        descussions.add(new DescussionsList("Current User","today","now",text));

                        adapter.notifyAll();
        //                adapter.notifyDataSetChanged();


                    }
                                   }
        );


    }

    public void populateData(){

        //Async all ...
        currentEvent=new EventsList("Title 1","descruption ","date","time","imageURL");


        descussions.add(new DescussionsList("name1","date1","time1","description"));
        descussions.add(new DescussionsList("name2","date1","time1","description"));
        descussions.add(new DescussionsList("name3","date1","time1","description"));
        descussions.add(new DescussionsList("name4","date1","time1","description"));

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
}
