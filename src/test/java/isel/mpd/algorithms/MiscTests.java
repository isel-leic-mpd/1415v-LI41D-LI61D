package isel.mpd.algorithms;

import org.junit.Test;

/**
 * Created by lfalcao on 18/03/2015.
 */
public class MiscTests {
    @Test
    public void testWithSwitchWithOne() throws Exception {
        makeSomething(1, "SLB");
    }

    @Test
    public void testWithSwitchWithTwo() throws Exception {
        makeSomething(2, "Benfica");
    }

    @Test
    public void testWithSwitchWithThree() throws Exception {
        makeSomething(3, "Glorioso");
    }

    private void makeSomethingOld(int i, Object o) {
        switch(i) {
            case 1: option1(o); break;
            case 2: option2(o); break;
            case 3: option3(o); break;
            default : defaultOption(o); break;
        }

    }

    private void makeSomething(int i, Object o) {
        MyConsumer[] consumers = { this::option1, this::option2, this::option3 };
        MyConsumer defaultConsumer = this::defaultOption;
        if(i > consumers.length) {
            defaultConsumer.accept(o);
            return;
        }

        consumers[i-1].accept(o);

    }

    private void defaultOption(Object o) {
        System.out.println("Invalid option");
    }

    private void option3(Object o) {
        System.out.println("Option3: " + o.getClass().getName());
    }

    private void option2(Object o) {
        System.out.println("Option2: " + o.hashCode());
    }

    private void option1(Object o) {
        System.out.println("Option1: " + o.toString());
    }


    @FunctionalInterface
    interface MyConsumer {
        void accept(Object o);
    }
}

