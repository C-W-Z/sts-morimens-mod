package morimensmod.vfx;

import static morimensmod.MorimensMod.makeVFXPath;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import morimensmod.misc.Animator;

public class PokerEffect extends AbstractGameEffect {

    public static final String[] NAMES = { "Poker0", "Poker1", "Poker2", "Poker3" };

    protected static Animator animator;
    protected float x, y;

    public PokerEffect(int variety, float centerX, float bottomY, boolean flipX) {
        assert variety >= 1 && variety <= 3;
        initializeAnimator();
        animator.setFlip(flipX, false);
        animator.setDefaultAnim(NAMES[variety]);
        this.x = centerX;
        this.y = bottomY;
        this.duration = animator.getDuration() * 5;
    }

    public void initializeAnimator() {
        animator = new Animator();
        animator.addAnimation(
                NAMES[1],
                makeVFXPath(NAMES[1] + ".png"),
                4, 7, 0, false, 0, 0);
        animator.addAnimation(
                NAMES[2],
                makeVFXPath(NAMES[2] + ".png"),
                4, 7, 0, false, 0, 0);
        animator.addAnimation(
                NAMES[3],
                makeVFXPath(NAMES[3] + ".png"),
                5, 6, 2, false, 0, 0);
        animator.setScale(1F);
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
