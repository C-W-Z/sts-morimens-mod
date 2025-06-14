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

public class QueensSword extends AbstractEasyCard {
    public static final String ID = makeID("QueensSword");

    private static final int MAX_ATK_TIMES = 6;
    public static final int INIT_ATK_TIMES = 3;

    public static int attackTimesThisCombat = INIT_ATK_TIMES;

    /* see Main Mod File */
    /**
     * @Override
     * public void receiveOnBattleStart() {
     * QueensSword.attackTimesThisCombat = INIT_ATK_TIMES; // 每場戰鬥重新設定為3
     * }
     */

    public QueensSword() {
        super(ID, 3, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY, CHAOS_COLOR);
        this.baseDamage = 3;
        this.magicNumber = this.baseMagicNumber = attackTimesThisCombat;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new QueensSwordAction(m, this.damage, attackTimesThisCombat, damageTypeForTurn));

        for (int i = 0; i < magicNumber; i++) {
            addToBot(new QueensSwordAction(m, damage, damageTypeForTurn, AttackEffect.SMASH));
            // 每次造成傷害
            // dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
            // 每次造成傷害後獲得1力量（本回合）
            // addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1), 1));
            // addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, 1), 1));
        }

        // 打出後將 hits 次數 +1（最多 6）
        if (attackTimesThisCombat < MAX_ATK_TIMES) {
            attackTimesThisCombat++;
            updateAllAttackTimes();
        }
    }

    @Override
    public void applyPowers() {
        this.magicNumber = this.baseMagicNumber = attackTimesThisCombat;
        super.applyPowers();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.magicNumber = this.baseMagicNumber = attackTimesThisCombat;
        super.calculateCardDamage(mo);
    }

    @Override
    public void upp() {
        upgradeDamage(1); // 升級後每擊 +1 傷害（可調整）
    }

    @Override
    public AbstractCard makeCopy() {
        QueensSword copy = new QueensSword();
        copy.magicNumber = copy.baseMagicNumber = attackTimesThisCombat;
        return copy;
    }

    public static void updateAllAttackTimes() {
        for (AbstractCard c : hand().group) {
            if (c instanceof QueensSword) {
                c.magicNumber = c.baseMagicNumber = QueensSword.attackTimesThisCombat;
                c.initializeDescription();
            }
        }
        for (AbstractCard c : drawPile().group) {
            if (c instanceof QueensSword) {
                c.magicNumber = c.baseMagicNumber = QueensSword.attackTimesThisCombat;
                c.initializeDescription();
            }
        }
        for (AbstractCard c : discardPile().group) {
            if (c instanceof QueensSword) {
                c.magicNumber = c.baseMagicNumber = QueensSword.attackTimesThisCombat;
                c.initializeDescription();
            }
        }
        // 可選：更新手牌以外的 zones（如 exhaustPile、limbo 等）
        for (AbstractCard c : exhaustPile().group) {
            if (c instanceof QueensSword) {
                c.magicNumber = c.baseMagicNumber = QueensSword.attackTimesThisCombat;
                c.initializeDescription();
            }
        }
        for (AbstractCard c : limbo().group) {
            if (c instanceof QueensSword) {
                c.magicNumber = c.baseMagicNumber = QueensSword.attackTimesThisCombat;
                c.initializeDescription();
            }
        }
    }
}
