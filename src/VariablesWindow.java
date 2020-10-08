import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VariablesWindow extends JFrame implements ActionListener {
    VariablesPanel panel;
    UI ui;

    public VariablesWindow(UI ui){
        this.ui=ui;
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        panel = new VariablesPanel(ui,this);
        this.getContentPane().add (panel);
        this.pack();
        this.setVisible (true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==panel.OK){
            for( String name: panel.variables.getText().replace(" ","").split(",")){
                ui.addVar(name);
            }
            ui.refreshSummery();
            this.setVisible(false);
        }
    };
        
}
