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




    public static String despTime="selected time.",duration[]={"2 Hrs.","4 Hrs.","8 Hrs.","16 Hrs.","One Day","One Week","One Month","One Year"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);





        settingsCategory = (ListView) findViewById(R.id.lv_settings_layout);
        saveSettings= (Button) findViewById(R.id.b_settings_save);
        saveSettings.setOnClickListener(this);

        pref = SettingsPage.this.getSharedPreferences("pref", 0);
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
        String time="24";

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


                if(despTime.equals("2 Hrs.")){
                    time="2";
                }
                else if(despTime.equals("4 Hrs.")){
                    time="4";
                }

                else if(despTime.equals("8 Hrs.")){
                    time="8";
                }
                else if(despTime.equals("16 Hrs.")){
                    time="16";
                }
                else if(despTime.equals("One Day")){
                    time="24";
                }
                else if(despTime.equals("One Week")){
                    time="200";
                }
                else if(despTime.equals("One Month")){
                    time="760";
                }
                else if(despTime.equals("One Year")){
                    time="8760";
                }
                JSONStringer expDriveCellJSON=new JSONStringer()
                        .object()
                        .key("fbUserID").value(fbUserID)
                        .key("Road_Condition").value(String.valueOf(categoryArray[0]))
                        .key("Garbage").value(String.valueOf(categoryArray[1]))
                        .key("Traffic_Jam").value(String.valueOf(categoryArray[2]))
                        .key("Transit_Overload").value(String.valueOf(categoryArray[3]))
                        .key("Power_Issue").value(String.valueOf(categoryArray[4]))
                        .key("Water_Issue").value(String.valueOf(categoryArray[5]))
                        .key("Not_Sure").value(String.valueOf(categoryArray[6]))
                        .key("time").value(time)


				/*.key("latitude").value(String.valueOf(latitude))
				.key("longitude").value(String.valueOf(longitude))
				 */.endObject();

                System.out.println("settings: "+expDriveCellJSON.toString());

                StringEntity expDriveCellEntity = new StringEntity(expDriveCellJSON.toString());
                expDriveCellEntity.setContentType("application/json;charset=UTF-8");//text/plain;charset=UTF-8
                expDriveCellEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,"application/json;charset=UTF-8"));
                expDriveCellPost.setEntity(expDriveCellEntity);

                HttpResponse httpExpDriveCellResponse=expDriveCellClient.execute(expDriveCellPost);

                HttpEntity httpExpDriveCellEntity = httpExpDriveCellResponse.getEntity();

            }

            catch(Exception e)
            {

            }

        }
    }




}







































