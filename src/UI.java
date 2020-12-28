import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.util.Map;
import java.awt.*;
import javax.swing.*;


public class UI extends JFrame implements KeyListener, ActionListener,ComponentListener {

    JButton helpBut;
    JButton newBasisBut;
    JButton newMatrixBut;
    JButton variablesBut;
    JButton newSpringBut;
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

        helpBut = new JButton(new ImageIcon("./images/support.png"));
        total.add(helpBut);
        helpBut.addActionListener(this);

        loadBut = new JButton(new ImageIcon("./images/upload.png"));
        total.add(loadBut);
        loadBut.addActionListener(this);

        variablesBut = new JButton(new ImageIcon("./images/angle.png"));
        total.add(variablesBut);
        variablesBut.addActionListener(this);

        newBasisBut = new JButton(new ImageIcon("./images/axis.png"));
        total.add(newBasisBut);
        newBasisBut.addActionListener(this);

        newMatrixBut = new JButton(new ImageIcon("./images/matrix.png"));
        total.add(newMatrixBut);
        newMatrixBut.addActionListener(this);

        newSolidBut = new JButton(new ImageIcon("./images/block.png"));
        total.add(newSolidBut);
        newSolidBut.addActionListener(this);

        newSpringBut = new JButton(new ImageIcon("./images/spring.png"));
        total.add(newSpringBut);
        newSpringBut.addActionListener(this);

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


        placeComponents();
        Kernel.initialize();
        this.refresh();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                File figures = new File("./figures");
                for(File file:figures.listFiles()){
                    file.delete();
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
             newSpringBut.setBounds(at(6,0,1,1));

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
            refresh();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== helpBut) {
            try {
                URI oURL = new URI("README.html");
                Desktop.getDesktop().browse(oURL);
            }catch(Exception e2){
                e2.printStackTrace();
            }
        }else if( e.getSource()==newSpringBut){
            println("Not available yet");
        }else if(e.getSource()==loadBut){
            Kernel.load();
            refresh();
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

    void refresh(){
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
            sumUp += basisName+"\n";
        }
        sumUp+="________________\n       ~ SOLIDS ~\n";
        for (Map.Entry<String, Solid> entry : Kernel.solids.entrySet()) {
            String solidName = entry.getKey();
            sumUp += solidName +"\n";
        }
        summary.setText(sumUp);
        figures.repaint();
        this.setVisible(true);
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
