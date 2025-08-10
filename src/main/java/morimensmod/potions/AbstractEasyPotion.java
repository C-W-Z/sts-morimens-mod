package morimensmod.potions;

import basemod.abstracts.CustomPotion;

import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;

public abstract class AbstractEasyPotion extends CustomPotion {

    private static Logger logger = LogManager.getLogger(AbstractEasyPotion.class);

    public AbstractPlayer.PlayerClass pool;
    protected static PotionStrings strings;
    protected static String[] DESCRIPTIONS;

    public AbstractEasyPotion(String id, PotionRarity rarity, PotionSize size, Color liquidColor, Color hybridColor,
            Color spotsColor) {
        super("", id, rarity, size, PotionColor.WHITE);
        this.liquidColor = liquidColor;
        this.hybridColor = hybridColor;
        this.spotsColor = spotsColor;
        initializeData();
    }

    public AbstractEasyPotion(String id, PotionRarity rarity, PotionSize size, Color liquidColor, Color hybridColor,
            Color spotsColor, Color labOutlineColor) {
        this(id, rarity, size, liquidColor, hybridColor, spotsColor);
        this.labOutlineColor = labOutlineColor;
    }

    public AbstractEasyPotion(String id, PotionRarity rarity, PotionSize size, Color liquidColor, Color hybridColor,
            Color spotsColor, AbstractPlayer.PlayerClass pool, Color labOutlineColor) {
        this(id, rarity, size, liquidColor, hybridColor, spotsColor);
        this.labOutlineColor = labOutlineColor;
        this.pool = pool;
    }

    @Override
    public void initializeData() {
        potency = getPotency();

        strings = CardCrawlGame.languagePack.getPotionString(ID);
        name = strings.NAME;
        DESCRIPTIONS = strings.DESCRIPTIONS;
        description = getDescription();

        tips.clear();
        tips.add(new PowerTip(name, description));
        addAdditionalTips();
    }

    public abstract String getDescription();

    public void addAdditionalTips() {
    }

    public AbstractPotion makeCopy() {
        try {
            return getClass().getConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException
                | InvocationTargetException e) {
            logger.fatal("BaseMod failed to auto-generate makeCopy for potion: " + ID);
            return null;
        }
    }
}
