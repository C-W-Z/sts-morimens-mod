package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.CustomTags;
import morimensmod.powers.SoulblightPower;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.applyToSelf;

public class Soulblight extends AbstractEasyCard {
    public final static String ID = makeID(Soulblight.class.getSimpleName());

    public Soulblight() {
        super(ID, 3, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF, CHAOS_COLOR);
        tags.add(CustomTags.ROUSE);
        aliemus = baseAliemus = 30;
        heal = baseHeal = 5;
        magicNumber = baseMagicNumber = 50; // 觸發中毒%
        selfRetain = true; // 保留
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));
        applyToSelf(new SoulblightPower(p, heal));
    }

    @Override
    public void upp() {
        upgradeAliemus(20);
        upgradeBaseCost(2);
    }

    @Override
    public void applyPowers() {
        // 只有直接獲得的狂氣在這裡計算加成，回血、回狂和中毒在Power中計算加成
        int aliemusAmplify = 100 + baseAliemusAmplify + AbstractAwakener.baseAliemusAmplify;
        applyedBaseAmplifies(100, 100, 100, aliemusAmplify);
        if (aliemusAmplify != 100) {
            isAliemusModified = true;
            aliemus = baseAliemus;
        }
    }
}
