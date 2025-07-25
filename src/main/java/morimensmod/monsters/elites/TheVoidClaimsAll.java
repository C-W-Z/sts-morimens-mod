package morimensmod.monsters.elites;

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
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractMorimensMonster;

public class TheVoidClaimsAll extends AbstractMorimensMonster {

    public static final String ID = makeID(TheVoidClaimsAll.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = 0;
    private static final float yOffset = 0;

    private int strengthAmt = 3;
    private int weakAmt = 2;
    private int frailAmt = 2;

    public TheVoidClaimsAll(float x, float y) {
        this(x, y, 0);
    }

    public TheVoidClaimsAll(float x, float y, int turnOffset) {
        super(NAME, ID, getMaxHP(), 450F, 550F, x, y, turnOffset);

        this.type = EnemyType.ELITE;

        int dmgAddition = AbstractDungeon.actNum - 1;

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_ELITE_DMG) {
            addDamage(dmgAddition + 13, 1);
            addDamage(dmgAddition + 17, 1);
            addDamage(dmgAddition + 8, 3);
            addDamage(dmgAddition + 9, 2);
        } else {
            addDamage(dmgAddition + 11, 1);
            addDamage(dmgAddition + 15, 1);
            addDamage(dmgAddition + 6, 3);
            addDamage(dmgAddition + 7, 2);
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_ELITE_ACTION) {
            strengthAmt = 4 + AbstractDungeon.floorNum / 25;
            weakAmt = 3;
            frailAmt = 3;
        } else {
            strengthAmt = 3 + AbstractDungeon.floorNum / 25;
            weakAmt = 2;
            frailAmt = 2;
        }
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_ELITE_HP)
            return 210 + 5 * AbstractDungeon.floorNum;
        return 190 + 4 * AbstractDungeon.floorNum;
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
            case 0: setIntent(0, Intent.ATTACK_DEBUFF); break;
            case 1: setIntent(1, Intent.ATTACK_BUFF);   break;
            case 2: setIntent(2, Intent.ATTACK);        break;
            case 3: setIntent(3, Intent.ATTACK_DEBUFF); break;
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(27F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_HEAVY);
                addToBot(new ApplyPowerAction(p(), this, new WeakPower(p(), weakAmt, true)));
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(27F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_HEAVY);
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(27F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_LIGHT);
                break;
            case 3:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(27F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_LIGHT);
                addToBot(new ApplyPowerAction(p(), this, new FrailPower(p(), frailAmt, true)));
                break;
        }

        super.takeTurn();
    }
}
