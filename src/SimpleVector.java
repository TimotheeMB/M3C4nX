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
    public Scalar dot(SimpleVector two) throws NonSenseException {
        Scalar vectorMultiplication=this.basisVector.dot(two.basisVector);
        if (vectorMultiplication.isOne()) {
            return  (Scalar) this.expression.dot(two.expression);
        } else if (vectorMultiplication.isNull()) {
            return new Scalar("0");
        }else{
            throw new NonSenseException();
        }
    }
    public SimpleVector dot(Scalar two) throws NonSenseException {
        return new SimpleVector((Scalar) expression.dot(two),basisVector);
    }
    public Vector cross(SimpleVector two) throws NonSenseException {
        return (Vector) this.expression.dot(two.expression).dot(this.basisVector.cross(two.basisVector));
    }

    public Vector expressIn(Basis destination) throws NonSenseException {
        return (Vector) expression.dot(basisVector.expressIn(destination));
    }

    public SimpleVector differentiate() throws NonSenseException {
        return new SimpleVector(expression.differentiate(),basisVector);
    }

    public Vector newDiff(Basis wrt) throws NonSenseException {
        if(this.basisVector.belongsTo(wrt)){
            return new Vector(new SimpleVector(expression.differentiate(),basisVector));
        }else{
            Vector one=new Vector(new SimpleVector(expression.differentiate(),basisVector));
            Basis b =this.basisVector.basis.get(0);
            Vector omega = b.omega(wrt);
            new Vector(this);
            Vector crossProduct=omega.cross(new Vector(this));
            return (Vector) one.plus(crossProduct);
        }
    }

    //USEFUL
    public SimpleVector opposite(){
        return new SimpleVector(expression.opposite(),basisVector);
    }
    public SimpleVector copy(){
        return new SimpleVector(expression.copy(),basisVector);
    }

    //TESTS
    public boolean sameBasis(SimpleVector two){
        return this.basisVector.sameBasis(two.basisVector);
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
