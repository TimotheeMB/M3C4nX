import java.io.*;
import java.util.HashMap;
import java.util.Scanner;


public class UI {

    //ATTRIBUTES
    HashMap<String,Maob> maobs = new HashMap<>();
    HashMap<String,Basis> basis = new HashMap<>();

    Scanner sc = new Scanner(System.in);

    public UI () {
        String intro = "";
        intro += "-----------------------------------\n";
        intro += "|             M3C4n'X             |\n";
        intro += "-----------------------------------\n";
        intro += "For more info try the command \"help\"";

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
        }else if(s.contains("dwrt")){
            tmp = s.split("dwrt");
            return ((Vector)compute(tmp[0])).differentiate(basis.get(tmp[1]));
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
