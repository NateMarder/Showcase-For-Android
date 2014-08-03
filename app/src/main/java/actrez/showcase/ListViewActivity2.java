package actrez.showcase;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewActivity2 extends ListActivity {
    //object/data declarations...
    private static final String TAG_POSITION = "position";
    protected static ArrayList<RezObject> thisBook = new ArrayList<RezObject>();
    protected ListView mainListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.factivity5_layout); //links this activity to the it's corresponding .xml layout resource
        mainListView = getListView();
        thisBook = Activity_Splash.getBook();
        ListAdapter rezdapter = new ListViewAdapter(ListViewActivity2.this, R.layout.listview_singlerow_layout, thisBook);
        setListAdapter(rezdapter);

        /**Depending on which item is clicked, different data is passed from the 1st to 2nd activities.
         * Data is gathered from the mainListView, which gathers data from the this activity's
         * ListView of which there's only one.
         * **/
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(ListViewActivity2.this, FragtActivity_VertSlide.class);
                in.putExtra(TAG_POSITION,position);
                startActivity(in);
            }
        });

        Log.i("System.out", "---> ListViewActivity2");
    }


    /**
     * This ListViewAdapter extends the ArrayAdapter class, and is responsible
     * for feeding data into the ListView element within the Activity_MainOptions
     * activity.
     */
    public class ListViewAdapter extends ArrayAdapter<RezObject> {
        ArrayList<RezObject> activitiesToDisplay;
        Context context;
        int resource;

        public ListViewAdapter(Context context, int resource, ArrayList<RezObject> objects) {
            super(context, resource, objects);
            this.resource = resource;
            this.context = context;
            this.activitiesToDisplay = objects;
        }

        public class ViewHolder {
            TextView txtTitle;
            TextView txtLocation;
            TextView txtImageCount;
            TextView txtDescription;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder = null;

            if (view == null) {
                LayoutInflater inflater = ListViewActivity2.this.getLayoutInflater();
                view = inflater.inflate(resource, parent, false);
                holder = new ViewHolder();
                holder.txtTitle = (TextView) view.findViewById(R.id.TextView_Title_ListView2);
                holder.txtLocation = (TextView) view.findViewById(R.id.TextView_Location_ListView2);
                holder.txtImageCount = (TextView) view.findViewById(R.id.TextView_ImageCount_ListView2);
                holder.txtDescription = (TextView) view.findViewById(R.id.TextView_Description_ListView2);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            String title = activitiesToDisplay.get(position).getTitle();
            String location = activitiesToDisplay.get(position).getDest();/*+" image count: "+activitiesToDisplay.get(position).getImageCount();*/
            String imageCount = "Images: "+activitiesToDisplay.get(position).getImageCount()+" ";
            String description = activitiesToDisplay.get(position).getDesc();
            holder.txtTitle.setText(title);
            holder.txtLocation.setText(location);
            holder.txtImageCount.setText(imageCount);
            holder.txtDescription.setText(description);

            return view;
        }


    }//ends ListViewAdapter inner-class
}//ends Activity_MainOptions class
