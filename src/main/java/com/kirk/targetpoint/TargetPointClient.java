package com.kirk.targetpoint;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

public class TargetPointClient implements ClientModInitializer {
    public static final String SETTING_CATEGORY = "category.targetpoint.general";

    private static KeyBinding showPointKey;
    //private static KeyBinding addPointKey;
    //private static KeyBinding removePointKey;
    private static KeyBinding menuKey;

    TargetPointManager manager = new TargetPointManager();

    @Override
    public void onInitializeClient(){
        
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            manager.loadPoints();
        });

        showPointKey = new KeyBinding("key.targetpoint.show_point", GLFW.GLFW_KEY_N, SETTING_CATEGORY);
        //addPointKey = new KeyBinding("key.targetpoint.add_point", GLFW.GLFW_KEY_M, SETTING_CATEGORY);
        //removePointKey = new KeyBinding("key.targetpoint.remove_point", GLFW.GLFW_KEY_P, SETTING_CATEGORY);
        menuKey = new KeyBinding("key.targetpoint.open_menu", GLFW.GLFW_KEY_K, SETTING_CATEGORY);

        KeyBindingHelper.registerKeyBinding(showPointKey);
        //KeyBindingHelper.registerKeyBinding(addPointKey);
        //KeyBindingHelper.registerKeyBinding(removePointKey);
        KeyBindingHelper.registerKeyBinding(menuKey);
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(menuKey.wasPressed()){
                client.setScreen(new TargetPointScreenMenu(manager, Text.literal("Target Point menu")));
            }

            if(showPointKey.wasPressed()){
                var point = manager.getPoints();
                
                for(int i = 0; i < point.size(); i++){
                    String x = String.valueOf(point.get(i).getX());
                    String y = String.valueOf(point.get(i).getY());
                    String z = String.valueOf(point.get(i).getZ());
                    String name = point.get(i).getName();
                    
                    client.player.sendMessage(Text.literal("Name: " + name + "\nX: " + x + "\nY: " + y + "\nZ: " + z + "\n"), false);
                }
            }
//
            //if(addPointKey.wasPressed()){
            //    double x = client.player.getX();
            //    double y = client.player.getY();
            //    double z = client.player.getZ();
            //    String name = "null";
//
            //    manager.addPoint(x, y, z, name);
            //}
//
            //if(removePointKey.wasPressed()){
            //    boolean result = manager.removePoint("null");
            //    client.player.sendMessage(Text.literal(String.valueOf((result) ? "Point deleted" : "Point isn`t deleted")), false);
            //}
        });
    }   
}
