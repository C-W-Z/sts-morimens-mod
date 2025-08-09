package morimensmod.config;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.modID;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;

import basemod.EasyConfigPanel;
import basemod.ModLabeledToggleButton;

public class ConfigPanel extends EasyConfigPanel {

    public static final String ID = makeID(ConfigPanel.class.getSimpleName());
    public static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    public static boolean USE_MORIMENS_POWER_ICON = true;
    public static boolean AWAKENER_ENCOUNTER_MOD_MONSTER = true;
    public static boolean OTHER_CHAR_ENCOUNTER_MOD_MONSTER = false;

    // public static int DAMAGE_BLIGHT_LVL = 0;

    public ConfigPanel() {
        super(modID, UI_STRINGS, UI_STRINGS.TEXT[0]);

        float sX = 400F;
        float sY = 720F;

        ModLabeledToggleButton useMorimensPowerIconButton = new ModLabeledToggleButton(
                UI_STRINGS.TEXT_DICT.get("USE_MORIMENS_POWER_ICON"),
                sX, sY,
                Settings.CREAM_COLOR, FontHelper.charDescFont,
                USE_MORIMENS_POWER_ICON,
                this,
                label -> {},
                button -> {
                    USE_MORIMENS_POWER_ICON = button.enabled;
                    this.save();
                });

        ModLabeledToggleButton awakenerModMonsterEncounterButton = new ModLabeledToggleButton(
                UI_STRINGS.TEXT_DICT.get("AWAKENER_ENCOUNTER_MOD_MONSTER"),
                sX, sY - 50F,
                Settings.CREAM_COLOR, FontHelper.charDescFont,
                AWAKENER_ENCOUNTER_MOD_MONSTER,
                this,
                label -> {},
                button -> {
                    AWAKENER_ENCOUNTER_MOD_MONSTER = button.enabled;
                    this.save();
                });

        ModLabeledToggleButton otherCharModMonsterEncounterButton = new ModLabeledToggleButton(
                UI_STRINGS.TEXT_DICT.get("OTHER_CHAR_ENCOUNTER_MOD_MONSTER"),
                sX, sY - 100F,
                Settings.CREAM_COLOR, FontHelper.charDescFont,
                OTHER_CHAR_ENCOUNTER_MOD_MONSTER,
                this,
                label -> {},
                button -> {
                    OTHER_CHAR_ENCOUNTER_MOD_MONSTER = button.enabled;
                    this.save();
                });

        this.addUIElement(useMorimensPowerIconButton);
        this.addUIElement(awakenerModMonsterEncounterButton);
        this.addUIElement(otherCharModMonsterEncounterButton);

        // ModLabel blightLabel = new ModLabel("Blights", 400F, 425F, this, label -> {});
        // this.addUIElement(blightLabel);

        // this.setNumberRange("DAMAGE_BLIGHT_LVL", 0, DamageBlight.MAX_LVL);
    }
}
