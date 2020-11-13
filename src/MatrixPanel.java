//Generated by GuiGenie - Copyright (c) 2004 Mario Awad.
//Home Page http://guigenie.cjb.net - Check often for new versions!

import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class MatrixPanel extends JPanel {
    public JLabel jcomp1;
    public JTextField Name;
    public JLabel jcomp3;
    public JComboBox jcomp4;
    public JTextField A;
    public JTextField F;
    public JTextField E;
    public JTextField B;
    public JTextField D;
    public JTextField C;
    public JLabel Fbis;
    public JLabel Ebis;
    public JLabel Dbis;
    public JButton OK;
    UI ui;

    public MatrixPanel(UI ui, New window) {
        this.ui =ui;

        //construct preComponents
        String[] items=new String[Kernel.basis.size()];
        int i=0;
        for (Map.Entry<String, Basis> entry : Kernel.basis.entrySet()) {
            String basisName = entry.getKey();
            Basis value = entry.getValue();
            items[i]=basisName;
            i++;
        }

        //construct components
        jcomp1 = new JLabel ("  Name");
        Name = new JTextField (5);
        jcomp3 = new JLabel ("  Basis");
        jcomp4 = new JComboBox (items);
        A = new JTextField ("A");
        F = new JTextField ("- F");
        E = new JTextField ("- E");
        B = new JTextField ("B");
        D = new JTextField ("- D");
        C = new JTextField ("C");
        Fbis = new JLabel ("- F");
        Ebis = new JLabel ("- E");
        Dbis = new JLabel ("- D");
        OK = new JButton ("OK");
        OK.addActionListener(window);
        //adjust size and set layout
        setPreferredSize (new Dimension (307, 302));
        setLayout (null);

        //add components
        add (jcomp1);
        add (Name);
        add (jcomp3);
        add (jcomp4);
        add (A);
        add (F);
        add (E);
        add (B);
        add (D);
        add (C);
        add (Fbis);
        add (Ebis);
        add (Dbis);
        add (OK);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (30, 15, 65, 25);
        Name.setBounds (95, 15, 155, 25);
        jcomp3.setBounds (30, 45, 65, 25);
        jcomp4.setBounds (95, 45, 155, 25);
        A.setBounds (40, 100, 65, 30);
        F.setBounds (115, 100, 65, 30);
        E.setBounds (190, 100, 65, 30);
        B.setBounds (115, 150, 65, 30);
        D.setBounds (190, 150, 65, 30);
        C.setBounds (190, 195, 65, 30);
        Fbis.setBounds (45, 150, 60, 30);
        Ebis.setBounds (45, 195, 60, 30);
        Dbis.setBounds (115, 195, 65, 30);
        OK.setBounds (100, 255, 100, 25);
    }
}