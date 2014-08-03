package actrez.showcase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Nathan on 7/30/2014.
 */
public class FragActivity_ImageGallery extends FragmentActivity {
    ViewPager pager;
    String[] imageURLs;
    private static final String TAG_URLs_ARRAY = "urls array key";
    protected int pagerPosition = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_container_layout);
        Intent in = getIntent();//this data is being sent from FragtActivity_VertSlide
        imageURLs=in.getStringArrayExtra(TAG_URLs_ARRAY);
        pager = (ViewPager) findViewById(R.id.ViewPager_Horizontal);
        pager.setAdapter(new GalleryImage(FragActivity_ImageGallery.this, imageURLs));
        pager.setCurrentItem(pagerPosition);
        Log.i("System.out", "---> FragActivity_ImageGallery");
    }

    class GalleryImage extends PagerAdapter {

        private String[] images;
        private LayoutInflater inflater;
        ImageLoader imageLoader;

        GalleryImage(Context context, String[] images) {
            this.images = images;
            inflater = getLayoutInflater();
            imageLoader = ImageLoader.getInstance();
            if (!this.imageLoader.isInited()){
                imageLoader.destroy();//just in case
                imageLoader = ImageLoader.getInstance();
                imageLoader.init(ImageLoaderConfiguration.createDefault(context));
            }
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object instantiateItem(View view, int position) {
            final View imageLayout = inflater.inflate(R.layout.imageview_container_layout, null);
            final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.ImageView_ActivityImage_Layout);
            imageLoader.displayImage(images[position], imageView);
            ((ViewPager) view).addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
    }
}