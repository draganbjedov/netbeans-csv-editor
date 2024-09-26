package draganbjedov.netbeans.csv.view.ccp;

/*
 * Pair.java
 *
 * Created on Feb 15, 2014, 8:18:30 PM
 *
 * @author Dragan Bjedov
 */
public class Pair<First, Second> {

    private final First first;
    private final Second second;

    public Pair(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    public First first() {
        return first;
    }

    public Second second() {
        return second;
    }
}
