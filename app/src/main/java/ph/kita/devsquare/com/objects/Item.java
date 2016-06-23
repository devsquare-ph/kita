package ph.kita.devsquare.com.objects;

import java.util.Date;

/**
 * Created by abnonymous on 6/22/16.
 */
public class Item {

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
}
