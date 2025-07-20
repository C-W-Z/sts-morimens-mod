package morimensmod.cards;

public class CardImgID {
    public static final String RamonaAttack = makeAttack("Ramona");
    public static final String RamonaSkill  = makeSkill("Ramona");
    public static final String LotanAttack  = makeAttack("Lotan");
    public static final String DollSkill    = makeSkill("Doll");
    public static final String OgierAttack  = makeAttack("Ogier");
    public static final String OgierSkill   = makeSkill("Ogier");

    public static final String makeAttack(String filename) {
        return filename + "_Attack";
    }

    public static final String makeSkill(String filename) {
        return filename + "_Skill";
    }

    public static final String removePofix(String filename) {
        return filename.replace("_Attack", "").replace("_Skill", "");
    }
}
