package com.example.audioharmonizer;

import android.app.Application;
import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

public class GlobalClass extends Application{

    private String NameOfSong;
    private String BeatsPerMeasure;
    private String BeatsPerMinute;
    private BluetoothDevice device;
    private String[] AutomaticArray = {"Filler", "Filler", "Filler", "Filler", "Filler", "Filler", "Filler", "Filler", "Filler"};
    ArrayList<String> ManualArrayList = new ArrayList<String>();
    private String[] ListOfRecordings;
    private BluetoothConnectionService mBluetoothConnection;

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice aDevice) {
        device = aDevice;
    }


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

    public ArrayList<String> getManualArrayList() {
        return ManualArrayList;
    }

    public void setManualArrayList(ArrayList<String>  ManualArrayList) {
        this.ManualArrayList = ManualArrayList;
    }

    public String[] getListOfRecordings() {
        return ListOfRecordings;
    }

    public void setListOfRecordings(String[] ListOfRecordings) {
        this.ListOfRecordings = ListOfRecordings;
    }

    public BluetoothConnectionService getmBluetoothConnection() {
        return mBluetoothConnection;
    }

    public void setmBluetoothConnection(BluetoothConnectionService mBluetoothConnection) {
        this.mBluetoothConnection = mBluetoothConnection;
    }
}