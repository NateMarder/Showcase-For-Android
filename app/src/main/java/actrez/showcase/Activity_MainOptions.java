package actrez.showcase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;

public class Activity_MainOptions extends Activity {
    private static final String TAG_OPTION = "1";


    @Override
    public void onCreate(Bundle savedInstanceState) {
            //if this activity is not already destroyed...
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_options_layout); //links this activity to the it's corresponding .xml layout resource

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setText("LAUNCH METHOD 1 ");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_MainOptions.this,Activity_ListView.class);
                i.putExtra(TAG_OPTION, 1);
                startActivity(i);

            }
        });
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setText("LAUNCH METHOD 2 ");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_MainOptions.this, Activity_ListView.class);
                i.putExtra(TAG_OPTION,2);
                startActivity(i);

            }
        });
        Button button3 = (Button) findViewById(R.id.Button3_Settings);
        button3.setText("SETTINGS ");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Activity_MainOptions.this, PrefActivity_Settings.class);
                startActivity(i);

            }
        });
        Button button4 = (Button) findViewById(R.id.Button4_Refresh);
        button4.setText("REFRESH DATA ");
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_Splash.activityBook = new ArrayList<RezObject>();
                Intent i = new Intent(Activity_MainOptions.this, Activity_Splash.class);
                startActivity(i);
                Activity_MainOptions.this.finish();
            }
        });

        if (!ImageLoader.getInstance().isInited()){ setImageLoader(); }// Set up Universal ImageLoader
        Log.i("System.out", "---> Activity_MainOptions");
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
                .showImageOnFail(R.drawable.nothing) // resource or drawable
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