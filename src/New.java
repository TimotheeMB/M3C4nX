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
                this.panel = new VariablesPanel(ui,this);
                break;
        }



        this.setResizable(false);
        this.ui=ui;
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
    }



    private void doYourThing() {
        switch (type){
            case "solid":
                break;
            case "matrix":
                MatrixPanel mp = (MatrixPanel) panel;
                ui.maobs.put(mp.Name.getText(),new Matrix(mp.A.getText(), mp.B.getText(), mp.C.getText(), mp.D.getText(), mp.E.getText(), mp.F.getText(),ui.basis.get(mp.jcomp4.getSelectedItem())));
                break;
            case "basis":
                BasisPanel bp = (BasisPanel) panel;
                String axis="x";
                if(bp.y.isSelected()){
                    axis="y";
                }else if(bp.z.isSelected()){
                    axis="z";
                }
                ui.basis.put(bp.name.getText(),new Basis(bp.name.getText(),ui.basis.get(bp.predecessor.getSelectedItem()),axis, bp.angle.getText()));
                break;
            case "variables":
                VariablesPanel vp = (VariablesPanel) panel;
                for( String name: vp.variables.getText().replace(" ","").split(",")){
                    ui.addVar(name);
                }
                break;
        }
        ui.refreshSummery();
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        doYourThing();
    }


}