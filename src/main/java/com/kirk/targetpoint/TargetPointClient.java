package com.kirk.targetpoint;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

public class TargetPointClient implements ClientModInitializer {
    public static final String SETTING_CATEGORY = "category.targetpoint.general";

    private static KeyBinding keyBinding;

    @Override
    public void onInitializeClient(){
        keyBinding = new KeyBinding("key.targetpoint.create_point", GLFW.GLFW_KEY_M, SETTING_CATEGORY);
        
        KeyBindingHelper.registerKeyBinding(keyBinding);
        
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(keyBinding.wasPressed()){
                String x = String.valueOf(client.player.getX());
                String y = String.valueOf(client.player.getY());
                String z = String.valueOf(client.player.getZ());
                client.player.sendMessage(Text.literal("X: " + x + "\nY: " + y + "\nZ: " + z), false);
            }
        });
    }   
}
