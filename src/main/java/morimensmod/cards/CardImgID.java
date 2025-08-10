package morimensmod.cards;

import static morimensmod.MorimensMod.makeID;
import static morimensmod.util.General.removeModID;

public class CardImgID {

    public static class Ramona {
        public static final String ID           = makeID(Ramona.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class RamonaTimeworm {
        public static final String ID           = makeID(RamonaTimeworm.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Lotan {
        public static final String ID           = makeID(Lotan.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Doll {
        public static final String ID           = makeID(Doll.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class DollInferno {
        public static final String ID           = makeID(DollInferno.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Ogier {
        public static final String ID           = makeID(Ogier.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Alva {
        public static final String ID           = makeID(Alva.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Nymphaea {
        public static final String ID           = makeID(Nymphaea.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Pandia {
        public static final String ID           = makeID(Pandia.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Nautila {
        public static final String ID           = makeID(Nautila.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Karen {
        public static final String ID           = makeID(Karen.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Ryker {
        public static final String ID           = makeID(Ryker.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Hameln {
        public static final String ID           = makeID(Hameln.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Tawil {
        public static final String ID           = makeID(Tawil.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Goliath {
        public static final String ID           = makeID(Goliath.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Uvhash {
        public static final String ID           = makeID(Uvhash.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

    public static class Horla {
        public static final String ID           = makeID(Horla.class.getSimpleName());
        public static final String Attack       = makeAttack(removeModID(ID));
        public static final String Skill        = makeSkill(removeModID(ID));
    }

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
