import java.io.Serializable;

public class Wrench extends Maob implements Serializable {
    Vector sum;
    Vector moment;

    public Wrench(Vector sum, Vector moment) {
        this.sum = sum;
        this.moment = moment;
    }

    @Override
    public Maob dot(Maob two) throws  NonSenseException {
        if(two instanceof Scalar){
            Scalar b = (Scalar) two;
            return new Wrench((Vector)this.sum.dot(b),(Vector)this.moment.dot(b));
        }else {
            throw new NonSenseException();
        }
    }

    @Override
    public Maob plus(Maob two) throws  NonSenseException {
        if(two instanceof Wrench){
            Wrench b = (Wrench) two;
            return new Wrench((Vector)this.sum.plus(b.sum),(Vector)this.moment.plus(b.moment));
        }else {
            throw new NonSenseException();
        }
    }

    @Override
    public Maob minus(Maob two) throws  NonSenseException {
        if(two instanceof Wrench){
            Wrench b = (Wrench) two;
            return new Wrench((Vector)this.sum.minus(b.sum),(Vector)this.moment.minus(b.moment));
        }else {
            throw new NonSenseException();
        }
    }

    @Override
    public Maob expressIn(Basis basis) throws NonSenseException {
        return new Wrench(sum.expressIn(basis),moment.expressIn(basis));
    }

    @Override
    public Maob differentiate(Basis basis) throws NonSenseException {
        return new Wrench(sum.differentiate(basis),moment.differentiate(basis));
    }

    public Wrench shift(Vector v) throws  NonSenseException {
        return new Wrench(sum,(Vector)moment.plus(sum.cross(v)));
    }

    @Override
    public String toString() {
        return "{" + sum + " ; " + moment + "}";
    }

}
