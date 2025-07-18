package morimensmod.monsters.enemies;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeMonsterPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.shuffleIn;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import basemod.animations.AbstractAnimation;
import morimensmod.actions.NewWaitAction;
import morimensmod.cards.status.Wound;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractMorimensMonster;

public class Fastrunner extends AbstractMorimensMonster {

    public static final String ID = makeID(Fastrunner.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = -32;
    private static final float yOffset = 0;

    private int woundAmt = 1;

    public Fastrunner(float x, float y) {
        this(x, y, 0);
    }

    public Fastrunner(float x, float y, int turnOffset) {
        super(NAME, ID, getMaxHP(), 240F, 270F, x, y, turnOffset);

        int dmgAddition = AbstractDungeon.actNum - 1;

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_DMG) {
            addDamage(dmgAddition + 7, 1);
            addNoDamage();
            addDamage(dmgAddition + 4, 2);
        } else {
            addDamage(dmgAddition + 5, 1);
            addNoDamage();
            addDamage(dmgAddition + 2, 2);
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_MONSTER_ACTION) {
            woundAmt = 3;
        } else {
            woundAmt = 2;
        }
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            return 39 + AbstractDungeon.floorNum;
        return 29 + AbstractDungeon.floorNum;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        // xOffset是Idle_1和另一張圖置中疊在一起之後，另一張要水平移動多少距離才會和Idle_1水平位置重合
        // yOffset是Idle_1和另一張貼圖齊底部疊在一起後，另一張要垂直移動多少才會和Idle_1高度相同
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.MONSTER_IDLE_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_IDLE_ANIM + ".png"),
                12, 17, 7, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_HIT_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_HIT_ANIM + ".png"),
                5, 4, 0, false, xOffset + 19.5F, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_ATTACK_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_ATTACK_ANIM + ".png"),
                7, 5, 0, false, xOffset - 21F, yOffset - 15F);
        // 這個xOffset不知道為什麼特別奇怪
        animator.addAnimation(
                ModSettings.MONSTER_SKILL1_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_SKILL1_ANIM + ".png"),
                6, 7, 0, false, xOffset + 27F, yOffset);
        animator.setDefaultAnim(ModSettings.MONSTER_IDLE_ANIM);
        return animator;
    }

    @Override
    public void getMove(int num) {
        switch (turn % 3) {
            case 0: setIntent(0, Intent.ATTACK); break;
            case 1: setIntent(1, Intent.DEBUFF); break;
            case 2: setIntent(2, Intent.ATTACK); break;
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(15F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_HEAVY);
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_SKILL1_ANIM));
                addToBot(new NewWaitAction(12F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                shuffleIn(new Wound(), woundAmt);
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(15F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
                attackAction(nextMove, AttackEffect.BLUNT_LIGHT);
                break;
        }

        super.takeTurn();
    }
}
