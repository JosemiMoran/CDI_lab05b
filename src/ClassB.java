import java.util.Dictionary;

/**
 * @author JosemiMoran
 * @version 1.0, 22/04/21
 */
public class ClassB implements Runnable {


    private ClassA classA;
    private int id;
    private int counter;

    /**
     * ClassB constructor
     *
     * @param id     Thread's id
     * @param classA ClassA Object
     */
    public ClassB(ClassA classA, int id) {
        this.classA = classA;
        this.id = id;
        counter = 0;
    }

    //ClassB constructor
    public ClassB() {
    }

    public ClassA getClassA() {
        return classA;
    }


    @Override
    public void run() {
        try {
            synchronized (classA) {
                classA.wait();
            }
            while (classA.EnterAndPlay()) {
                //System.out.println("Class A id: " + classA.getId() + " waiting");
                System.out.println("The player " + id + " has made its turn!");
                counter++;
            }
        } catch (Exception e) {
            System.out.println("Error on EnterAndPlay!");
        } finally {
            MyProblem.statistic.put(id, counter);
        }
    }
}
