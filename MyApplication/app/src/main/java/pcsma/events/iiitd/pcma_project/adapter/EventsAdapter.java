package pcsma.events.iiitd.pcma_project.adapter;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.populatingClass.Events;
import pcsma.events.iiitd.pcma_project.populatingClass.EventsList;

/**
 * Created by Rahul on 2/25/2015.
 */
public class EventsAdapter extends BaseAdapter {


    ArrayList<Events> eventsArrayList = new ArrayList();
    LayoutInflater inflater;
    Context context;

    public EventsAdapter(ArrayList<Events> eventsArrayList, Context context) {
        this.eventsArrayList = eventsArrayList;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {

        return eventsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewElements{
        ImageView url;
        TextView title,description, date, time;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewElements viewElements;

        if(view == null) {

            view= inflater.inflate(R.layout.events_lv_row, parent, false);
            viewElements=new ViewElements();
            view.setTag(viewElements);

        } else {
            viewElements = (ViewElements) view.getTag();
        }


        viewElements.title=(TextView) view.findViewById(R.id.events_lv_row_title);
        viewElements.description=(TextView) view.findViewById(R.id.events_lv_row_description);
        viewElements.time=(TextView) view.findViewById(R.id.events_lv_row_time);
        viewElements.date=(TextView) view.findViewById(R.id.events_lv_row_date);
        viewElements.url=(ImageView) view.findViewById(R.id.events_lv_row_image);


        viewElements.title.setText(eventsArrayList.get(position).getName());
        String tTime=eventsArrayList.get(position).getDate();

        viewElements.time.setText(tTime.substring(11,18));
        viewElements.description.setText(eventsArrayList.get(position).getDescription());

        viewElements.date.setText(tTime.substring(0,10));
       //viewElements.url.setImageResource(R.drawable.com_facebook_button_like);

System.out.println("123458 :"+position);
        //.load(eventsArrayList.get(position).getImageUrl())
        if(eventsArrayList.get(position).getCover_url()!=null || eventsArrayList.get(position).getCover_url().equals("")) {
            Picasso.with(context)
                    .load(eventsArrayList.get(position).getCover_url())
                    .placeholder(R.drawable.com_facebook_button_like) // optional
                    .error(R.drawable.com_facebook_button_like)         // optional
                    .into(viewElements.url);
        }

        else
        {
            Picasso.with(context)
                    .load("hj")
                    .placeholder(R.drawable.com_facebook_button_like) // optional
                    .error(R.drawable.com_facebook_button_like)         // optional
                    .into(viewElements.url);

        }
        return view;
    }



}
