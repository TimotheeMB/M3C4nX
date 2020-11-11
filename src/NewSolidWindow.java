import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewSolidWindow extends JFrame implements ActionListener {
    NewSolidPanel panel;
    UI ui;

    public NewSolidWindow(UI ui) {
        this.setResizable(false);
        this.setTitle("New Solid");
        this.panel = new NewSolidPanel(ui,this);
        this.ui=ui;
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
    }
}