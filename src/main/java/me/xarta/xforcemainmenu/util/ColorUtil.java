package me.xarta.xforcemainmenu.util;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public final class ColorUtil {
    private ColorUtil() {}

    public static Component literalFromLangWithColors(String key) {
        String s = I18n.get(key);
        if (s.isEmpty()) s = key;
        s = s.replace("&", "§");
        return Component.literal(s);
    }
}