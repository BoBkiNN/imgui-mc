package xyz.breadloaf.imguimc.theme;

import imgui.ImGui;

public class ImGuiLightTheme implements Theme {
    @Override
    public void preRender() {
        ImGui.styleColorsLight();
    }

}
