package morimensmod.glowinfos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import morimensmod.patches.CustomTags;

public class ForcePlayableCardGlowInfo extends AbstractGlowInfo {

    public static final String ID = makeID(ForcePlayableCardGlowInfo.class.getSimpleName());

    @Override
    public Color getColor(AbstractCard card) {
        return Color.RED.cpy();
    }

    @Override
    public String glowID() {
        return ID;
    }

    @Override
    public boolean test(AbstractCard card) {
        return (p() != null &&
                card.hasTag(CustomTags.FORCE_PLAYABLE) &&
                !(EnergyPanel.totalCount >= card.costForTurn || card.freeToPlay() || card.isInAutoplay) &&
                card.hasEnoughEnergy());
    }
}
