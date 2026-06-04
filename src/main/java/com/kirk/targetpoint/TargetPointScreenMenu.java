package com.kirk.targetpoint;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class TargetPointScreenMenu extends Screen {
    
    private final TargetPointManager manager;
    private TextFieldWidget textFieldWidget;
    
    protected TargetPointScreenMenu(TargetPointManager m, Text title) {
        super(title);
        this.manager = m;
    }
    
    @Override
    protected void init(){
        int guiScale = client.options.getGuiScale().getValue();

        //расположение поля для ввода
        int panelWidth = Math.min(600, width - 20);
        int panelHeight;
        if(guiScale > 2){
            panelHeight = Math.min(400, height - 20);
        } else{
            panelHeight = Math.max(400, height - 20);
        }
    
        int panelX = ((width - panelWidth) / 2) - 30;
        int panelY = (height - panelHeight) / 2;

        //расположение кнопок
        int buttonsAreaWidth = (int)(panelWidth * 0.7);
        int buttonWidth = buttonsAreaWidth / 4;
        int startX = panelX + (panelWidth - buttonsAreaWidth) / 2;
        int buttonY = panelY + (int)(panelHeight * 0.8); //раньше было (int) (height / 1.15
        
        ButtonWidget button1 = ButtonWidget.builder(Text.literal("Cancel"), button -> {
            if(client.player != null){
                client.setScreen(null);
            }
        }).dimensions(startX, buttonY, 100, 20).build();
        addDrawableChild(button1);

        //поле ввода имени точки
        int textFieldWidth = (int)(panelWidth * 0.37);
        textFieldWidget = new TextFieldWidget(textRenderer, (panelX + (panelWidth - textFieldWidth) / 2) + 30, panelY + (int)(panelHeight * 0.10), 200, 20, Text.literal("Enter name"));
        addDrawableChild(textFieldWidget);

        ButtonWidget button2 = ButtonWidget.builder(Text.literal("Create"), button -> {
            if(client.player != null){
                String name = textFieldWidget.getText();
                
                double x = client.player.getX();
                double y = client.player.getY();
                double z = client.player.getZ();
                
                manager.addPoint(x, y, z, name);
                
                client.player.sendMessage(Text.literal("Point is added"));
            }
        }).dimensions(startX + buttonWidth + 20, buttonY, 100, 20).build();
        addDrawableChild(button2);

        ButtonWidget button3 = ButtonWidget.builder(Text.literal("Edit"), button -> {
            if(client.player != null){
                client.setScreen(new TargetPointScreenEditPoint(this, title));
            }
        }).dimensions(startX + (buttonWidth + 20) * 2, buttonY, 100, 20).build();
        addDrawableChild(button3);

        ButtonWidget button4 = ButtonWidget.builder(Text.literal("Delete"), button -> {
            if(client.player != null){
                
            }
        }).dimensions(startX + (buttonWidth + 20) * 3, buttonY, 100, 20).build();
        addDrawableChild(button4);
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta){
        renderBackground(context, mouseX, mouseY, delta);
        
        super.render(context, mouseX, mouseY, delta);
        
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Target Point menu"), width / 2, 20, 0xFFFFFF);
        //context.drawHorizontalLine(RenderLayer.getGui(), width - width, width, height / 2 + 80, 0xFFA9A9A9);
    }
}
