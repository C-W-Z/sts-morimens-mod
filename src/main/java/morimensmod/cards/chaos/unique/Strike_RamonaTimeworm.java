package morimensmod.cards.chaos.unique;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.getPowerAmount;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.patches.enums.CustomTags;
import morimensmod.powers.NegentropyPower;

public class Strike_RamonaTimeworm extends AbstractEasyCard {
    public final static String ID = makeID(Strike_RamonaTimeworm.class.getSimpleName());

    public Strike_RamonaTimeworm() {
        super(ID, CardImgID.RamonaTimewormAttack, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        tags.add(CardTags.STRIKE);
        tags.add(CardTags.STARTER_STRIKE);
        tags.add(CustomTags.LOOP);
        damage = baseDamage = 6;
        attackCount = baseAttackCount = 1;
        aliemus = baseAliemus = 5;
        magicNumber = 4;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));

        int additionAttackCount = 0;
        if (getPowerAmount(p, NegentropyPower.POWER_ID) >= NegentropyPower.INVOKE_AMOUNT) {
            addToBot(new ReducePowerAction(p, p, NegentropyPower.POWER_ID, NegentropyPower.INVOKE_AMOUNT));
            additionAttackCount = magicNumber;
        }

        for (int i = 0; i < attackCount + additionAttackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                dmgTop(m, AttackEffect.NONE);
            });
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeAliemus(5);
    }
}
