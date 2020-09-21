import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class Variable extends Element implements Serializable {

    static LinkedList<String> toBeVar=new LinkedList<>();

    //ATTRIBUTES
    String value;
    Boolean constant;

    //CONSTRUCTORS

    public Variable(String value, Boolean constant) {
        this.value = value;
        this.constant = constant;
        if(!constant) {
            toBeVar.add(value);
        }
    }
    public Variable(String value){
        this(value,!toBeVar.contains(value));
    }


    public Scalar differentiate(){
        if(constant){
            return new Scalar("0");
        }else{
            Scalar s =new Scalar();
            Term t= new Term();
            Variable v=new Variable(value+"dot",false);
            t.add(v);
            s.add(t);
            return s;
        }
    }

    //TOSTRING
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(value, variable.value) &&
                Objects.equals(constant, variable.constant);
    }

}
