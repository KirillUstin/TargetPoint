package com.kirk.targetpoint;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ElementListWidget;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import java.util.List;

public class TargetPointListWidget extends ElementListWidget<TargetPointListWidget.TargetPointEntry> {

    public TargetPointListWidget(
            MinecraftClient client,
            int width,
            int height,
            int y,
            int itemHeight
    ) {
        super(client, width, height, y, itemHeight);
    }

    public void addPoint(TargetPointData point) {
        this.addEntry(new TargetPointEntry(this, point));
    }

    @Override
    public int getRowWidth() {
        return 250;
    }

    public TargetPointData getSelectedPoint() {
        TargetPointEntry selected = getSelectedOrNull();

        if (selected == null) {
            return null;
        }

        return selected.point;
    }

    public TargetPointEntry getSelectedEntry() {
        return this.getSelectedOrNull();
    }

    public boolean isEntrySelected(TargetPointEntry entry) {
        return entry == this.getSelectedOrNull();
    }

    public void clear(){
        this.children().clear();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        TargetPointEntry entry = this.getEntryAtPosition(mouseX, mouseY);

        if(!isMouseOver(mouseX, mouseY)){
            return false;
        }

        if (entry != null && button == 0) {
            this.setSelected(entry);
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public static class TargetPointEntry extends ElementListWidget.Entry<TargetPointEntry> {

        private final TargetPointListWidget list;
        private final TargetPointData point;

        public TargetPointEntry(TargetPointListWidget list, TargetPointData point) {
            this.list = list;
            this.point = point;
        }

        @Override
        public void render(
                DrawContext context,
                int index,
                int y,
                int x,
                int entryWidth,
                int entryHeight,
                int mouseX,
                int mouseY,
                boolean hovered,
                float tickDelta
        ) {
            MinecraftClient client = MinecraftClient.getInstance();

            boolean isHovered = hovered;
            boolean selected = list.isEntrySelected(this);

            if (selected) {
                context.fill(x - 1, y - 1, x + entryWidth + 1, y + entryHeight + 1, 0xFFFFFFFF);
                context.fill(x, y, x + entryWidth, y + entryHeight, 0xFF808080);
            } else if (isHovered) {
                context.fill(x, y, x + entryWidth, y + entryHeight, 0x40FFFFFF);
            }

            context.drawTextWithShadow(
                    client.textRenderer,
                    point.getName(),
                    x + 5,
                    y + 4,
                    0xFFFFFF
            );

            String coords =
                    (int) point.getX() + " "
                            + (int) point.getY() + " "
                            + (int) point.getZ();

            context.drawTextWithShadow(
                    client.textRenderer,
                    coords,
                    x + 5,
                    y + 16,
                    0xAAAAAA
            );
        }

        @Override
        public List<? extends Element> children() {
            return List.of();
        }
        
        @Override
        public List<? extends Selectable> selectableChildren() {
            return List.of();
        }

        public TargetPointData getPoint() {
            return this.point;
        }
    }
}