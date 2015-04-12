package pcsma.events.iiitd.pcma_project.adapter;




        import java.util.ArrayList;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;
        import android.widget.RelativeLayout;
        import pcsma.events.iiitd.pcma_project.R;
        import pcsma.events.iiitd.pcma_project.populatingClass.SettingsAttributes;

public class SettingsListViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<SettingsAttributes> settingsContent;

    public SettingsListViewAdapter(Context context, ArrayList<SettingsAttributes> settingsContent) {
        this.context = context;
        this.settingsContent = settingsContent;
    }

    static class ViewHolder{
        TextView header, content;
        RelativeLayout rowlayout;
    }
    @Override
    public int getCount() {
        return settingsContent.size();
    }

    @Override
    public Object getItem(int position) {
        return settingsContent.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.settings_row_layout, parent, false);
            holder = new ViewHolder();

            holder.rowlayout=(RelativeLayout) convertView.findViewById(R.id.settings_row);
            holder.content = (TextView) convertView.findViewById(R.id.settings_content);
            holder.header = (TextView) convertView.findViewById(R.id.settings_header);


            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.header.setText(((SettingsAttributes) getItem(position)).getHeading());
        holder.content.setText(((SettingsAttributes) getItem(position)).getContent());


        return convertView;
    }

}
