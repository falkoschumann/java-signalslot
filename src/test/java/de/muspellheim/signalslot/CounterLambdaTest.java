/*
 * Copyright (c) 2013-2015 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.signalslot;

import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.IntConsumer;

import static org.junit.Assert.*;

/**
 * Ported Qt simple example of signals and slots to Java 8 with Lambdas.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public final class CounterLambdaTest {

    @Test
    public void testCounter() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        a.addConsumer(b::setValue);

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());

        b.setValue(48);
        assertEquals(12, a.getValue());
        assertEquals(48, b.getValue());
    }

    @Test(expected = NullPointerException.class)
    public void testConnectNull() {
        final Counter a = new Counter();
        a.addConsumer(null);
    }

    @Test(expected = NullPointerException.class)
    public void testDisconnectNull() {
        final Counter a = new Counter();
        a.removeConsumer(null);
    }

    @Test
    public void testChainSignals() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        final Counter c = new Counter();
        a.addConsumer(b::setValue);
        b.addConsumer(c::setValue);

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());
        assertEquals(12, c.getValue());
    }

    @Test
    public void testSetSameValue_SecondTryEmitNoSignal() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        final Counter c = new Counter();

        a.addConsumer(b::setValue);
        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());

        a.addConsumer(c::setValue);
        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(0, c.getValue());
    }

    @Test
    public void testBehaviourIdentity() {
        final Counter a = new Counter();
        final IntConsumer c1 = a::setValue;
        final IntConsumer c2 = a::setValue;

        assertNotSame(c1, c2);
    }


    @Test
    public void testBehaviourEquality() {
        final Counter a = new Counter();
        final IntConsumer c1 = a::setValue;
        final IntConsumer c2 = a::setValue;

        assertNotEquals(c1, c2);
    }

    @Test
    public void testRemoveConsumer_DontWorkWithMethodReference() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        a.addConsumer(b::setValue);

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());

        a.removeConsumer(b::setValue);
        a.setValue(42);
        assertEquals(42, a.getValue());
        assertNotEquals(12, b.getValue());
    }

    @Test
    public void testRemoveConsumer_Workaround() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        final IntConsumer consumer = b::setValue;
        a.addConsumer(consumer);

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());

        // TODO Workaround: to remove a consumer, we must remember the method reference
        a.removeConsumer(consumer);
        a.setValue(42);
        assertEquals(42, a.getValue());
        assertEquals(12, b.getValue());
    }

    /**
     * This class holds a integer value.
     */
    public static final class Counter {

        private List<IntConsumer> consumers = new CopyOnWriteArrayList<>();
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(final int value) {
            if (value != this.value) {
                this.value = value;
                valueChanged(value);
            }
        }

        public void valueChanged(final int newValue) {
            consumers.forEach(c -> c.accept(newValue));
        }

        public void addConsumer(final IntConsumer consumer) {
            Objects.requireNonNull(consumer, "consumer");
            consumers.add(consumer);
        }

        public void removeConsumer(final IntConsumer consumer) {
            Objects.requireNonNull(consumer, "consumer");
            consumers.remove(consumer);
        }

    }

}
