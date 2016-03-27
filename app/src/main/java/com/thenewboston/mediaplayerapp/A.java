package com.thenewboston.mediaplayerapp;

/**
 * Created by PC on 26-03-2016.
 */
public class A {

    public int index;
    public String starttime;
    public String endtime;
    public String Line;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        int x=3600*Integer.parseInt(starttime.substring(0,2));
        int y=60*Integer.parseInt(starttime.substring(3,5));
        int z=Integer.parseInt(starttime.substring(6,8));
        int a=x+y+z;

        this.starttime=Integer.toString(a);
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        int x=3600*Integer.parseInt(starttime.substring(0,2));
        int y=60*Integer.parseInt(starttime.substring(3,5));
        int z=Integer.parseInt(starttime.substring(6,8));
        int a=x+y+z;

        this.endtime=Integer.toString(a);
    }

    public String getLine() {
        return Line;
    }

    public void setLine(String line) {
        Line = line;
    }
}
