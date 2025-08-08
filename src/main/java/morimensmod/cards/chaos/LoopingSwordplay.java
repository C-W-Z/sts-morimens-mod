package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomSavable;
import morimensmod.actions.LoopingSwordplayAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.patches.enums.CustomTags;

public class LoopingSwordplay extends AbstractEasyCard implements CustomSavable<Integer> {
    public final static String ID = makeID(LoopingSwordplay.class.getSimpleName());

    public LoopingSwordplay() {
        super(ID, CardImgID.LotanAttack, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.STRIKE);
        damage = baseDamage = misc = 16;
        attackCount = baseAttackCount = 1;
        magicNumber = baseMagicNumber = 3;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                addToTop(new LoopingSwordplayAction(m, new DamageInfo(p, damage, damageTypeForTurn), magicNumber, this));
            });
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public void applyPowers() {
        baseDamage = misc;
        super.applySuperPower();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        baseDamage = misc;
        super.calculateCardDamage(mo);
    }

    @Override
    public void onLoad(Integer misc) {
        this.misc = misc;
        damage = baseDamage = misc;
        initializeDescription();
    }

    @Override
    public Integer onSave() {
        return misc;
    }
}
