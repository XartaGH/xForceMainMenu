package me.xarta.xforcemainmenu.mixin;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.CommandSuggestions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CommandSuggestions.class)
public abstract class CommandSuggestionsMixin {

    @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;II)V", at = @At("HEAD"), cancellable = true)
    private void xforcemainmenu$cancelRender(GuiGraphics g, int mouseX, int mouseY, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderSuggestions(Lnet/minecraft/client/gui/GuiGraphics;II)Z", at = @At("HEAD"), cancellable = true)
    private void xforcemainmenu$cancelRenderSuggestions(GuiGraphics g, int mouseX, int mouseY, CallbackInfoReturnable<Boolean> cir) {
       cir.setReturnValue(false);
    }

    @Inject(method = "renderUsage(Lnet/minecraft/client/gui/GuiGraphics;)V", at = @At("HEAD"), cancellable = true)
    private void xforcemainmenu$cancelRenderUsage(GuiGraphics g, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "keyPressed(III)Z", at = @At("HEAD"), cancellable = true)
    private void xforcemainmenu$ignoreKeys(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "mouseScrolled(D)Z", at = @At("HEAD"), cancellable = true)
    private void xforcemainmenu$ignoreScroll(double amount, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "mouseClicked(DDI)Z", at = @At("HEAD"), cancellable = true)
    private void xforcemainmenu$ignoreClick(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "showSuggestions(Z)V", at = @At("HEAD"), cancellable = true)
    private void xforcemainmenu$cancelShow(boolean narrateFirst, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "isVisible()Z", at = @At("HEAD"), cancellable = true)
    private void xforcemainmenu$alwaysInvisible(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "updateCommandInfo()V", at = @At("HEAD"), cancellable = true)
    private void xforcemainmenu$cancelUpdate(CallbackInfo ci) {
        ci.cancel();
    }
}