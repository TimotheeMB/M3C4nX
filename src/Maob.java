import java.io.Serializable;

public abstract class Maob implements Serializable {

    public abstract Maob dot(Maob b) throws NonSenseException;

    public abstract Maob plus(Maob b) throws NonSenseException;

    public abstract Maob minus(Maob b) throws NonSenseException;

    public abstract Maob expressIn(Basis basis) throws NonSenseException;

    public abstract Maob differentiate(Basis basis) throws NonSenseException;
}
