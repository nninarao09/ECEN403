package com.example.audioharmonizer;

import android.app.Application;

public class GlobalClass extends Application{

    private String NameOfSong;
    private int BeatsPerMeasure;
    private int BeatsPerMinute;


    public String getNameOfSong() {
        return NameOfSong;
    }

    public void setNameOfSong(String aName) {
        NameOfSong = aName;
    }

    public int getBeatsPerMeasure() {
        return BeatsPerMeasure;
    }

    public void setBeatsPerMeasure(int aBeatsPerMeasure) {
        BeatsPerMeasure = aBeatsPerMeasure;
    }

    public int getBeatsPerMinute() {
        return BeatsPerMinute;
    }

    public void setBeatsPerMinute(int aBeatsPerMinute) {
        BeatsPerMinute = aBeatsPerMinute;
    }

}