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

    Scalar kineticEnergy() throws NonSenseException {
        return (Scalar) m.dot(pointOfTheMatrix.newDiff(Kernel.basis.get("0")).dot(pointOfTheMatrix.newDiff(Kernel.basis.get("0"))));
    }
}
