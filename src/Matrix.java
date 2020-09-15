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
    public Maob dot(Maob two) throws NonSenseException {
        if (two instanceof Vector){
            Vector result=new Vector();
            Vector v=((Vector)two).expressIn(basis);
            Scalar a = null,b=null,c=null;
            for (SimpleVector sv:v.expression) {
                if(sv.basisVector.equals(basis.x)){
                    a = sv.expression;
                }
                if(sv.basisVector.equals(basis.y)){
                    b = sv.expression;
                }
                if(sv.basisVector.equals(basis.z)){
                    c = sv.expression;
                }
            }
            Scalar resultX = (Scalar) a.dot(A).minus(b.dot(F)).minus(c.dot(E));
            Scalar resultY = (Scalar) b.dot(B).minus(a.dot(F)).minus(c.dot(D));
            Scalar resultZ = (Scalar) c.dot(D).minus(a.dot(E)).minus(b.dot(D));
            result.add(new SimpleVector(resultX, basis.x));
            result.add(new SimpleVector(resultY, basis.y));
            result.add(new SimpleVector(resultZ, basis.z));
            return result;
        }else{
            throw new NonSenseException();
        }
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
