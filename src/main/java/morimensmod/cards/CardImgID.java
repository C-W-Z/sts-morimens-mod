package morimensmod.cards;

public class CardImgID {
    public static final String RamonaAttack = makeAttack("Ramona");
    public static final String RamonaSkill  = makeSkill("Ramona");

    public static final String DollSkill    = makeSkill("Doll");

    public static final String OgierAttack  = makeAttack("Ogier");
    public static final String OgierSkill   = makeSkill("Ogier");

    public static final String LotanAttack  = makeAttack("Lotan");

    public static final String RamonaTimewormAttack = makeAttack("RamonaTimeworm");
    public static final String RamonaTimewormSkill  = makeSkill("RamonaTimeworm");

    // public static final String DollInfernoSkill     = makeSkill("DollInferno");

    public static final String AlvaAttack   = makeAttack("Alva");
    public static final String AlvaSkill    = makeSkill("Alva");

    public static final String NymphaeaAttack   = makeAttack("Nymphaea");
    public static final String NymphaeaSkill    = makeSkill("Nymphaea");

    public static final String PandiaAttack = makeAttack("Pandia");
    public static final String PandiaSkill  = makeSkill("Pandia");

    public static final String NautilaSkill = makeSkill("Nautila");

    public static final String KarenSkill   = makeSkill("Karen");

    public static final String HamelnAttack = makeAttack("Hameln");

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
