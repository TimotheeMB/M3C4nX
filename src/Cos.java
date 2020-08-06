public class Cos extends Function {

    //CONSTRUCTORS
    public Cos(Variable variable) {
        super(variable);
    }

    //TOSTRING
    public String toString() {
        return "cos(" + variable + ')';
    }

    @Override
    public Scalar differentiate() throws NonSenseException {
        Scalar diff = new Scalar(new Sin(variable),false);
        diff = (Scalar) diff.dot(variable.differentiate());
        return diff;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return variable==((Cos) obj).variable;
        
    }
}
