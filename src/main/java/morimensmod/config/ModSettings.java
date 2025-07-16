package morimensmod.config;

import com.badlogic.gdx.graphics.Color;

public class ModSettings {
    public static final Color CHOAS_COLOR               = new Color(227 / 255F, 201 / 255F, 110 / 255F, 1);
    public static final Color AEQUOR_COLOR              = new Color(106 / 255F, 171 / 255F, 236 / 255F, 1);
    public static final Color CARO_COLOR                = new Color(226 / 255F, 97 / 255F, 97 / 255F, 1);
    public static final Color ULTRA_COLOR               = new Color(194 / 255F, 146 / 255F, 236 / 255F, 1);
    public static final Color WHEEL_OF_DESTINY_COLOR    = new Color(148 / 255F, 155 / 255F, 165 / 255F, 1);
    public static final Color BUFF_COLOR                = Color.LIGHT_GRAY;
    public static final Color SYMPTOM_COLOR             = new Color(30 / 255F, 30 / 255F, 34 / 255F, 1);
    public static final Color STATUS_COLOR              = new Color(47 / 255F, 28 / 255F, 27 / 255F, 1);
    public static final Color POSSE_COLOR               = Color.WHITE;

    public static final Color ALIEMUS_INCREASE_TEXT_COLOR   = Color.GOLD;
    public static final Color ALIEMUS_DECREASE_TEXT_COLOR   = Color.FIREBRICK;
    public static final Color KEYFLARE_INCREASE_TEXT_COLOR  = Color.WHITE;
    public static final Color KEYFLARE_DECREASE_TEXT_COLOR  = Color.FIREBRICK;
    public static final Color DEATH_RESISTANCE_TEXT_COLOR   = Color.RED;

    public static final float UI_THOUGHT_BUBBLE_TIME        = 3.0F;
    public static final float DICE_THOUGHT_BUBBLE_TIME      = 1.0F;

    public static final Color STATUS_CARD_FLAVOR_BOX_COLOR  = new Color(119 / 255F, 48 / 255F, 63 / 255F, 1);
    public static final Color STATUS_CARD_FLAVOR_TEXT_COLOR = Color.WHITE;

    public static final float EXALT_PROTRAIT_DURATION       = 1.0F;
    public static final float EXALT_PROTRAIT_FLASH_IN_TIME  = 0.25F;
    public static final float EXALT_PROTRAIT_STAY_TIME      = 0.5F;
    public static final float EXALT_PROTRAIT_FADE_TIME      = 0.5F;

    public static final float SPRITE_SHEET_ANIMATION_FPS    = 30F;

    public static final float CLICKABLE_UI_ICON_SIZE        = 120F;
    public static final float CLICKABLE_UI_ICON_SCALE       = 0.75F;

    public static final String PLAYER_IDLE_ANIM             = "Idle_1";
    public static final String PLAYER_ATTACK_ANIM           = "Attack";
    public static final String PLAYER_DEFENCE_ANIM          = "Defence";
    public static final String PLAYER_HIT_ANIM              = "Hit";
    public static final String PLAYER_ROUSE_ANIM            = "Exalt";
    public static final String PLAYER_EXALT_ANIM            = "ExSkill";
    public static final String PLAYER_SKILL1_ANIM           = "Skill1";
    public static final String PLAYER_SKILL2_ANIM           = "Skill2";

    public static final String MONSTER_IDLE_ANIM            = "Idle_1";
    public static final String MONSTER_HIT_ANIM             = "Hit";
    public static final String MONSTER_ATTACK_ANIM          = "Attack";
    public static final String MONSTER_SKILL1_ANIM          = "Skill1";

    public static final class ASCENSION_LVL {
        public static final int HIGHER_MONSTER_DMG      = 2;
        public static final int HIGHER_ELITE_DMG        = 3;
        public static final int HIGHER_BOSS_DMG         = 4;
        public static final int HIGHER_MONSTER_HP       = 7;
        public static final int HIGHER_ELITE_HP         = 8;
        public static final int HIGHER_BOSS_HP          = 8;
        public static final int ENHANCE_MONSTER_ACTION  = 17;
        public static final int ENHANCE_ELITE_ACTION    = 18;
        public static final int ENHANCE_BOSS_ACTION     = 19;
    }
}
