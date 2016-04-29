package id.co.meda.survey.model;

import java.io.Serializable;

/**
 * Created by Faris on 27/04/2016.
 */
public class Product implements Serializable{

    private String name;
    private String category;
    private String description;
    private String photo;
    private Barcode barcode;

    public Product(String name, String category, String description, String photo, Barcode barcode) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.photo = photo;
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }
}
