package morimensmod.monsters.minions;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.Wiz.topDeck;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;

import basemod.animations.AbstractAnimation;
import morimensmod.actions.NewWaitAction;
import morimensmod.cards.status.Joker;
import morimensmod.config.ModSettings;
import morimensmod.config.ModSettings.ASCENSION_LVL;
import morimensmod.powers.BarrierPower;

public class CasiahMinion extends AbstractMinion {

    public static final String ID = makeID(CasiahMinion.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAnimation'");
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
