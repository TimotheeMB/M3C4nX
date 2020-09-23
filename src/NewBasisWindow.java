import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewBasisWindow extends JFrame implements ActionListener {
    NewBasisPanel panel;
    UI ui;

    public NewBasisWindow(UI ui) {
        this.setResizable(false);
        this.setTitle("New Basis");
        this.panel = new NewBasisPanel(ui,this);
        this.ui=ui;
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== panel.OK){
            String axis="x";
            if(panel.y.isSelected()){
                axis="y";
            }else if(panel.z.isSelected()){
                axis="z";
            }
            ui.basis.put(panel.name.getText(),new Basis(panel.name.getText(),ui.basis.get(panel.predecessor.getSelectedItem()),axis, panel.angle.getText()));
            ui.refreshSummery();
            this.setVisible(false);
        }
    }
}
