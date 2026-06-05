package com.kirk.targetpoint;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class TargetPointScreenEditPoint extends Screen{

    private TargetPointManager manager;
    private TargetPointData point;

    private Screen parent;

    private int textY;

    protected TargetPointScreenEditPoint(Text title, Screen screen, TargetPointManager manager, TargetPointData point) {
        super(title);
        this.parent = screen; 
        this.manager = manager;
        this.point = point;
    }
    
    @Override
    protected void init(){
        int guiScale = client.options.getGuiScale().getValue();

        int uiPadding = 10;

        //общие настройки панели и зон
        int panelWidth = (guiScale > 2) ? Math.min(700, width - 2 * uiPadding) : Math.max(700, width - 2 * uiPadding);
        int panelHeight = Math.min(400, height - 2 * uiPadding);

        int panelX = (width - panelWidth) / 2;
        int panelY = (height - panelHeight) / 2;

        int headerHeight = (int)(panelHeight * 0.20);
        int listHeight   = (int)(panelHeight * 0.55);
        int footerHeight = panelHeight - headerHeight - listHeight;

        int centerX = panelX + panelWidth / 2;

        //расположение поля ввода текста
        int textFieldWidth = (int)(panelWidth * 0.35);
        int textFieldHeight = 20;
        
        int textFieldX = centerX - textFieldWidth / 2;
        int textFieldY = panelY + (headerHeight / 2) - (textFieldHeight / 2);
        textY = textFieldY;

        //поле ввода текста
        TextFieldWidget newName = new TextFieldWidget(textRenderer, textFieldX, textFieldY, textFieldWidth, textFieldHeight, Text.literal(""));
        newName.setPlaceholder(Text.literal("New point name"));
        addDrawableChild(newName);

        //расположение кнопок
        int buttonWidth = 200;
        int buttonHeight = 20;
        int buttonSpacing = 5;

        int totalButtonsWidth = 2 * buttonWidth + 3 * buttonSpacing;

        int buttonsStartX = centerX - totalButtonsWidth / 2;

        int buttonY = panelY + headerHeight + listHeight + (footerHeight / 2) - (buttonHeight / 2);

        //кнопки
        ButtonWidget button1 = ButtonWidget.builder(Text.literal("Cancel"), button -> {
            if(client.player != null){
                client.setScreen(parent);
            }
        }).dimensions(buttonsStartX, buttonY, buttonWidth, buttonHeight).build();
        addDrawableChild(button1);

        ButtonWidget button2 = ButtonWidget.builder(Text.literal("Save"), button -> {
            if(client.player != null){
                manager.updatePoint(point, newName.getText());
                
                client.setScreen(parent);

                if(parent instanceof TargetPointScreenMenu menu){
                    menu.refreshPointList();
                }
            }
        }).dimensions(buttonsStartX + (buttonWidth + buttonSpacing), buttonY, buttonWidth, buttonHeight).build();
        addDrawableChild(button2);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta){
        renderBackground(context, mouseX, mouseY, delta);

        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Edit point"), width / 2, 20, 0xFFFFFF);

        context.drawCenteredTextWithShadow(textRenderer, Text.literal("Enter a new point name"), width / 2, textY - 20, 0xFFFFFF);
    }
}
