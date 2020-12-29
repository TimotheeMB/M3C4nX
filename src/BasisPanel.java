import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class BasisPanel extends JPanel{
    public JTextField name;
    public JLabel jcomp2;
    public JRadioButton x;
    public JRadioButton y;
    public JRadioButton z;
    public JLabel jcomp6;
    public JComboBox predecessor;
    public JLabel jcomp8;
    public JLabel jcomp9;
    public JTextField angle;
    public JButton OK;
    UI ui;

    static int nameByDefault=1;

    public BasisPanel(UI ui, New window) {
        this.ui=ui;

        //construct components
        name = new JTextField (""+nameByDefault);
        jcomp2 = new JLabel ("   Name of the basis");
        x = new JRadioButton ("x");
        y = new JRadioButton ("y");
        z = new JRadioButton ("z");
        jcomp6 = new JLabel (" Axis in common");
        String[] items=new String[Kernel.basis.size()];
        int i=0;
        for (Map.Entry<String, Basis> entry : Kernel.basis.entrySet()) {
            String basisName = entry.getKey();
            items[i]=basisName;
            i++;
        }
        predecessor = new JComboBox (items);
        jcomp8 = new JLabel (" Predecessor");
        jcomp9 = new JLabel (" Angle");
        angle = new JTextField (5);
        angle.addActionListener(window);
        OK = new JButton ("OK");
        OK.addActionListener(window);

        //adjust size and set layout
        setPreferredSize (new Dimension (379, 231));
        setLayout (null);

        //add components
        add (name);
        add (jcomp2);
        add (x);
        add (y);
        add (z);
        add (jcomp6);
        add (predecessor);
        add (jcomp8);
        add (jcomp9);
        add (angle);
        add (OK);

        //set component bounds (only needed by Absolute Positioning)
        name.setBounds (165, 20, 130, 30);
        jcomp2.setBounds (25, 20, 125, 25);
        x.setBounds (150, 105, 40, 25);
        y.setBounds (195, 105, 40, 25);
        z.setBounds (235, 105, 40, 25);
        jcomp6.setBounds (35, 105, 100, 25);
        predecessor.setBounds (175, 65, 100, 25);
        jcomp8.setBounds (40, 65, 100, 25);
        jcomp9.setBounds (40, 145, 100, 25);
        angle.setBounds (165, 145, 100, 25);
        OK.setBounds (135, 190, 100, 25);

        nameByDefault++;
    }

}
