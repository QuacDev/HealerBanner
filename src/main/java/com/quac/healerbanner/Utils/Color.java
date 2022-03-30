package com.quac.healerbanner.Utils;

import net.minecraft.util.EnumChatFormatting;

public class Color {
    public static String translate(String s) {
        return EnumChatFormatting.getTextWithoutFormattingCodes(s).replaceAll("&", "\u00A7");
    }
}
