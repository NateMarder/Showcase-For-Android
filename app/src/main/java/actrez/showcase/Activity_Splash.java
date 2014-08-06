/**
 * @Author Nate Marder [http://nathanmarder.com | https://github.com/NateMarder]
 * @Company ActivityRez [http://activityrez.com]
 * @Date 8/06/2014
 * @Program_Description This program was designed as a type of kiosk-application which would allow
 *  provide tourists and customers with a simple user interface within which they would be able to
 *  peruse potential activities they would like to purchase.  For more info about ActivityRez and
 *  their unique business model, see their YouTube channel below
 *  [https://www.youtube.com/channel/UC9GOamzTVopgCIZqizjrPQQ]
 * @Class_Description The class contains the private-inner-asynchronous class populateRezActs which is
 *  itself responsible for grabbing data from the ActivityRez server.  This class is the lauches
 *  the application and it automatically sends the user to the Activity_MainOptions class when
 *  it has performed it's duty of grabbing data
 *
 */
package actrez.showcase;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_Splash extends Activity {

    private static final String TAG_DATA = "data";  //JSON Nodes
    private static final String TAG_ACTIVITYNAME = "title";
    private static final String TAG_DESCRIPTION = "shortDesc";
    private static final String TAG_DESTINATION = "destination";
    private static final String TAG_MEDIA = "media";
    private static final String TAG_HASH = "hash";
    private static final String TAG_JSONINPUT = "json_input";
    protected static ArrayList<RezObject> activityBook = new ArrayList<RezObject>();
    protected static String web_booker_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_layout);
        ImageView imageView = (ImageView) findViewById(R.id.imageView_splash);
        TextView textView = (TextView) findViewById(R.id.textView_splash);
        imageView.setImageResource(R.drawable.logo_fullsize);
        textView.setText("Loading Items...");

        /**
         * Each activity booking agency has a unique web booker ID code that can be added to
         * their user preferences.  Depending on their web booker ID, specific activities from
         * vendors they work with will automatically populate this application's RezObjects.
         * For the purposes of seeing how this application works, two different dummy IDs
         * can be used: [114568] OR [200024].  The application's default is set to [200024]
         */

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        web_booker_id = sharedPref.getString("web_booker_key", "200024");

        //only get data if you need too
        if (activityBook.size() == 0) {
            new populateRezActs().execute();
        } else {
            int SPLASH_TIME_OUT = 2000;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(Activity_Splash.this, Activity_MainOptions.class);
                    startActivity(i);
                    Activity_Splash.this.finish();//remove Activity from the Activity Stack
                }
            }, SPLASH_TIME_OUT);
        }
    }//ends onCreate Method

    // API call must happen asynchronously to prevent application crash
    private class populateRezActs extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            ServiceHandler_API sh = new ServiceHandler_API();// Creating service handler class instance

            /**
             * There are a couple of sub-domains with dummy data.  Two of which need Web Booker IDs added...
             * 1) https://-----.activityrez.com/wp-content/plugins/flash-api/wsrv.php?  +
             *    token=NEW&service=lookup&action=activities&data[showInWB]=
             * 2) https://demo.activityrez.com/wp-content/plugins/flash-api/wsrv.php?  +
             *    token=NEW&service=lookup&action=activities&data[showInWB]=
             *
             *    [the first URL was removed]
             */

            String url = "https://demo.activityrez.com/wp-content/plugins/flash-api/wsrv.php?token=" +
                    "NEW&service=lookup&action=activities&data[showInWB]=" + web_booker_id;
            String jsonStr = sh.makeServiceCall(url, ServiceHandler_API.GET);
            if (jsonStr != null) {
                try {
                    /**
                     * The following for-loop marches through json data, populating our activity book with different
                     * RezObject objects, that are ultimately displayed in the primary scroll-list.  The first
                     * node that is addressed is the 'data' node, which is an array of json objects - each one has all
                     * the ingredients to make a unique RezObject object, which is done here.  A nested for-loop is
                     * used below to grab media/image URLs; the nested for-loop is needed because the the image URLs are
                     * stored within a nested json-array (media[URLs]);
                     *
                     */
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray activities = jsonObj.getJSONArray(TAG_DATA);
                    for (int i = 0; i < activities.length(); i++) {
                        JSONObject nextData = activities.getJSONObject(i);
                        String theTitle = nextData.getString(TAG_ACTIVITYNAME).replace("&amp;", "&");
                        String theDesc = nextData.getString(TAG_DESCRIPTION).replace("&amp;", "&");
                        String theDest = nextData.getString(TAG_DESTINATION);

                        //the ArrayList of RezObject Objects gets another object
                        RezObject nextRez = new RezObject();
                        nextRez.addTitle(theTitle);
                        nextRez.addDesc(theDesc);
                        nextRez.addDest(theDest);

                        //nested for-loop for grabbing media/image elements out of the json_input-node
                        JSONObject json_input = nextData.getJSONObject(TAG_JSONINPUT);
                        if (json_input.has(TAG_MEDIA)) {
                            JSONArray images = json_input.getJSONArray(TAG_MEDIA);
                            for (int j = 0; j < images.length(); j++) {
                                JSONObject nextMedia = images.getJSONObject(j);
                                String theHash = nextMedia.getString(TAG_HASH);
                                nextRez.addImageURL(theHash);
                            }
                        }
                        activityBook.add(nextRez);
                    }//ends for-loop which populates the Book of activities ("RezObjects")
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Intent i = new Intent(Activity_Splash.this, Activity_MainOptions.class);
            startActivity(i);
            Activity_Splash.this.finish();//remove Activity from the Activity Stack
        }
    }//ends async task

    protected static ArrayList<RezObject> getBook() {
        return activityBook;
    }
}
