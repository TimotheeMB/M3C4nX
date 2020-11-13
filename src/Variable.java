import java.io.Serializable;
import java.util.Objects;

public class Variable extends Element implements Serializable {

    //ATTRIBUTES
    String value;
    Boolean variable;

    //CONSTRUCTORS
    public Variable(String value) {
        this.value = value;
        this.variable = Kernel.toBeVar.contains(value);
    }


    public Scalar differentiate(){
        if(!variable){
            return new Scalar("0");
        }else{
            Scalar s =new Scalar();
            Term t= new Term();
            String nameDerivative = value+"dot";
            UI.addVar(nameDerivative);
            Variable v=new Variable(nameDerivative);
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
                Objects.equals(this.variable, variable.variable);
    }

}
