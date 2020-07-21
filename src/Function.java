import java.io.Serializable;

public abstract class Function implements Serializable {

    //ATTRIBUTES
    Variable variable;

    //CONSTRUCTORS
    public Function(Variable variable) {
        this.variable = variable;
    }
}
