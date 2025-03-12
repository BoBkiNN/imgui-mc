package xyz.breadloaf.imguimc;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import xyz.breadloaf.imguimc.debug.DebugRenderableWindow;

@SuppressWarnings("unused")
public class ImGuiMcMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            ImGuiMc.LOGGER.info("In development environment, pushing debug renderable.");
            ImGuiMc.pushRenderable(new DebugRenderableWindow());
        }
    }

}
