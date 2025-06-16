package morimensmod.posses;

public abstract class AbstractPosse {
    public String posseID;
    public AbstractPosse(String ID) {
        posseID = ID;
    }
    public abstract void activate();
    public abstract String getTitle();
    public abstract String getDescription();
}
