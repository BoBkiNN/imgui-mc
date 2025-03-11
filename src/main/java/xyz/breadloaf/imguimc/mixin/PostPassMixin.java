package xyz.breadloaf.imguimc.mixin;

import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(PostPass.class)
public class PostPassMixin {

//    @Inject(method = "addToFrame", at = @At("HEAD"), cancellable = true)
//    public void addToFrame(FrameGraphBuilder frameGraphBuilder, Map<ResourceLocation, ResourceHandle<RenderTarget>> map, Matrix4f matrix4f, CallbackInfo ci) {
//        if (WindowScaling.DISABLE_POST_PROCESSORS)
//            ci.cancel();
//    }

}
