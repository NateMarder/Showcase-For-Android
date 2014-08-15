/**
 * @Author Nate Marder [http://nathanmarder.com | https://github.com/NateMarder]
 * @Company ActivityRez [http://activityrez.com]
 * @Date 8/06/2014
 * @Program_Description This program was designed as a type of kiosk-application which would allow
 *  provide tourists and customers with a simple user interface within which they would be able to
 *  peruse potential activities they would like to purchase.  For more info about ActivityRez and
 *  their unique business model, see their YouTube channel below
 *  [https://www.youtube.com/channel/UC9GOamzTVopgCIZqizjrPQQ]
 * @Class_Description the FragActivity_VertSlide class allows user to swipe vertically through FragmentForVertSlide -
 *  fragments which show all the information for an activity.  This class' functioning is supported
 *  by two other classes: the VerticalViewPager class and a private-inner class called
 *  FragPagerAdapter, which extends the FragmentPagerAdapter class.  The FragmentPagerAdapter feeds
 *  data into the VerticalViewPager instance (line 49).  This class knows tells the
 *  VerticalViewPager instance where to begin through data received from an intent from the
 *  Activity_ListView (line 43-44).
 *
 */
package actrez.showcase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class FragActivity_VertSlide extends FragmentActivity {

    protected static ArrayList<RezObject> thisBook = new ArrayList<RezObject>();
    private static final String TAG_POSITION = "position";
    protected static int indexPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thisBook = Activity_Splash.getBook();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.factivity2_layout);

        Intent i = getIntent(); //comes from the Activity_ListView  onclick listener
        indexPosition = i.getIntExtra(TAG_POSITION, 0);

        //This setup enables vertical swiping of fragment elements
        FragPagerAdapter fragPagerAdapter = new FragPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        VerticalViewPager vertical_ViewPager = (VerticalViewPager) findViewById(R.id.VerticalViewPager_Within_FA2Layout);
        vertical_ViewPager.setAdapter(fragPagerAdapter);
        vertical_ViewPager.setCurrentItem(indexPosition);
    }

    //This inner class Feeds fragment instances into vertical view pager instance...
    private class FragPagerAdapter extends FragmentPagerAdapter {
        private static final String TitleKey = "title_key";
        private static final String NewDescriptionKey = "new_description_key";
        private static final String LocationKey = "location_key";
        private static final String URLsKey_Array = "urls_key";
        private static final String PositionKey = "position_key";
        protected ArrayList<RezObject> activities;
        protected String[] titles;
        protected String[] locations;
        protected String[] descriptions;
        protected ArrayList<String[]> urls_arraylist_of_arrays;

        public FragPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            activities = Activity_Splash.getBook();
            titles = new String[activities.size()];
            for (int i = 0; i < activities.size(); i++) {
                titles[i] = activities.get(i).getTitle();
            }
            locations = new String[activities.size()];
            for (int i = 0; i < activities.size(); i++) {
                locations[i] = activities.get(i).getDest();
            }
            descriptions = new String[activities.size()];
            for (int i = 0; i < activities.size(); i++) {
                descriptions[i] = activities.get(i).getDesc();
            }
            urls_arraylist_of_arrays = new ArrayList<String[]>(activities.size());
            for (int i = 0; i < activities.size(); i++) {
                urls_arraylist_of_arrays.add(i, activities.get(i).getImageURL_ArrayCollection());
            }
        }

        @Override
        public Fragment getItem(int index) {
            //bundle is needed to create FragmentForVertSlide instances
            Bundle bundle = new Bundle(); // Goes to the Fragment Class "FragmentForVertSlide"....
            bundle.putString(TitleKey, titles[index]); //Title of Activity
            bundle.putString(NewDescriptionKey, descriptions[index]); //Description of Activity
            bundle.putString(LocationKey, locations[index]); //Location of Activity
            bundle.putStringArray(URLsKey_Array, urls_arraylist_of_arrays.get(index)); //Associated image URLs
            bundle.putInt(PositionKey, index); //which activity to display
            FragmentForVertSlide fragVerticalSlide = new FragmentForVertSlide();
            fragVerticalSlide.setArguments(bundle);
            return fragVerticalSlide;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return activities.size();
        }
    }//ends FragmentPagerAdapter Class
}


