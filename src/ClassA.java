import java.util.concurrent.TimeUnit;

/**
 * @author JosemiMoran
 * @version 1.0, 22/04/2021
 */
public class ClassA {

    static int counter = 10;
    private final int id;
    private ClassA next;

    /**
     * ClassA Constructor
     */
    public ClassA(int id) {
        this.id = id;
    }

    public ClassA getNext() {
        return next;
    }

    public void setNext(ClassA next) {
        this.next = next;
    }
    public int getId() {
        return id;
    }

    /**
     * @return false if counter is 0 the player can't play this turn
     * else return true
     */
    public synchronized boolean EnterAndPlay() throws InterruptedException {
        boolean toRet = false;
        if (!HaveFinished()) {
            counter--;
            toRet = true;
        }
        synchronized (this.getNext()) {
            this.getNext().notify();
        }
        if (!HaveFinished()) wait();
        return toRet;
    }

    /**
     * @return true if counter is 0
     * else return false
     */
    public synchronized boolean HaveFinished() {
        return counter == 0;
    }
}
