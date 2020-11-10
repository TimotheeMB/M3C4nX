import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class BasisVector implements Serializable {

    //ATTRIBUTES
    String name;
    LinkedList<Basis> basis;

    //CONSTRUCTORS
    public BasisVector(String name) {
        basis = new LinkedList<>();
        this.name = name;
    }

    //ADD
    public void add(Basis two){
        basis.add(two);
    }

    //OPERATIONS
    public Scalar dot(BasisVector two) throws NonSenseException {
        if(this.equals(two)){
            return new Scalar("1");
        }else if (this.sameBasis(two)){
            return new Scalar("0");
        }else{
            throw new NonSenseException();
        }
    }

    public Vector cross(BasisVector two) throws NonSenseException {
        if(this.equals(two)){
            return new Vector ();
        }else if (this.sameBasis(two)){
            Basis bas = basisInCommonWith(two);
            if(this.equals(bas.x)){
                if(two.equals(bas.y)){
                    return new Vector(new SimpleVector(new Scalar("1"),bas.z));
                }else if(two.equals(bas.z)){
                    return new Vector(new SimpleVector(new Scalar("1",false),bas.y));
                }
            }else if (this.equals(bas.y)){
                if(two.equals(bas.x)){
                    return new Vector(new SimpleVector(new Scalar("1",false),bas.z));
                }else if(two.equals(bas.z)){
                    return new Vector(new SimpleVector(new Scalar("1"),bas.x));
                }
            }else{
                if(two.equals(bas.x)){
                    return new Vector(new SimpleVector(new Scalar("1"),bas.y));
                }else if(two.equals(bas.y)){
                    return new Vector(new SimpleVector(new Scalar("1",false),bas.x));
                }
            }
        }else{
            throw new NonSenseException();
        }
        return null;
    }

    public Vector expressInNeighbor(Basis destination) throws NonSenseException {
        for (Basis two : basis) {
            boolean pred = destination.equals(two.predecessor);
            Variable angle=(pred ? two.angle:destination.angle);
            if ((destination.x).equals(two.x)) {
                if (name.equals("y")) {
                    return new Vector(new Scalar("0"), new Scalar(new Cos(angle)), new Scalar(new Sin(angle),pred), destination);
                } else if (name.equals("z")) {
                    return new Vector(new Scalar("0"), new Scalar(new Sin(angle), !pred), new Scalar(new Cos(angle)), destination);
                } else {
                    return new Vector("1", "0", "0", destination);
                }
            } else if ((destination.y).equals(two.y)) {
                if (name == "z") {
                    return new Vector(new Scalar(new Sin(angle),pred), new Scalar("0"), new Scalar(new Cos(angle)), destination);
                } else if (name == "x") {
                    return new Vector(new Scalar(new Cos(angle)), new Scalar("0"), new Scalar(new Sin(angle), !pred), destination);
                } else {
                    return new Vector("0", "1", "0", destination);
                }
            } else if ((destination.z).equals(two.z)) {
                if (name == "x") {
                    return new Vector(new Scalar(new Cos(angle)), new Scalar(new Sin(angle),pred), new Scalar("0"), destination);
                } else if (name == "y") {
                    return new Vector(new Scalar(new Sin(angle), !pred), new Scalar(new Cos(angle)), new Scalar("0"), destination);
                } else {
                    return new Vector("0", "0", "1", destination);
                }
            }
        }
        System.out.println("this was probably not a neighbor");
        return null;
    }

    public Vector expressIn(Basis goal) throws NonSenseException {

        if(basis.contains(goal)){
            return new Vector(new SimpleVector(new Scalar("1"),this));
        }else {

            HashMap<Basis, Basis> came_from = goal.dijkstra(basis.getFirst());

            Basis current = came_from.get(basis.getFirst());
            Vector result = this.expressInNeighbor(current);
            while (!current.equals(goal)) {
                current = came_from.get(current);
                result = result.expressIn(current);
            }
            return result;
        }
    }

    //USEFUL
    public Basis basisInCommonWith(BasisVector two) throws NonSenseException {
        for (Basis b1: this.basis) {
            for (Basis b2: two.basis) {
                if (b1.equals(b2)) return b1;
            }
        }
        throw new NonSenseException();
    }

    /*
    public Basis dijkstra(Basis goal,HashMap<Basis, Basis> came_from){
        HashMap<Basis, Integer> cost_so_far = new HashMap<Basis, Integer>();
        PriorityQueue<ValuedBasis> priority = new PriorityQueue<ValuedBasis>(new ValuedBasisComparator());
        Basis start = goal;
        Basis source = null;

        priority.add(new ValuedBasis(start, 0));
        came_from.put(start, start);
        cost_so_far.put(start, 0);

        while (!priority.isEmpty()) {
            source = priority.poll().basis;
            if (this.basis.contains(source)) {
                break;
            }
            for (Basis destination : source.neighbors()) {
                int new_cost = 1 + cost_so_far.get(source);
                if (!cost_so_far.containsKey(destination) || new_cost < cost_so_far.get(destination)) {
                    cost_so_far.put(destination, new_cost);
                    came_from.put(destination, source);
                    priority.offer(new ValuedBasis(destination, new_cost));
                }
            }
        }
        return source;
    }
     */

    //TESTS
    public boolean sameBasis(BasisVector v){
        for (Basis b1: this.basis) {
            for (Basis b2: v.basis) {
                if (b1.equals(b2)) return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasisVector that = (BasisVector) o;
        if (!name.equals(that.name)){
            return false;
        }
        for (Basis two:basis) {
            if(!that.basis.contains(two)){
                return false;
            }
        }
        return true;
    }


    //TOSTRING
    public String toString() {
        String r= name;
        for(Basis two:basis){
           r+=two.toString();
        }
        return r;
    }

}
