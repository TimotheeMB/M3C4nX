public class Solid {
    Basis basis;
    Scalar m;
    Matrix I;
    Vector pointOfTheMatrix;

    public Solid(Basis basis, Scalar m, Matrix i, Vector pointOfTheMatrix) {
        this.basis = basis;
        this.m = m;
        I = i;
        this.pointOfTheMatrix = pointOfTheMatrix;
    }

    Scalar kineticEnergy(){
        return new Scalar();
    }
}
