import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class New extends JFrame implements ActionListener {
    JPanel panel;
    UI ui;
    String type;

    public New(String type, UI ui) {

        this.type=type;
        switch (type){
            case "solid":
                this.setTitle("New Solid");
                this.panel = new SolidPanel(ui,this);
                break;
            case "matrix":
                this.setTitle("New Matrix");
                this.panel = new MatrixPanel(ui,this);
                break;
            case "basis":
                this.setTitle("New Basis");
                this.panel = new BasisPanel(ui,this);
                break;
            case "variables":
                this.setTitle("Declare Variables");
                this.panel = new VariablesPanel(this);
                break;
        }



        this.setResizable(false);
        this.ui=ui;
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
    }



    private void doYourThing(){
        switch (type){
            case "solid":
                SolidPanel sp = (SolidPanel) panel;
                try {
                    Kernel.solids.put(sp.name.getText(),new Solid(Kernel.basis.get(sp.basisChoice.getSelectedItem()),new Scalar(sp.masse.getText()), (Matrix) Kernel.maobs.get(sp.nameMatrix.getText()),(Vector) Kernel.quickInput(sp.pointOfMatrix.getText())));
                } catch (NonSenseException e) {
                    e.printStackTrace();
                }
                break;
            case "matrix":
                MatrixPanel mp = (MatrixPanel) panel;
                Kernel.maobs.put(mp.Name.getText(),new Matrix(mp.A.getText(), mp.B.getText(), mp.C.getText(), mp.D.getText(), mp.E.getText(), mp.F.getText(),Kernel.basis.get(mp.jcomp4.getSelectedItem())));
                break;
            case "basis":
                BasisPanel bp = (BasisPanel) panel;
                String axis="x";
                if(bp.y.isSelected()){
                    axis="y";
                }else if(bp.z.isSelected()){
                    axis="z";
                }
                Kernel.basis.put(bp.name.getText(),new Basis(bp.name.getText(),Kernel.basis.get(bp.predecessor.getSelectedItem()),axis, bp.angle.getText()));
                Kernel.drawFigures();
                break;
            case "variables":
                VariablesPanel vp = (VariablesPanel) panel;
                for( String name: vp.variables.getText().replace(" ","").split(",")){
                    Kernel.addVar(name);
                }
                break;
        }
        ui.refresh();
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        doYourThing();
    }


}