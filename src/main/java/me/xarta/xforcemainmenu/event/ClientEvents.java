package me.xarta.xforcemainmenu.event;

import me.xarta.xforcemainmenu.XForceMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = XForceMainMenu.MODID, value = Dist.CLIENT)
public final class ClientEvents {

    private ClientEvents() {}

    private static volatile boolean pendingForceTitle = false;
    private static volatile long    pendingUntilNanos = 0L;

    private static final long PENDING_TIMEOUT_NS = 3_000_000_000L;

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
        if (!pendingForceTitle) return;

        if (System.nanoTime() > pendingUntilNanos) {
            pendingForceTitle = false;
            return;
        }

        Minecraft mc = Minecraft.getInstance();

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
