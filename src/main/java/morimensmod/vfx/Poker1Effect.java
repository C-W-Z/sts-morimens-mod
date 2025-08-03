package morimensmod.vfx;

import static morimensmod.MorimensMod.makeVFXPath;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import morimensmod.misc.Animator;

public class Poker1Effect extends AbstractGameEffect {

    public static final String NAME = "Poker1";

    protected static Animator animator;
    protected float x, y;

    static { initializeAnimator(); }

    public Poker1Effect(float centerX, float bottomY, boolean flipX) {
        animator.setFlip(flipX, false);
        this.x = centerX;
        this.y = bottomY;
        animator.setAnimation(NAME, true);
        this.duration = animator.getDuration() * 6;
    }

    public static void initializeAnimator() {
        animator = new Animator();
        animator.addAnimation(
                NAME,
                makeVFXPath(NAME + ".png"),
                4, 7, 0, false, 0, 0);
        animator.setScale(1F);
        animator.setDefaultAnim(NAME);
    }

    @Override
    public void render(SpriteBatch sb) {
        animator.renderSprite(sb, x, y + AbstractDungeon.sceneOffsetY);
    }

    @Override
    public void update() {
        super.update();
        if (animator.isFinished() && this.duration < animator.getDuration())
            this.isDone = true;
    }

    @Override
    public void dispose() {}
}
