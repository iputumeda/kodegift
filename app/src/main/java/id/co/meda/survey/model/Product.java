package id.co.meda.survey.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Faris on 27/04/2016.
 */
public class Product implements Serializable {

    private String name;
    private String category;
    private String description;
    private List<byte[]> photos;
    private Barcode barcode;
    private int point;

    public Product(String name, String category, String description, List<byte[]> photos, Barcode barcode) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.photos = photos;
        this.barcode = barcode;
        this.point = 500;
    }

    public Product(String name, String category, String description,Barcode barcode) {
        this(name, category, description, new ArrayList<byte[]>(), barcode);
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

    public List<byte[]> getPhotos() {
        return photos;
    }

    public void addPhoto(byte[] photo){
        photos.add(photo);
    }

    public void removePhoto(int index){
        photos.remove(index);
    }

    public void setPhoto(int index, byte[] newPhoto){
        if(photos.get(index)!= null){
            photos.add(index, newPhoto);
        }else{
            addPhoto(newPhoto);
        }
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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", photos =" + photos.toString() +
                ", barcode=" + barcode +
                ", point=" + point +
                '}';
    }
}
