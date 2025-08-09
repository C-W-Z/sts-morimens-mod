package morimensmod.config;

import static morimensmod.MorimensMod.modID;

import java.util.Properties;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;

import morimensmod.cards.posses.VoicesInYourHead;

public class ModConfig {

    public static SpireConfig charConfig;
    public static class Char {
        public static final String PosseSelectUI = "PosseSelect";
    }

    public static void initialize() {
        try {
            Properties defaults = new Properties();
            defaults.setProperty(Char.PosseSelectUI, VoicesInYourHead.ID);
            charConfig = new SpireConfig(modID, "Char", defaults);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
