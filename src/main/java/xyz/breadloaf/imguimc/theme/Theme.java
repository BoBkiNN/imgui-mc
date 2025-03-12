package xyz.breadloaf.imguimc.theme;

@SuppressWarnings("unused")
public interface Theme {

    ImGuiDarkTheme DARK = new ImGuiDarkTheme();
    ImGuiLightTheme LIGHT = new ImGuiLightTheme();
    ImGuiClassicTheme CLASSIC = new ImGuiClassicTheme();

    void preRender();
    default void postRender() {}
}
