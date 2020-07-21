import java.util.Comparator;

public class ValuedBasisComparator implements Comparator<ValuedBasis> {
    public int compare(ValuedBasis one, ValuedBasis two) {
        return (one.value-two.value) ;
    }
}
