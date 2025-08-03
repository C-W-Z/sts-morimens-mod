package morimensmod.vfx;

import static morimensmod.MorimensMod.makeVFXPath;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
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

    public CetaceanEffect(float centerX, float bottomY) {
        this(centerX, bottomY, false);
    }

    public CetaceanEffect(float centerX, float bottomY, boolean flipX) {
        animator.setFlip(flipX, false);
        this.x = centerX;
        this.y = bottomY;
        animator.setAnimation(NAME, true);
        this.duration = animator.getDuration();
    }

    public static void initializeAnimator() {
        animator = new Animator();
        animator.addAnimation(
                NAME,
                makeVFXPath(NAME + ".png"),
                7, 5, 2, false, -108, -80);
        animator.setScale(1.5F);
        animator.setDefaultAnim(NAME);
    }

    protected static float getX() {
        if (AbstractDungeon.getMonsters().monsters.isEmpty())
            return 3 * Settings.WIDTH / 4;
        float centerX = 0f;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            centerX += m.hb.cX;
        centerX /= AbstractDungeon.getMonsters().monsters.size();
        return centerX;
    }

    protected static float getY() {
        float bottomY = Settings.HEIGHT / 2F;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            bottomY = Math.min(bottomY, m.hb.cY - m.hb.height / 2);
        return bottomY;
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
