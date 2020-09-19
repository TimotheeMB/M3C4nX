import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewBasisWindow extends JFrame implements ActionListener {
    NewBasisPanel nbp;
    UI ui;

    public NewBasisWindow(UI ui) {
        nbp = new NewBasisPanel(ui,this);
        this.ui=ui;
        this.getContentPane().add(nbp);
        this.pack();
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==nbp.OK){
            String axis="x";
            if(nbp.y.isSelected()){
                axis="y";
            }else if(nbp.z.isSelected()){
                axis="z";
            }
            ui.basis.put(nbp.name.getText(),new Basis(nbp.name.getText(),ui.basis.get(nbp.predecessor.getSelectedItem()),axis,nbp.angle.getText()));
            ui.refreshSummery();
            this.setVisible(false);
        }
    }
}
