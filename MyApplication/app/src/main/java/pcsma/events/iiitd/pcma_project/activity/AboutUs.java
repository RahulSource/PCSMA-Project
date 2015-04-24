package pcsma.events.iiitd.pcma_project.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;

import pcsma.events.iiitd.pcma_project.R;

public class AboutUs extends ActionBarActivity implements View.OnClickListener, LocationListener {

    double latitude=0.0;
    double longitude=0.0;
    private String provider;
    private LocationManager locationManager;
    Button bt1;
    TextView tv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        bt1=(Button) findViewById(R.id.about_us_button1);
        tv1=(TextView) findViewById(R.id.about_us_tv);
        bt1.setOnClickListener(this);

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
        onLocationChanged(location);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_us, menu);


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

        String uri = "http://maps.google.com/maps?saddr=" + latitude+","+longitude+"&daddr="+"28.544608"+","+"77.272388";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);



    }

    @Override
    public void onLocationChanged(Location location) {


        latitude= location.getLatitude();
        longitude=location.getLongitude();
    }
}
