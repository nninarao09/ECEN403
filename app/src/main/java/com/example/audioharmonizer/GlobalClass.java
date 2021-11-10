package com.example.audioharmonizer;

import android.app.Application;

public class GlobalClass extends Application{

    private String NameOfSong;
    private String BeatsPerMeasure;
    private String BeatsPerMinute;
    private String[] AutomaticArray = {"Filler", "Filler", "Filler", "Filler", "Filler", "Filler", "Filler", "Filler", "Filler"};


    public String getNameOfSong() {
        return NameOfSong;
    }

    public void setNameOfSong(String aName) {
        NameOfSong = aName;
    }

    public String getBeatsPerMeasure() {
        return BeatsPerMeasure;
    }

    public void setBeatsPerMeasure(String aBeatsPerMeasure) {
        BeatsPerMeasure = aBeatsPerMeasure;
    }

    public String getBeatsPerMinute() {
        return BeatsPerMinute;
    }

    public void setBeatsPerMinute(String aBeatsPerMinute) {
        BeatsPerMinute = aBeatsPerMinute;
    }


    public String[] getAutomaticArray() {
        return AutomaticArray;
    }

    public void setAutomaticArray(String[] AutomaticArray) {
        this.AutomaticArray = AutomaticArray;
    }

}