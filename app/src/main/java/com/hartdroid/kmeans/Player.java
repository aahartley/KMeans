package com.hartdroid.kmeans;

public class Player {
    private double apm;
    private double height;
    private double timePlayed;
    private double age;
    private double ppm;
    private int label;
    private double distance;

    @Override
    public String toString() {
        return "Player{" +
                "apm=" + apm +
                ", height=" + height +
                ", timePlayed=" + timePlayed +
                ", age=" + age +
                ", ppm=" + ppm +
                ", label="+ label+
                ", distance="+ distance+
                '}';
    }
    public Player(){

    }
    public Player(double apm, double height, double timePlayed, double age, double ppm){
        this.apm = apm;
        this.height = height;
        this.timePlayed = timePlayed;
        this.age = age;
        this.ppm = ppm;

    }
    public int getLabel(){
        return label;
    }
    public void setLabel(int label){
        this.label = label;
    }
    public double getDistance(){
        return distance;
    }
    public void setDistance(double distance){
        this.distance = distance;
    }

    public double getApm() {
        return apm;
    }

    public void setApm(double apm) {
        this.apm = apm;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(double timePlayed) {
        this.timePlayed = timePlayed;
    }

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getPpm() {
        return ppm;
    }

    public void setPpm(double ppm) {
        this.ppm = ppm;
    }
}
