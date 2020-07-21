import java.io.Serializable;
import java.util.LinkedList;

public class Basis implements Serializable  {

    //ATTRIBUTES
    String name;

    Basis predecessor;
    Variable angle;
    LinkedList<Basis> successors;

    BasisVector x;
    BasisVector y;
    BasisVector z;

    //CONSTRUCTORS
    public Basis(){
        successors=new LinkedList<Basis>();
    }
    public Basis(String name) {
        this();
        this.name=name;
        x = new BasisVector("x");
        y = new BasisVector("y");
        z = new BasisVector("z");
        init();
    }
    public Basis(String name, Basis predecessor, String axisInCommon, String angle) {
        this();
        this.name = name;
        this.predecessor= predecessor;
        predecessor.successors.add(this);
        this.angle=new Variable(angle,false);
        switch (axisInCommon) {
            case "x":
                x = predecessor.x;
                y = new BasisVector("y");
                z = new BasisVector("z");
                break;
            case "y":
                y = predecessor.y;
                x = new BasisVector("x");
                z = new BasisVector("z");
                break;
            case "z":
                z = predecessor.z;
                x = new BasisVector("x");
                y = new BasisVector("y");
                break;
        }
        init();
    }
    public void init(){
        x.add(this);
        y.add(this);
        z.add(this);
    }

    //USEFUL
    public LinkedList<Basis> neighbors(){
        LinkedList<Basis> r=new LinkedList<Basis>();
        if(predecessor!=null){
            r.add(predecessor);
        }
        for(Basis suc:successors){
            r.add(suc);
        }
        return r;
    }

    //TOSTRING
    public String toString() {
        return name;
    }
}
