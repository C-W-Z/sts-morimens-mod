package morimensmod.potions;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class AntidotePotion extends AbstractEasyPotion {
    public static String ID = makeID(AntidotePotion.class.getSimpleName());

    public AntidotePotion() {
        super(ID, PotionRarity.COMMON, PotionSize.SPHERE, Settings.GREEN_TEXT_COLOR,
                new Color(0f, 0.6f, 0.2f, 1f), null, Settings.GREEN_RELIC_COLOR);
    }

    public int getPotency(int ascensionlevel) {
        return 0;
    }

    public void use(AbstractCreature creature) {
        addToBot(new RemoveSpecificPowerAction(p(), p(), PoisonPower.POWER_ID));
    }

    public String getDescription() {
        return DESCRIPTIONS[0];
    }
}
