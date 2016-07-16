package id.co.meda.survey.model;

import java.io.Serializable;

/**
 * Created by Faris on 27/04/2016.
 */
public class Barcode implements Serializable{

    private String contents;
    private String format;

    public Barcode(String contents, String format) {
        this.contents = contents;
        this.format = format;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
