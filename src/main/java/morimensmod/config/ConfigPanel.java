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

    public static boolean OTHER_CHAR_ENCOUNTER_MOD_MONSTER = false;

    public ConfigPanel() {
        super(modID, UI_STRINGS, UI_STRINGS.TEXT[0]);

        float sY = 720.0F;

        ModLabeledToggleButton modMonsterEncounterButton = new ModLabeledToggleButton(
                UI_STRINGS.TEXT_DICT.get("OTHER_CHAR_ENCOUNTER_MOD_MONSTER"),
                400.0F, sY - 50.0F,
                Settings.CREAM_COLOR, FontHelper.charDescFont,
                OTHER_CHAR_ENCOUNTER_MOD_MONSTER,
                this,
                label -> {},
                button -> {
                    OTHER_CHAR_ENCOUNTER_MOD_MONSTER = button.enabled;
                    this.save();
                });

        this.addUIElement(modMonsterEncounterButton);
    }
}
