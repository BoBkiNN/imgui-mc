package xyz.breadloaf.imguimc.debug;

import imgui.ImGui;
import xyz.breadloaf.imguimc.ImGuiMcWindow;
import xyz.breadloaf.imguimc.WindowBoundaryProvider;

public class DebugRenderableWindow extends ImGuiMcWindow {

    public static boolean showAboutWindow = false;
    public static boolean showDemoWindow = false;
    public static boolean showMetricsWindow = false;
    public static boolean showUserGuide = false;

    private WindowBoundaryProvider currentBoundary = null;

    public DebugRenderableWindow() {
        super("imgui-mc debug window");
    }

    @Override
    public WindowBoundaryProvider getBoundary() {
        return currentBoundary;
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
        WindowBoundaryProvider[] windowBoundaryProviders = new WindowBoundaryProvider[]{WindowBoundaryProvider.MINECRAFT, WindowBoundaryProvider.SCREEN_WORK_AREA, null};
        for (int i = 0; i < windowBoundaryProviders.length; i++) {
            var value = windowBoundaryProviders[i];
            if (ImGui.radioButton("b"+i, currentBoundary == value)) {
                currentBoundary = value;
            }
        }
        if (currentBoundary != null) {
            ImGui.text(currentBoundary.asString());
        }

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
