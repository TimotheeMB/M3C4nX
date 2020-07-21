import java.io.Serializable;
import java.util.LinkedList;

public class Term implements Serializable {

    //ATTRIBUTES
    LinkedList<Variable> variables;
    LinkedList<Function> functions;
    boolean positive;
    boolean one;
    boolean zero;

    //CONSTRUCTORS
    public Term() {
        variables = new LinkedList<Variable>();
        functions = new LinkedList<Function>();
        positive =true;
        one=false;
        zero=false;
    }
    public Term(Function f, boolean positive){
        this();
        this.positive = positive;
        functions.add(f);
    }
    public Term(Function f){
        this(f,true);
    }
    public Term(String s){
        this();
        if(s.equals("1")){
            one=true;
        }else if(s.equals("0")) {
            zero = true;
        }
        variables.add(new Variable(s));
    }
    public Term(String s, boolean positive){
        this(s);
        this.positive=positive;
    }

    //ADD
    public void add(Variable v){
        this.variables.add(v);
    }
    public void add(Function f){
        this.functions.add(f);
    }

    //OPERATIONS
    public Term dot(Term b) {
        Term result = new Term();

        if (!this.isOne()) {
            for (Variable v : this.variables) {
                result.add(v);
            }
            for (Function f : this.functions) {
                result.add(f);
            }
        }
        if (!b.isOne()){
            for (Variable v : b.variables) {
                result.add(v);
            }
            for (Function f : b.functions) {
                result.add(f);
            }
        }

        if (this.isNull() ||  b.isNull()) {
            result.zero=true;
        }
        if(this.positive!=b.positive){
            result.positive=false;
        }

        return result;
    }

    //USEFUL
    public Term copy(){
        Term copy = new Term();
        copy.variables=this.variables;
        copy.functions=this.functions;
        copy.positive=this.positive;
        return copy;
    }
    public Term opposite(){
        Term r= new Term();
        r.functions=functions;
        r.variables=variables;
        r.positive=!positive;
        return r;
    }

    //TESTS
    public boolean isPositive(){
        return positive;
    }
    public boolean isNull(){
        return zero;
    }
    public boolean isOne(){
        return one;
    }

    //TOSTRING
    public String toString() {
        String r="";
        for (Variable v: variables) {
            r+=v;
        }
        for (Function f:functions) {
            r+=f;
        }
        return r;
    }
}
