import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewMatrixWindow extends JFrame implements ActionListener {

    UI ui;
    NewMatrixPanel panel;

    public NewMatrixWindow(UI ui){
        this.ui=ui;
        this.setResizable(false);
        panel = new NewMatrixPanel(ui,this);
        this.getContentPane().add (panel);
        this.pack();
        this.setVisible (true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== panel.OK){
            this.setVisible(false);
            ui.maobs.put(panel.Name.getText(),new Matrix(panel.A.getText(), panel.B.getText(), panel.C.getText(), panel.D.getText(), panel.E.getText(), panel.F.getText(),ui.basis.get(panel.jcomp4.getSelectedItem())));
            ui.refreshSummery();
        }
    }
}
