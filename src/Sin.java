public class Sin extends Function {

    //CONSTRUCTORS
    public Sin(Variable variable) {
        super(variable);
    }

    //TOSTRING
    public String toString() {
        return "sin(" + variable + ')';
    }


    public Scalar differentiate() throws NonSenseException {
        Scalar diff = new Scalar(new Cos(variable));
        diff = (Scalar) diff.dot(variable.differentiate());
        return diff;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return variable==((Sin) obj).variable;

    }
}
