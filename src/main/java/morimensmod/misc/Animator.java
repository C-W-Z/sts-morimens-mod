package morimensmod.misc;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.core.Settings;

import basemod.animations.AbstractAnimation;
import morimensmod.config.ModSettings;
import morimensmod.util.TexLoader;

public class Animator extends AbstractAnimation {

    private float scale = 1F;
    private boolean flipX = false;
    private boolean flipY = false;

    public class AnimationState {
        public Animation<TextureRegion> anim;
        public boolean loop;
        public float xOffset;
        public float yOffset;
    }

    private HashMap<String, AnimationState> animations = new HashMap<>();
    private AnimationState currentAnim;
    private float stateTime;

    private String defaultAnim;

    @Override
    public Type type() {
        return Type.SPRITE;
    }

    public void addAnimation(
            String name, String imgurl, int rows, int columns, int emptyFrames,
            boolean loop, float xOffset, float yOffset) {
        Texture tmpTexture = TexLoader.getTexture(imgurl);

        TextureRegion[][] tmp = TextureRegion.split(
                tmpTexture,
                tmpTexture.getWidth() / columns,
                tmpTexture.getHeight() / rows);
        TextureRegion[] Frames = new TextureRegion[columns * rows - emptyFrames];
        int index = 0;
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < columns && (i != rows - 1 || j != columns - emptyFrames); ++j)
                Frames[index++] = tmp[i][j];

        AnimationState state = new AnimationState();
        state.anim = new Animation<>(1F / ModSettings.SPRITE_SHEET_ANIMATION_FPS, Frames);
        state.loop = loop;
        state.xOffset = xOffset * Settings.scale;
        state.yOffset = yOffset * Settings.scale;

        animations.put(name, state);
    }

    public void setDefaultAnim(String name) {
        this.defaultAnim = name;
        setAnimation(name);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setFlip(boolean horizontal, boolean vertical) {
        this.flipX = horizontal;
        this.flipY = vertical;
    }

    public void renderSprite(SpriteBatch sb, float centerX, float bottomY) {

        if (isFinished()) {
            if (currentAnim.loop)
                stateTime = 0F;
            else
                setAnimation(defaultAnim);
        } else {
            stateTime += Gdx.graphics.getDeltaTime() * (Settings.FAST_MODE ? 2 : 1);
        }

        TextureRegion currentFrame = currentAnim.anim.getKeyFrame(stateTime, currentAnim.loop);

        sb.setColor(Color.WHITE);

        float width = (float) currentFrame.getRegionWidth() * Settings.scale * scale;
        float height = (float) currentFrame.getRegionHeight() * Settings.scale * scale;

        float x = (flipX ? -currentAnim.xOffset : currentAnim.xOffset) + centerX - width / 2F;
        float y = currentAnim.yOffset + bottomY;

        sb.draw(currentFrame.getTexture(),
                x, y, 0, 0,
                width, height,
                1, 1, 0,
                currentFrame.getRegionX(),
                currentFrame.getRegionY(),
                currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight(),
                flipX,
                flipY);
    }

    public void setAnimation(String name) {
        setAnimation(name, false);
    }

    public void setAnimation(String name, boolean forceReset) {
        AnimationState nextAnim = animations.get(name);
        if (nextAnim == null)
            throw new IllegalArgumentException("No such animation: " + name);

        // 若是不同動畫，或要求強制重設時間，才設置為新的動畫
        if (currentAnim != nextAnim || forceReset) {
            currentAnim = nextAnim;
            stateTime = 0f;
        }
    }

    public boolean isCurrent(String name) {
        return animations.get(name) == currentAnim;
    }

    public float getDuration() {
        return currentAnim.anim.getAnimationDuration();
    }

    public float getDuration(String name) {
        return animations.get(name).anim.getAnimationDuration();
    }

    public boolean isFinished() {
        return currentAnim.anim.isAnimationFinished(stateTime);
    }
}
