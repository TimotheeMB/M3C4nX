import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;


public class UI extends JFrame {

    //ATTRIBUTES
    HashMap<String,Maob> maobs = new HashMap<>();
    HashMap<String,Basis> basis = new HashMap<>();

    Scanner sc = new Scanner(System.in);

    public UI () {

        this.setSize(1000,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("M3C4nX");
        this.setLocationRelativeTo(null);
        JPanel total = new JPanel();
        total.setBackground(Color.DARK_GRAY);
        this.add(total);

        GridBagLayout gblTot = new GridBagLayout();
        total.setLayout(gblTot);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.ipady = gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 0.8;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy=0;
        Color veryDarkGrey = new Color(40,40, 50);
        JPanel commandPan = new JPanel();
        commandPan.setBackground(veryDarkGrey);
        total.add(commandPan, gbc);

        GridBagLayout gblCom = new GridBagLayout();
        commandPan.setLayout(gblCom);
        gbc.weightx = 0.2;
        gbc.weighty = 0.1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy=0;
        JButton helpBut = new JButton("Help");
        commandPan.add(helpBut, gbc);

        gbc.gridx = 1;
        gbc.gridy= 0;
        JButton quitBut = new JButton("Quit");
        commandPan.add(quitBut,gbc);

        gbc.gridx = 2;
        gbc.gridy= 0;
        JButton saveBut = new JButton("Save");
        commandPan.add(saveBut,gbc);

        gbc.gridx = 3;
        gbc.gridy= 0;
        JButton loadBut = new JButton("Load");
        commandPan.add(loadBut,gbc);

        gbc.weightx = 1;
        gbc.weighty = 0.9;
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy = 1;
        TextArea terminal = new TextArea();
        Font f = new Font ("Calibri", Font. BOLD, 16);
        terminal.setForeground(Color.PINK);
        terminal.setBackground(Color.DARK_GRAY);
        terminal.setFont(f);
        String intro = "";
        intro += "-----------------------------------\n";
        intro += "|             M3C4n'X             |\n";
        intro += "-----------------------------------\n";
        intro += "For more info try the command \"help\"";
        terminal.setText(intro);
        commandPan.add(terminal, gbc);

        gbc.weightx = 0.2;
        gbc.weighty = 1;
        gbc.gridx = 1;
        gbc.gridy =0;
        JPanel summaryPan = new JPanel();
        total.add(summaryPan, gbc);

        GridBagLayout gblSum = new GridBagLayout();
        summaryPan.setLayout(gblSum);
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        TextArea summary = new TextArea();
        summary.setEditable(false);
        summary.setFont(f);
        summary.setForeground(Color.CYAN);
        summary.setBackground(Color.GRAY);
        summary.setText ("This will be \n the summary of all the basis,\n variable and stuff you'll use \n during your computation");
        summaryPan.add(summary,gbc);



        this.setVisible(true);

        String help = "";
        help += "----------------------------------------------------------------------------------------\n";
        help += "                             # GENERAL DESCRIPTION                                     \n";
        help += "M3C4n'X is the perfect tool for mechanics.\n";
        help += "You can do things like shift wrenches or inertia matrices, express maobs in any basis.\n";
        help += "You'll see it's pretty cool...\n";
        help += "----------------------------------------------------------------------------------------\n";
        help += "                                  # COMMANDS                                            \n";
        help += "\n";
        help += "##SPECIAL COMMANDS\n";
        help += "¤ help\n";
        help += "¤ quit\n";
        help += "¤ list\n";
        help += "¤ save\n";
        help += "¤ load\n";
        help += "\n";
        help += "##AFFECTATION\n";
        help += "NameOfTheVariable = Whatever\n";
        help += "\n";
        help += "##DECLARATION\n";
        help += "¤ Scalar:<nameOfScalar>\n";
        help += "¤ Basis: newbasis (Then follow the instructions)\n";
        help += "¤ Vector: <coef>,<coef>,<coef>,<basis>\n";
        help += "¤ Wrench: <vector>;<vector>\n";
        help += "\n";
        help += "##OPERATION\n";
        help += "¤ Plus: +\n";
        help += "¤ Minus: -\n";
        help += "¤ Dot product: .\n";
        help += "¤ Cross product: *\n";
        help += "¤ Shift: ->\n";
        help += "¤ Express in a basis: in\n";
        help += "\n";
        help += "REMARK: You can use affectation, declaration and operation all together\n";

        boolean running = true;
        basis.put("0",new Basis("0"));


        System.out.println(intro);
        while (running) {

            System.out.print(">> ");
            String input = sc.nextLine().replace(" ", "");

            switch (input) {
                case "quit":
                    running = false;
                    break;
                case "help":
                    System.out.println(help);
                    break;
                case "newbasis":
                    System.out.print("name: ");
                    String name = sc.nextLine();
                    System.out.print("name predecessor: ");
                    String namePred = sc.nextLine();
                    System.out.print("axis: ");
                    String axis = sc.nextLine();
                    System.out.print("angle: ");
                    String angle = sc.nextLine();
                    basis.put(name, new Basis(name, basis.get(namePred), axis, angle));
                    System.out.println("\n                            new basis created");
                    break;
                case "list":
                    System.out.println("------------------------ Variables -------------------------------");
                    maobs.forEach((variableName, value) -> System.out.println(variableName + " = " + value));
                    System.out.println("-------------------------  Basis  --------------------------------");
                    basis.forEach((basisName,basis)->System.out.println(basisName));
                    System.out.println("------------------------------------------------------------------");
                    break;
                case "save":
                    try {
                        FileOutputStream fs = new FileOutputStream("variables.ser");
                        ObjectOutputStream os = new ObjectOutputStream(fs);
                        os.writeObject(maobs); // 3
                        os.close();
                        fs = new FileOutputStream("basis.ser");
                        os = new ObjectOutputStream(fs);
                        os.writeObject(basis); // 3
                        os.close();
                        System.out.println("                           saved");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "load":
                    try {
                        FileInputStream fis = new FileInputStream("variables.ser");
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        this.maobs = ( HashMap<String,Maob>) ois.readObject();
                        ois.close();
                        fis = new FileInputStream("basis.ser");
                        ois = new ObjectInputStream(fis);
                        this.basis = ( HashMap<String,Basis>) ois.readObject();
                        ois.close();
                        System.out.println("                           loaded");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    String[] affectation = input.split("=");
                    if (affectation.length == 1) {
                        affectation = new String[]{"ans", affectation[0]};
                    }
                    try {
                        Maob result = compute(affectation[1]);
                        System.out.println(affectation[0] + " = \n");
                        System.out.println("                           " + result);
                        maobs.put(affectation[0], result);
                    } catch (Exception e) {
                        System.out.println("Sorry I can not compute " + affectation[1] + " :");
                        String s = e.toString();
                        if ("java.lang.ArrayIndexOutOfBoundsException: 3".equals(s)) {
                            System.out.println("You have to specify the basis (" + s + ")");
                        } else if ("java.lang.NullPointerException".equals(s)) {
                            System.out.println("This basis does not exist (" + s + ")");
                        } else if ("NonSenseException".equals(s)) {
                            System.out.println("This doesn't mean anything (" + s + ")");
                        } else {
                            System.out.println(e.toString());
                        }
                    }
                    break;
            }
        }
    }


    public Maob compute(String s) throws NonSenseException {
        String[] tmp;
        if (maobs.containsKey(s)) {
            return maobs.get(s);
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
        }else if(s.contains("-")){
            tmp = s.split("\\-");
            Maob result = compute(tmp[0]);
            for (int i = 1; i <tmp.length ; i++) {
                result=result.minus(compute(tmp[i]));
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
            return new Vector(tmp[0],tmp[1],tmp[2],basis.get(tmp[3]));
        }else {
            return new Scalar(s);
        }
    }
}
