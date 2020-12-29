public class Spring {
    Scalar stiffness;
    Scalar parameter;

    public Spring(Scalar stiffness, Scalar parameter) {
        this.stiffness = stiffness;
        this.parameter = parameter;
    }

    Scalar u() throws NonSenseException {
        return (Scalar) new Scalar("-(1/2)").dot(stiffness).dot(parameter).dot(parameter);
    }
}
