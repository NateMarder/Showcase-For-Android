/**
 * @Author Nate Marder [http://nathanmarder.com | https://github.com/NateMarder]
 * @Company ActivityRez [http://activityrez.com]
 * @Date 8/06/2014
 * @Program_Description This program was designed as a type of kiosk-application which would allow
 *  provide tourists and customers with a simple user interface within which they would be able to
 *  peruse potential activities they would like to purchase.  For more info about ActivityRez and
 *  their unique business model, see their YouTube channel below
 *  [https://www.youtube.com/channel/UC9GOamzTVopgCIZqizjrPQQ]
 * @Class_Description The RezObject class has many functions but can be summarized by stating that an
 *  instance of this class represents an instance of an actual ActivityRez activity.  Instances from
 *  this class are used to populate elements throughout this project.
 *
 */
package actrez.showcase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class RezObject implements Parcelable {
    private String title;
    private String vendor;
    private String destination;
    private String description;
    private ArrayList<String> imageURLS_arraylist;
    private int imageCount;
    private int bitmapCount;
    String[] imageURLs_array;

    //activity_main_options_layout constructor
    public RezObject() {
        this.title = "";
        this.vendor = "";
        this.destination = "";
        this.description = "";
        this.imageURLS_arraylist = new ArrayList<String>();
        this.imageCount = 0;
        this.bitmapCount = 0;
    }

    //Parcelable instance constructor
    private RezObject(Parcel in) {
        title = in.readString();
        vendor = in.readString();
        destination = in.readString();
        description = in.readString();
        imageURLS_arraylist = (ArrayList<String>) in.readSerializable();
        imageCount = in.readInt();
        bitmapCount = in.readInt();
    }

    //More Parcelable Implementation Methods....
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeString(vendor);
        out.writeString(destination);
        out.writeString(description);
        out.writeSerializable(imageURLS_arraylist);
        out.writeInt(imageCount);
        out.writeInt(bitmapCount);
    }

    public int describeContents() {
        return 0;
    }

    public static final Creator<RezObject> CREATOR
            = new Creator<RezObject>() {
        public RezObject createFromParcel(Parcel in) {
            return new RezObject(in);
        }

        public RezObject[] newArray(int size) {
            return new RezObject[size];
        }
    };

    //getters and setters....
    public void addDesc(String desc) {
        //This business gets rid of short descriptions with spacing and mistakes and
        //also combines multiple paragraphs into one which makes formatting easier
        StringTokenizer tokenizer = new StringTokenizer(desc);
        String next = tokenizer.nextToken();
        while (tokenizer.hasMoreElements()) {
            if (next.endsWith(".") || next.endsWith("!") || next.endsWith("?")) {
                next += "  " + tokenizer.nextToken();
            } else {
                next += " " + tokenizer.nextToken();
            }
        }
        this.description = next;
    }

    public void addDest(String dest) {
        this.destination = dest.trim();
    }

    public void addTitle(String title) {
        this.title = title.trim();
    }

    public void addImageURL(String hash) {
        //This URL prefix/postfix brings through the the full-sized image version...
        String addTHIS = "https://devmedia.activityrez.com/media/" + hash + "/display";
        imageURLS_arraylist.add(addTHIS);
        imageCount++;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDesc() {
        return this.description;
    }

    public String getDest() {
        return this.destination;
    }

    public Integer getImageCount() {
        return this.imageCount;
    }

    public ArrayList getImageURLCollection() {
        if (this.imageURLS_arraylist.size() == 0) {
            //no image available image hosted within a public folder within google drive
            imageURLS_arraylist.add("https://5bf9fc6a06a40f21655de95c16758804f9ccd7fd.googledrive.com/host/0BxqBg0gGtrRkR1ktQ2FSQmNMR2s/no_images.png");
        }
        return this.imageURLS_arraylist;
    }

    // Converts ArrayList to  STRING ARRAY then returns it...
    public String[] getImageURL_ArrayCollection() {
        //avoid null pointer exceptions...
        if (imageURLS_arraylist.size() == 0) {
            //no image available image hosted within a public folder within google drive
            String emptyURL = "https://5bf9fc6a06a40f21655de95c16758804f9ccd7fd.googledrive.com/host/0BxqBg0gGtrRkR1ktQ2FSQmNMR2s/no_images.png";
            imageURLS_arraylist.add(emptyURL);
        }
        //convert arraylist <String> to String[]
        this.imageURLs_array = new String[imageURLS_arraylist.size()];
        for (int i = 0; i < imageURLS_arraylist.size(); i++) {
            imageURLs_array[i] = imageURLS_arraylist.get(i);
        }
        return imageURLs_array;
    }
}










