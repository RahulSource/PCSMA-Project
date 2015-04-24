package pcsma.events.iiitd.pcma_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.populatingClass.DescussionsList;
import pcsma.events.iiitd.pcma_project.populatingClass.Discussion;


/**
 * Created by dell on 4/7/2015.
 */
public class DescussionPageAdapter extends BaseAdapter{

    ArrayList<Discussion> descussions;
    Context context;
    LayoutInflater inflater;

    public DescussionPageAdapter(ArrayList<Discussion> descussions,Context context){

        this.descussions=descussions;
        this.context=context;
        inflater = LayoutInflater.from(this.context);


    }
    @Override
    public int getCount() {
        return descussions.size();
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

        TextView date,time,name, description;

    }



    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewElements viewElements;

        if(view == null) {

            view= inflater.inflate(R.layout.descussion_page_row, parent, false);
            viewElements=new ViewElements();
            view.setTag(viewElements);

        } else {
            viewElements = (ViewElements) view.getTag();
        }


        viewElements.name=(TextView) view.findViewById(R.id.descussion_page_row_tv_name);
        viewElements.description=(TextView) view.findViewById(R.id.descussion_page_row_tv_description);
        viewElements.time=(TextView) view.findViewById(R.id.descussion_page_row_tv_time);
        viewElements.date=(TextView) view.findViewById(R.id.descussion_page_row_tv_date);

        viewElements.name.setText(descussions.get(position).getUser_name());

        String tTime,tDate;

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String te= descussions.get(position).getDate();
        if(te.length()<17 || te==null){

            te=df.format(date);
            tTime=te.substring(10, 18);
            tDate=te.substring(0, 10);
            viewElements.time.setText(tTime);
            viewElements.date.setText(tDate);
        }
        else{

            tTime=te.substring(10, 18);
            tDate=te.substring(0, 10);
            viewElements.time.setText(tTime);
            viewElements.date.setText(tDate);

        }

        viewElements.description.setText(descussions.get(position).getDescription());


        return view;
    }
}

