package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.actB;
import static morimensmod.util.Wiz.discardPile;
import static morimensmod.util.Wiz.drawPile;
import static morimensmod.util.Wiz.exhaustPile;
import static morimensmod.util.Wiz.hand;
import static morimensmod.util.Wiz.limbo;
import static morimensmod.util.Wiz.p;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import morimensmod.actions.KeyflareChangeAction;
import morimensmod.actions.QueensSwordAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.enums.CustomTags;

public class QueensSword extends AbstractEasyCard {

    private static final Logger logger = LogManager.getLogger(QueensSword.class);

    public static final String ID = makeID(QueensSword.class.getSimpleName());

    private static final int MAX_EXTRA_ATKCOUNT = 3;
    public static final int INIT_EXTRA_ATKCOUNT = 0;

    public static int extraAtkCountThisCombat = INIT_EXTRA_ATKCOUNT;

    private boolean isEndTurnDiscard = false;

    // called in Main Mod File
    public static void onBattleStart() {
        extraAtkCountThisCombat = INIT_EXTRA_ATKCOUNT; // 每場戰鬥重設
    }

    public QueensSword() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY, CHAOS_COLOR, CardImgID.Ramona.ID);
        tags.add(CustomTags.COMMAND);
        damage = baseDamage = 4;
        attackCount = baseAttackCount = 3;
        magicNumber = baseMagicNumber = extraAtkCountThisCombat;
        secondMagic = baseSecondMagic = attackCount + extraAtkCountThisCombat; // only for display
        thirdMagic = baseThirdMagic = 0; // 臨力
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // addToBot(new QueensSwordAction(m, this.damage, attackTimesThisCombat,
        // damageTypeForTurn));

        for (int i = 0; i < attackCount + magicNumber; i++) {
            actB(() -> {
                calculateCardDamage(m);
                addToTop(new QueensSwordAction(
                        m,
                        new DamageInfo(p, damage, damageTypeForTurn),
                        thirdMagic,
                        AttackEffect.SLASH_HORIZONTAL));
            });
        }

        // 打出後將額外攻擊次數 +1（最多 3）
        if (extraAtkCountThisCombat < MAX_EXTRA_ATKCOUNT) {
            extraAtkCountThisCombat++;
            updateAllAttackTimes();
        }
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        if (hand().group.contains(this))
            isEndTurnDiscard = true;
        logger.debug("triggerOnEndOfPlayerTurn: " + isEndTurnDiscard);
    }

    @Override
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        if (!isEndTurnDiscard)
            return;
        isEndTurnDiscard = false;
        if (!(p() instanceof AbstractAwakener))
            return;
        AbstractAwakener awaker = (AbstractAwakener) p();
        logger.debug("onMoveToDiscard:" + costForTurn * awaker.keyflareRegen);
        addToTop(new KeyflareChangeAction(p(), costForTurn * awaker.keyflareRegen));
    }

    @Override
    public void triggerOnManualDiscard() {
        super.triggerOnManualDiscard();
        if (!(p() instanceof AbstractAwakener))
            return;
        AbstractAwakener awaker = (AbstractAwakener) p();
        logger.debug("triggerOnManualDiscard:" + costForTurn * awaker.keyflareRegen);
        addToTop(new KeyflareChangeAction(p(), costForTurn * awaker.keyflareRegen));
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
        upgradeThirdMagic(1);
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
