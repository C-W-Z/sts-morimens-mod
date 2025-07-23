package morimensmod.glowinfos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;
import static morimensmod.util.Wiz.getPowerAmount;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import morimensmod.patches.enums.CustomTags;
import morimensmod.powers.NegentropyPower;

public class LoopCardGlowInfo extends AbstractGlowInfo {

    public static final String ID = makeID(LoopCardGlowInfo.class.getSimpleName());

    @Override
    public Color getColor(AbstractCard card) {
        return Settings.GOLD_COLOR.cpy();
    }

    @Override
    public String glowID() {
        return ID;
    }

    @Override
    public boolean test(AbstractCard card) {
        return (p() != null &&
                card.hasTag(CustomTags.LOOP) &&
                getPowerAmount(p(), NegentropyPower.POWER_ID) >= NegentropyPower.INVOKE_AMOUNT);
    }
}
