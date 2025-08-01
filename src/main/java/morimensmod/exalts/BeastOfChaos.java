package morimensmod.exalts;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.*;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTarget;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.powers.PenNibPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.helpers.CardModifierManager;
import morimensmod.actions.NewWaitAction;
import morimensmod.cardmodifiers.EtherealModifier;
import morimensmod.cardmodifiers.ExhaustModifier;
import morimensmod.cards.NullCard;
import morimensmod.cards.chaos.Strike;
import morimensmod.characters.AbstractAwakener;
import morimensmod.characters.Lotan;
import morimensmod.config.ModSettings;
import morimensmod.vfx.CetaceanEffect;
import morimensmod.vfx.LargPortraitFlashInEffect;

public class BeastOfChaos extends AbstractExalt {

    public static final String ID = makeID(BeastOfChaos.class.getSimpleName());
    private static final UIStrings UI_STRINGS = CardCrawlGame.languagePack.getUIString(ID);

    private static final int BASE_DAMAGE = 15;
    private static final int ADDITION_DMG_PER_STRIKE = 2;
    private int strikeCount = 0;

    private static final int PERCENT_DAMAGE = 15;

    public BeastOfChaos() {
        damage = baseDamage = BASE_DAMAGE;
    }

    @Override
    public void onBattleStart() {
        super.onBattleStart();
        strikeCount = 0;
    }

    @Override
    public void onCardUse(AbstractCard card) {
        if (isStrikeOrAsStrike(card))
            strikeCount++;
    }

    @Override
    public void exalt() {
        atb(new VFXAction(p(), new LargPortraitFlashInEffect(removeModID(ID)), ModSettings.EXALT_PROTRAIT_DURATION, true));

        atb(new RemoveSpecificPowerAction(p(), p(), WeakPower.POWER_ID));

        if (p() instanceof Lotan) {
            ((Lotan) p()).setAnimation(ModSettings.PLAYER_EXALT_ANIM);
            atb(new NewWaitAction(53F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
        }

        atb(new VFXAction(new CetaceanEffect(), 3F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));

        actB(() -> {
            for (int i = 0; i < 2; i++) {
                calculateExaltDamage();
                att(new DamageAllEnemiesAction(p(), multiDamage, DamageType.NORMAL, AttackEffect.NONE));
            }
        });
        AbstractCard strike = new Strike();
        CardModifierManager.addModifier(strike, new ExhaustModifier());
        CardModifierManager.addModifier(strike, new EtherealModifier());
        makeInHand(strike, 2);
    }

    @Override
    public void overExalt() {
        exalt();
        calculateOverExaltDamage();
        atb(new DamageAllEnemiesAction(p(), multiDamage, DamageType.NORMAL, AttackEffect.SLASH_HEAVY));
        actB(() -> baseDamageAmplify += 100);
    }

    @Override
    public String getExaltTitle() {
        return UI_STRINGS.TEXT[0];
    }

    @Override
    public String getExaltDescription() {
        applyPowersToExalt();
        return UI_STRINGS.TEXT[1] + damage + UI_STRINGS.TEXT[2] + ADDITION_DMG_PER_STRIKE + UI_STRINGS.TEXT[3];
    }

    @Override
    public String getOverExaltTitle() {
        return UI_STRINGS.EXTRA_TEXT[0];
    }

    @Override
    public String getOverExaltDescription() {
        return UI_STRINGS.EXTRA_TEXT[1];
    }

    public void applyPowersToExalt() {
        int damageAmplify = 100 + baseDamageAmplify + AbstractAwakener.baseDamageAmplify;
        baseDamage = MathUtils.ceil(BASE_DAMAGE * damageAmplify / 100F) + strikeCount * ADDITION_DMG_PER_STRIKE;

        AbstractCard nullCard = new NullCard(CardType.ATTACK, CardTarget.ALL_ENEMY);

        AbstractPlayer player = AbstractDungeon.player;
        ArrayList<AbstractMonster> m = (AbstractDungeon.getCurrRoom()).monsters.monsters;
        float[] tmp = new float[m.size()];
        int i;
        for (i = 0; i < tmp.length; i++) {
            tmp[i] = baseDamage;
            for (AbstractRelic r : player.relics)
                tmp[i] = r.atDamageModify(tmp[i], nullCard);
            for (AbstractPower p : player.powers)
                if (p.ID != PenNibPower.POWER_ID && p.ID != DoubleDamagePower.POWER_ID)
                    tmp[i] = p.atDamageGive(tmp[i], DamageType.NORMAL);
            tmp[i] = player.stance.atDamageGive(tmp[i], DamageType.NORMAL);
            for (AbstractPower p : player.powers)
                tmp[i] = p.atDamageFinalGive(tmp[i], DamageType.NORMAL);
            if (tmp[i] < 0.0F)
                tmp[i] = 0.0F;
        }
        multiDamage = new int[tmp.length];
        for (i = 0; i < tmp.length; i++)
            multiDamage[i] = MathUtils.floor(tmp[i]);
        damage = multiDamage[0];
    }

    public void calculateExaltDamage() {
        int damageAmplify = 100 + baseDamageAmplify + AbstractAwakener.baseDamageAmplify;
        baseDamage = MathUtils.ceil(BASE_DAMAGE * damageAmplify / 100F) + strikeCount * ADDITION_DMG_PER_STRIKE;

        AbstractCard nullCard = new NullCard(CardType.ATTACK, CardTarget.ALL_ENEMY);

        AbstractPlayer player = AbstractDungeon.player;
        ArrayList<AbstractMonster> m = (AbstractDungeon.getCurrRoom()).monsters.monsters;
        float[] tmp = new float[m.size()];
        int i;
        for (i = 0; i < tmp.length; i++) {
            tmp[i] = this.baseDamage;
            for (AbstractRelic r : player.relics)
                tmp[i] = r.atDamageModify(tmp[i], nullCard);
            for (AbstractPower p : player.powers)
                if (p.ID != PenNibPower.POWER_ID && p.ID != DoubleDamagePower.POWER_ID)
                    tmp[i] = p.atDamageGive(tmp[i], DamageType.NORMAL);
            tmp[i] = player.stance.atDamageGive(tmp[i], DamageType.NORMAL);
            for (AbstractPower p : ((AbstractMonster) m.get(i)).powers) {
                if (!((AbstractMonster) m.get(i)).isDying && !((AbstractMonster) m.get(i)).isEscaping)
                    tmp[i] = p.atDamageReceive(tmp[i], DamageType.NORMAL);
            }
            for (AbstractPower p : player.powers)
                tmp[i] = p.atDamageFinalGive(tmp[i], DamageType.NORMAL);
            for (AbstractPower p : ((AbstractMonster) m.get(i)).powers) {
                if (!((AbstractMonster) m.get(i)).isDying && !((AbstractMonster) m.get(i)).isEscaping)
                    tmp[i] = p.atDamageFinalReceive(tmp[i], DamageType.NORMAL);
            }
            if (tmp[i] < 0.0F)
                tmp[i] = 0.0F;
        }
        this.multiDamage = new int[tmp.length];
        for (i = 0; i < tmp.length; i++)
            this.multiDamage[i] = MathUtils.floor(tmp[i]);
        this.damage = this.multiDamage[0];
    }

    public void calculateOverExaltDamage() {
        ArrayList<AbstractMonster> m = (AbstractDungeon.getCurrRoom()).monsters.monsters;
        multiDamage = new int[m.size()];
        int i;
        for (i = 0; i < multiDamage.length; i++) {
            multiDamage[i] = MathUtils.ceil(m.get(i).maxHealth * PERCENT_DAMAGE / 100F);
            if (multiDamage[i] < 0)
                multiDamage[i] = 0;
        }
    }
}
