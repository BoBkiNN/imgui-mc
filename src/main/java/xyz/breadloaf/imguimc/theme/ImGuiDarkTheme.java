package xyz.breadloaf.imguimc.theme;

import imgui.ImGui;

public class ImGuiDarkTheme implements Theme {
    @Override
    public void preRender() {
        ImGui.styleColorsDark();
    }

    @Override
    public void postRender() {

    }
}
