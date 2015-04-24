package pcsma.events.iiitd.pcma_project.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.populatingClass.Rsvp;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

public class MainMenu extends Activity implements View.OnClickListener {

    Button bt1,bt2,bt4,bt3,bt5;

    private boolean isOnline() {
        ConnectivityManager cm =   (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        bt1=(Button) findViewById(R.id.menu_button1);
        bt2=(Button) findViewById(R.id.menu_button2);
        bt3=(Button) findViewById(R.id.menu_button3);
        bt4=(Button) findViewById(R.id.menu_button4);
bt5=(Button) findViewById(R.id.menu_button5);




        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);


        if(isOnline()) {

    Session session = Session.getActiveSession();
//search?type=event&q=IIIT%20Delhi
    new Request(
            session,
            "/me/events",
            null,
            HttpMethod.GET,
            new Request.Callback() {
                public void onCompleted(Response response) {


                    try {
                        String res = response.toString();
                        System.out.println("123 : " + res);
                        int st = res.indexOf('[');
                        int ed = res.lastIndexOf(']') + 1;
                        res = res.substring(st, ed);
                        JSONArray jarray = null;

                        try {
                            JSONObject jsonObject;

                            try {
                                jarray = new JSONArray(res);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ArrayList<Rsvp> rsvp = new ArrayList<Rsvp>();

                            for (int i = 0; i < jarray.length(); i++) {
                                jsonObject = null;
                                try {
                                    jsonObject = jarray.getJSONObject(i);

                                    rsvp.add(new Rsvp(jsonObject.getString("id"), jsonObject.getString("rsvp_status")));
                                    System.out.println("id" + jsonObject.getString("id") + "status" + jsonObject.getString("rsvp_status"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            SharedPreferences pref;
                            SharedPreferences.Editor edit;


                            pref = MainMenu.this.getSharedPreferences("IIITD_Events", 0);
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


                            String user_id = pref.getString("FB_USER_ID", "");
                            String user_name = pref.getString("FB_USER_NAME", "");


                            publicEvents.createUserRSVPTable(rsvp, user_id, new Callback<Rsvp>() {
                                @Override
                                public void success(Rsvp rsvp, retrofit.client.Response response) {

                                }

                                @Override
                                public void failure(RetrofitError error) {

                                }


                            });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {

                    }

                }


            }


    ).executeAsync();

}
    else{
    }
}





protected void onResume(){
    super.onResume();

    bt1=(Button) findViewById(R.id.menu_button1);
    bt2=(Button) findViewById(R.id.menu_button2);
    bt3=(Button) findViewById(R.id.menu_button3);
    bt4=(Button) findViewById(R.id.menu_button4);

    bt1.setOnClickListener(this);
    bt2.setOnClickListener(this);
    bt3.setOnClickListener(this);
    bt4.setOnClickListener(this);


}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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

            case R.id.menu_button1:

                intent = new Intent(MainMenu.this, ProfilePage.class);

                startActivity(intent);

                break;
            case R.id.menu_button2:

                intent = new Intent(MainMenu.this, PopulatingEvents.class);

                startActivity(intent);

                break;
            case R.id.menu_button3:

                 intent = new Intent(MainMenu.this, CalenderPage.class);

                startActivity(intent);

                break;
            case R.id.menu_button4:
/*

                urlParser up=new urlParser();
                up.execute();
*/


                intent = new Intent(MainMenu.this, SettingsPage.class);

                startActivity(intent);

                break;


            case R.id.menu_button5:

                intent =new Intent(MainMenu.this,AboutUs.class);
                startActivity(intent);
                break;

        }
    }


    public class urlParser extends AsyncTask<String, String, String> {

        String url = "https://graph.facebook.com/v2.3/1590279131249787?fields=cover&access_token=CAAEfAkvKAxcBAMTpoRGN4hvMQ67lwZCu4T6zMhaYhBhsuDGyP2EZBsprYuXvwLLcbFpF3hgUwkW6PHzeAAldUkyMsAZCRRPc7BePuPxaHuFtWPMvKgEpm13jCdZAiAc3KczY0XoAQxAFnWyC5bnCAjsGTstDM3XmalJF9KdnRZBf4ZB1JqhiZBuUlNkDZCYeVDTOnFFREh8aLquwPLvDR7MH";

        @Override
        protected String doInBackground(String... params) {


            try {

                HttpClient client = new DefaultHttpClient();
                HttpGet get = new HttpGet();
                get.setURI(new URI(url));
                HttpResponse res = client.execute(get);
                HttpEntity entity = res.getEntity();
                String resStr = EntityUtils.toString(entity);
                System.out.println("123 " + resStr);


                if (res == null) {
                    //"Could not fetch the responce"
                } else {


                    JSONObject jObj = new JSONObject(resStr);

                    JSONObject src = new JSONObject(jObj.getString("cover"));
                    String source = src.getString("source");
                    System.out.println("123 " + source);

                }


            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

    }







}
