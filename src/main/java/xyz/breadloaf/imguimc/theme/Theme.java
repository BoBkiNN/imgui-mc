package xyz.breadloaf.imguimc.theme;

public interface Theme {
    void preRender();
    default void postRender() {}
}
