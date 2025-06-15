package morimensmod.cards.chaos;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.CustomTags;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.p;

public class EquivalentExchange extends AbstractEasyCard {
    public final static String ID = makeID(EquivalentExchange.class.getSimpleName());

    public EquivalentExchange() {
        super(ID, 3, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        heal = baseHeal = 15;
        magicNumber = baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscardAction(p, p, p.hand.size(), false));
        addToBot(new HealAction(p, p, heal));
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public void upp() {
        selfRetain = true;
    }

    @Override
    public void applyPowers() {
        // TODO:

        baseBlock = (p().hand.size() - 1) * magicNumber;
        heal = baseHeal + baseBlock;

        int blockAmplify = 100 + baseBlockAmplify + AbstractAwakener.baseBlockAmplify;
        int healAmplify = 100 + baseHealAmplify + AbstractAwakener.baseHealAmplify;

        baseBlock = MathUtils.ceil(baseBlock * blockAmplify / 100F);
        heal = MathUtils.ceil((baseHeal + baseBlock) * healAmplify / 100F);

        super.applyPowersToBlock();

        if (block != 0)
            isBlockModified = true;
        if (heal != baseHeal)
            isHealModified = true;

        if (upgraded)
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
        else
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }
}
