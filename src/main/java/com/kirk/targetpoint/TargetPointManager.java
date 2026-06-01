package com.kirk.targetpoint;

import java.util.ArrayList;
import java.util.List;

public class TargetPointManager {
    ArrayList<TargetPointData> points = new ArrayList<>();

    public List<TargetPointData> getPoints(){
        return points;
    }

    public void addPoint(double x, double y, double z, String name){
        TargetPointData tpd = new TargetPointData(x, y, z, name);
        points.add(tpd);
    }

    public boolean removePoint(String target){
        for(int i = 0; i < points.size(); i++){
            if(points.get(i).getName().equals(target)){
                points.remove(i);
                return true;
            }
        }

        return false;
    }
}
