import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
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

        gbc.gridx = 1;
        gbc.gridy = 0;
        newBasisBut = new JButton("New Basis");
        total.add(newBasisBut, gbc);
        newBasisBut.addActionListener(this);

        gbc.gridx = 2;
        gbc.gridy = 0;
        newMatrixBut = new JButton("New Matrix");
        total.add(newMatrixBut, gbc);
        newMatrixBut.addActionListener(this);

        gbc.gridx = 3;
        gbc.gridy = 0;
        variablesBut = new JButton("Variables");
        total.add(variablesBut, gbc);
        variablesBut.addActionListener(this);

        gbc.gridx = 4;
        gbc.gridy = 0;
        newSolidBut = new JButton("New Solid");
        total.add(newSolidBut, gbc);
        newSolidBut.addActionListener(this);

        gbc.gridx = 5;
        gbc.gridy = 0;
        saveBut = new JButton("Save");
        total.add(saveBut, gbc);
        saveBut.addActionListener(this);

        gbc.gridx = 6;
        gbc.gridy = 0;
        loadBut = new JButton("Load");
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
        boolean running = true;
        Kernel.basis.put("0", new Basis("0"));
        refreshSummery();

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==10){//if Enter
            String text = terminal.getText();
            int beginningInput =text.lastIndexOf(">>")+2;
            String input="";
            for (int i = beginningInput; i <text.length() ; i++) {
                input += text.charAt(i);
            }
            input=input.replace(" ","");
            input=input.replace("\n","");
            input=input.replace("\r","");

            String[] affectation = input.split("=");


            if (affectation.length == 1) {
                affectation = new String[]{"ans", affectation[0]};
            }
            try {
                char[] tmpArray = affectation[1].toCharArray();
                for (int i = 0; i < tmpArray.length; i++) {
                    if (tmpArray[i] == '-' && (i==0 || tmpArray[i-1] == ',' || tmpArray[i-1] == '+' || tmpArray[i-1] == '-')){
                        tmpArray[i]='~';//minus the sign
                    }
                }
                affectation[1]=new String(tmpArray);
                Maob result = Kernel.compute(affectation[1]);
                terminal.append(affectation[0] + " = \n");
                terminal.append("                           " + result);
                Kernel.maobs.put(affectation[0], result);
            } catch (Exception e4) {
                terminal.append("Sorry I can not compute " + affectation[1] + " :  ");
                String s = e4.toString();
                if ("java.lang.ArrayIndexOutOfBoundsException: 3".equals(s)) {
                    terminal.append("You have to specify the basis (" + s + ")");
                } else if ("java.lang.NullPointerException".equals(s)) {
                    terminal.append("This basis does not exist (" + s + ")");
                } else if ("NonSenseException".equals(s)) {
                    terminal.append("This doesn't mean anything (" + s + ")");
                } else {
                    terminal.append(e.toString());
                }
            }
            terminal.append("\n>>");
            refreshSummery();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== helpBut) {
            String text = terminal.getText();
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

            text += help;
            terminal.setText(text);
        } else if( e.getSource()==saveBut){
            try {
                FileOutputStream fs = new FileOutputStream("variables.ser");
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(Kernel.maobs); // 3
                os.close();
                fs = new FileOutputStream("basis.ser");
                os = new ObjectOutputStream(fs);
                os.writeObject(Kernel.basis); // 3
                os.close();
                fs = new FileOutputStream("var.ser");
                os = new ObjectOutputStream(fs);
                os.writeObject(Kernel.toBeVar); // 3
                os.close();
                terminal.append("\n                           saved\n>>");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if(e.getSource()==loadBut){
            try {
                FileInputStream fis = new FileInputStream("variables.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                Kernel.maobs = ( HashMap<String,Maob>) ois.readObject();
                ois.close();
                fis = new FileInputStream("basis.ser");
                ois = new ObjectInputStream(fis);
                Kernel.basis = ( HashMap<String,Basis>) ois.readObject();
                ois.close();
                fis = new FileInputStream("var.ser");
                ois = new ObjectInputStream(fis);
                Kernel.toBeVar = (LinkedList<String>) ois.readObject();
                ois.close();
                terminal.append("\n                           loaded\n>>");
                refreshSummery();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
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
        String sumUp="";
        sumUp+="            ===OBJECTS===\n";
        for (Map.Entry<String, Maob> entry : Kernel.maobs.entrySet()) {
            String variableName = entry.getKey();
            Maob value = entry.getValue();
            sumUp += variableName + " = " + value+"\n";
        }
        sumUp+="\n\n          ===VARIABLES===\n";
        for (String s:Kernel.toBeVar) {
            if(!s.contains("dot")) {
                sumUp += s + "\n";
            }
        }
        sumUp+="\n\n              ===BASIS===\n";
        for (Map.Entry<String, Basis> entry : Kernel.basis.entrySet()) {
            String basisName = entry.getKey();
            Basis value = entry.getValue();
            sumUp += basisName+"\n";
        }
        summary.setText(sumUp);
    }

    static void addVar(String name){
        if(!Kernel.toBeVar.contains(name)){
            Kernel.toBeVar.add(name);
            Kernel.toBeVar.add(name+"dot");
            Kernel.toBeVar.add(name+"dotdot");
        }
    }


    public static void print(String s) {
        terminal.append(s);
    }


}
