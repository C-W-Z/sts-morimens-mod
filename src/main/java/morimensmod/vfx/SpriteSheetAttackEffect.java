package morimensmod.vfx;

import static morimensmod.MorimensMod.makeVFXPath;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import morimensmod.misc.SpriteSheetAnimation;

public class SpriteSheetAttackEffect extends AbstractGameEffect {
    private SpriteSheetAnimation animation;
    private float x, y;
    private boolean flipX, flipY;

    public SpriteSheetAttackEffect(String spriteSheetPath, int rows, int cols, int emptyFrames,
            float x, float y,
            float xOffset, float yOffset,
            boolean flipX, boolean flipY) {
        this.animation = new SpriteSheetAnimation(
                makeVFXPath(spriteSheetPath + ".png"),
                rows, cols, emptyFrames, false, xOffset, yOffset);
        this.x = x;
        this.y = y;
        this.flipX = flipX;
        this.flipY = flipY;
        this.duration = animation.getDuration();
    }

    @Override
    public void update() {
        animation.tick();
        if (animation.isFinished())
            this.isDone = true;
    }

    @Override
    public void render(SpriteBatch sb) {
        animation.renderCentered(sb, x, y, flipX, flipY);
    }

    @Override
    public void dispose() {
    }
}
