package pcsma.events.iiitd.pcma_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import pcsma.events.iiitd.pcma_project.R;
import pcsma.events.iiitd.pcma_project.populatingClass.DescussionsList;


/**
 * Created by dell on 4/7/2015.
 */
public class DescussionPageAdapter extends BaseAdapter{

    ArrayList<DescussionsList> descussions;
    Context context;
    LayoutInflater inflater;

    public DescussionPageAdapter(ArrayList<DescussionsList> descussions,Context context){

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

        viewElements.name.setText(descussions.get(position).getName());
        viewElements.time.setText(descussions.get(position).getTime());
        viewElements.description.setText(descussions.get(position).getDescription());
        viewElements.date.setText(descussions.get(position).getDate());

        return view;
    }
}

