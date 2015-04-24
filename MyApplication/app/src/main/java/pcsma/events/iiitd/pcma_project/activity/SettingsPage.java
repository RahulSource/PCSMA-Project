package pcsma.events.iiitd.pcma_project.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import 	android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONStringer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.AdapterView.OnItemClickListener;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.adapter.SettingsListViewAdapter;
import pcsma.events.iiitd.pcma_project.populatingClass.SettingsAttributes;
import pcsma.events.iiitd.pcma_project.populatingClass.User;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SettingsPage extends ActionBarActivity implements OnItemClickListener, View.OnClickListener {

    ViewPager pager;

    public Button saveSettings;
    boolean checked[] = {false,false,false,false,false,false,false};
    String category[]={"FooBar","Byld","MadToes","Electroholics","Game Craft","Seminar","Select All"};
    SharedPreferences pref;
    Editor edit;
    public String fbUserID;
    public static int categoryArray[]={0,0,0,0,0,0,0};
    int tcategoryArray[] ={0,0,0,0,0,0,0};

    protected String SERVICE_URI="";

    ViewGroup container;
    LayoutInflater inflater;
    Bundle savedInstanceState;
    List<Fragment> fragments;
    SettingsListViewAdapter adapter;
    ArrayList<SettingsAttributes> settingsContent = new ArrayList<SettingsAttributes>();
    ListView settingsCategory;
    String categoryText="";




    public static String despTime="selected time.",duration[]={"2 Hrs.","4 Hrs.","8 Hrs.","16 Hrs.","One Day","One Week","One Month"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);





        settingsCategory = (ListView) findViewById(R.id.lv_settings_layout);
        saveSettings= (Button) findViewById(R.id.b_settings_save);
        saveSettings.setOnClickListener(this);

        pref = SettingsPage.this.getSharedPreferences("IIITD_Events", 0);
        edit = pref.edit();

        despTime=pref.getString("SelectedTime","selected time.");

        settingsCategory.setOnItemClickListener(this);


        try{

            pref = SettingsPage.this.getSharedPreferences("pref", 0);
            int size = pref.getInt("categoryArray_size", 0);
            int i;
            for( i=0;i<size;i++)  {
                categoryArray[i] = pref.getInt("categoryArray_" + i, 0);
            }


        }
        catch(Exception e){

        }
        prepData();

        adapter = new SettingsListViewAdapter(getBaseContext(), settingsContent);
        settingsCategory.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings_page, menu);
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






    public void onResume() {
        super.onResume();

        pref = SettingsPage.this.getSharedPreferences("pref", 0);
        edit = pref.edit();

        try{

            pref = SettingsPage.this.getSharedPreferences("pref", 0);
            int size = pref.getInt("categoryArray_size", 0);
            int i;
            for( i=0;i<size;i++)  {
                categoryArray[i] = pref.getInt("categoryArray_" + i, 0);
            }

        }
        catch(Exception e){

        }

    }




    public void alertMultipleChoiceItems(){

        // where we will store or remove selected items


        for(int w=0;w<7;w++){
            tcategoryArray[w]=categoryArray[w];
        }

        int t=0;
        for(t=0;t<7;t++){
            if(categoryArray[t]==1){
                checked[t]=true;
            }
            else{
                checked[t]=false;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // set the dialog title
        builder.setTitle("Choose One or More")

                .setMultiChoiceItems(category, checked, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                        if (isChecked) {
                            // if the user checked the item, add it to the selected items

                            tcategoryArray[which]=1;
                        }

                        else if(!isChecked ){
                            tcategoryArray[which]=0;
                        }


                    }

                })

                        // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                        if(tcategoryArray[6]==1){
                            for(int w=0;w<7;w++){
                                categoryArray[w]=1;
                            }
                        }
                        else{
                            for(int w=0;w<7;w++){
                                categoryArray[w]=tcategoryArray[w];
                            }
                        }

                        prepData();
                        adapter.notifyDataSetChanged();

                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                    }
                })

                .show();

    }



    public void setcategoryText(){
        int c=0;
        categoryText="";
        for(int i=0;i<6;i++){

            if(categoryArray[i]==1){
                if(c!=0){
                    categoryText+=',';
                }
                categoryText+=category[i];
                c=1;

            }
        }

    }
    public void prepData(){
        setcategoryText();
        settingsContent.clear();
        settingsContent.add(new SettingsAttributes("Settings","Select the kind of updates you want and localize them to your needs"));
        settingsContent.add(new SettingsAttributes("Select Category ",categoryText));
        settingsContent.add(new SettingsAttributes("Select Time Duration","You will get updates of your preferences that will occur in next "+despTime));

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.b_settings_save:


String time="0 2:0:0.0";

                if(despTime.equals("2 Hrs.")){
                    time="0 2:0:0.0";
                }
                else if(despTime.equals("4 Hrs.")){
                    time="0 4:0:0.0";
                }

                else if(despTime.equals("8 Hrs.")){
                    time="0 8:0:0.0";
                }
                else if(despTime.equals("16 Hrs.")){
                    time="0 16:0:0.0";
                }
                else if(despTime.equals("One Day")){
                    time="1 0:0:0.0";
                }
                else if(despTime.equals("One Week")){
                    time="7 0:0:0.0";
                }
                else if(despTime.equals("One Month")){
                    time="31 0:0:0.0";
                }
                else if(despTime.equals("One Year")){
                    time="0 0:0:0.0";
                }



                SharedPreferences pref;
                SharedPreferences.Editor edit;


                pref = SettingsPage.this.getSharedPreferences("IIITD_Events", 0);
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

                PublicEvents publicEvents = restAdapter.create(PublicEvents.class);



                String user_id=pref.getString( "FB_USER_ID", "");
                String user_name=pref.getString("FB_USER_NAME","");

                User user=new User( user_id,  user_name, time,  categoryArray[0], categoryArray[1],  categoryArray[2], categoryArray[3],  categoryArray[4],  categoryArray[5],  categoryArray[6]);



                publicEvents.updateUserTable(user, new Callback<User>(){
                    public void failure(RetrofitError arg0) {

                        System.out.println("fail 123");
                    }
                    public void success(User arg0, Response arg1) {
                        System.out.println("no fail 123");


                    }
                });




                edit.putString("SelectedTime",despTime);
                edit.commit();
                System.out.println("793 --");


//                ServiceConnection connService=new ServiceConnection();
//                connService.execute();
//

                Intent intent=new Intent(this,MainMenu.class);
                startActivity(intent);

                pref = this.getSharedPreferences("pref", 0);
                edit = pref.edit();
                edit.putInt("categoryArray_size", categoryArray.length);
                for(int i=0;i<categoryArray.length;i++)  {
                    edit.putInt("categoryArray_" + i, categoryArray[i]);
                }

                edit.commit();
                break;

            default:
                break;
        }


    }



    public void alertTime(){
        int temp=0;
        int time=-1;
        if(despTime.equals("2 Hrs.")){
            time=0;
        }
        else if(despTime.equals("4 Hrs.")){
            time=1;
        }
        else if(despTime.equals("8 Hrs.")){
            time=2;
        }
        else if(despTime.equals("16 Hrs.")){
            time=3;
        }
        else if(despTime.equals("One Day")){
            time=4;
        }
        else if(despTime.equals("One Week")){
            time=5;
        }
        else if(despTime.equals("One Month")){
            time=6;
        }
        else if(despTime.equals("One Year")){
            time=7;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Duration...");
        builder.setSingleChoiceItems(duration, time, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                despTime=duration[item];
            }
        })

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        prepData();
                        adapter.notifyDataSetChanged();
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // removes the AlertDialog in the screen
                    }
                }).show();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) {

        switch(position){
            case 0:
                break;
            case 1:
                alertMultipleChoiceItems();

                adapter.notifyDataSetChanged();

                break;

            case 2:
                alertTime();
                break;

            default:
                break;
        }

    }







    private class ServiceConnection extends AsyncTask
    {


        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected Object doInBackground(Object... params) {

            System.out.println("792 1 --");
            connect();
            System.out.println("792 2 --");
            return null;
        }


        private void connect(){

            // %% Added for cellId and Lac

            try
            {



                HttpClient expDriveCellClient=new DefaultHttpClient();
                HttpPost expDriveCellPost=new HttpPost(SERVICE_URI);

                String user_id=pref.getString("FB_USER_ID","0");
                String name=pref.getString("FB_USER_NAME","unKnown");




            }

            catch(Exception e)
            {

            }

        }
    }

}
