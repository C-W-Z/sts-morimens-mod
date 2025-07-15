package morimensmod.monsters.enemies;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.MorimensMod.makeMonsterPath;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.shuffleIn;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.animations.AbstractAnimation;
import morimensmod.actions.NewWaitAction;
import morimensmod.cards.status.Wound;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractMorimensMonster;
import morimensmod.util.ModSettings;
import morimensmod.util.ModSettings.ASCENSION_LVL;

public class KingOfKids extends AbstractMorimensMonster {

    public static final String ID = makeID(KingOfKids.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = -32;
    private static final float yOffset = 0;

    private int woundAmt = 1;
    private int strengthAmt = 2;
    private int blockAmt = 10;

    public KingOfKids(float x, float y) {
        this(x, y, 0);
    }

    public KingOfKids(float x, float y, int turnOffset) {
        super(NAME, ID, getMaxHP(), 240F, 270F, x, y, turnOffset);

        int dmgAddition = AbstractDungeon.floorNum / 17;

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_DMG) {
            addDamage(dmgAddition + 10, 1);
            addDamage(0, 0);
            addDamage(dmgAddition + 6, 2);
        } else {
            addDamage(dmgAddition + 8, 1);
            addDamage(0, 0);
            addDamage(dmgAddition + 4, 2);
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_MONSTER_ACTION) {
            woundAmt = 2;
            strengthAmt = 3 + AbstractDungeon.floorNum / 25;
        } else {
            woundAmt = 1;
            strengthAmt = 2 + AbstractDungeon.floorNum / 25;
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            blockAmt = 10 + AbstractDungeon.floorNum / 4;
        else
            blockAmt = 7 + AbstractDungeon.floorNum / 4;
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            return 40 + AbstractDungeon.floorNum;
        return 30 + AbstractDungeon.floorNum;
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
                5, 4, 0, false, xOffset + 18.5F, yOffset);
        animator.addAnimation(
                ModSettings.MONSTER_ATTACK_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_ATTACK_ANIM + ".png"),
                7, 5, 0, false, xOffset - 17F, yOffset - 6F);
        // 這個xOffset不知道為什麼特別奇怪
        animator.addAnimation(
                ModSettings.MONSTER_SKILL1_ANIM,
                makeMonsterPath(removeModID(ID) + "/" + ModSettings.MONSTER_SKILL1_ANIM + ".png"),
                6, 7, 0, false, xOffset + 28F, yOffset);
        animator.setDefaultAnim(ModSettings.MONSTER_IDLE_ANIM);
        return animator;
    }

    @Override
    public void getMove(int num) {
        switch (turn % 3) {
            case 0:
                setAttackIntent(0, Intent.ATTACK_DEBUFF);
                break;
            case 1:
                setMove((byte) 1, Intent.BUFF, 0);
                break;
            case 2:
                setAttackIntent(2, Intent.ATTACK);
                break;
        }
    }

    @Override
    public void takeTurn() {
        switch (nextMove) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(0.5F));
                attackAction(0, AttackEffect.BLUNT_HEAVY);
                shuffleIn(new Wound(), woundAmt);
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_SKILL1_ANIM));
                addToBot(new NewWaitAction(0.4F));
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                addToBot(new GainBlockAction(this, this, blockAmt));
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
                addToBot(new NewWaitAction(0.5F));
                attackAction(2, AttackEffect.BLUNT_LIGHT);
                break;
        }

        super.takeTurn();
    }
}
