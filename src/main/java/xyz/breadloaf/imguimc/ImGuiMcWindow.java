package xyz.breadloaf.imguimc;

import imgui.ImGui;
import xyz.breadloaf.imguimc.theme.Theme;

public abstract class ImGuiMcWindow implements Renderable {
    private final String name;

    private float width, height = -1;
    private float winX, winY = 0;
    private float mouseX, mouseY = 0;

    private boolean isCursorIn = false;

    private boolean inRender = false;

    public float getMouseX() {
        return mouseX;
    }

    public float getMouseY() {
        return mouseY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getWinX() {
        return winX;
    }

    public float getWinY() {
        return winY;
    }

    public boolean isCursorIn() {
        return isCursorIn;
    }

    public ImGuiMcWindow(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Theme getTheme() {
        return Theme.DARK;
    }

    public abstract void renderContent();

    /**
     * Called after end()
     */
    public void postRender() {}

    public void updateWindowData() {
        checkIsRender();
        winX = ImGui.getWindowPosX();
        winY = ImGui.getWindowPosY();
        width = ImGui.getWindowWidth();
        height = ImGui.getWindowHeight();
    }

    public void checkIsRender() {
        if (!inRender) throw new IllegalStateException("Cannot perform this operation outside render method");
    }

    @Override
    public void render() {
        ImGui.begin(name);
        inRender = true;
        updateWindowData();
        mouseX = ImGui.getMousePosX();
        mouseY = ImGui.getMousePosY();
        isCursorIn = (mouseX >= winX && mouseX <= (winX + width)) &&
                (mouseY >= winY && mouseY <= (winY + height));
        renderContent();
        inRender = false;
        ImGui.end();
        postRender();
    }

    @Override
    public String toString() {
        return "ImGuiMcWindow("+name+";"+winX+","+winY+";"+width+","+height+")";
    }
}
