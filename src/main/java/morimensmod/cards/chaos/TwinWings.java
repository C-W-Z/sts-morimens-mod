package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import morimensmod.actions.AliemusChangeAction;
import morimensmod.actions.QueensSwordAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.cards.CardImgID;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.enums.CustomTags;
import morimensmod.actions.KeyflareChangeAction;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.*;

public class TwinWings extends AbstractEasyCard {
    public final static String ID = makeID(TwinWings.class.getSimpleName());

    public TwinWings() {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CHAOS_COLOR, CardImgID.Tawil.ID);
        tags.add(CustomTags.COMMAND);
        damage = baseDamage = 2;
        attackCount = baseAttackCount = 2; // 攻擊次數
        aliemus = baseAliemus = 5; // 狂氣值
        draw = baseDraw = 1;
        cardsToPreview = new FourWings();
        exhaust = true; // 消耗詞條
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (EnergyPanel.totalCount >= this.costForTurn || freeToPlay() || this.isInAutoplay) {
            normalUse(p, m);
        } else if (p instanceof AbstractAwakener && AbstractAwakener.getKeyflare() >= 500) {
            freeToPlayOnce = true;
            normalUse(p, m);
            addToTop(new KeyflareChangeAction(p(), -500));
        }
    }

    public void normalUse(AbstractPlayer p, AbstractMonster m) {
        // 造成2刺傷害
        for (int i = 0; i < attackCount; i++) {
            actB(() -> {
                calculateCardDamage(m);
                dmgTop(m, AttackEffect.SLASH_HORIZONTAL);
            });
        }
        addToBot(new AliemusChangeAction(p, aliemus)); // 獲得狂氣
        shuffleIn(cardsToPreview); // 把「四翼漸生」洗入抽牌堆中
        addToBot(new DrawCardAction(p, draw)); // 抽1張卡
    }

    // @Override
    // public boolean canUse(AbstractPlayer p, AbstractMonster m) {
    //     if (EnergyPanel.totalCount >= this.costForTurn || freeToPlay() || this.isInAutoplay) {
    //         return true;
    //     }
    //     if (p instanceof AbstractAwakener && AbstractAwakener.getKeyflare() >= 500) {
    //         return true;
    //     }
    //     this.cantUseMessage = "算力不足，且銀鑰能量不足500！";
    //     return false;
    // }

    @Override
    public boolean hasEnoughEnergy() {
        if (AbstractDungeon.actionManager.turnHasEnded) {
            this.cantUseMessage = TEXT[9];
            return false;
        }
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (!p.canPlayCard(this)) {
                this.cantUseMessage = TEXT[13];
                return false;
            }
        }
        if (AbstractDungeon.player.hasPower("Entangled") && this.type == CardType.ATTACK) {
            this.cantUseMessage = TEXT[10];
            return false;
        }
        for (AbstractRelic r : AbstractDungeon.player.relics) {
            if (!r.canPlay(this))
                return false;
        }
        for (AbstractBlight b : AbstractDungeon.player.blights) {
            if (!b.canPlay(this))
                return false;
        }
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!c.canPlay(this))
                return false;
        }
        if (EnergyPanel.totalCount >= this.costForTurn || freeToPlay() || this.isInAutoplay)
            return true;
        if (AbstractDungeon.player instanceof AbstractAwakener) {
            if (AbstractAwakener.getKeyflare() >= 500)
                return true;
            this.cantUseMessage = TEXT[11];
            return false;
        }
        this.cantUseMessage = TEXT[11];
        return false;
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        cardsToPreview.upgrade();
    }
}
