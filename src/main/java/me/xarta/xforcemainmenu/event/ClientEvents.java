package me.xarta.xforcemainmenu.event;

import me.xarta.xforcemainmenu.XForceMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = XForceMainMenu.MODID, value = Dist.CLIENT)
public final class ClientEvents {

    private ClientEvents() {}

    private static volatile boolean pendingForceTitle = false;
    private static volatile long pendingUntilNanos = 0L;
    private static final long PENDING_TIMEOUT_NS = 3_000_000_000L;

    private static volatile boolean patchedDisconnectButton = false;
    private static volatile int lastScreenHash = 0;

    @SubscribeEvent
    public static void onLoggedOut(ClientPlayerNetworkEvent.LoggingOut event) {
        Minecraft mc = Minecraft.getInstance();
        mc.execute(() -> {
            if (mc.screen instanceof DisconnectedScreen) {
                pendingForceTitle = false;
                return;
            }
            pendingForceTitle = true;
            pendingUntilNanos = System.nanoTime() + PENDING_TIMEOUT_NS;
        });
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post tick) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen == null) return;

        if (mc.screen instanceof DisconnectedScreen) {
            int currHash = System.identityHashCode(mc.screen);
            if (!patchedDisconnectButton || currHash != lastScreenHash) {
                mc.screen.children().stream()
                        .filter(Button.class::isInstance)
                        .map(Button.class::cast)
                        .findFirst()
                        .ifPresent(btn -> btn.setMessage(
                                Component.translatable("xforcemainmenu.button.back_from_disconnect")
                        ));
                patchedDisconnectButton = true;
                lastScreenHash = currHash;
            }
        } else {
            patchedDisconnectButton = false;
        }

        if (pendingForceTitle) {
            if (System.nanoTime() > pendingUntilNanos) {
                pendingForceTitle = false;
                return;
            }
            if (mc.screen instanceof DisconnectedScreen) {
                pendingForceTitle = false;
                return;
            }
            if (mc.screen instanceof ConnectScreen) {
                pendingForceTitle = false;
                return;
            }
            if (mc.screen instanceof JoinMultiplayerScreen) {
                mc.setScreen(new TitleScreen(true));
                pendingForceTitle = false;
            }
        }
    }
}