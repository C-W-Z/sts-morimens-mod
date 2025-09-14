package morimensmod.glowinfos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.p;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import morimensmod.patches.enums.CustomTags;

public class PlayableByKeyflareGlowInfo extends AbstractGlowInfo {

    public static final String ID = makeID(PlayableByKeyflareGlowInfo.class.getSimpleName());

    @Override
    public Color getColor(AbstractCard card) {
        return Color.GOLD.cpy();
    }

    @Override
    public String glowID() {
        return ID;
    }

    @Override
    public boolean test(AbstractCard card) {
        return (p() != null &&
                card.hasTag(CustomTags.PLAYABLE_BY_KEYFLARE) &&
                !(EnergyPanel.totalCount >= card.costForTurn || card.freeToPlay() || card.isInAutoplay) &&
                card.hasEnoughEnergy());
    }
}
