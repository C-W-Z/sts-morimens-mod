package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.buffs.Insight;
import morimensmod.patches.enums.CustomTags;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.makeInHand;
import static morimensmod.util.Wiz.shuffleIn;

public class MemoryRondo extends AbstractEasyCard {
    public final static String ID = makeID(MemoryRondo.class.getSimpleName());

    private static boolean islastUseForcedPlay = false;

    public MemoryRondo() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CustomTags.FORCE_PLAYABLE);
        magicNumber = baseMagicNumber = 1; // 力量
        secondMagic = baseSecondMagic = 2; // 靈感
        cardsToPreview = new Insight();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (purgeOnUse) {
            if (islastUseForcedPlay)
                forceUse(p, m);
            else
                normalUse(p, m);

            return;
        }
        if (EnergyPanel.totalCount >= this.costForTurn || freeToPlay() || this.isInAutoplay) {
            normalUse(p, m);
            islastUseForcedPlay = false;
        } else {
            freeToPlayOnce = true;
            forceUse(p, m);
            islastUseForcedPlay = true;
        }
    }

    public void normalUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));
        makeInHand(cardsToPreview, secondMagic);
    }

    public void forceUse(AbstractPlayer p, AbstractMonster m) {
        if (upgraded)
            applyToSelf(new StrengthPower(p, magicNumber));
        shuffleIn(cardsToPreview, secondMagic);
    }

    @Override
    public boolean hasEnoughEnergy() {
        if (AbstractDungeon.actionManager.turnHasEnded) {
            this.cantUseMessage = TEXT[9];
            return false;
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (!p.canPlayCard(this)) {
                this.cantUseMessage = TEXT[13];
                return false;
            }
        }
        if (AbstractDungeon.player.hasPower("Entangled") && this.type == CardType.ATTACK) {
            this.cantUseMessage = TEXT[10];
            return false;
        }
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (!r.canPlay(this))
                return false;
        }
        for (AbstractBlight b : AbstractDungeon.player.blights) {
            if (!b.canPlay(this))
                return false;
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!c.canPlay(this))
                return false;
        }
        // if (EnergyPanel.totalCount >= this.costForTurn || freeToPlay() || this.isInAutoplay)
        //     return true;
        // this.cantUseMessage = TEXT[11];
        // return false;
        return true;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}
