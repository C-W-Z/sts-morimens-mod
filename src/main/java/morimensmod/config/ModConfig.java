package morimensmod.config;

import static morimensmod.MorimensMod.modID;

import java.util.Properties;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;

import morimensmod.cards.posses.VoicesInYourHead;

public class ModConfig {

    public static class Char {
        public static SpireConfig config;
        public static final String PosseSelectUI = "PosseSelect";

        public static void initialize() {
            try {
                Properties defaults = new Properties();
                defaults.setProperty(Char.PosseSelectUI, VoicesInYourHead.ID);
                config = new SpireConfig(modID, "Char", defaults);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static String getPosseSelect() {
            return config.getString(ModConfig.Char.PosseSelectUI);
        }

        public static void savePosseSelect(String posseID) {
            try {
                config.setString(PosseSelectUI, posseID);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void initialize() {
        Char.initialize();
    }
}
