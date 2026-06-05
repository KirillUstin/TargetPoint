package com.kirk.targetpoint;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class TargetPointScreenMenu extends Screen {
    
    private TargetPointManager manager;
    private TargetPointListWidget pointList;

    private ButtonWidget editButton;
    private ButtonWidget deleteButton;
    private ButtonWidget teleportButton;
    
    protected TargetPointScreenMenu(TargetPointManager m, Text title) {
        super(title);
        this.manager = m;
    }

    private void updateButtons() {
        boolean hasSelection = pointList.getSelectedPoint() != null;

        editButton.active = hasSelection;
        deleteButton.active = hasSelection;
        teleportButton.active = hasSelection && client.getServer() != null && client.getServer().getSaveProperties().areCommandsAllowed();
    }

    protected void refreshPointList(){
        TargetPointData selected = pointList.getSelectedPoint(); 

        pointList.clear();
        
        for(TargetPointData point : manager.getPoints()){
            pointList.addPoint(point);
        }

        if(selected == null || !manager.getPoints().contains(selected)){
            pointList.setSelected(null);
        }
    }
    
    @Override
    protected void init(){
        int guiScale = client.options.getGuiScale().getValue();

        int uiPadding = 10;

        //общие настройки панели и зон
        int panelWidth = (guiScale > 2) ? Math.min(600, width - 2 * uiPadding) : Math.max(600, width - 2 * uiPadding);
        int panelHeight = Math.min(400, height - 2 * uiPadding);

        int panelX = (width - panelWidth) / 2;
        int panelY = (height - panelHeight) / 2;

        int headerHeight = (int)(panelHeight * 0.20);
        int listHeight   = (int)(panelHeight * 0.55);
        int footerHeight = panelHeight - headerHeight - listHeight;

        int centerX = panelX + panelWidth / 2;
        
        //расположение спика точек
        int listWidth = panelWidth;
        int listX = panelX - 30;
        int listY = panelY + headerHeight;
        
        //список точек
        pointList = new TargetPointListWidget(
            client,
            listWidth,
            listHeight,
            listY,
            30
        );
        pointList.setWidth(width + 30);
        pointList.setX(listX);
        
        for (TargetPointData point : manager.getPoints()) {
            pointList.addPoint(point);
        }
        addDrawableChild(pointList);
        
        //расположение поля ввода имени
        int textFieldWidth = (int)(panelWidth * 0.35);
        int textFieldHeight = 20;
        
        int textFieldX = centerX - textFieldWidth / 2;
        int textFieldY = panelY + (headerHeight / 2) - (textFieldHeight / 2);
        
        //поле ввода имени точки
        TextFieldWidget textFieldWidget = new TextFieldWidget(textRenderer, textFieldX, textFieldY, textFieldWidth, textFieldHeight, Text.literal(""));
        textFieldWidget.setPlaceholder(Text.literal("Name point"));
        addDrawableChild(textFieldWidget);
        
        //расположение кнопок
        int buttonWidth = 100;
        int buttonHeight = 20;
        int buttonSpacing = 5;

        int totalButtonsWidth = 4 * buttonWidth + 3 * buttonSpacing;

        int buttonsStartX = centerX - totalButtonsWidth / 2;

        int buttonY = panelY + headerHeight + listHeight + (footerHeight / 2) - (buttonHeight / 2);
        
        //кнопки
        ButtonWidget button1 = ButtonWidget.builder(Text.literal("Cancel"), button -> {
            if(client.player != null){
                client.setScreen(null);
            }
        }).dimensions(buttonsStartX, buttonY, buttonWidth, buttonHeight).build();
        addDrawableChild(button1);

        ButtonWidget button2 = ButtonWidget.builder(Text.literal("Create"), button -> {
            if(client.player != null){
                String name = textFieldWidget.getText();
                
                double x = client.player.getX();
                double y = client.player.getY();
                double z = client.player.getZ();
                
                manager.addPoint(x, y, z, name);
                refreshPointList();
            }
        }).dimensions(buttonsStartX + (buttonWidth + buttonSpacing), buttonY, buttonWidth, buttonHeight).build();
        addDrawableChild(button2);

        editButton = ButtonWidget.builder(Text.literal("Edit"), button -> {
            TargetPointData point = pointList.getSelectedPoint();

            if(client.player != null){
                client.setScreen(new TargetPointScreenEditPoint(title, this, manager, point));
            }
            
        }).dimensions(buttonsStartX + 2 * (buttonWidth + buttonSpacing), buttonY, buttonWidth, buttonHeight).build();
        addDrawableChild(editButton);

        deleteButton = ButtonWidget.builder(Text.literal("Delete"), button -> {
            TargetPointData point = pointList.getSelectedPoint();
            
            if(client.player != null){
                if(point != null){
                    manager.removePoint(point);
                    refreshPointList();
                    updateButtons();
                }
            }

        }).dimensions(buttonsStartX + 3 * (buttonWidth + buttonSpacing), buttonY, buttonWidth, buttonHeight).build();
        addDrawableChild(deleteButton);

        //расположение верхнего ряда кнопок
        int topButtonWidth = buttonWidth * 2 + buttonSpacing;
        int topButtonsWidth = 2 * topButtonWidth + buttonSpacing;
        int topButtonsStartX = centerX - topButtonsWidth / 2;
        int topButtonY = buttonY - buttonHeight - 6;

        teleportButton = ButtonWidget.builder(Text.literal("Teleport"), button -> {
            //teleportButton.active = client.getServer() != null && client.getServer().getSaveProperties().areCommandsAllowed();

            TargetPointData point = pointList.getSelectedPoint();

            if(client.player != null){
                if(point != null){
                    client.player.refreshPositionAfterTeleport(point.getX(), point.getY(), point.getZ());
                }
            }
        }).dimensions(topButtonsStartX, topButtonY, topButtonWidth, buttonHeight).build();
        addDrawableChild(teleportButton);

        ButtonWidget deleteAllButton = ButtonWidget.builder(Text.literal("Delete all points"), button -> {
            if(client.player != null){
                manager.removeAllPoint();
                refreshPointList();
                updateButtons();
            }
        }).dimensions(topButtonsStartX + (topButtonWidth + buttonSpacing), topButtonY, topButtonWidth, buttonHeight).build();
        addDrawableChild(deleteAllButton);

        updateButtons();
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta){
        renderBackground(context, mouseX, mouseY, delta);
        
        super.render(context, mouseX, mouseY, delta);
        
        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Target Point menu"), width / 2, 20, 0xFFFFFF);
        
        updateButtons();
    }
}
