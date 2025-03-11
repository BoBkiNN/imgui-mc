package xyz.breadloaf.imguimc;

import xyz.breadloaf.imguimc.theme.Theme;

public interface Renderable {
    String getName();
    Theme getTheme();

    void render();
}
