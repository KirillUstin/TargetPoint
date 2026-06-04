package com.kirk.targetpoint;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;

import net.minecraft.client.MinecraftClient;

public class TargetPointManager {
    ArrayList<TargetPointData> points = new ArrayList<>();

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create(); 

    public List<TargetPointData> getPoints(){
        return points;
    }

    public void addPoint(double x, double y, double z, String name){
        TargetPointData tpd = new TargetPointData(x, y, z, name);
        points.add(tpd);
        savePoints();
    }

    public boolean removePoint(String target){
        for(int i = 0; i < points.size(); i++){
            if(points.get(i).getName().equals(target)){
                points.remove(i);
                savePoints();
                return true;
            }
        }

        return false;
    }

    private Path getSaveFile() {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.getServer() == null) {
            return null;
        }

        String worldName = client.getServer().getSaveProperties().getLevelName();

        Path folder = Path.of("targetpoint");

        try {
            Files.createDirectories(folder);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return folder.resolve(worldName + ".json");
    }

    public void savePoints() {
        Path file = getSaveFile();

        if (file == null) {
            return;
        }

        try {
            String json = gson.toJson(points);

            Files.writeString(file, json);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPoints() {
        Path file = getSaveFile();

        if (file == null) {
            return;
        }

        try {
            if (!Files.exists(file)) {
                return;
            }

            String json = Files.readString(file);

            Type type = new TypeToken<ArrayList<TargetPointData>>() {}.getType();

            points = gson.fromJson(json, type);

            if (points == null) {
                points = new ArrayList<>();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
