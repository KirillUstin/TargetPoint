package com.kirk.targetpoint;

public class TargetPointData {
    private double X;
    private double Y;
    private double Z;
    private String name;

    public TargetPointData(double x, double y, double z, String n){
        this.X = x;
        this.Y = y;
        this.Z = z;
        this.name = n;
    }

    public double getX(){
        return this.X;
    }

    public double getY(){
        return this.Y;
    }
    
    public double getZ(){
        return this.Z;
    }

    public String getName(){
        return this.name;
    }
}
