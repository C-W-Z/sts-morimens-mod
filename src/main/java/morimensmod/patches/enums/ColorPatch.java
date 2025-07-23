package morimensmod.patches.enums;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType;

public class ColorPatch {
    public static class CardColorPatch {
        @SpireEnum
        public static CardColor AWAKENER_COLOR;
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
        @SpireEnum
        public static CardColor STATUS_COLOR;
        @SpireEnum
        public static CardColor POSSE_COLOR;
        @SpireEnum
        public static CardColor DERIVATIVE_COLOR;

        public static boolean isMorimensModCardColor(CardColor color) {
            return color == AWAKENER_COLOR ||
                    color == CHAOS_COLOR || color == AEQUOR_COLOR || color == CARO_COLOR || color == ULTRA_COLOR ||
                    color == WHEEL_OF_DESTINY_COLOR || color == BUFF_COLOR || color == SYMPTOM_COLOR ||
                    color == STATUS_COLOR || color == POSSE_COLOR || color == DERIVATIVE_COLOR;
        }
    }

    public static class LibColorPatch {
        @SpireEnum
        public static LibraryType AWAKENER_COLOR;
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
        @SpireEnum
        public static LibraryType STATUS_COLOR;
        @SpireEnum
        public static LibraryType POSSE_COLOR;
        @SpireEnum
        public static LibraryType DERIVATIVE_COLOR;
    }
}
