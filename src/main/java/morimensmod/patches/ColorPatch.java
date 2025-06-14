package morimensmod.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType;

public class ColorPatch {
    public static class CardColorPatch {
        @SpireEnum
        public static CardColor CHAOS_COLOR;
        @SpireEnum
        public static CardColor AEQUOR_COLOR;
        @SpireEnum
        public static CardColor CARO_COLOR;
        @SpireEnum
        public static CardColor ULTRA_COLOR;
        @SpireEnum
        public static CardColor WHEEL_OF_DESTINY_COLOR;
        @SpireEnum
        public static CardColor BUFF_COLOR;
        @SpireEnum
        public static CardColor SYMPTOM_COLOR;
    }

    public static class LibColorPatch {
        @SpireEnum
        public static LibraryType CHAOS_COLOR;
        @SpireEnum
        public static LibraryType AEQUOR_COLOR;
        @SpireEnum
        public static LibraryType CARO_COLOR;
        @SpireEnum
        public static LibraryType ULTRA_COLOR;
        @SpireEnum
        public static LibraryType WHEEL_OF_DESTINY_COLOR;
        @SpireEnum
        public static LibraryType BUFF_COLOR;
        @SpireEnum
        public static LibraryType SYMPTOM_COLOR;
    }
}
