import java.io.Serializable;

public class Variable implements Serializable {

    //ATTRIBUTES
    String value;
    Boolean constant;

    //CONSTRUCTORS
    public Variable(String value) {
        this(value,true);
    }
    public Variable(String value, Boolean constant) {
        this.value = value;
        this.constant = constant;
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
}
