package ph.kita.devsquare.com.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by abnonymous on 6/22/16.
 */
public class Item implements Parcelable{

    private int id;
    private String name;
    private float price;
    private String tag;
    private float stock;
    private float qualitytNWeight;
    private String imageURL;
    private Date dateCreated;

    public Item(){}

    public Item(int id, String name, float price, String tag, float stock, float qualitytNWeight, String imageURL, Date dateCreated){
        this.id = id;
        this.name = name;
        this.price = price;
        this.tag = tag;
        this.stock = stock;
        this.qualitytNWeight = qualitytNWeight;
        this.imageURL = imageURL;
        this.dateCreated = dateCreated;
    }

    protected Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        price = in.readFloat();
        tag = in.readString();
        stock = in.readFloat();
        qualitytNWeight = in.readFloat();
        imageURL = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getStock() {
        return stock;
    }

    public void setStock(float stock) {
        this.stock = stock;
    }

    public float getQualitytNWeight() {
        return qualitytNWeight;
    }

    public void setQualitytNWeight(float qualitytNWeight) {
        this.qualitytNWeight = qualitytNWeight;
    }

    public float incQualitytNWeight() {
        ++qualitytNWeight;
        return this.qualitytNWeight;
    }

    public float decQualitytNWeight() {
        --qualitytNWeight;
        return this.qualitytNWeight;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeFloat(price);
        parcel.writeString(tag);
        parcel.writeFloat(stock);
        parcel.writeFloat(qualitytNWeight);
        parcel.writeString(imageURL);
    }

}
