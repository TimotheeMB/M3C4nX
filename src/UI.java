import java.awt.event.*;
import java.io.*;
import java.util.Map;
import java.awt.*;
import javax.swing.*;


public class UI extends JFrame implements KeyListener, ActionListener,ComponentListener {
    
    JButton helpBut;
    JButton newBasisBut;
    JButton newMatrixBut;
    JButton variablesBut;
    JButton saveBut;
    JButton loadBut;
    JButton newSolidBut;

    static TextArea terminal;
    TextArea summary;
    FiguresPanel figures;

    public UI () {

        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("M3C4n'X");
        this.setLocationRelativeTo(null);
        this.addComponentListener(this);

        JPanel total = new JPanel();
        Color veryDarkGrey = new Color(40, 40, 50);
        total.setBackground(veryDarkGrey);
        total.setLayout(null);
        this.add(total);

        helpBut = new JButton("Help");
        total.add(helpBut);
        helpBut.addActionListener(this);

        loadBut = new JButton("Load Model");
        total.add(loadBut);
        loadBut.addActionListener(this);

        variablesBut = new JButton("Variables");
        total.add(variablesBut);
        variablesBut.addActionListener(this);

        newBasisBut = new JButton("New Basis");
        total.add(newBasisBut);
        newBasisBut.addActionListener(this);

        newMatrixBut = new JButton("New Matrix");
        total.add(newMatrixBut);
        newMatrixBut.addActionListener(this);

        newSolidBut = new JButton("New Solid");
        total.add(newSolidBut);
        newSolidBut.addActionListener(this);

        saveBut = new JButton("...");
        total.add(saveBut);
        saveBut.addActionListener(this);

        terminal = new TextArea("",10, 1,TextArea.SCROLLBARS_NONE);
        terminal.setForeground(Color.PINK);
        terminal.setBackground(Color.DARK_GRAY);
        Font f = new Font("Calibri", Font.BOLD, 16);
        terminal.setFont(f);
        terminal.setText(">>");
        total.add(terminal);
        terminal.addKeyListener(this);

        summary = new TextArea("",10, 1,TextArea.SCROLLBARS_NONE);
        summary.setEditable(false);
        summary.setFont(f);
        summary.setForeground(Color.CYAN);
        summary.setBackground(Color.DARK_GRAY);
        total.add(summary);

        figures = new FiguresPanel();
        total.add(figures);
        this.setVisible(true);

        placeComponents();
        Kernel.initialize();
        this.refreshSummery();

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
    }

    public void placeComponents(){

             helpBut.setBounds(at(0,0,1,1));
             loadBut.setBounds(at(1,0,1,1));
        variablesBut.setBounds(at(2,0,1,1));
         newBasisBut.setBounds(at(3,0,1,1));
        newMatrixBut.setBounds(at(4,0,1,1));
         newSolidBut.setBounds(at(5,0,1,1));
             saveBut.setBounds(at(6,0,1,1));

            terminal.setBounds(at(0,1,7,9));

             summary.setBounds(at(7,0,3,5));

             figures.setBounds(at(7,5,3,5));

    }

    Rectangle at(int x, int y, int width, int height){
        return new Rectangle((int) ((this.getWidth()-16)*x*0.1),(int) ((this.getHeight()-40)*y*0.1),(int) ((this.getWidth()-16)*width*0.1),(int) ((this.getHeight()-40)*height*0.1));
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
        return input;
    }


    public void componentResized(ComponentEvent e) {
        placeComponents();
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {}
    public void componentMoved(ComponentEvent e) {}
    public void componentShown(ComponentEvent e) {}
    public void componentHidden(ComponentEvent e) {}
}
