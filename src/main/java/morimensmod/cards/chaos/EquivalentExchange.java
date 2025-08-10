package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.patches.enums.CustomTags;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.p;

public class EquivalentExchange extends AbstractEasyCard {
    public final static String ID = makeID(EquivalentExchange.class.getSimpleName());

    public EquivalentExchange() {
        super(ID, CardImgID.DollSkill, 3, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.HEALING);
        heal = baseHeal = 4;
        block = baseBlock = 0;
        magicNumber = baseMagicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
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
        int amount = (p().hand.size() - 1) * magicNumber;

        int originBaseBlock = baseBlock;
        baseBlock += amount;
        super.applyPowersToBlock();
        baseBlock = originBaseBlock;
        if (block != baseBlock)
            isBlockModified = true;

        int originBaseHeal = baseHeal;
        baseHeal += amount;
        applyHealAmplify();
        baseHeal = originBaseHeal;
        if (heal != baseHeal)
            isHealModified = true;

        rawDescription = cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }
}
