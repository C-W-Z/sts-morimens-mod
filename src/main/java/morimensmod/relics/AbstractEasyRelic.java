package morimensmod.relics;

import basemod.abstracts.CustomRelic;
import morimensmod.util.TexLoader;

import static morimensmod.MorimensMod.makeRelicPath;
import static morimensmod.util.General.removeModID;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public abstract class AbstractEasyRelic extends CustomRelic {
    public AbstractCard.CardColor color;

    public AbstractEasyRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx) {
        this(setId, tier, sfx, null);
    }

    public AbstractEasyRelic(String setId, AbstractRelic.RelicTier tier, AbstractRelic.LandingSound sfx, AbstractCard.CardColor color) {
        super(setId, TexLoader.getTexture(makeRelicPath(removeModID(setId) + ".png")), tier, sfx);
        outlineImg = TexLoader.getTexture(makeRelicPath(removeModID(setId) + "Outline.png"));
        largeImg = TexLoader.getTexture(makeRelicPath(removeModID("large/" + setId) + ".png"));
        this.color = color;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}