package xyz.breadloaf.imguimc.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.breadloaf.imguimc.ImGuiMc;
import xyz.breadloaf.imguimc.imgui.ImguiLoader;

@Mixin(value = RenderSystem.class, remap = false)
public class TailRenderMixin {
    @Inject(at = @At("HEAD"), method="flipFrame")
    private static void runTickTail(CallbackInfo ci) {
        ImGuiMc.MINECRAFT.getProfiler().push("ImGui Render");
        ImguiLoader.onFrameRender();
        ImGuiMc.MINECRAFT.getProfiler().pop();
    }
}
