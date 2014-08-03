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

/**
 * Created by Nathan on 7/29/2014.
 */

public class ListViewActivity1 extends ListActivity {
    //object/data declarations...
    private static final String TAG_ACTIVITYNAME = "title";
    private static final String TAG_DESCRIPTION = "shortDesc";
    private static final String TAG_DESTINATION = "destination";
    private static final String TAG_URLs = "urls_arraylist_of_arrays";
    private static final String TAG_POSITION = "position";
    private static final String TAG_SIZE = "size";
    protected static ArrayList<RezObject> thisBook = new ArrayList<RezObject>();
    public static Intent i;
    ListView mainListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.factivity5_layout); //links this activity to the it's corresponding .xml layout resource
        mainListView = getListView();
        thisBook = Activity_Splash.getBook();
        ListAdapter rezdapter = new ListViewAdapter(ListViewActivity1.this, R.layout.listview_singlerow_layout, thisBook);
        setListAdapter(rezdapter);

        /**Depending on which item is clicked, different data is passed from the 1st to 2nd activities.
         * Data is gathered from the mainListView, which gathers data from the this activity's
         * ListView of which there's only one.
         * **/

         mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(ListViewActivity1.this, FragActivity_NestedGallery.class);
                in.putExtra(TAG_ACTIVITYNAME, thisBook.get(position).getTitle());
                in.putExtra(TAG_DESTINATION, thisBook.get(position).getDest());
                in.putExtra(TAG_DESCRIPTION, thisBook.get(position).getDesc());
                in.putExtra(TAG_POSITION,position);
                in.putExtra(TAG_SIZE,thisBook.size());
                in.putStringArrayListExtra(TAG_URLs, thisBook.get(position).getImageURLCollection());
                startActivity(in);
            }
        });

        Log.i("System.out", "---> ListViewActivity1");
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
                LayoutInflater inflater = ListViewActivity1.this.getLayoutInflater();
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
