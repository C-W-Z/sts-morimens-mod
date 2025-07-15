package morimensmod.cards.chaos;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.isStrikeOrAsStrike;
import static morimensmod.util.Wiz.makeInHand;
import static morimensmod.util.WizArt.showThoughtBubble;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.helpers.CardModifierManager;
import morimensmod.actions.AliemusChangeAction;
import morimensmod.cardmodifiers.ExhaustModifier;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.enums.CustomTags;
import morimensmod.util.ModSettings;

public class Showdown extends AbstractEasyCard {
    public final static String ID = makeID(Showdown.class.getSimpleName());

    private static final int MAX_ATTACK_COUNT = 6;

    public Showdown() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        damage = baseDamage = 6;
        attackCount = baseAttackCount = MAX_ATTACK_COUNT;
        aliemus = baseAliemus = 15;
        magicNumber = baseMagicNumber = 1; // 1 張
        secondMagic = baseSecondMagic = 0; // 狂氣 per 攻擊
        cardsToPreview = new UnexpectedGain();
        cardsToPreview.modifyCostForCombat(-cardsToPreview.cost);
        cardsToPreview.freeToPlayOnce = true;
        CardModifierManager.addModifier(cardsToPreview, new ExhaustModifier());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AliemusChangeAction(p, aliemus));

        attackCount = AbstractDungeon.cardRandomRng.random(1, attackCount);

        showThoughtBubble("" + attackCount, ModSettings.DICE_THOUGHT_BUBBLE_TIME);

        for (int i = 0; i < attackCount; i++)
            actB(() -> {
                calculateCardDamage(m);
                dmgTop(m, AttackEffect.NONE);
                addToTop(new AliemusChangeAction(p, secondMagic));
            });

        if (attackCount >= MAX_ATTACK_COUNT) {
            makeInHand(cardsToPreview, magicNumber);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeSecondMagic(3);
    }

    @Override
    public void applyPowers() {
        int damageAmplify = 100 + baseDamageAmplify + AbstractAwakener.baseDamageAmplify;
        if (isStrikeOrAsStrike(this))
            damageAmplify += baseStrikeDamageAmplify;
        int aliemusAmplify = 100 + baseAliemusAmplify + AbstractAwakener.baseAliemusAmplify;

        AbstractEasyCard tmp = (AbstractEasyCard) CardLibrary.getCopy(cardID, timesUpgraded, misc);
        baseDamage = MathUtils.ceil(tmp.baseDamage * damageAmplify / 100F);
        baseAliemus = MathUtils.ceil(tmp.baseAliemus * aliemusAmplify / 100F);
        baseSecondMagic = MathUtils.ceil(tmp.baseSecondMagic * aliemusAmplify / 100F);

        super.applySuperPower();

        if (damageAmplify != 100)
            isDamageModified = true;
        if (aliemusAmplify != 100) {
            isAliemusModified = true;
            aliemus = baseAliemus;
            isSecondMagicModified = true;
            secondMagic = baseSecondMagic;
        }
    }
}
