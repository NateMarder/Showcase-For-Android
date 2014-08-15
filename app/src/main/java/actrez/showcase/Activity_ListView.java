/**
 * @Author Nate Marder [http://nathanmarder.com | https://github.com/NateMarder]
 * @Company ActivityRez [http://activityrez.com]
 * @Date 8/06/2014
 * @Program_Description This program was designed as a type of kiosk-application which would allow
 *  provide tourists and customers with a simple user interface within which they would be able to
 *  peruse potential activities they would like to purchase.  For more info about ActivityRez and
 *  their unique business model, see their YouTube channel below
 *  [https://www.youtube.com/channel/UC9GOamzTVopgCIZqizjrPQQ]
 * @Class_Description The Activity_ListView class provides the user with a customized and vertically-scrollable listview
 *  element to get a quick overview of possible activities to explore further.  Clicking on any
 *  item within this listview launches the user into a detailed view of that item with images
 *  (FragActivity_VertSlide call).  Data for the listview is fed from a a customized ArrayAdapter-
 *  class called ListViewAdapter.
 *
 */


package actrez.showcase;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Activity_ListView extends ListActivity {
    //object/data declarations...
    private static final String TAG_POSITION = "position";
    protected static ArrayList<RezObject> thisBook = new ArrayList<RezObject>();
    protected static ListView mainListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.factivity5_layout); //links this activity to the it's corresponding .xml layout resource
        mainListView = getListView();
        thisBook = Activity_Splash.getBook();

        //populate the Listview using data supplied by the rezdapter
        ListAdapter rezdapter = new ListViewAdapter(Activity_ListView.this, R.layout.listview_singlerow_layout, thisBook);
        setListAdapter(rezdapter);

        //set the onITEM click listener for the ListView instance called mainListView
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(Activity_ListView.this, FragActivity_VertSlide.class);
                in.putExtra(TAG_POSITION, position);
                startActivity(in);
            }
        });
    }

    /**
     * This ListViewAdapter extends the ArrayAdapter class, and is responsible
     * for feeding data into the ListView element within the Activity_MainOptions
     * activity.
     */
    private class ListViewAdapter extends ArrayAdapter<RezObject> {
        /*        ArrayList<RezObject> activitiesToDisplay;*/
        Context context;
        private int resource;
        private String[] lvTitles;
        private String[] lvLocations;
        private String[] lvDescriptions;
        private int[] lvImageCounts;
        private int size;

        public ListViewAdapter(Context context, int resource, ArrayList<RezObject> objects) {
            super(context, resource, objects);
            this.resource = resource;
            this.context = context;
            size = objects.size();

            lvTitles = new String[size];
            for (int i = 0; i < objects.size(); i++) {
                lvTitles[i] = objects.get(i).getTitle();
            }

            lvLocations = new String[size];
            for (int i = 0; i < objects.size(); i++) {
                lvLocations[i] = objects.get(i).getDest();
            }

            lvDescriptions = new String[size];
            for (int i = 0; i < objects.size(); i++) {
                lvDescriptions[i] = objects.get(i).getDesc();
            }

            lvImageCounts = new int[size];
            for (int i = 0; i < objects.size(); i++) {
                lvImageCounts[i] = objects.get(i).getImageCount();
            }
        }

        private class ViewHolder {
            TextView txtTitle;
            TextView txtLocation;
            TextView txtImageCount;
            TextView txtDescription;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;// = null;

            if (view == null) {
                LayoutInflater inflater = Activity_ListView.this.getLayoutInflater();
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

            holder.txtTitle.setText(lvTitles[position]);
            holder.txtLocation.setText(lvLocations[position]);
            holder.txtImageCount.setText("Images: " + lvImageCounts[position] + " ");
            holder.txtDescription.setText(lvDescriptions[position]);
            return view;
        }

    }//ends ListViewAdapter inner-class
}//ends Activity_MainOptions class
