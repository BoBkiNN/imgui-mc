package xyz.breadloaf.imguimc.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.MouseHandler;
import org.joml.Vector2d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.breadloaf.imguimc.ImGuiMc;
import xyz.breadloaf.imguimc.ImGuiMcMod;
import xyz.breadloaf.imguimc.ImGuiMcWindow;
import xyz.breadloaf.imguimc.WindowScaling;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @WrapMethod(method = "onMove")
    public void onMove(long l, double d, double e, Operation<Void> original) {
        Vector2d scaled = WindowScaling.unscalePoint(d, e);
        original.call(l, scaled.x, scaled.y);
    }

    @WrapOperation(method = {"grabMouse", "releaseMouse"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;getScreenWidth()I"))
    public int calculateDoubledCentreX(Window instance, Operation<Integer> original) {
        return (WindowScaling.X_OFFSET + (WindowScaling.WIDTH / 2)) * 2;
    }

    @WrapOperation(method = {"grabMouse", "releaseMouse"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/Window;getScreenHeight()I"))
    public int calculateDoubledCentreY(Window instance, Operation<Integer> original) {
        return (WindowScaling.Y_OFFSET + (WindowScaling.HEIGHT / 2)) * 2;
    }

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
