package xyz.breadloaf.imguimc.mixin;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.breadloaf.imguimc.ImGuiMc;
import xyz.breadloaf.imguimc.ImGuiMcWindow;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    public void onPress(long l, int i, int action, int k, CallbackInfo ci) {
        for (var r : ImGuiMc.renderStack) {
            if (!(r instanceof ImGuiMcWindow w)) continue;
            if (w.isCursorIn()) {
                ci.cancel();
                break;
            }
        }
    }

    @Inject(method = "onMove", at = @At("HEAD"), cancellable = true)
    public void onMove(long l, double xPos, double yPos, CallbackInfo ci) {
        for (var r : ImGuiMc.renderStack) {
            if (!(r instanceof ImGuiMcWindow w)) continue;
            if (w.isCursorIn()) {
                ci.cancel();
                break;
            }
        }
    }
}
