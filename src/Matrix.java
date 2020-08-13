public class Matrix extends Maob {
    Scalar A,B,C,D,E,F;
    Basis basis;

    public Matrix(Scalar a, Scalar b, Scalar c, Scalar d, Scalar e, Scalar f, Basis basis) {
        A = a;
        B = b;
        C = c;
        D = d;
        E = e;
        F = f;
        this.basis = basis;
    }

    @Override
    public Maob dot(Maob b) throws NonSenseException {
        if (b instanceof Vector){
            Vector v=((Vector)b).expressIn(basis);
        }
        return null;
    }

    @Override
    public Maob plus(Maob b) throws  NonSenseException {
        return null;
    }

    @Override
    public Maob minus(Maob b) throws  NonSenseException {
        return null;
    }

    @Override
    public Maob expressIn(Basis basis) throws NonSenseException {
        return null;
    }

    @Override
    public Maob differentiate(Basis basis) throws NonSenseException {
        return null;
    }

    //TOSTRING

    @Override
    public String toString() {
        return "Matrix{" +
                "A=" + A +
                ", B=" + B +
                ", C=" + C +
                ", D=" + D +
                ", E=" + E +
                ", F=" + F +
                ", basis=" + basis +
                '}';
    }
}
