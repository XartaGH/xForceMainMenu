package me.xarta.xforcemainmenu.mixin;

import me.xarta.xforcemainmenu.util.ColorUtil;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DisconnectedScreen.class)
public abstract class DisconnectedScreenMixin {

    @ModifyArg(
            method = "<init>(Lnet/minecraft/client/gui/screens/Screen;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/DisconnectionDetails;<init>(Lnet/minecraft/network/chat/Component;)V"
            ),
            index = 0
    )
    private static Component xforcemainmenu$replaceReasonForDetails(Component originalReason) {
        String text = (originalReason == null ? "" : originalReason.getString()).toLowerCase();
        if (text.contains("getsockopt") || text.contains("connection refused")) {
            return ColorUtil.literalFromLangWithColors("xforcemainmenu.disconnect.connection_refused");
        }
        return originalReason;
    }
}