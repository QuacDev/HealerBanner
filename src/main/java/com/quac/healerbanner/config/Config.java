package com.quac.healerbanner.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

public class Config extends Vigilant {
    @Property(
            type = PropertyType.SWITCH,
            name = "Enabled",
            category = "Main"
    )
    public static boolean enabled = false;

    @Property(
            type = PropertyType.SWITCH,
            name = "Ignore Add",
            description = "If disabled will only kick",
            category = "Main"
    )
    public static boolean ignore = false;

    @Property(
            type = PropertyType.TEXT,
            name = "Kick Message",
            description = "Leave empty for no message (Will still send if you are not party leader)",
            category = "Main"
    )
    public static String kickMessage = "";

    @Property(
            type = PropertyType.SLIDER,
            min = 0, max = 50,
            name = "Max Level",
            description = "Set to 0 if any",
            category = "Main"
    )
    public static int maxLevel = 0;

    @Property(
            type = PropertyType.SWITCH,
            name = "Send Help Message",
            description = "If enabled will send a message when you join a world",
            category = "QOL"
    )
    public static boolean sendHelpMessage = true;

    public Config(File file) {
        super(file);
        initialize();
    }
}
