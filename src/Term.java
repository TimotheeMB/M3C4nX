import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class Term implements Serializable {

    //ATTRIBUTES
    LinkedList<Element> elements;
    boolean positive;
    boolean one;
    boolean zero;

    //CONSTRUCTORS
    public Term() {
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
    public Term dot(Term two) {
        Term result = new Term();

        if (!this.isOne()) {
            for (Element v : this.elements) {
                result.add(v);
            }
        }
        if (!two.isOne()){
            for (Element v : two.elements) {
                result.add(v);
            }
        }

        if (this.isNull() ||  two.isNull()) {
            result.zero=true;
        }
        if(this.positive!=two.positive){
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
        copy.one=one;
        copy.zero=zero;
        return copy;
    }
    public Term opposite(){
        Term r= new Term();
        r.elements=elements;
        r.one=one;
        r.zero=zero;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        if(!(positive == term.positive && one == term.one && zero == term.zero)){
            return false;
        }
        for(Element e : elements){
            if(!term.elements.contains(e)){
                return false;
            }
        }
        for(Element e : term.elements){
            if(!elements.contains(e)){
                return false;
            }
        }
        return true;
    }
}
