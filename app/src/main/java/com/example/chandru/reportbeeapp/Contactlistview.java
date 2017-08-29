package com.example.chandru.reportbeeapp;


public class Contactlistview {
    String name, cont;
    int position;

    public Contactlistview(String name, String cont, int position)
    {

        this.setName(name);
        this.setCont(cont);
        this.setPosition(position);
    }

    public int getposition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }
}
