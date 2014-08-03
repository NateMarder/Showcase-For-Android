package actrez.showcase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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
        title=in.readString();
        vendor=in.readString();
        destination=in.readString();
        description=in.readString();
        imageURLS_arraylist=(ArrayList<String>)in.readSerializable();
        imageCount=in.readInt();
        bitmapCount=in.readInt();
    }

    //More Parcelable Implementation Methods....
    public void writeToParcel(Parcel out, int flags){
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
        this.description = desc;
    }

    public void addDest(String dest) {
        this.destination = dest;
    }

    public void addTitle(String title) {
        this.title = title;
    }

    public void addImageURL(String hash) {
        // This URL brings through a re-sized 400px thumbnail version of the original image
        // String addTHIS = "https://devmedia.activityrez.com/media/" + hash + "/thumbnail/height/400";

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
        if (this.imageURLS_arraylist.size()==0){
            imageURLS_arraylist.add("http://www.clubwebsite.co.uk/img/misc/noImageAvailable.jpg");
        }
        return this.imageURLS_arraylist;
    }

    public String[] getImageURL_ArrayCollection(){
        //avoid null pointer exceptions...
        if (imageURLS_arraylist.size()==0){
            String emptyURL = "http://www.clubwebsite.co.uk/img/misc/noImageAvailable.jpg";//stupid cat
            imageURLS_arraylist.add(emptyURL);
        }
        //convert arraylist <String> to String[]
        this.imageURLs_array = new String[imageURLS_arraylist.size()];
        for (int i = 0; i<imageURLS_arraylist.size(); i++){
            imageURLs_array[i]=imageURLS_arraylist.get(i);
        }
        return imageURLs_array;
    }

}










