package com.example.android.customerapp;

public class noteProductModel {
    private String fnote;

    private noteProductModel(){}

    private noteProductModel(String fnote){
        this.fnote=fnote;
    }


    public String getFnote() {
        return fnote;
    }

    public void setFnote(String fnote) {
        this.fnote = fnote;
    }
}
