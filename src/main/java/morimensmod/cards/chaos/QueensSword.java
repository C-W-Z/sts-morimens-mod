package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
// import com.megacrit.cardcrawl.actions.AbstractGameAction;
// import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
// import com.megacrit.cardcrawl.powers.StrengthPower;
// import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.discardPile;
import static morimensmod.util.Wiz.drawPile;
import static morimensmod.util.Wiz.exhaustPile;
import static morimensmod.util.Wiz.hand;
import static morimensmod.util.Wiz.limbo;

import morimensmod.actions.QueensSwordAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.patches.CustomTags;

public class QueensSword extends AbstractEasyCard {
    public static final String ID = makeID(QueensSword.class.getSimpleName());

    private static final int MAX_EXTRA_ATKCOUNT = 3;
    public static final int INIT_EXTRA_ATKCOUNT = 0;

    public static int extraAtkCountThisCombat = INIT_EXTRA_ATKCOUNT;

    // called in Main Mod File
    public static void onBattleStart() {
        extraAtkCountThisCombat = INIT_EXTRA_ATKCOUNT; // 每場戰鬥重設
    }

    public QueensSword() {
        super(ID, 3, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        damage = baseDamage = 3;
        attackCount = baseAttackCount = 3;
        magicNumber = baseMagicNumber = extraAtkCountThisCombat;
        baseSecondMagic = baseAttackCount + baseMagicNumber; // only for display
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new QueensSwordAction(m, this.damage, attackTimesThisCombat, damageTypeForTurn));

        for (int i = 0; i < attackCount + magicNumber; i++) {
            addToBot(new QueensSwordAction(m, damage, damageTypeForTurn, AttackEffect.SLASH_HORIZONTAL));
            // 每次造成傷害
            // dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
            // 每次造成傷害後獲得1力量（本回合）
            // addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
            // addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1), 1));
        }

        // 打出後將額外攻擊次數 +1（最多 3）
        if (extraAtkCountThisCombat < MAX_EXTRA_ATKCOUNT) {
            extraAtkCountThisCombat++;
            updateAllAttackTimes();
        }
    }

    @Override
    public void applyPowers() {
        magicNumber = baseMagicNumber = extraAtkCountThisCombat;
        secondMagic = baseSecondMagic = attackCount + extraAtkCountThisCombat;
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        magicNumber = baseMagicNumber = extraAtkCountThisCombat;
        secondMagic = baseSecondMagic = attackCount + extraAtkCountThisCombat;
        super.calculateCardDamage(mo);
    }

    @Override
    public void upp() {
        upgradeAttackCount(1); // 升級後攻擊次數+1
    }

    public static void updateAllAttackTimes() {
        for (AbstractCard c : hand().group)
            if (c instanceof QueensSword)
                updateExtraAtkCount(c);
        for (AbstractCard c : drawPile().group)
            if (c instanceof QueensSword)
                updateExtraAtkCount(c);
        for (AbstractCard c : discardPile().group)
            if (c instanceof QueensSword)
                updateExtraAtkCount(c);
        // 可選：更新手牌以外的 zones（如 exhaustPile、limbo 等）
        for (AbstractCard c : exhaustPile().group)
            if (c instanceof QueensSword)
                updateExtraAtkCount(c);
        for (AbstractCard c : limbo().group)
            if (c instanceof QueensSword)
                updateExtraAtkCount(c);
    }

    private static void updateExtraAtkCount(AbstractCard c) {
        AbstractEasyCard card = (AbstractEasyCard) c;
        card.magicNumber = card.baseMagicNumber = extraAtkCountThisCombat;
        card.secondMagic = card.baseSecondMagic = card.attackCount + extraAtkCountThisCombat;
        card.initializeDescription();
    }
}
