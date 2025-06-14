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

    public static final Logger logger = LogManager.getLogger(SpriteSheetAnimation.class);

    private Animation<TextureRegion> anim;
    private float stateTime;
    private boolean loop;

    // private int rows;
    // private int cols;
    // private int emptyFrames;

    public SpriteSheetAnimation(String imgurl, int rows, int columns, int emptyFrames, boolean loop, float fps) {
        // this.rows = rows;
        // this.cols = columns;
        // this.emptyFrames = emptyFrames;

        Texture tmpTexture = TexLoader.getTexture(imgurl);

        logger.info(imgurl + ", width:" + tmpTexture.getWidth() + ", hieght:" + tmpTexture.getHeight());

        TextureRegion[][] tmp = TextureRegion.split(
                tmpTexture,
                tmpTexture.getWidth() / columns,
                tmpTexture.getHeight() / rows);
        TextureRegion[] Frames = new TextureRegion[columns * rows - emptyFrames];
        int index = 0;
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < columns && (i != rows - 1 || j != columns - emptyFrames); ++j) {
                Frames[index++] = tmp[i][j];
            }
        }

        this.anim = new Animation<>(1F / fps, Frames);
        this.stateTime = 0F;
        this.loop = loop;

        logger.info("create:" + index);
    }

    public void tick() {
        stateTime += Gdx.graphics.getDeltaTime();
        // logger.info("stateTime:" + stateTime);
    }

    public void renderPlayerImage(SpriteBatch sb, AbstractPlayer player) {
        TextureRegion currentFrame = anim.getKeyFrame(stateTime, loop);
        sb.setColor(Color.WHITE);
        // sb.draw(currentFrame,
        // drawX - (float) currentFrame.getRegionWidth() * Settings.scale / 2.0F +
        // animX,
        // drawY,
        // (float) currentFrame.getRegionWidth() * Settings.scale,
        // (float) currentFrame.getRegionHeight() * Settings.scale,
        // 0,
        // 0,
        // currentFrame.getRegionWidth(),
        // currentFrame.getRegionHeight(),
        // false,
        // false);
        sb.draw(currentFrame,
                player.drawX - (float) currentFrame.getRegionWidth() * Settings.scale / 2.0F + player.animX,
                player.drawY,
                (float) currentFrame.getRegionWidth() * Settings.scale,
                (float) currentFrame.getRegionHeight() * Settings.scale);

        logger.info("x:" + (player.drawX - (float) currentFrame.getRegionWidth() * Settings.scale / 2.0F + player.animX)
                + ", y:" + player.drawY
                + ", w:" + (float) currentFrame.getRegionWidth() * Settings.scale
                + ", h:" + (float) currentFrame.getRegionHeight() * Settings.scale);

        if (!anim.isAnimationFinished(stateTime))
            return;
        if (loop)
            stateTime = 0F;
        logger.info("finish:" + stateTime);
    }
}
