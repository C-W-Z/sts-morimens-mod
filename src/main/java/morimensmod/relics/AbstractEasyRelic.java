package morimensmod.relics;

import basemod.abstracts.CustomRelic;
import morimensmod.util.TexLoader;

import static morimensmod.MorimensMod.makeRelicPath;
import static morimensmod.util.General.removeModID;

import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

public abstract class AbstractEasyRelic extends CustomRelic {
    public CardColor[] colors;

    public AbstractEasyRelic(String setId, RelicTier tier, LandingSound sfx) {
        this(setId, tier, sfx, new CardColor[] {});
    }

    public AbstractEasyRelic(String setId, RelicTier tier, LandingSound sfx, CardColor color) {
        this(setId, tier, sfx, new CardColor[] { color });
    }

    public AbstractEasyRelic(String setId, RelicTier tier, LandingSound sfx, CardColor[] colors) {
        super(setId, TexLoader.getTexture(makeRelicPath(removeModID(setId) + ".png")), tier, sfx);
        outlineImg = TexLoader.getTexture(makeRelicPath(removeModID(setId) + "Outline.png"));
        largeImg = TexLoader.getTexture(makeRelicPath(removeModID("large/" + setId) + ".png"));
        this.colors = colors;
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
