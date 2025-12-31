package me.xarta.xforcemainmenu;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@SuppressWarnings("unused")
@Mod(XForceMainMenu.MODID) // Declare this class as mod's main class
public class XForceMainMenu {

    public static final String MODID = "xforcemainmenu"; // Define modification's ID
    public static final Logger LOGGER = LogUtils.getLogger(); // Create logger

    public XForceMainMenu(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("XForceMainMenu is initializing..."); // Print initialization message

        LOGGER.info("XForceMainMenu is on."); // Print success message
    }

}