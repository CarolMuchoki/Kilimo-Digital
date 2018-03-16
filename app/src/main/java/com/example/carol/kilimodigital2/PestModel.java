package com.example.carol.kilimodigital2;

import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carol on 3/13/2018.
 */

public class PestModel {
    private String pestName;
    private int photoId;
    private String readMore;
    private Button showMore;

    public String getPestName() {
        return pestName;
    }
    public void setPestName(String pestName) {
        this.pestName = pestName;
    }
    public String getReadMore() {
        return readMore;
    }
    public void setReadMore(String readMore) {
        this.readMore = readMore;
    }

    public int getPhotoId() {

        return photoId;
    }

    public void setPhotoIdId(int photoId) {
        this.photoId = photoId;
    }

   /*public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }*/
}
