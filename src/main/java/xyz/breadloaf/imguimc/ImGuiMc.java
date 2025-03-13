package xyz.breadloaf.imguimc;

import imgui.ImGui;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


@Environment(EnvType.CLIENT)
public class ImGuiMc {
    public static final String MOD_ID = "imgui-mc";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static final Minecraft MINECRAFT = Minecraft.getInstance();
    public static ArrayList<Renderable> renderStack = new ArrayList<>();

    public static ArrayList<Renderable> toRemove = new ArrayList<>();

    public static Renderable pushRenderable(Renderable renderable) {
        renderStack.add(renderable);
        return renderable;
    }

    public static Renderable pullRenderable(Renderable renderable) {
        renderStack.remove(renderable);
        return renderable;
    }

    public static Renderable pullRenderableAfterRender(Renderable renderable) {
        toRemove.add(renderable);
        return renderable;
    }

    /**
     * Check whether game keyboard inputs are being cancelled.
     */
    public static boolean shouldCancelGameKeyboardInputs() {
        return ImGui.isAnyItemActive() || ImGui.isAnyItemFocused();
    }
}
