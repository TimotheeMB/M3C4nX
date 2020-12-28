import java.io.Serializable;
import java.util.LinkedList;

public class Scalar extends Maob{

    //ATTRIBUTES
    LinkedList<Term> terms;


    //CONSTRUCTORS
    public Scalar() {
        terms=new LinkedList<Term>();

    }//default

    public Scalar(Term t) {
        this();
        add(t);
    }//simple

    public Scalar(Function f, boolean positive){
        this();
        add(new Term(f,positive));
    }//useful
    public Scalar(Function f){
        this(f,true);
    }

    public Scalar(String s, boolean positive){
        this();
        add(new Term(s,positive));
    }//best
    public Scalar(String s){
        this(s, true);
    }


    //ADD
    public void add(Term t){
        if(t.isNull()) {
        }else if(terms.contains(t.opposite())) {
            terms.remove(t.opposite());
        }else{
            terms.add(t);
        }
    }


    //OPERATIONS
    public Maob dot(Maob two) throws NonSenseException {
        if (two instanceof Scalar) {
            Scalar result = new Scalar();
            for (Term myTerm : this.terms) {
                for (Term hisTerm : ((Scalar) two).terms) {
                    result.add(myTerm.dot(hisTerm));
                }
            }
            return result;

        }else if(two instanceof Vector){
            return two.dot(this);
        }else if(two instanceof Wrench){
            return two.dot(this);
        }else {
            throw new NonSenseException();
        }
    }
    public SimpleVector dot(SimpleVector two) throws NonSenseException {
        return two.dot(this);
    }

    public Maob plus(Maob two) throws NonSenseException {
        if (two instanceof Scalar){
            Scalar result = new Scalar();
            for (Term t:this.terms) {
                result.add(t);
            }
            for (Term t:((Scalar) two).terms) {
                result.add(t);
            }
            return result;
        }else {
            throw new NonSenseException();
        }
    }

    public Maob minus(Maob two) throws NonSenseException {
        if (two instanceof Scalar){
            Scalar result = new Scalar();
            for (Term t:this.terms) {
                result.add(t);
            }
            for (Term t:((Scalar) two).terms) {
                t.positive=!t.positive;
                result.add(t);
            }
            return result;
        }else {
            throw new NonSenseException();
        }
    }


    public Scalar differentiate() throws NonSenseException {
        Scalar r = new Scalar();
        for (Term t:terms){
            r= (Scalar) r.plus(t.differentiate());
        }
        return r;
    }
    public Scalar differentiate(Basis useless) throws NonSenseException {
        return differentiate();
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
