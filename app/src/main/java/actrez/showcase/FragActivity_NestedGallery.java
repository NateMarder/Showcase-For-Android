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
        import android.widget.TextView;

        import com.nostra13.universalimageloader.core.ImageLoader;
        import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

        import java.util.ArrayList;


public class FragActivity_NestedGallery extends FragmentActivity {

    private static final String TAG_NAME = "title";
    private static final String TAG_DESTINATION = "destination";
    private static final String TAG_DESCRIPTION = "shortDesc";
    private static final String TAG_URLs = "urls_arraylist_of_arrays";
    protected int pagerPosition = 0;
    protected String emptyURL = " ";
    protected ViewPager pager;
    protected ArrayList <String> imageurls = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_layout);
        Intent in = getIntent();

        imageurls=in.getStringArrayListExtra(TAG_URLs);
        if (imageurls.size()==0){imageurls.add(emptyURL);}
        String[] imageURLs = new String[imageurls.size()];
        for(int i =0; i<imageurls.size();i++){
            imageURLs[i]=imageurls.get(i);
        }

        TextView lblName = (TextView)findViewById(R.id.TextView_Title_ActivityDetailLayout);
        TextView lblDest = (TextView) findViewById(R.id.TextView_Location_ActivityDetailLayout);
        TextView lblDesc = (TextView) findViewById(R.id.TextView_Description_ActivityDetailLayout);
        TextView lblBottomRight = (TextView) findViewById(R.id.TextView_UnderImage_Layout);
        pager = (ViewPager) findViewById(R.id.ViewPager_Horizontal);

        lblName.setText(in.getStringExtra(TAG_NAME));
        lblDest.setText(in.getStringExtra(TAG_DESTINATION));
        lblDesc.setText(in.getStringExtra(TAG_DESCRIPTION));
        if (imageURLs.length>1){ lblBottomRight.setText("Swipe For Next Image ");}

        pager.setAdapter(new GalleryImage(FragActivity_NestedGallery.this, imageURLs));
        pager.setCurrentItem(pagerPosition);
        Log.i("System.out", "---> FragActivity_NestedGallery ");
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