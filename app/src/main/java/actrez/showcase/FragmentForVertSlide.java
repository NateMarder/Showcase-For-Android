package actrez.showcase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class FragmentForVertSlide extends Fragment {
    private static final String TitleKey = "title_key";
    private static final String NewDescriptionKey = "new_description_key";
    private static final String LocationKey = "location_key";
    private static final String URLsKey_Array = "urls_key";
    private static final String PositionKey = "position_key";
    private static final String TAG_URLs_ARRAY = "urls array key";
    private static final String TAG_IMAGECOUNT = "image_count";
    ImageLoader imageLoader;
    String [] imageURL_array;
    String title = "";
    String location = "";
    String description = "";
    Integer currentPosition = 0;
    Integer imageCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.vertical_slide_frag_layout, container, false);
        Bundle bundle = getArguments();

        //Prepare image loader, and don't prevent it from re-initializing
        this.imageLoader =ImageLoader.getInstance();
        if (!this.imageLoader.isInited()){
            imageLoader.destroy();//just in case
            imageLoader = ImageLoader.getInstance();
            this.imageLoader.init(ImageLoaderConfiguration.createDefault(FragmentForVertSlide.this.getActivity().getApplicationContext()));
        }

        if (bundle != null) {
            // bundle is sent from FragmentPager Class within FragtActivity_VertSlide
            title = bundle.getString(TitleKey);
            location = bundle.getString(LocationKey);
            description = bundle.getString(NewDescriptionKey);
            imageURL_array = bundle.getStringArray(URLsKey_Array);
            currentPosition = bundle.getInt(PositionKey);
            imageCount = bundle.getInt(TAG_IMAGECOUNT);
        }

        TextView textView1 = (TextView) view.findViewById(R.id.TextView_Layout1);
        textView1.setText(title);
        TextView textView2 = (TextView) view.findViewById(R.id.TextView_Layout2);
        textView2.setText(location);
        TextView textView3 = (TextView) view.findViewById(R.id.TextView_Layout3);
        textView3.setText(description);
        ImageView imageView = (ImageView) view.findViewById(R.id.ImageView_Layout);
        this.imageLoader.displayImage(imageURL_array[0], imageView);
        imageView.isClickable();
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //do this when clicked...
                if (imageURL_array[0].matches("http://www.clubwebsite.co.uk/img/misc/noImageAvailable.jpg")) {
                    Toast toast = new Toast(FragmentForVertSlide.this.getActivity());
                    toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                    Toast.makeText(FragmentForVertSlide.this.getActivity(), "Sorry, no images!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent in = new Intent(getActivity(), FragActivity_ImageGallery.class);
                    in.putExtra(TAG_URLs_ARRAY, imageURL_array);
                    startActivity(in);
                }
            }
        });//ends onclickListener
        Log.i("System.out", "---> FragmentForVertSlide");
        return view;

    }
}//Ends Main FragmentForVertSlide Class...
