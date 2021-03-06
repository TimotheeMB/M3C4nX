import java.util.LinkedList;

public class Vector extends Maob{

    //ATTRIBUTES
    LinkedList<SimpleVector> expression;

    //CONSTRUCTORS
    public Vector(){
        expression = new LinkedList<>();
    }
    public Vector(SimpleVector v1) throws NonSenseException {
        this();
        this.add(v1);
    }
    public Vector(Scalar x, Scalar y, Scalar z, Basis basis) throws NonSenseException {
        this();
        add(new SimpleVector(x,basis.x));
        add(new SimpleVector(y,basis.y));
        add(new SimpleVector(z,basis.z));
    }
    public Vector(String x, String y, String z,Basis basis) throws NonSenseException {
        this(new Scalar(x),new Scalar(y),new Scalar(z),basis);
    }

    //ADD
    public void add(SimpleVector vectorToAdd) throws NonSenseException {
        if(!vectorToAdd.isNull()) {
            boolean added = false;
            for (SimpleVector v : expression) {
                if (vectorToAdd.basisVector.equals(v.basisVector)) {
                    v.expression = (Scalar) v.expression.plus(vectorToAdd.expression);
                    added = true;
                }
            }
            if (!added) {
                expression.add(vectorToAdd);
            }
        }
    }

    //OPERATIONS
    public Maob dot(Maob b) throws  NonSenseException {
        if(b instanceof Vector) {
            Scalar result = new Scalar();
            for (SimpleVector v1 : expression) {
                for (SimpleVector v2 : ((Vector) b).expression){
                    Scalar toAdd;
                    if (v1.sameBasis(v2)) {
                        toAdd = v1.dot(v2);
                    } else {
                        toAdd= (Scalar) v1.expressIn(v2.basisVector.basis.getFirst()).dot(new Vector(v2));
                    }
                    result= (Scalar) result.plus(toAdd);
                }
            }
            return result;
        }else if(b instanceof Scalar) {
            Vector r=new Vector();
            for (SimpleVector v: this.expression) {
                r.add(v.dot((Scalar) b));
            }
            return r;
        }else {
            throw new NonSenseException();
        }
    }

    public Maob plus(Maob b) throws NonSenseException {
        if(b instanceof Vector) {
            Vector r=new Vector();
            for (SimpleVector v : this.expression) {
                r.add(v.copy());
            }
            for (SimpleVector v :((Vector) b).expression) {
                r.add(v.copy());
            }
            return r;
        }else{
            throw new NonSenseException();
        }
    }

    public Maob minus(Maob b) throws NonSenseException {
        if(b instanceof Vector) {
            Vector r = new Vector();
            for (SimpleVector v : this.expression) {
                r.add(v.copy());
            }
            for (SimpleVector v : ((Vector) b).expression) {
                r.add(v.opposite());
            }
            return r;
        }else{
            throw new NonSenseException();
        }
    }

    public Vector cross(Vector b) throws NonSenseException {
        Vector result = new Vector();
        for (SimpleVector v1 : expression) {
            for (SimpleVector v2 : b.expression) {
                Vector toAdd;
                if (v1.sameBasis(v2)) {
                    toAdd = v1.cross(v2);
                } else {
                    Vector v1InBasisOfV2 =v1.expressIn(v2.basisVector.basis.getFirst());
                    toAdd = v1InBasisOfV2.cross(new Vector(v2));
                }
                result = (Vector) result.plus(toAdd);
            }
        }
        return result;
    }

    public Vector expressIn(Basis destination) throws NonSenseException {
        Vector r=new Vector();
        for(SimpleVector v:expression){
            r= (Vector) r.plus(v.expressIn(destination));
        }
        return r;
    }

    public Vector oldDifferentiate(Basis wrt) throws NonSenseException {
        Vector inWrt = this.expressIn(wrt);
        Vector r = new Vector();
        for (SimpleVector v : inWrt.expression) {
            SimpleVector diff=v.oldDifferentiate();
            r.add(diff);
        }
        return r;
    }

    public Vector differentiate (Basis wrt) throws NonSenseException {
        Vector r = new Vector();
        for (SimpleVector v : this.expression) {
            Vector diff = v.differentiate(wrt);
            r= (Vector) r.plus(diff);
        }
        return r;
    }


    //TESTS
    public boolean isNull(){
        return expression.isEmpty();
    }

    public void clear(){
        expression.removeIf(SimpleVector::isNull);
    }


    //TOSTRING
    public String toString() {
        clear();
        StringBuilder r = new StringBuilder();
        if(this.isNull()){
            return "-0->";
        }else {
            for (int i = 0; i < expression.size(); i++) {
                SimpleVector v = expression.get(i);
                r.append(v.toString());
                if ((i != expression.size() - 1)&&(expression.get(i+1).expression.terms.getFirst().positive||expression.get(i+1).expression.terms.size()>1)) {//si c'est pas le dernier et que le premier term du suivant est positif
                    r.append(" + ");
                }
            }
            return r.toString();
        }
    }
}
