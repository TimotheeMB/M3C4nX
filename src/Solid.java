import javax.swing.*;

public class Solid {
    Basis basis;
    Scalar m;
    Matrix I;
    Vector centreOfGravity;

    public Solid(Basis basis, Scalar m, Matrix i, Vector centreOfGravity) {
        this.basis = basis;
        this.m = m;
        I = i;
        this.centreOfGravity = centreOfGravity;
    }

    Scalar kineticEnergy() throws NonSenseException {
        Vector vOfG = centreOfGravity.differentiate(Kernel.get0());
        Scalar translationalPart = (Scalar) m.dot(vOfG).dot(vOfG);
        Vector omega = basis.omega(Kernel.get0());
        Scalar rotationalPart = (Scalar) I.dot(omega).dot(omega);

        return (Scalar) translationalPart.plus(rotationalPart);
    }

    Scalar u() throws NonSenseException {
        if(Kernel.gravity==null){
            try {
                Kernel.gravity = (Vector) Kernel.quickInput(JOptionPane.showInputDialog("Enter the value of the gravity"));
            }catch (Exception e4){}
        }
        return (Scalar) m.dot(Kernel.gravity.dot(centreOfGravity));
    }
}
