package morimensmod.monsters.minions;

import static morimensmod.MorimensMod.makeCharacterPath;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.topDeck;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import basemod.animations.AbstractAnimation;
import morimensmod.actions.NewWaitAction;
import morimensmod.cards.status.Joker;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.misc.Animator;
import morimensmod.powers.BarrierPower;

public class CasiahMinion extends AbstractMinion {

    public static final String ID = makeID(CasiahMinion.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;

    public static final String CasiahID = makeID("Casiah");
    private static final float xOffset = -40;
    private static final float yOffset = -3;

    private int jokerDmg = Joker.DEFAULT_DAMAGE;
    private int jokerAmt = 1;
    private int barrierAmt = 3;

    public CasiahMinion(float x, float y) {
        super(NAME, ID, getMaxHP(), 220, 340, x, y, 0);

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_DMG) {
            addDamage(10, 1);
            jokerDmg = Joker.DEFAULT_DAMAGE + 2;
        } else {
            addDamage(8, 1);
            jokerDmg = Joker.DEFAULT_DAMAGE;
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_MONSTER_ACTION) {
            barrierAmt = 4;
        } else if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            barrierAmt = 3;
        else {
            barrierAmt = 2;
        }
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_MONSTER_HP)
            return 39;
        return 29;
    }

    @Override
    protected void onSummon() {
        addPower(new BarrierPower(this, barrierAmt));
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.PLAYER_IDLE_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_IDLE_ANIM + ".png"),
                7, 9, 2, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_ATTACK_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_ATTACK_ANIM + ".png"),
                5, 7, 2, false, xOffset + 31F, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_DEFENCE_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_DEFENCE_ANIM + ".png"),
                6, 7, 1, false, xOffset + 42F, yOffset - 2F);
        animator.addAnimation(
                ModSettings.PLAYER_HIT_ANIM,
                makeCharacterPath(removeModID(CasiahID) + "/" + ModSettings.PLAYER_HIT_ANIM + ".png"),
                4, 5, 0, false, xOffset - 59F, yOffset - 14F);
        animator.setFlip(true, false);
        animator.setDefaultAnim(ModSettings.PLAYER_IDLE_ANIM);
        return animator;
    }

    @Override
    protected void getMove(int num) {
        setIntent(MOVES[0], 0, Intent.ATTACK_DEBUFF);
    }

    @Override
    public void takeTurn() {
        addToBot(new ChangeStateAction(this, ModSettings.MONSTER_ATTACK_ANIM));
        addToBot(new NewWaitAction(12F / ModSettings.SPRITE_SHEET_ANIMATION_FPS));
        attackAction(0, AttackEffect.BLUNT_LIGHT);
        topDeck(new Joker(jokerDmg), jokerAmt);

        super.takeTurn();
    }
}
