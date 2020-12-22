import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

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
    public Basis(String name, Basis predecessor, String axisInCommon, String angle){
        this();


            this.name = name;
            this.predecessor= predecessor;
            predecessor.successors.add(this);
            this.angle=new Variable(angle);


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

    //Omega
    public Vector omega(Basis reference) throws NonSenseException {
        HashMap<Basis,Basis> cameFrom = reference.dijkstra(this);

        Basis current = this;
        Basis tmpRef = cameFrom.get(this);
        Vector omega = new Vector();
        while(!current.equals(reference)){
            omega= (Vector) omega.plus(new Vector(new SimpleVector(new Scalar(new Term(current.angle)).differentiate(),current.axisInCommonWith(tmpRef))));
            current = tmpRef;
            tmpRef = cameFrom.get(current);
        }
        return omega;
    }

    //DIJKSTRA
    public HashMap<Basis, Basis> dijkstra(Basis stop){
        HashMap<Basis, Basis> came_from = new HashMap<Basis, Basis>();
        HashMap<Basis, Integer> cost_so_far = new HashMap<Basis, Integer>();
        PriorityQueue<ValuedBasis> priority = new PriorityQueue<ValuedBasis>(new ValuedBasisComparator());
        Basis start = this;
        Basis source = null;

        priority.add(new ValuedBasis(start, 0));
        came_from.put(start, start);
        cost_so_far.put(start, 0);

        while (!priority.isEmpty()) {
            source = priority.poll().basis;
            if (source.equals(stop)) {
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
        return came_from;
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

    public BasisVector axisInCommonWith(Basis otherBasis) throws NonSenseException {
        if(this.x.equals(otherBasis.x)){
            return this.x;
        }else if(this.y.equals(otherBasis.y)){
            return this.y;
        }else if(this.z.equals(otherBasis.z)){
            return this.z;
        }else{
            throw new NonSenseException();
        }
    }

    public void drawFigure(){
        try {
            BufferedImage image = ImageIO.read(new File("./images/blank_figure.png"));
            Graphics graphics = image.getGraphics();
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial Black", Font.BOLD, 50));
            graphics.drawString(angle.toString(), 190, 400);
            graphics.drawString(angle.toString(), 780, 720);

            String common = null,predRight = null,thisRight = null,predUp = null,thisUp = null;

            if(this.z.equals(predecessor.z)){
                common=z.toString();
                predRight=predecessor.x.toString();
                thisRight=x.toString();
                predUp=predecessor.y.toString();
                thisUp=y.toString();
            }else if(this.y.equals(predecessor.y)){
                common=y.toString();
                predRight=predecessor.z.toString();
                thisRight=z.toString();
                predUp=predecessor.x.toString();
                thisUp=x.toString();
            }else{
                common=x.toString();
                predRight=predecessor.y.toString();
                thisRight=y.toString();
                predUp=predecessor.z.toString();
                thisUp=z.toString();
            }
            graphics.drawString(common, 300, 800);
            graphics.drawString(predRight, 1170, 800);
            graphics.drawString(thisRight, 1150, 560);
            graphics.drawString(predUp, 300, 40);
            graphics.drawString(thisUp, 70, 90);

            ImageIO.write(image, "png", new File("./figures/figure_"+name+".png"));
        }catch(Exception e){UI.print(e.toString());}
    }

/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basis basis = (Basis) o;
        return Objects.equals(this.name, basis.name);
 }
*/
    //TOSTRING
    public String toString() {
        return name;
    }
}
