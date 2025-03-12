package xyz.breadloaf.imguimc.debug;

import imgui.ImGui;
import xyz.breadloaf.imguimc.ImGuiMcWindow;
import xyz.breadloaf.imguimc.Renderable;
import xyz.breadloaf.imguimc.theme.Theme;

public class DebugRenderableWindow extends ImGuiMcWindow {

    public static boolean showAboutWindow = false;
    public static boolean showDemoWindow = false;
    public static boolean showMetricsWindow = false;
    public static boolean showUserGuide = false;

    public DebugRenderableWindow() {
        super("imgui-mc debug window");
    }

    @Override
    public void renderContent() {
        ImGui.text("This window is being shown because you are in a fabric mod\ndevelopment environment. It will not be shown in-game.\nYou can enable some builtin imgui windows below.");
        if (ImGui.checkbox("Show about window", showAboutWindow))
            showAboutWindow = !showAboutWindow;
        if (ImGui.checkbox("Show demo window", showDemoWindow))
            showDemoWindow = !showDemoWindow;
        if (ImGui.checkbox("Show metrics window", showMetricsWindow))
            showMetricsWindow = !showMetricsWindow;
        if (ImGui.checkbox("Show user guide", showUserGuide))
            showUserGuide = !showUserGuide;

//        float mouseX = ImGui.getMousePosX();
//        float mouseY = ImGui.getMousePosY();
//
//        float winX = ImGui.getWindowPosX();
//        float winY = ImGui.getWindowPosY();
//        float winW = ImGui.getWindowWidth();
//        float winH = ImGui.getWindowHeight();
//        boolean isInside = (mouseX >= winX && mouseX <= (winX + winW)) &&
//                (mouseY >= winY && mouseY <= (winY + winH));
        ImGui.text("Mouse inside: "+isCursorIn());
        ImGui.text(this.toString());
        ImGui.text("Mouse pos: "+getMouseX()+" "+getMouseY());
    }

    @Override
    public void postRender() {
        if (showAboutWindow)
            ImGui.showAboutWindow();
        if (showDemoWindow)
            ImGui.showDemoWindow();
        if (showMetricsWindow)
            ImGui.showMetricsWindow();
        if (showUserGuide)
            ImGui.showUserGuide();
    }
}
