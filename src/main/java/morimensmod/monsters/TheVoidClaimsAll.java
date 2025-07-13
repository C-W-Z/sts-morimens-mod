package morimensmod.monsters;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeMonsterPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import basemod.animations.AbstractAnimation;
import morimensmod.actions.NewWaitAction;
import morimensmod.misc.Animator;
import morimensmod.util.ModSettings;

public class TheVoidClaimsAll extends AbstractMorimensMonster {

    public static final String ID = makeID(TheVoidClaimsAll.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = 0;
    private static final float yOffset = 0;

    public TheVoidClaimsAll(float x, float y) {
        super(NAME, ID, 200, 450F, 550F, x, y);

        if (AbstractDungeon.ascensionLevel >= 7)
            setHp(200, 220);
        else
            setHp(180, 200);

        if (AbstractDungeon.ascensionLevel >= 2) {
            addDamage(12, 1);
            addDamage(16, 1);
            addDamage(7, 3);
            addDamage(8, 2);
        } else {
            addDamage(10, 1);
            addDamage(14, 1);
            addDamage(5, 3);
            addDamage(6, 2);
        }
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.MONSTER_IDLE_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_IDLE_ANIM + ".png"),
                9, 18, 1, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_HIT_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_HIT_ANIM + ".png"),
                3, 6, 0, false, xOffset + 26.5F, yOffset - 9F);
        animator.addAnimation(
                ModSettings.MONSTER_ATTACK_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_ATTACK_ANIM + ".png"),
                5, 7, 0, false, xOffset - 149.5F, yOffset - 18F);
        animator.setDefaultAnim(ModSettings.MONSTER_IDLE_ANIM);
        // animator.setScale(0.8F); 直接對素材做縮放了，這裡就不用再縮放
        return animator;
    }

    @Override
    public void getMove(int num) {
        switch (turn % 4) {
            case 0:
                setAttackIntent(0, Intent.ATTACK_DEBUFF);
                break;
            case 1:
                setAttackIntent(1, Intent.ATTACK_BUFF);
                break;
            case 2:
                setAttackIntent(2, Intent.ATTACK);
                break;
            case 3:
                setAttackIntent(3, Intent.ATTACK_DEBUFF);
                break;
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(0.9F));
                attackAction(0, AttackEffect.BLUNT_HEAVY);
                addToBot(new ApplyPowerAction(p(), this, new WeakPower(p(), 2, true)));
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(0.9F));
                attackAction(1, AttackEffect.BLUNT_HEAVY);
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 3)));
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(0.9F));
                attackAction(2, AttackEffect.BLUNT_LIGHT);
                break;
            case 3:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(0.9F));
                attackAction(3, AttackEffect.BLUNT_LIGHT);
                addToBot(new ApplyPowerAction(p(), this, new FrailPower(p(), 2, true)));
                break;
        }

        super.takeTurn();
    }
}
