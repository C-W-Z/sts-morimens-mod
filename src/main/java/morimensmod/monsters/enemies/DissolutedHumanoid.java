package morimensmod.monsters.enemies;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeMonsterPath;
import static morimensmod.util.General.removeModID;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import basemod.animations.AbstractAnimation;
import morimensmod.actions.NewWaitAction;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractMorimensMonster;

public class DissolutedHumanoid extends AbstractMorimensMonster {

    public enum Skin {
        B, C
    }

    public static final String ID = makeID(DissolutedHumanoid.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = -27;
    private static final float yOffset = 0;

    private int blockAmt = 5;

    public DissolutedHumanoid(float x, float y, Skin skin) {
        this(x, y, skin, 0);
    }

    public DissolutedHumanoid(float x, float y, Skin skin, int turnOffset) {
        super(NAME, ID, getMaxHP(), 260, 440, getAnimation(skin), x, y, turnOffset);

        int dmgAddition = 2 * (AbstractDungeon.actNum - 1);

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_DMG) {
            addDamage(dmgAddition + 10, 1);
            addDamage(dmgAddition + 6, 1);
            addDamage(dmgAddition + 7, 1);
        } else {
            addDamage(dmgAddition + 8, 1);
            addDamage(dmgAddition + 4, 1);
            addDamage(dmgAddition + 5, 1);
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_MONSTER_ACTION)
            blockAmt = 12 + AbstractDungeon.floorNum / 5;
        else
            blockAmt = 8 + AbstractDungeon.floorNum / 5;
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            return 48 + 2 * AbstractDungeon.floorNum;
        return 38 + 2 * AbstractDungeon.floorNum;
    }

    protected static AbstractAnimation getAnimation(Skin skin) {
        Animator animator = new Animator();

        String prefixPath = removeModID(ID);
        switch (skin) {
            case B: prefixPath += "/b/"; break;
            case C: prefixPath += "/c/"; break;
        }

        animator.addAnimation(
                ModSettings.MONSTER_IDLE_ANIM,
                makeMonsterPath(prefixPath + ModSettings.MONSTER_IDLE_ANIM + ".png"),
                7, 12, 3, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_HIT_ANIM,
                makeMonsterPath(prefixPath + ModSettings.MONSTER_HIT_ANIM + ".png"),
                4, 5, 1, false, xOffset + 30F, yOffset + 7.5F);
        animator.addAnimation(
                ModSettings.MONSTER_ATTACK_ANIM,
                makeMonsterPath(prefixPath + ModSettings.MONSTER_ATTACK_ANIM + ".png"),
                4, 8, 1, false, xOffset - 84F, yOffset + 6F);
        animator.addAnimation(
                ModSettings.MONSTER_SKILL1_ANIM,
                makeMonsterPath(prefixPath + ModSettings.MONSTER_SKILL1_ANIM + ".png"),
                7, 7, 0, false, xOffset - 39F, yOffset + 4F);
        animator.setDefaultAnim(ModSettings.MONSTER_IDLE_ANIM);
        return animator;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        return null;
    }

    @Override
    public void getMove(int num) {
        switch (turn % 3) {
            case 0: setIntent(0, Intent.ATTACK);        break;
            case 1: setIntent(1, Intent.ATTACK_DEFEND); break;
            case 2: setIntent(2, Intent.ATTACK);        break;
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(13F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_HEAVY);
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_SKILL1_ANIM));
                addToBot(new NewWaitAction(22F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.NONE);
                addToBot(new GainBlockAction(this, blockAmt));
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(13F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_LIGHT);
        }

        super.takeTurn();
    }
}
