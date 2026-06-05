package com.kirk.targetpoint;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

public class TargetPointClient implements ClientModInitializer {
    public static final String SETTING_CATEGORY = "Target Point settings";

    private static KeyBinding menuKey;

    TargetPointManager manager = new TargetPointManager();

    @Override
    public void onInitializeClient(){
        
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            manager.loadPoints();
        });

        menuKey = new KeyBinding("Open Menu", GLFW.GLFW_KEY_K, SETTING_CATEGORY);

        KeyBindingHelper.registerKeyBinding(menuKey);
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(menuKey.wasPressed()){
                client.setScreen(new TargetPointScreenMenu(manager, Text.literal("Target Point menu")));
            }
        });
    }   
}
