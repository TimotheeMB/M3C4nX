import java.awt.event.*;
import java.io.*;
import java.util.Map;
import java.awt.*;
import javax.swing.*;


public class UI extends JFrame implements KeyListener, ActionListener {
    
    JButton helpBut;
    JButton newBasisBut;
    JButton newMatrixBut;
    JButton variablesBut;
    JButton saveBut;
    JButton loadBut;
    JButton newSolidBut;

    static TextArea terminal;
    TextArea summary;

    public UI () {

        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                File images = new File("./images");
                for(File file:images.listFiles()){
                    if(!file.getName().equals("blank_figure.png")) {
                        file.delete();
                    }
                }
                super.windowClosing(e);
            }
        });

        this.setTitle("M3C4n'X");
        this.setLocationRelativeTo(null);
        JPanel total = new JPanel();
        Color veryDarkGrey = new Color(40, 40, 50);
        total.setBackground(veryDarkGrey);
        this.add(total);

        GridBagLayout gblTot = new GridBagLayout();
        total.setLayout(gblTot);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.weightx = 0.05;
        gbc.weighty = 0.1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        helpBut = new JButton("Help");
        total.add(helpBut, gbc);
        helpBut.addActionListener(this);

        gbc.gridx = 3;
        gbc.gridy = 0;
        newBasisBut = new JButton("New Basis");
        total.add(newBasisBut, gbc);
        newBasisBut.addActionListener(this);

        gbc.gridx = 4;
        gbc.gridy = 0;
        newMatrixBut = new JButton("New Matrix");
        total.add(newMatrixBut, gbc);
        newMatrixBut.addActionListener(this);

        gbc.gridx = 2;
        gbc.gridy = 0;
        variablesBut = new JButton("Variables");
        total.add(variablesBut, gbc);
        variablesBut.addActionListener(this);

        gbc.gridx = 5;
        gbc.gridy = 0;
        newSolidBut = new JButton("New Solid");
        total.add(newSolidBut, gbc);
        newSolidBut.addActionListener(this);

        gbc.gridx = 6;
        gbc.gridy = 0;
        saveBut = new JButton("...");
        total.add(saveBut, gbc);
        saveBut.addActionListener(this);

        gbc.gridx = 1;
        gbc.gridy = 0;
        loadBut = new JButton("Load Model");
        total.add(loadBut, gbc);
        loadBut.addActionListener(this);


        gbc.weightx = 0.6;
        gbc.weighty = 0.9;
        gbc.gridwidth = 7;
        gbc.gridx = 0;
        gbc.gridy = 1;
        terminal = new TextArea("",10, 1,TextArea.SCROLLBARS_NONE);
        terminal.setForeground(Color.PINK);
        terminal.setBackground(Color.DARK_GRAY);
        Font f = new Font("Calibri", Font.BOLD, 16);
        terminal.setFont(f);
        terminal.setText(">>");
        total.add(terminal, gbc);
        terminal.addKeyListener(this);

        gbc.weightx = 0.4;
        gbc.weighty = 0.9;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.gridx = 7;
        gbc.gridy = 0;
        summary = new TextArea("",10, 1,TextArea.SCROLLBARS_NONE);
        summary.setEditable(false);
        summary.setFont(f);
        summary.setForeground(Color.CYAN);
        summary.setBackground(Color.DARK_GRAY);
        total.add(summary, gbc);
        this.setVisible(true);

        Kernel.basis.put("0", new Basis("0"));
        refreshSummery();
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==10){//if Enter
            Kernel.inputOutput(read());
            refreshSummery();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== helpBut) {
            String help = "\n";
            help += "----------------------------------------------------------------------------------------\n";
            help += "                             # GENERAL DESCRIPTION                                     \n";
            help += "M3C4n'X is the perfect tool for mechanics.\n";
            help += "You can do things like shift wrenches or inertia matrices, express anything in any basis.\n";
            help += "You'll see it's pretty cool...\n";
            help += "----------------------------------------------------------------------------------------\n";
            help += "                                  # COMMANDS                                            \n";
            help += "##AFFECTATION\n";
            help += "NameOfTheVariable = Whatever\n";
            help += "\n";
            help += "##DECLARATION\n";
            help += "¤ Scalar:<nameOfScalar>\n";
            help += "¤ Vector: <coef>,<coef>,<coef>,<basis>\n";
            help += "¤ Wrench: <vector>;<vector>\n";
            help += "¤ Basis/Matrix/Variables: look at the buttons\n";
            help += "\n";
            help += "##OPERATION\n";
            help += "¤ Plus: +\n";
            help += "¤ Minus: -\n";
            help += "¤ Dot product: .\n";
            help += "¤ Cross product: *\n";
            help += "¤ Shift: ->\n";
            help += "¤ Express in a basis: in\n";
            help += "¤ Differentiate with respect to: diff\n";
            help += "\n";
            help += "REMARK: You can use affectation, declaration and operation all together\n";
            help += "----------------------------------------------------------------------------------------\n";
            help += "                                     # TIPS                                             \n";
            help += "You should proceed in the following order:\n";
            help += "- Declare all your variables\n";
            help += "- Create all the basis\n";
            help += "- And THEN do all your computations\n";
            help += "\n";

            print(help);
        } else if( e.getSource()==saveBut){
            Kernel.save();
        } else if(e.getSource()==loadBut){
            Kernel.load();
            refreshSummery();
        }else if(e.getSource()==newBasisBut){
            new New("basis",this);
        }else if(e.getSource()==newMatrixBut){
            new New("matrix",this);
        }else if(e.getSource()==variablesBut){
            new New("variables",this);
        }else if(e.getSource()==newSolidBut){
            new New("solid",this);
        }
    }

    void refreshSummery(){
        String sumUp="### SUMMARY ###\n";
        sumUp+="________________\n    ~ OBJECTS ~\n";
        for (Map.Entry<String, Maob> entry : Kernel.maobs.entrySet()) {
            String variableName = entry.getKey();
            Maob value = entry.getValue();
            sumUp += variableName + " = " + value+"\n";
        }
        sumUp+="________________\n  ~ VARIABLES ~\n";
        for (String s:Kernel.toBeVar) {
            if(!s.contains("dot")) {
                sumUp += s + "\n";
            }
        }
        sumUp+="________________\n       ~ BASIS ~\n";
        for (Map.Entry<String, Basis> entry : Kernel.basis.entrySet()) {
            String basisName = entry.getKey();
            Basis value = entry.getValue();
            sumUp += basisName+"\n";
        }
        summary.setText(sumUp);
    }

    public static void print(String s) {
        terminal.append(s);
    }
    public static void println(String s) {
        print("\n"+s+"\n>>");
    }

    public static String read() {
        String totalText = terminal.getText();
        int beginningInput = totalText.lastIndexOf(">>")+2;
        String input ="";
        for (int i = beginningInput; i < totalText.length() ; i++) {
            input += totalText.charAt(i);
        }
        return input.replace(" ","").replace("\n","").replace("\r","");
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {}
}
