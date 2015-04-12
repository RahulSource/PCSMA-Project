package pcsma.events.iiitd.pcma_project.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.widget.ProfilePictureView;

import java.util.ArrayList;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;


/**
 * Created by Rahul on 3/14/2015.
 */
public class ProfilePageAdapter extends BaseAdapter {

    ArrayList<EventsList> eventsArrayList = new ArrayList();
    LayoutInflater inflater;
    Context context;

    SharedPreferences pref;
    public SharedPreferences.Editor edit;

    String fbUserID, fbUserName, location;


    public ProfilePageAdapter(ArrayList<EventsList> eventsArrayList, Context context) {
        this.eventsArrayList = eventsArrayList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        System.out.println("position=getcount() 365 --  " +(eventsArrayList.size()+1) );
        int count=(eventsArrayList.size()+1);
        return  count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public class ProfilePageContent{
        ImageView url;
        TextView title,description, date, time;

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
/*

        View view=ContentView;
        ProfilePageContent ppContent;
        ppContent = new ProfilePageContent();
        if(view == null) {

            if(position==0){
                System.out.println("position=1 365  " +position );
                view= inflater.inflate(R.layout.profile_page_head, parent, false);

                TextView profilePic;
                ProfilePictureView ppv;
                System.out.println("position=2 365  " +position );
                profilePic=(TextView) view.findViewById(R.id.profile_page_tv_name);
                ppv = (ProfilePictureView) view.findViewById(R.id.profile_page_facebook_profileimage);

              */
/*  ppv.setProfileId("1010469483");
                profilePic.setText("Rahul Kohli");  //user name*//*


            }
            else {
                System.out.println("position=4 365  " +position );
                view = inflater.inflate(R.layout.events_lv_row, parent, false);

                System.out.println("position=4 365  " +position );
                System.out.println("view = null 365");
            }

        }

        System.out.println("position=3 365  " +position );
*/

        ProfilePageContent ppContent=new ProfilePageContent();
        if(position ==0){
            view= inflater.inflate(R.layout.profile_page_head, parent, false);

            TextView profilePic;
            ProfilePictureView ppv;

            profilePic=(TextView) view.findViewById(R.id.profile_page_tv_name);
            ppv = (ProfilePictureView) view.findViewById(R.id.profile_page_facebook_profileimage);
            ppv.setProfileId("1010469483");
            profilePic.setText("Rahul Kohli");

            System.out.println("position=2 365  " +position );


            return view;
        }

        else if(position!=0) {

            System.out.println("position=1 365  " +position );
            view= inflater.inflate(R.layout.events_lv_row, parent, false);
            //ppContent = new ProfilePageContent();
          //  view.setTag(ppContent);

        }




        //if(position!=0) {
          System.out.println("position=5 365  " +position );

        position=position-1;
            ppContent.title=(TextView) view.findViewById(R.id.events_lv_row_title);
            ppContent.description=(TextView) view.findViewById(R.id.events_lv_row_description);
            ppContent.time=(TextView) view.findViewById(R.id.events_lv_row_time);
            ppContent.date=(TextView) view.findViewById(R.id.events_lv_row_date);
            ppContent.url=(ImageView) view.findViewById(R.id.events_lv_row_image);


            ppContent.title.setText(eventsArrayList.get(position).getTitle());
            ppContent.time.setText(eventsArrayList.get(position).getTime());
            ppContent.description.setText(eventsArrayList.get(position).getDescription());
            ppContent.date.setText(eventsArrayList.get(position).getDate());
            ppContent.url.setImageResource(R.drawable.com_facebook_button_like);


        //}

        System.out.println("position=45 365  " +position );
        return view;
    }
}




