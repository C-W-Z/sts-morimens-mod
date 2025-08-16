package morimensmod.cards.chaos;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import morimensmod.actions.KeyflareChangeAction;
import morimensmod.actions.PierceDamageAction;
import morimensmod.cards.AbstractEasyCard;
import morimensmod.characters.AbstractAwakener;
import morimensmod.patches.enums.CustomTags;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.patches.enums.ColorPatch.CardColorPatch.CHAOS_COLOR;
import static morimensmod.util.Wiz.*;

public class SixWings extends AbstractEasyCard {
    public final static String ID = makeID(SixWings.class.getSimpleName());

    public SixWings() {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CHAOS_COLOR);
        tags.add(CustomTags.COMMAND);
        damage = baseDamage = 6;
        attackCount = baseAttackCount = 6; // 攻擊次數
        exhaust = true; // 消耗詞條
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractAwakener awaker = (AbstractAwakener) p();
        if (EnergyPanel.totalCount >= this.costForTurn || freeToPlay() || this.isInAutoplay) {
            normalUse(p, m);
        }
        else if(awaker.getKeyflare() >= 500) {
            normalUse(p, m);
            addToTop(new KeyflareChangeAction(p(), -500));
            // 有個小問題是，如果有消耗500銀鑰能量也會把剩下的能量消耗完，所以這邊要補回去
            if (EnergyPanel.totalCount > 0) {
                addToBot(new GainEnergyAction(EnergyPanel.totalCount));
            }
        }
    }

    public void normalUse(AbstractPlayer p, AbstractMonster m) {
        // 造成6次穿刺傷害
        for (int i = 0; i < attackCount; i++) {
            actB(() -> {
                calculateCardDamage(m);
                addToTop(new PierceDamageAction(m, new DamageInfo(p, damage), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            });
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        AbstractAwakener awaker = (AbstractAwakener) p();
        if (EnergyPanel.totalCount >= this.costForTurn || freeToPlay() || this.isInAutoplay) {
            return true;
        }
        if (awaker.getKeyflare() >= 500) {
            return true;
        }

        this.cantUseMessage = "算力不足，且銀鑰能量不足500！";
        return false;
    }

    @Override
    public boolean hasEnoughEnergy() {
        AbstractAwakener awaker = (AbstractAwakener) p();
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
        // if (EnergyPanel.totalCount >= this.costForTurn || freeToPlay() || this.isInAutoplay)
        //     return true;
        // this.cantUseMessage = TEXT[11];
        // return false;
//        if (awaker.getKeyflare()<500)
//            return false;
        return true;
    }
    @Override
    public void upp() {
        upgradeDamage(6);
    }
}
