import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class Kernel {


    //ATTRIBUTES
    static HashMap<String,Maob> maobs = new HashMap<>();
    static HashMap<String,Basis> basis = new HashMap<>();
    static HashMap<String,Solid> solids = new HashMap<>();
    static LinkedList<String> toBeVar = new LinkedList<>();

    static Vector gravity;

    public static void inputOutput(String input){

        String[] affectation = affectation(simplify(input));

        try {
            Maob result = computePlus(affectation[1]);
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

    public static Maob quickInput(String input) throws NonSenseException {
        return computePlus(simplify(input));
    }

    public static String[] affectation(String input){
        String[] affectation = input.split("=");
        if (affectation.length == 1) {
            affectation = new String[]{"ans", affectation[0]};
        }
        return affectation;
    }

    public static String simplify(String input){
        return input.replace(" ","").replace("\n","").replace("\r","");
    }

    public static Maob computePlus (String s) throws NonSenseException {
        return compute(manageMinusSign(s));
    }

    public static Maob compute(String s) throws NonSenseException {
        String[] tmp;
        if (maobs.containsKey(s)) {
            return maobs.get(s);
        }else if(s.contains("KE")){
            s = s.replace("KE","");
            if(s.equals("")){
                return Kernel.kineticEnergy();
            }else {
                return (Kernel.solids.get(s).kineticEnergy());
            }
        }else if(s.contains("U")){
            s = s.replace("U","");
            if(s.equals("")){
                return Kernel.u();
            }else {
                return (Kernel.solids.get(s).u());
            }
        }else if(s.contains("diff")){
            tmp = s.split("diff");
            if(tmp.length==1){
                return ((Vector) compute(tmp[0])).differentiate(basis.get("0"));
            }
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
        }else if(s.contains("/")){
            tmp = s.split("/");
            Basis basis = Kernel.basis.get(String.valueOf(tmp[1].charAt(1)));
            BasisVector basisVector;
            if(tmp[1].charAt(0)=='x'){
                basisVector=basis.x;
            }else if(tmp[1].charAt(0)=='y'){
                basisVector=basis.y;
            }else if(tmp[1].charAt(0)=='z'){
                basisVector=basis.z;
            }else{
                throw new NonSenseException();
            }
            return new Vector(new SimpleVector((Scalar) compute(tmp[0]),basisVector));
        }else if(s.contains("omega")){
            tmp = s.split("omega");
            return (basis.get(tmp[0])).omega(basis.get(tmp[1]));
        }else {
            return new Scalar(s.replace("~",""),!s.contains("~")); // minus the sign
        }
    }

    public static String manageMinusSign(String input){
        char[] tmpArray = input.toCharArray();
        for (int i = 0; i < tmpArray.length; i++) {
            if (tmpArray[i] == '-' && (i==0 || tmpArray[i-1] == ',' || tmpArray[i-1] == '+' || tmpArray[i-1] == '-')){
                tmpArray[i]='~';//minus the sign
            }
        }
        return new String(tmpArray);
    }

    static void addVar(String name){
        if(!toBeVar.contains(name)){
            toBeVar.add(name);
            toBeVar.add(name+"dot");
            toBeVar.add(name+"dotdot");
        }
    }

    static void drawFigures(){
        for (Map.Entry<String, Basis> entry : basis.entrySet()) {
            Basis basis = entry.getValue();
            if(!basis.name.equals("0")) {
                basis.drawFigure();
            }
        }
    }

    static Scalar kineticEnergy() throws NonSenseException {
        Scalar sum=new Scalar("0");
        for (Map.Entry<String, Solid> entry : Kernel.solids.entrySet()) {
            Solid solid = entry.getValue();
            sum= (Scalar) sum.plus(solid.kineticEnergy());
        }
        return sum;
    }

    static Scalar u() throws NonSenseException {
        Scalar sum=new Scalar("0");
        for (Map.Entry<String, Solid> entry : Kernel.solids.entrySet()) {
            Solid solid = entry.getValue();
            sum= (Scalar) sum.plus(solid.u());
        }
        return sum;
    }

    static void save(){
        UI.println("Not available yet");
    }

    static void load(){

        String[] models={"double pendulum"};
        int chosenModel = JOptionPane.showOptionDialog(null, "Chose your model", "load", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, models, models[0]);

        if(chosenModel==0){
            toBeVar.add("theta");
            toBeVar.add("psi");
            basis.put("1",new Basis("1",basis.get("0"),"x","theta"));
            basis.put("2",new Basis("2",basis.get("1"),"x","psi"));
            Kernel.drawFigures();
            UI.println("done ;)");
        }else{
            UI.println("This model doesn't exist");
        }


    }

    static void initialize(){
        Path figures = Paths.get("./figures");
        try {
            if(!Files.exists(figures)) {
                Files.createDirectory(figures);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        basis.put("0", new Basis("0"));
    }

    static Basis get0(){
        return basis.get("0");
    }

}
