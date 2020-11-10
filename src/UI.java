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

    //ATTRIBUTES
    HashMap<String,Maob> maobs = new HashMap<>();
    HashMap<String,Basis> basis = new HashMap<>();
    static LinkedList<String> toBeVar=new LinkedList<>();

    JButton helpBut;
    JButton newBasisBut;
    JButton newMatrixBut;
    JButton variablesBut;
    JButton saveBut;
    JButton loadBut;

    Scanner sc = new Scanner(System.in);
    public static TextArea terminal;
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
        saveBut = new JButton("Save");
        total.add(saveBut, gbc);
        saveBut.addActionListener(this);

        gbc.gridx = 5;
        gbc.gridy = 0;
        loadBut = new JButton("Load");
        total.add(loadBut, gbc);
        loadBut.addActionListener(this);

        gbc.weightx = 0.6;
        gbc.weighty = 0.9;
        gbc.gridwidth = 6;
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
        gbc.gridx = 6;
        gbc.gridy = 0;
        summary = new TextArea("",10, 1,TextArea.SCROLLBARS_NONE);
        summary.setEditable(false);
        summary.setFont(f);
        summary.setForeground(Color.CYAN);
        summary.setBackground(Color.DARK_GRAY);
        total.add(summary, gbc);

        this.setVisible(true);
        boolean running = true;
        basis.put("0", new Basis("0"));
        refreshSummery();

    }


    public Maob compute(String s) throws NonSenseException {
        String[] tmp;
        if (maobs.containsKey(s)) {
            return maobs.get(s);
        }else if(s.contains("diff")){
            tmp = s.split("diff");
            if(tmp.length==1){
                return (compute(tmp[0])).differentiate(basis.get("0"));
            }
            return (compute(tmp[0])).differentiate(basis.get(tmp[1]));
        }else if(s.contains("in")){
            tmp = s.split("in");
            return (compute(tmp[0])).expressIn(basis.get(tmp[1]));
        }else if(s.contains("->")){
            tmp = s.split("->");
            return ((Wrench) compute(tmp[0])).shift((Vector) compute(tmp[1]));
        }else if(s.contains("+")) {
            tmp = s.split("\\+");
            Maob result = compute(tmp[0]);
            for (int i = 1; i <tmp.length ; i++) {
                result=result.plus(compute(tmp[i]));
            }
            return result;
        }else if(s.contains("-")){ //Minus the operation
            tmp = s.split("-");
            Maob result = compute(tmp[0]);
            for (int i = 1; i < tmp.length; i++) {
                result = result.minus(compute(tmp[i]));
            }
            return result;
        }else if(s.contains("*")){
            tmp = s.split("\\*");
            return ((Vector) compute(tmp[0])).cross((Vector) compute(tmp[1]));
        }else if(s.contains(".")) {
            tmp = s.split("\\.");
            return (compute(tmp[0])).dot(compute(tmp[1]));
        }else if(s.contains(";")){
            tmp = s.split(";");
            return new Wrench((Vector)compute(tmp[0]),(Vector)compute(tmp[1]));
        }else if(s.contains(",")){
            tmp = s.split(",");
            return new Vector((Scalar)compute(tmp[0]),(Scalar)compute(tmp[1]),(Scalar)compute(tmp[2]),basis.get(tmp[3]));
        }else if(s.contains("omega")){
            tmp = s.split("omega");
            return (basis.get(tmp[0])).omega(basis.get(tmp[1]));
        }else {
            return new Scalar(s.replace("~",""),!s.contains("~")); // minus the sign
        }
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
                Maob result = compute(affectation[1]);
                terminal.append(affectation[0] + " = \n");
                terminal.append("                           " + result);
                maobs.put(affectation[0], result);
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
                os.writeObject(maobs); // 3
                os.close();
                fs = new FileOutputStream("basis.ser");
                os = new ObjectOutputStream(fs);
                os.writeObject(basis); // 3
                os.close();
                fs = new FileOutputStream("var.ser");
                os = new ObjectOutputStream(fs);
                os.writeObject(toBeVar); // 3
                os.close();
                terminal.append("\n                           saved\n>>");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if(e.getSource()==loadBut){
            try {
                FileInputStream fis = new FileInputStream("variables.ser");
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.maobs = ( HashMap<String,Maob>) ois.readObject();
                ois.close();
                fis = new FileInputStream("basis.ser");
                ois = new ObjectInputStream(fis);
                this.basis = ( HashMap<String,Basis>) ois.readObject();
                ois.close();
                fis = new FileInputStream("var.ser");
                ois = new ObjectInputStream(fis);
                this.toBeVar = (LinkedList<String>) ois.readObject();
                ois.close();
                terminal.append("\n                           loaded\n>>");
                refreshSummery();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }else if(e.getSource()==newBasisBut){
            new NewBasisWindow(this);
        }else if(e.getSource()==newMatrixBut){
            new NewMatrixWindow(this);
        }else if(e.getSource()==variablesBut){
            new VariablesWindow(this);
        }
    }

    void refreshSummery(){
        String sumUp="";
        sumUp+="            ===OBJECTS===\n";
        for (Map.Entry<String, Maob> entry : maobs.entrySet()) {
            String variableName = entry.getKey();
            Maob value = entry.getValue();
            sumUp += variableName + " = " + value+"\n";
        }
        sumUp+="\n\n          ===VARIABLES===\n";
        for (String s:toBeVar) {
            if(!s.contains("dot")) {
                sumUp += s + "\n";
            }
        }
        sumUp+="\n\n              ===BASIS===\n";
        for (Map.Entry<String, Basis> entry : basis.entrySet()) {
            String basisName = entry.getKey();
            Basis value = entry.getValue();
            sumUp += basisName+"\n";
        }
        summary.setText(sumUp);
    }

    static void addVar(String name){
        if(!toBeVar.contains(name)){
            toBeVar.add(name);
            toBeVar.add(name+"dot");
            toBeVar.add(name+"dotdot");
        }
    }
}
