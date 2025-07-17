package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;
import static morimensmod.util.Wiz.atb;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.actions.EasyXCostAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.enums.CustomTags;

public class UnstableBarrier extends AbstractEasyCard {
    public final static String ID = makeID(UnstableBarrier.class.getSimpleName());

    public UnstableBarrier() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        block = baseBlock = 5;
        aliemus = baseAliemus = 5;
        magicNumber = baseMagicNumber = 1; // 力量
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            addToTop(new AliemusChangeAction(p, aliemus * (effect + params[0])));
            for (int i = 0; i < effect + params[0] + 1; i++)
                addToTop(new GainBlockAction(p, block));
            return true;
        }, 0));
        applyToSelf(new StrengthPower(p, magicNumber));
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}
