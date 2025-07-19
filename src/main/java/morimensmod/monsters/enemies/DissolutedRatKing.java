package morimensmod.monsters.enemies;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeMonsterPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.p;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.animations.AbstractAnimation;
import morimensmod.actions.NewWaitAction;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractMorimensMonster;

public class DissolutedRatKing extends AbstractMorimensMonster {

    public static final String ID = makeID(DissolutedRatKing.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = 0;
    private static final float yOffset = 0;

    private int blockAmt = 8;
    private int strengthAmt = 2;
    private int frailAmt = 1;

    public DissolutedRatKing(float x, float y) {
        this(x, y, 0);
    }

    public DissolutedRatKing(float x, float y, int turnOffset) {
        super(NAME, ID, getMaxHP(), 230F, 300F, x, y, turnOffset);

        int dmgAddition = AbstractDungeon.actNum - 1;

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_DMG) {
            addNoDamage();
            addDamage(dmgAddition + 6, 2);
            addDamage(dmgAddition + 6, 1);
            addDamage(dmgAddition + 10, 1);
        } else {
            addNoDamage();
            addDamage(dmgAddition + 4, 2);
            addDamage(dmgAddition + 4, 1);
            addDamage(dmgAddition + 8, 1);
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_MONSTER_ACTION) {
            strengthAmt = 2 + AbstractDungeon.floorNum / 25;
            frailAmt = 2;
        } else {
            strengthAmt = 1 + AbstractDungeon.floorNum / 25;
            frailAmt = 1;
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            blockAmt = 11 + AbstractDungeon.floorNum / 5;
        else
            blockAmt = 7 + AbstractDungeon.floorNum / 5;
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            return 47 + 2 * AbstractDungeon.floorNum;
        return 37 + 2 * AbstractDungeon.floorNum;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.MONSTER_IDLE_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_IDLE_ANIM + ".png"),
                3, 17, 0, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_HIT_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_HIT_ANIM + ".png"),
                4, 5, 0, false, xOffset + 19F, yOffset -21F);
        animator.addAnimation(
                ModSettings.MONSTER_ATTACK_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_ATTACK_ANIM + ".png"),
                4, 11, 1, false, xOffset - 137F, yOffset -2F);
        animator.addAnimation(
                ModSettings.MONSTER_SKILL1_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_SKILL1_ANIM + ".png"),
                18, 3, 1, false, xOffset + 42.5F, yOffset - 9F);
        animator.setDefaultAnim(ModSettings.MONSTER_IDLE_ANIM);
        return animator;
    }

    @Override
    public void getMove(int num) {
        switch (turn % 4) {
            case 0: setIntent(0, Intent.DEFEND_BUFF);   break;
            case 1: setIntent(1, Intent.ATTACK);        break;
            case 2: setIntent(2, Intent.ATTACK_DEBUFF); break;
            case 3: setIntent(3, Intent.ATTACK);        break;
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_SKILL1_ANIM));
                addToBot(new NewWaitAction(21F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                addToBot(new GainBlockAction(this, blockAmt));
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(19F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_LIGHT);
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(19F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_HEAVY);
                addToBot(new ApplyPowerAction(p(), this, new FrailPower(p(), frailAmt, true)));
                break;
            case 3:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(19F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_HEAVY);
                break;
        }

        super.takeTurn();
    }
}
