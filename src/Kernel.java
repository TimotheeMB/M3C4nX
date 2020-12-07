import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

public final class Kernel {

    //ATTRIBUTES
    static HashMap<String,Maob> maobs = new HashMap<>();
    static HashMap<String,Basis> basis = new HashMap<>();
    static HashMap<String,Solid> solids = new HashMap<>();
    static LinkedList<String> toBeVar=new LinkedList<>();

    public static void input(String input){
        int beginningInput =input.lastIndexOf(">>")+2;
        String inputSimplified ="";
        for (int i = beginningInput; i <input.length() ; i++) {
            inputSimplified += input.charAt(i);
        }
        inputSimplified = inputSimplified.replace(" ","");
        inputSimplified = inputSimplified.replace("\n","");
        inputSimplified = inputSimplified.replace("\r","");

        String[] affectation = inputSimplified.split("=");


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
            UI.print(affectation[0] + " = \n");
            UI.print("                           " + result);
            maobs.put(affectation[0], result);
        } catch (Exception e4) {
            UI.print("Sorry I can not compute " + affectation[1] + " :  ");
            String s = e4.toString();
            if ("java.lang.ArrayIndexOutOfBoundsException: 3".equals(s)) {
                UI.print("You have to specify the basis (" + s + ")");
            } else if ("java.lang.NullPointerException".equals(s)) {
                UI.print("This basis does not exist (" + s + ")");
            } else if ("NonSenseException".equals(s)) {
                UI.print("This doesn't mean anything (" + s + ")");
            } else {
                UI.print(s);
            }
        }
        UI.print("\n>>");
    }


    public static Maob compute(String s) throws NonSenseException {
        String[] tmp;
        if (maobs.containsKey(s)) {
            return maobs.get(s);
        }else if(s.contains("newdiff")){
            tmp = s.split("newdiff");
            if(tmp.length==1){
                return ((Vector) compute(tmp[0])).newDiff(basis.get("0"));
            }
            return ((Vector)compute(tmp[0])).newDiff(basis.get(tmp[1]));
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


    static void addVar(String name){
        if(!toBeVar.contains(name)){
            toBeVar.add(name);
            toBeVar.add(name+"dot");
            toBeVar.add(name+"dotdot");
        }
    }

    static void save(){
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
            UI.print("\n                           saved\n>>");
        } catch (Exception e2) {
            UI.print("\n               I was unable to save :(\n");
        }
    }

    static void load(){
        try {
            FileInputStream fis = new FileInputStream("variables.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            maobs = ( HashMap<String,Maob>) ois.readObject();
            ois.close();
            fis = new FileInputStream("basis.ser");
            ois = new ObjectInputStream(fis);
            basis = ( HashMap<String,Basis>) ois.readObject();
            ois.close();
            fis = new FileInputStream("var.ser");
            ois = new ObjectInputStream(fis);
            toBeVar = (LinkedList<String>) ois.readObject();
            ois.close();
            UI.print("\n                           loaded\n>>");
        } catch (Exception e3) {
            UI.print("\n               I was unable to load (probably because the save is from an older version) :(\n");
        }
    }


}
