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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nathan on 7/29/2014.
 */

public class Activity_ListView extends ListActivity {
    //object/data declarations...
    private static final String TAG_ACTIVITYNAME = "title";
    private static final String TAG_DESCRIPTION = "shortDesc";
    private static final String TAG_DESTINATION = "destination";
    private static final String TAG_URLs = "urls_arraylist_of_arrays";
    private static final String TAG_POSITION = "position";
    private static final String TAG_SIZE = "size";
    private static final String TAG_OPTION = "1";
    protected static ArrayList<RezObject> thisBook = new ArrayList<RezObject>();
    protected static Intent i;
    protected static ListView mainListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.factivity5_layout); //links this activity to the it's corresponding .xml layout resource
        mainListView = getListView();
        thisBook = Activity_Splash.getBook();

        //get the user's choice so we know which onClickListener to initiate
        i = getIntent();
        int option = i.getIntExtra(TAG_OPTION, -1);

        //populate the Listview using data supplied by the rezdapter
        ListAdapter rezdapter = new ListViewAdapter(Activity_ListView.this, R.layout.listview_singlerow_layout, thisBook);
        setListAdapter(rezdapter);

        //Where to go next, method 1 or method 2....
        switch (option) {
            case 1: // case one intent initiates FragActivity_NestedGallery
                mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent in = new Intent(Activity_ListView.this, FragActivity_NestedGallery.class);
                        in.putExtra(TAG_ACTIVITYNAME, thisBook.get(position).getTitle());
                        in.putExtra(TAG_DESTINATION, thisBook.get(position).getDest());
                        in.putExtra(TAG_DESCRIPTION, thisBook.get(position).getDesc());
                        in.putExtra(TAG_POSITION, position);
                        in.putExtra(TAG_SIZE, thisBook.size());
                        in.putStringArrayListExtra(TAG_URLs, thisBook.get(position).getImageURLCollection());
                        startActivity(in);
                    }
                });
                break;

            case 2: // case two intent initiates FragActivity_VerticalSlide
                mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent in = new Intent(Activity_ListView.this, FragtActivity_VertSlide.class);
                        in.putExtra(TAG_POSITION, position);
                        startActivity(in);
                    }
                });
                break;

            case -1: // case -1 means something went wrong, but user still goes to FragActivity_VerticalSlide
                mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast toast = new Toast(Activity_ListView.this);
                        toast.makeText(Activity_ListView.this, "Hmmmm...", Toast.LENGTH_SHORT).show();
                        //even though something went wrong lets not stop the action....
                        Intent in = new Intent(Activity_ListView.this, FragtActivity_VertSlide.class);
                        in.putExtra(TAG_POSITION, position);
                        startActivity(in);
                    }
                });
                break;
        }//ends switch statement that sets mainListView.setOnItemClickListener
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
