package com.kirk.targetpoint;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class TargetPointScreenEditPoint extends Screen{

    private Screen parent;

    protected TargetPointScreenEditPoint(Screen screen, Text title) {
        super(title);
        this.parent = screen; 
    }
    
    @Override
    protected void init(){
        ButtonWidget button1 = ButtonWidget.builder(Text.literal("Cancel"), button -> {
            if(client.player != null){
                client.setScreen(parent);;
            }
        }).dimensions(width / 10, (int) (height / 1.15), 100, 20).build();
        addDrawableChild(button1);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta){
        renderBackground(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Edit point"), width / 2, 20, 0xFFFFFF);
    }
}
