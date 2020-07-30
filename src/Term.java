import java.io.Serializable;
import java.util.LinkedList;

public class Term implements Serializable {

    //ATTRIBUTES
    //LinkedList<Element> elements;
    //LinkedList<Function> functions;
    LinkedList<Element> elements;
    boolean positive;
    boolean one;
    boolean zero;

    //CONSTRUCTORS
    public Term() {
        //elements = new LinkedList<Element>();
        //functions = new LinkedList<Function>();
        elements = new LinkedList<>();
        positive =true;
        one=false;
        zero=false;
    }
    public Term(Element e, boolean positive){
        this();
        this.positive = positive;
        elements.add(e);
    }
    public Term(Element e){
        this(e,true);
    }
    public Term(String s){
        this();
        if(s.equals("1")){
            one=true;
        }else if(s.equals("0")) {
            zero = true;
        }
        elements.add(new Variable(s));
    }
    public Term(String s, boolean positive){
        this(s);
        this.positive=positive;
    }

    //ADD
    public void add(Element e){
        this.elements.add(e);
    }

    //OPERATIONS
    public Term dot(Term b) {
        Term result = new Term();

        if (!this.isOne()) {
            for (Element v : this.elements) {
                result.add(v);
            }
        }
        if (!b.isOne()){
            for (Element v : b.elements) {
                result.add(v);
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

    public Scalar differentiate() throws NonSenseException {
        Scalar r=new Scalar();
        for (Element v:elements) {
            Term vBar = new Term();
            for (Element v1:elements){
                if(!v1.equals(v)){
                    vBar.add(v1);
                }
            }
            Scalar vBarScalar = new Scalar(vBar);
            r= (Scalar) r.plus(vBarScalar.dot(v.differentiate()));
        }
        return r;
    }

    //USEFUL
    public Term copy(){
        Term copy = new Term();
        copy.elements=this.elements;
        copy.positive=this.positive;
        return copy;
    }
    public Term opposite(){
        Term r= new Term();
        r.elements=elements;
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
        for (Element v: elements) {
            r+=v;
        }
        return r;
    }
}
