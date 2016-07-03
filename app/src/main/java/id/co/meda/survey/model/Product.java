package id.co.meda.survey.model;

import java.io.Serializable;

/**
 * Created by Faris on 27/04/2016.
 */
public class Product{

    private String name;
    private String category;
    private String description;
    private byte[] photo;
    private Barcode barcode;
    private int point;

    public Product(String name, String category, String description, byte[] photo, Barcode barcode) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.photo = photo;
        this.barcode = barcode;
        this.point = 500;
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

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
