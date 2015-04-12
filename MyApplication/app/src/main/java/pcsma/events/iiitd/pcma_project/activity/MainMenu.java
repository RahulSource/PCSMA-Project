package pcsma.events.iiitd.pcma_project.activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import pcsma.events.iiitd.pcma_project.R;

public class MainMenu extends Activity implements View.OnClickListener {

    Button bt1,bt2,bt4,bt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        bt1=(Button) findViewById(R.id.menu_button1);
        bt2=(Button) findViewById(R.id.menu_button2);
        bt3=(Button) findViewById(R.id.menu_button3);
        bt4=(Button) findViewById(R.id.menu_button4);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);



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

                intent = new Intent(MainMenu.this, SettingsPage.class);

                startActivity(intent);

                break;



        }
    }
}
