package morimensmod.vfx;

import static morimensmod.MorimensMod.makeVFXPath;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import morimensmod.misc.Animator;

public class CetaceanEffect extends AbstractGameEffect {

    public static final String NAME = "Cetacean";

    protected static Animator animator;
    protected float x, y;

    static { initializeAnimator(); }

    public CetaceanEffect() {
        this(getX(), getY(), false);
    }

    public CetaceanEffect(float x, float y) {
        this(x, y, false);
    }

    public CetaceanEffect(float x, float y, boolean flipX) {
        animator.setFlip(flipX, false);
        this.x = x;
        this.y = y;
        this.duration = animator.getDuration();
        animator.setAnimation(NAME, true);
    }

    public static void initializeAnimator() {
        animator = new Animator();
        animator.addAnimation(
                NAME,
                makeVFXPath(NAME + ".png"),
                7, 5, 2, false, -108, 0);
        animator.setScale(1.5F);
        animator.setDefaultAnim(NAME);
    }

    protected static float getX() {
        float centerX = 0f;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            centerX += m.hb.cX;
        if (!AbstractDungeon.getMonsters().monsters.isEmpty())
            centerX /= AbstractDungeon.getMonsters().monsters.size();
        return centerX;
    }

    protected static float getY() {
        float centerY = 0f;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            centerY += m.hb.cY - m.hb.height / 4;
        if (!AbstractDungeon.getMonsters().monsters.isEmpty())
            centerY /= AbstractDungeon.getMonsters().monsters.size();
        return centerY;
    }

    @Override
    public void render(SpriteBatch sb) {
        animator.renderSprite(sb, x, y + AbstractDungeon.sceneOffsetY);
    }

    @Override
    public void update() {
        if (animator.isFinished())
            this.isDone = true;
    }

    @Override
    public void dispose() {}
}
