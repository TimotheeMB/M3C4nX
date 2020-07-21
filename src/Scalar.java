import java.io.Serializable;
import java.util.LinkedList;

public class Scalar extends Maob implements Serializable {

    //ATTRIBUTES
    LinkedList<Term> terms;


    //CONSTRUCTORS
    public Scalar() {
        terms=new LinkedList<Term>();

    }
    public Scalar(Function f){
        this(f,true);
    }
    public Scalar(Function f, boolean positive){
        this();
        terms.add(new Term(f,positive));
    }
    public Scalar(String s, boolean positive){
        this();
        add(new Term(s,positive));
    }
    public Scalar(String s){
        this(s, true);
    }

    //ADD
    public void add(Term t){
        if(!t.isNull()) {
            terms.add(t);
        }
    }


    //OPERATIONS
    public Maob dot(Maob b) throws NonSenseException {
        if (b instanceof Scalar) {

            Scalar result = new Scalar();
            for (Term myTerm : this.terms) {
                for (Term hisTerm : ((Scalar) b).terms) {
                    result.add(myTerm.dot(hisTerm));
                }
            }
            return result;

        }else if(b instanceof Vector){
            return b.dot(this);
        }else if(b instanceof Wrench){
            return b.dot(this);
        }else {
            throw new NonSenseException();
        }
    }

    public SimpleVector dot(SimpleVector b) throws NonSenseException {
        return b.dot(this);
    }

    public Maob plus(Maob b) throws NonSenseException {
        if (b instanceof Scalar){
            Scalar result = new Scalar();
            for (Term t:this.terms) {
                result.add(t);
            }
            for (Term t:((Scalar) b).terms) {
                result.add(t);
            }
            return result;
        }else {
            throw new NonSenseException();
        }
    }

    public Maob minus(Maob b) throws NonSenseException {
        if (b instanceof Scalar){
            Scalar result = new Scalar();
            for (Term t:this.terms) {
                result.add(t);
            }
            for (Term t:((Scalar) b).terms) {
                t.positive=false;
                result.add(t);
            }
            return result;
        }else {
            throw new NonSenseException();
        }
    }

    @Override
    public Maob expressIn(Basis basis) throws NonSenseException {
        throw new NonSenseException();
    }

    //USEFUL
    public Scalar opposite(){
        Scalar r = new Scalar();
        for(Term t:terms){
            r.add(t.opposite());
        }
        return r;
    }
    public Scalar copy(){
        Scalar r = new Scalar();
        for(Term t:terms){
            r.add(t.copy());
        }
        return r;
    }

    //TESTS
    public boolean isNull(){
        for(Term t: terms){
            if(!t.isNull()){
                return false;
            }
        }
        return true;
    }
    public boolean isOne(){
        int countOnes=0;
        for (Term t: terms){
            if(!t.isNull()&&!t.isOne()){
                return false;
            }else if(t.isOne()){
                countOnes++;
            }
        }
        return (countOnes==1);
    }


    //TOSTRING
    public String toString() {
        String r="";
        for (int i = 0; i < terms.size(); i++) {
            Term t = terms.get(i);
            if (t.isPositive()&&i!=0) {
                r += " + " + t;
            } else if(!t.isPositive()) {
                r += " - " + t;
            }else{
                r+= t;
            }
        }
        return r;
    }
}
