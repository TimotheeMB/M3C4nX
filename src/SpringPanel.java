import java.awt.*;
import javax.swing.*;

public class SpringPanel extends JPanel {
    public JButton ok;
    public JTextField stiffness;
    public JTextField parameter;
    public JLabel stiffnessLabel;
    public JLabel parameterLabel;

    public SpringPanel(New window) {
        //construct components
        ok = new JButton ("OK");
        ok.addActionListener(window);
        stiffness = new JTextField (5);
        parameter = new JTextField (5);
        parameter.addActionListener(window);
        stiffnessLabel = new JLabel ("Stiffness :");
        parameterLabel = new JLabel ("Parameter :");

        //adjust size and set layout
        setPreferredSize (new Dimension (261, 149));
        setLayout (null);

        //add components
        add (ok);
        add (stiffness);
        add (parameter);
        add (stiffnessLabel);
        add (parameterLabel);

        //set component bounds (only needed by Absolute Positioning)
        ok.setBounds (75, 105, 100, 20);
        stiffness.setBounds (110, 15, 115, 25);
        parameter.setBounds (110, 55, 115, 25);
        stiffnessLabel.setBounds (30, 15, 100, 25);
        parameterLabel.setBounds (30, 55, 100, 25);
    }

}
