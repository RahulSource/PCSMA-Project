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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.populatingClass.Events;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;


/**
 * Created by Rahul on 3/14/2015.
 */
public class ProfilePageAdapter extends BaseAdapter {

    ArrayList<Events> eventsArrayList = new ArrayList();
    LayoutInflater inflater;
    Context context;

    SharedPreferences pref;
    public SharedPreferences.Editor edit;

    String fbUserID, fbUserName, location;


    public ProfilePageAdapter(ArrayList<Events> eventsArrayList, Context context) {
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


        ProfilePageContent ppContent=new ProfilePageContent();
        if(position ==0){
            view= inflater.inflate(R.layout.profile_page_head, parent, false);

            TextView profilePic;
            ProfilePictureView ppv;


            pref = context.getSharedPreferences("IIITD_Events", 0);
            edit = pref.edit();

            String userName=pref.getString("FB_USER_NAME", "Rahul Kohli");
            String userFbId=pref.getString("FB_USER_ID", "1010469483");
            profilePic=(TextView) view.findViewById(R.id.profile_page_tv_name);
            ppv = (ProfilePictureView) view.findViewById(R.id.profile_page_facebook_profileimage);
            ppv.setProfileId(userFbId);
            profilePic.setText(userName);

            return view;
        }

        else if(position!=0) {


            view= inflater.inflate(R.layout.events_lv_row, parent, false);

        }




        position=position-1;
            ppContent.title=(TextView) view.findViewById(R.id.events_lv_row_title);
            ppContent.description=(TextView) view.findViewById(R.id.events_lv_row_description);
            ppContent.time=(TextView) view.findViewById(R.id.events_lv_row_time);
            ppContent.date=(TextView) view.findViewById(R.id.events_lv_row_date);
            ppContent.url=(ImageView) view.findViewById(R.id.events_lv_row_image);


            ppContent.title.setText(eventsArrayList.get(position).getName());
            ppContent.time.setText(eventsArrayList.get(position).getStart_time().substring(11, 18));
            ppContent.description.setText(eventsArrayList.get(position).getDescription());
            ppContent.date.setText(eventsArrayList.get(position).getStart_time().substring(0, 10));

        Picasso.with(context)
                .load(eventsArrayList.get(position).getCover_url())
                .placeholder(R.drawable.com_facebook_button_like) // optional
                .error(R.drawable.com_facebook_button_like)         // optional
                .into(ppContent.url);

        return view;
    }
}
