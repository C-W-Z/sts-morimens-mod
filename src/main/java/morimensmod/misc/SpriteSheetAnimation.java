package morimensmod.misc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;

import morimensmod.util.TexLoader;

public class SpriteSheetAnimation {

    private static final Logger logger = LogManager.getLogger(SpriteSheetAnimation.class);

    private Animation<TextureRegion> anim;
    private float stateTime;
    private boolean loop;

    private float xOffset = 0;
    private float yOffset = 0;

    public SpriteSheetAnimation(String imgurl, int rows, int columns, int emptyFrames, boolean loop, float fps,
            float xOffset, float yOffset) {
        Texture tmpTexture = TexLoader.getTexture(imgurl);

        logger.debug(imgurl + ", width:" + tmpTexture.getWidth() + ", hieght:" + tmpTexture.getHeight());
        logger.debug("Settings.scale:" + Settings.scale + ", Settings.xScale:" + Settings.xScale + ", Settings.yScale:" + Settings.yScale);

        TextureRegion[][] tmp = TextureRegion.split(
                tmpTexture,
                tmpTexture.getWidth() / columns,
                tmpTexture.getHeight() / rows);
        TextureRegion[] Frames = new TextureRegion[columns * rows - emptyFrames];
        int index = 0;
        for (int i = 0; i < rows; ++i)
            for (int j = 0; j < columns && (i != rows - 1 || j != columns - emptyFrames); ++j)
                Frames[index++] = tmp[i][j];

        this.anim = new Animation<>(1F / fps, Frames);
        this.stateTime = 0F;
        this.loop = loop;

        this.xOffset = xOffset * Settings.scale;
        this.yOffset = yOffset * Settings.scale;

        logger.debug("create:" + index);
    }

    public void tick() {
        stateTime += Gdx.graphics.getDeltaTime();
        // logger.debug("stateTime:" + stateTime);
    }

    public float getDuration() {
        return anim.getAnimationDuration();
    }

    public boolean isFinished() {
        return anim.isAnimationFinished(stateTime);
    }

    public boolean renderPlayerImage(SpriteBatch sb, AbstractPlayer player) {
        TextureRegion currentFrame = anim.getKeyFrame(stateTime, loop);
        sb.setColor(Color.WHITE);

        float width = (float) currentFrame.getRegionWidth() * Settings.scale;
        float height = (float) currentFrame.getRegionHeight() * Settings.scale;

        float x = (player.flipHorizontal ? -xOffset : xOffset) + player.drawX - width / 2.0F + player.animX;
        float y = yOffset + player.drawY;

        sb.draw(currentFrame.getTexture(),
                x, y, 0, 0,
                width, height,
                1, 1, 0,
                currentFrame.getRegionX(),
                currentFrame.getRegionY(),
                currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight(),
                player.flipHorizontal,
                player.flipVertical);

        if (!anim.isAnimationFinished(stateTime))
            return false;
        if (loop) {
            stateTime = 0F;
            return false;
        }
        return true;
    }

    /**
     * 在螢幕上指定中心座標畫出動畫的當前幀
     *
     * @param sb      SpriteBatch
     * @param centerX 中心點 X 座標（以 Settings.scale 計算）
     * @param centerY 中心點 Y 座標
     * @param flipX   是否左右翻轉
     * @param flipY   是否上下翻轉
     * @return 是否動畫已經結束（非 loop 模式下）
     */
    public boolean renderCentered(SpriteBatch sb, float centerX, float centerY, boolean flipX, boolean flipY) {
        TextureRegion currentFrame = anim.getKeyFrame(stateTime, loop);
        sb.setColor(Color.WHITE);

        float width = currentFrame.getRegionWidth() * Settings.scale;
        float height = currentFrame.getRegionHeight() * Settings.scale;

        float x = centerX - width / 2.0F + (flipX ? -xOffset : xOffset);
        float y = centerY - height / 2.0F + yOffset;

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

        if (!anim.isAnimationFinished(stateTime))
            return false;
        logger.debug("finish");
        if (loop) {
            stateTime = 0F;
            return false;
        }
        return true;
    }
}
