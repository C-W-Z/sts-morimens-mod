package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.enums.CustomTags;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;

public class FatesDescent extends AbstractEasyCard {
    public final static String ID = makeID(FatesDescent.class.getSimpleName());

    public FatesDescent() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.HEALING);
        aliemus = baseAliemus = 30;
        heal = baseHeal = 2;
        magicNumber = baseMagicNumber = 50; // 每有50點狂氣重複一次
        secondMagic = baseSecondMagic = 2; // 施加中毒
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int aliemusBefore = AbstractAwakener.getAliemus();
        addToBot(new AliemusChangeAction(p, aliemus));
        for (int i = 0; i < 1 + (aliemusBefore / magicNumber); i++) {
            addToBot(new HealAction(p, p, heal, Settings.ACTION_DUR_FASTER));
        }
    }

    @Override
    public void upp() {
        upgradeAliemus(20);
        upgradeHeal(1);
        upgradeSecondMagic(1);
    }
}
