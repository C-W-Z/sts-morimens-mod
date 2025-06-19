package morimensmod.misc;

public enum PosseType {
    REGULAR, // 每回合第一次透過銀鑰能量正常釋放鑰令
    EXTRA, // 每回合第二次透過銀鑰能量正常釋放鑰令
    UNLIMITED, // 透過卡牌等方式直接釋放的額外鑰令（不消耗銀鑰能量）
    TMP, // 透過"生效兩次"的效果、門扉的答案、或者其他方式間接釋放的額外鑰令（不消耗銀鑰能量）
}
