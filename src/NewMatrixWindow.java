import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewMatrixWindow extends JFrame implements ActionListener {

    UI ui;
    NewMatrixPanel nmp;
    public NewMatrixWindow(UI ui){
        this.ui=ui;
        this.setResizable(false);
        nmp = new NewMatrixPanel(ui,this);
        this.getContentPane().add (nmp);
        this.pack();
        this.setVisible (true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==nmp.OK){
            this.setVisible(false);
            ui.maobs.put(nmp.Name.getText(),new Matrix(nmp.A.getText(),nmp.B.getText(),nmp.C.getText(),nmp.D.getText(),nmp.E.getText(),nmp.F.getText(),ui.basis.get(nmp.jcomp4.getSelectedItem())));
            ui.refreshSummery();
        }
    }
}
