package morimensmod.monsters.bosses;

import static morimensmod.MorimensMod.makeCharacterPath;
import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.removeModID;
import static morimensmod.util.Wiz.actB;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ChangeStateAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.UnawakenedPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;

import basemod.animations.AbstractAnimation;

import morimensmod.actions.NewWaitAction;
import morimensmod.characters.Lotan;
import morimensmod.misc.Animator;
import morimensmod.monsters.AbstractMorimensMonster;
import morimensmod.powers.MadnessPower;
import morimensmod.util.ModSettings;
import morimensmod.util.ModSettings.ASCENSION_LVL;

public class LotanBoss extends AbstractMorimensMonster {

    public static final String ID = makeID(LotanBoss.class.getSimpleName());
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;

    private static final float xOffset = 0;
    private static final float yOffset = 0;

    private int strengthAmt = 3;
    private int madnessAmt = 1;

    private int moveID;
    private boolean roused;

    public LotanBoss(float x, float y) {
        this(x, y, 0);
    }

    public LotanBoss(float x, float y, int turnOffset) {
        super(NAME, ID, getMaxHP(), 500, 310, x, y, turnOffset);

        this.type = EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_DMG) {
            addDamage(12, 3);
            addDamage(20, 1);
            addDamage(42, 1);
            addDamage(-1, 0);
        } else {
            addDamage(9, 3);
            addDamage(16, 1);
            addDamage(40, 1);
            addDamage(-1, 0);
        }

        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.ENHANCE_BOSS_ACTION)
            strengthAmt = 5;
        else
            strengthAmt = 4;

        moveID = 0;
        roused = false;
    }

    protected static int getMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
            return 800;
        return 720;
    }

    protected static int getRousedMaxHP() {
        if (AbstractDungeon.ascensionLevel >= ASCENSION_LVL.HIGHER_BOSS_HP)
            return 1200;
        return 1080;
    }

    @Override
    protected AbstractAnimation getAnimation() {
        Animator animator = new Animator();
        animator.addAnimation(
                ModSettings.PLAYER_IDLE_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_IDLE_ANIM + ".png"),
                9, 7, 2, true, xOffset, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_HIT_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_HIT_ANIM + ".png"),
                5, 4, 0, false, xOffset - 55F, yOffset - 21F);
        animator.addAnimation(
                ModSettings.PLAYER_DEFENCE_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_DEFENCE_ANIM + ".png"),
                3, 9, 0, false, xOffset - 75F, yOffset - 11F);
        animator.addAnimation(
                ModSettings.PLAYER_ATTACK_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_ATTACK_ANIM + ".png"),
                9, 3, 2, false, xOffset + 199.5F, yOffset);
        animator.addAnimation(
                ModSettings.PLAYER_ROUSE_ANIM,
                makeCharacterPath(removeModID(Lotan.ID) + "/" + ModSettings.PLAYER_ROUSE_ANIM + ".png"),
                7, 10, 3, false, xOffset - 28F, yOffset);
        animator.setFlip(true, false);
        animator.setDefaultAnim(ModSettings.PLAYER_IDLE_ANIM);
        return animator;
    }

    @Override
    public void getMove(int num) {
        setMoveIntent(moveID);
    }

    protected int getNextMoveID(int _moveID) {
        if (!roused && currentHealth <= 0 && _moveID != 3)
            return 3;
        switch (_moveID) {
            default:
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
            case 3:
                return 0;
        }
    }

    protected void setMoveIntent(int _moveID) {
        switch (_moveID) {
            case 0:
                setIntent(monsterStrings.MOVES[_moveID], _moveID, Intent.ATTACK);
                break;
            case 1:
                setIntent(monsterStrings.MOVES[_moveID], _moveID, Intent.ATTACK_BUFF);
                break;
            case 2:
                setIntent(monsterStrings.MOVES[_moveID], _moveID, Intent.ATTACK);
                break;
            case 3:
                setIntent(monsterStrings.MOVES[_moveID], _moveID, Intent.MAGIC);
                break;
        }
    }

    protected void takeMoveAction(int _moveID) {
        switch (_moveID) {
            case 0:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(9F / 30F));
                attackAction(_moveID, AttackEffect.SLASH_DIAGONAL);
                break;
            case 1:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(9F / 30F));
                attackAction(_moveID, AttackEffect.SLASH_HORIZONTAL);
                addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, strengthAmt)));
                strengthAmt++;
                break;
            case 2:
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ATTACK_ANIM));
                addToBot(new NewWaitAction(9F / 30F));
                attackAction(_moveID, AttackEffect.SLASH_DIAGONAL);
                break;
            case 3:
                addToBot(new VFXAction(this, new IntenseZoomEffect(this.hb.cX, this.hb.cY, true), 0.05F, true));
                addToBot(new ChangeStateAction(this, ModSettings.PLAYER_ROUSE_ANIM));
                addToBot(new NewWaitAction(31F / 30F));
                addToBot(new ApplyPowerAction(this, this, new MadnessPower(this, madnessAmt)));
                break;
            default:
                break;
        }
    }

    @Override
    public void takeTurn() {
        takeMoveAction(moveID);
        moveID = getNextMoveID(moveID);
        super.takeTurn();
    }

    @Override
    public void usePreBattleAction() {
        // CardCrawlGame.music.unsilenceBGM();
        // AbstractDungeon.scene.fadeOutAmbiance();
        // AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        AbstractDungeon.getCurrRoom().cannotLose = true;
        addToBot(new ApplyPowerAction(this, this, new UnawakenedPower(this)));
    }

    @Override
    public void damage(DamageInfo info) {
        super.damage(info);
        if (this.animation instanceof Animator
                && info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0)
            ((Animator) this.animation).setAnimation(ModSettings.PLAYER_HIT_ANIM);
        if (this.currentHealth <= 0 && !this.halfDead) {
            if (AbstractDungeon.getCurrRoom().cannotLose == true)
                this.halfDead = true;
            for (AbstractPower p : this.powers)
                p.onDeath();
            for (AbstractRelic r : AbstractDungeon.player.relics)
                r.onMonsterDeath(this);
            addToTop(new ClearCardQueueAction());
            this.powers.removeIf(p -> p.ID == UnawakenedPower.POWER_ID);
            moveID = getNextMoveID(moveID);
            setMoveIntent(moveID);
            createIntent();
            // addToBot(new ShoutAction(this, monsterStrings.DIALOG[0]));
            actB(() -> setMoveIntent(moveID));
            applyPowers();
        }
    }

    @Override
    public void changeState(String stateName) {
        super.changeState(stateName);
        if (stateName != ModSettings.PLAYER_ROUSE_ANIM)
            return;
        setHp(getRousedMaxHP());
        this.currentHealth = 0;
        this.halfDead = false;
        this.roused = true;
        addToBot(new HealAction(this, this, this.maxHealth));
        addToBot(new CanLoseAction());
    }

    public void die() {
        if (!AbstractDungeon.getCurrRoom().cannotLose) {
            super.die();
            useFastShakeAnimation(5.0F);
            CardCrawlGame.screenShake.rumble(4.0F);
            // if (this.saidPower) {
            //     CardCrawlGame.sound.play("VO_AWAKENEDONE_2");
            //     AbstractDungeon.effectList.add(
            //             new SpeechBubble(this.hb.cX + this.dialogX, this.hb.cY + this.dialogY, 2.5F, DIALOG[1], false));
            //     this.saidPower = true;
            // }
            onBossVictoryLogic();
            if (AbstractDungeon.actNum >= 3)
                onFinalBossVictoryLogic();
        }
    }
}
