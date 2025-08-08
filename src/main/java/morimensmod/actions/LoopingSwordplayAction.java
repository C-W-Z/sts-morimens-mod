package morimensmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static morimensmod.util.Wiz.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoopingSwordplayAction extends AbstractGameAction {

    private static final Logger logger = LogManager.getLogger(LoopingSwordplayAction.class);

    private int increaseAmount;
    private DamageInfo info;
    private AbstractCard card;

    private boolean triggered = false;

    public LoopingSwordplayAction(AbstractCreature target, DamageInfo info, int incAmount, AbstractCard card) {
        this.info = info;
        this.setValues(target, info);
        this.increaseAmount = incAmount;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FAST;
        this.card = card;
    }

    public void update() {
        if (duration == Settings.ACTION_DUR_FAST && (target == null || isKilled(target))) {
            isDone = true;
            return;
        }

        if (!triggered && duration <= Settings.ACTION_DUR_FASTER && target != null) {
            triggered = true;

            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
            target.damage(info);
            if (isKilled(target)) {
                for (AbstractCard c : deck().group) {
                    if (!c.uuid.equals(card.uuid))
                        continue;
                    c.damage = c.baseDamage = c.misc += increaseAmount;
                    c.isDamageModified = false;
                    logger.debug("deck c.misc = " + c.misc + ", c.baseDamage = " + c.baseDamage);
                    c.initializeDescription();
                }
                for (AbstractCard c : GetAllInBattleInstances.get(card.uuid)) {
                    c.damage = c.baseDamage = c.misc += increaseAmount;
                    c.applyPowers();
                    logger.debug("all battle instance c.misc = " + c.misc + ", c.baseDamage = " + c.baseDamage
                            + ", c.damage = " + c.damage);
                    c.initializeDescription();
                }
                addToBot(new MakeTempCardInHandAction(card, true, true));
                // actB(() -> {
                //     if (exhaustPile().group.contains(card)) {
                //         exhaustPile().moveToHand(card);
                //         logger.debug("return from exhaustPile");
                //     }
                //     else if (discardPile().group.contains(card))
                //         discardPile().moveToHand(card);
                //     else if (drawPile().group.contains(card))
                //         drawPile().moveToHand(card);
                //     else if (limbo().group.contains(card))
                //         limbo().moveToHand(card);
                //     logger.debug("return c.misc = " + card.misc + ", c.baseDamage = " + card.baseDamage + ", c.damage = " + card.damage);
                // });
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        tickDuration();
    }
}
