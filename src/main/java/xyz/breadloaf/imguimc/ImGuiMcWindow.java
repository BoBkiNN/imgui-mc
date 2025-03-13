package xyz.breadloaf.imguimc;

import imgui.ImGui;
import imgui.ImVec2;
import xyz.breadloaf.imguimc.theme.Theme;

public abstract class ImGuiMcWindow implements Renderable {
    private final String name;

    private float width, height = -1;
    private float winX, winY = 0;
    private float mouseX, mouseY = 0;

    private boolean isCursorIn = false;

    private boolean inRender = false;

    public ImGuiMcWindow(String name) {
        this.name = name;
    }

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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Theme getTheme() {
        return Theme.DARK;
    }

    public WindowBoundaryProvider getBoundary() {
        return null;
    }

    public abstract void renderContent();

    /**
     * Called after end()
     */
    public void postRender() {
    }

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


    private void setupBoundary(WindowBoundaryProvider provider) {
        float minX = provider.getX();
        float minY = provider.getY();
        float maxX = minX + provider.getWidth();
        float maxY = minY + provider.getHeight();

        // Get the current position and size
        ImVec2 pos = ImGui.getWindowPos();
        ImVec2 size = ImGui.getWindowSize();

        // Clamp position so the window stays inside
        float clampedX = Math.max(minX, Math.min(pos.x, maxX - size.x));
        float clampedY = Math.max(minY, Math.min(pos.y, maxY - size.y));

        // Directly override position to ensure it never escapes
        ImGui.setWindowPos(clampedX, clampedY);

        // Enforce size constraints
        float maxWidth = maxX - minX;
        float maxHeight = maxY - minY;
        ImGui.setWindowSize(Math.min(size.x, maxWidth), Math.min(size.y, maxHeight));
    }

    @Override
    public void render() {
        var boundary = getBoundary();
        if (boundary != null && boundary.onlyMainViewport()) {
            ImGui.setNextWindowViewport(ImGui.getMainViewport().getID());
        }
        ImGui.begin(name);
        inRender = true;
        if (boundary != null) setupBoundary(boundary);
        updateWindowData();
        mouseX = ImGui.getMousePosX();
        mouseY = ImGui.getMousePosY();
        isCursorIn = (mouseX >= winX && mouseX <= (winX + width)) && (mouseY >= winY && mouseY <= (winY + height));
        renderContent();
        inRender = false;
        ImGui.end();
        postRender();
    }

    @Override
    public String toString() {
        return "ImGuiMcWindow(" + name + ";" + winX + "," + winY + ";" + width + "," + height + ")";
    }
}
