package morimensmod.monsters;

import static morimensmod.util.Wiz.actB;

import com.megacrit.cardcrawl.actions.ClearCardQueueAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.UnawakenedPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import morimensmod.config.ModSettings;
import morimensmod.misc.Animator;

public abstract class AbstractAwakenableBoss extends AbstractMorimensMonster {

    private int moveID;
    protected boolean roused;
    protected String hitAnim;
    protected String defenceAnim;
    protected String rouseAnim;

    public AbstractAwakenableBoss(String name, String id, float hb_w, float hb_h, float x, float y) {
        super(name, id, 100, hb_w, hb_h, x, y, 0);
        this.type = EnemyType.BOSS;
        this.setHp(getMaxHP());
        this.moveID = getFirstMoveID();
        this.roused = false;
        this.setAnimStrings();
    }

    /**
     * Override Me
     */
    protected void setAnimStrings() {
        hitAnim     = ModSettings.PLAYER_HIT_ANIM;
        defenceAnim = ModSettings.PLAYER_DEFENCE_ANIM;
        rouseAnim   = ModSettings.PLAYER_ROUSE_ANIM;
    }

    @Override
    public final void getMove(int num) {
        setMoveIntent(moveID);
    }

    private final int getNextMoveID(int _moveID) {
        if (!roused && currentHealth <= 0 && _moveID != getRouseMoveID())
            return getRouseMoveID();
        return getNextMoveIDExceptRouse(_moveID);
    }

    protected abstract int getMaxHP();

    protected abstract int getRousedMaxHP();

    protected abstract void preBattle();

    protected abstract int getFirstMoveID();

    protected abstract int getRouseMoveID();

    /**
     * @param _moveID current move ID
     * @return next move ID, but should not be rouseMoveID
     */
    protected abstract int getNextMoveIDExceptRouse(int _moveID);

    protected abstract void setMoveIntent(int _moveID);

    protected abstract void takeMoveAction(int _moveID);

    protected abstract void onHalfDead();

    @Override
    public final void takeTurn() {
        takeMoveAction(moveID);
        moveID = getNextMoveID(moveID);
        super.takeTurn();
    }

    @Override
    public final void usePreBattleAction() {
        // CardCrawlGame.music.unsilenceBGM();
        // AbstractDungeon.scene.fadeOutAmbiance();
        // AbstractDungeon.getCurrRoom().playBgmInstantly("BOSS_BEYOND");
        AbstractDungeon.getCurrRoom().cannotLose = true;
        addToBot(new ApplyPowerAction(this, this, new UnawakenedPower(this)));
        preBattle();
    }

    @Override
    public final void damage(DamageInfo info) {
        int hp = currentHealth;
        int block = currentBlock;
        super.superDamage(info);
        if (this.animation instanceof Animator && info.owner != null
                && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
            if (hp == currentHealth && block > 0 && currentBlock >= 0 && info.owner.isPlayer)
                ((Animator) this.animation).setAnimation(defenceAnim);
            else
                ((Animator) this.animation).setAnimation(hitAnim);
        }
        if (this.currentHealth <= 0 && !this.halfDead) {
            if (AbstractDungeon.getCurrRoom().cannotLose)
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
            onHalfDead();
            // addToBot(new ShoutAction(this, monsterStrings.DIALOG[0]));
            actB(() -> setMoveIntent(moveID));
            applyPowers();
        }
    }

    @Override
    public final void changeState(String stateName) {
        super.changeState(stateName);
        if (stateName != rouseAnim)
            return;
        setHp(getRousedMaxHP());
        this.currentHealth = 0;
        this.halfDead = false;
        this.roused = true;
        addToBot(new HealAction(this, this, this.maxHealth));
        addToBot(new CanLoseAction());
    }

    @Override
    public final void die() {
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
