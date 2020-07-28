import java.io.Serializable;

public class SimpleVector implements Serializable {
    //ATTRIBUTES
    Scalar expression;
    BasisVector basisVector;

    //CONSTRUCTORS
    public SimpleVector(Scalar expression, BasisVector basisVector) {
        this.expression = expression;
        this.basisVector = basisVector;
    }


    //OPERATIONS
    public Scalar dot(SimpleVector b) throws NonSenseException {
        Scalar vectorMultiplication=this.basisVector.dot(b.basisVector);
        if (vectorMultiplication.isOne()) {
            return  (Scalar) this.expression.dot(b.expression);
        } else if (vectorMultiplication.isNull()) {
            return new Scalar("0");
        }else{
            throw new NonSenseException();
        }
    }
    public SimpleVector dot(Scalar b) throws NonSenseException {
        return new SimpleVector((Scalar) expression.dot(b),basisVector);
    }
    public Vector cross(SimpleVector b) throws NonSenseException {
        return (Vector) this.expression.dot(b.expression).dot(this.basisVector.cross(b.basisVector));
    }

    public Vector expressIn(Basis destination) throws NonSenseException {
        return (Vector) expression.dot(basisVector.expressIn(destination));
    }

    public SimpleVector differentiate() throws NonSenseException {
        return new SimpleVector(expression.differentiate(),basisVector);
    }

    //USEFUL
    public SimpleVector opposite(){
        return new SimpleVector(expression.opposite(),basisVector);
    }
    public SimpleVector copy(){
        return new SimpleVector(expression.copy(),basisVector);
    }

    //TESTS
    public boolean sameBasis(SimpleVector b){
        return this.basisVector.sameBasis(b.basisVector);
    }
    public boolean isNull(){
        return expression.isNull();
    }

    //TOSTRING
    public String toString(){
        String r="";
        if(expression.terms.size()>1) {
            r+= "(" + expression + ")";
        }else{
            r+= expression;
        }
        r+="." + basisVector;
        return r;
    }
}
