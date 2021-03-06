
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.*;


public class SolidPanel extends JPanel implements ActionListener {
    public JLabel jcomp1;
    public JComboBox basisChoice;
    public JLabel jcomp3;
    public JTextField masse;
    public JLabel jcomp5;
    public JTextField nameMatrix;
    public JLabel jcomp7;
    public JButton newMatrix;
    public JLabel jcomp9;
    public JTextField pointOfMatrix;
    public JButton OK;
    public JLabel jcomp12;
    public JTextField name;

    public UI ui;

    public SolidPanel(UI ui, New window) {
        this.ui=ui;
        //construct preComponents
        String[] items=new String[Kernel.basis.size()];
        int i=0;
        for (Map.Entry<String, Basis> entry : Kernel.basis.entrySet()) {
            String basisName = entry.getKey();
            items[i]=basisName;
            i++;
        }

        //construct components
        jcomp1 = new JLabel ("Basis : ");
        basisChoice = new JComboBox (items);
        jcomp3 = new JLabel ("Mass :");
        masse = new JTextField (5);
        jcomp5 = new JLabel ("Matrix :");
        nameMatrix = new JTextField (5);
        jcomp7 = new JLabel ("or");
        newMatrix = new JButton ("new");
        newMatrix.addActionListener(this);
        jcomp9 = new JLabel ("Center of Gravity :");
        pointOfMatrix = new JTextField (5);
        pointOfMatrix.addActionListener(window);
        OK = new JButton ("OK");
        OK.addActionListener(window);
        jcomp12 = new JLabel ("Name:");
        name = new JTextField (5);

        //adjust size and set layout
        setPreferredSize (new Dimension (309, 253));
        setLayout (null);

        //add components
        add (jcomp1);
        add (basisChoice);
        add (jcomp3);
        add (masse);
        add (jcomp5);
        add (nameMatrix);
        add (jcomp7);
        add (newMatrix);
        add (jcomp9);
        add (pointOfMatrix);
        add (OK);
        add (jcomp12);
        add (name);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (20, 70, 100, 25);
        basisChoice.setBounds (75, 65, 185, 25);
        jcomp3.setBounds (20, 105, 100, 25);
        masse.setBounds (75, 100, 165, 25);
        jcomp5.setBounds (20, 140, 100, 25);
        nameMatrix.setBounds (75, 135, 75, 25);
        jcomp7.setBounds (155, 135, 50, 25);
        newMatrix.setBounds (180, 135, 75, 25);
        jcomp9.setBounds (15, 175, 100, 25);
        pointOfMatrix.setBounds (120, 170, 125, 25);
        OK.setBounds (85, 210, 100, 25);
        jcomp12.setBounds (20, 30, 100, 25);
        name.setBounds (75, 30, 175, 25);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new New("matrix",ui);
    }
}