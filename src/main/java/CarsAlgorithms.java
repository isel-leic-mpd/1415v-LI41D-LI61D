import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by lfalcao on 04/03/2015.
 */
public class CarsAlgorithms {
    public <T> Iterable<T> filter(Iterable<T> seq, Predicate<T> pred) {
        ArrayList<T> filteredSeq = new ArrayList<>();
        for (T t : seq) {
            if (pred.test(t)) {
                filteredSeq.add(t);
            }
        }
        return filteredSeq;
    }

    public <T> Iterable<T> filterOptimized(Iterable<T> seq, Predicate<T> pred) {
        List<T> filteredSeq;

        if(seq instanceof Collection) {
            filteredSeq = new ArrayList<>( ((Collection<T>) seq).size());
        } else {
            filteredSeq = new LinkedList<>();
        }

        for (T t : seq) {
            if (pred.test(t)) {
                filteredSeq.add(t);
            }
        }
        return filteredSeq;
    }
}

