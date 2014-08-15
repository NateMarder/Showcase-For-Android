/**
 * @Author Nate Marder [http://nathanmarder.com | https://github.com/NateMarder]
 * @Company ActivityRez [http://activityrez.com]
 * @Date 8/06/2014
 * @Program_Description This program was designed as a type of kiosk-application which would allow
 *  provide tourists and customers with a simple user interface within which they would be able to
 *  peruse potential activities they would like to purchase.  For more info about ActivityRez and
 *  their unique business model, see their YouTube channel below
 *  [https://www.youtube.com/channel/UC9GOamzTVopgCIZqizjrPQQ]
 * @Class_Description The_MainOptions class provides the user with options to change
 *  preferences/settings, refresh data (Activity_Splash call), or to view the activities
 *  (Activity_Listview call).
 *
 */
package actrez.showcase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

public class Activity_MainOptions extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_options_layout);

        Button goto_ListView = (Button) findViewById(R.id.button2);
        goto_ListView.setText("View Activities ");
        goto_ListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_MainOptions.this, Activity_ListView.class);
                startActivity(i);
            }
        });

        Button goto_Settings = (Button) findViewById(R.id.Button3_Settings);
        goto_Settings.setText("Settings ");
        goto_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_MainOptions.this, PrefActivity_Settings.class);
                startActivity(i);
            }
        });

        Button goto_Refresh = (Button) findViewById(R.id.Button4_Refresh);
        goto_Refresh.setText("Refresh ");
        goto_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Splash.activityBook = new ArrayList<RezObject>();//get rid of old data;
                Intent i = new Intent(Activity_MainOptions.this, Activity_Splash.class);
                startActivity(i);
                Activity_MainOptions.this.finish();
            }
        });

        if (!ImageLoader.getInstance().isInited()) {
            setImageLoader();// Set up Universal ImageLoader
        }
    }

    private void setImageLoader() {
        /**
         * The ImageLoaderConfiguration is application global, and makes methods and instances contained
         * within the support library "Android-Universal-Image-Loader" possible.  Read more about this
         * library here: https://github.com/nostra13/Android-Universal-Image-Loader
         */
        DisplayImageOptions defaultOptions;
        ImageLoaderConfiguration config;

        defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageOnFail(R.drawable.no_image_found) //will not show with current configuration of RezObject getURL collection methods
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .cacheOnDisk(true)
                .showImageOnLoading(R.drawable.logo_fullsize)
                .build();
        config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).build();

        ImageLoader.getInstance().init(config);
    }
}//ends Activity_MainOptions class