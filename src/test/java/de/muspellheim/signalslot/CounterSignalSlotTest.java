/*
 * Copyright (c) 2013-2015 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.signalslot;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Ported Qt simple example of signals and slots to Java.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public final class CounterSignalSlotTest {

    @Test
    public void testCounter() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        a.valueChanged().connect(b::setValue);

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
        a.valueChanged.connect(null);
    }

    @Test(expected = NullPointerException.class)
    public void testDisconnectNull() {
        final Counter a = new Counter();
        a.valueChanged.disconnect(null);
    }

    @Test
    public void testChainSignals() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        final Counter c = new Counter();
        a.valueChanged().connect(b::setValue);
        b.valueChanged().connect(c::setValue);

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());
        assertEquals(12, c.getValue());
    }

    @Test
    public void testChainSignals_Variant() {
        final Signal<String> signal1 = new Signal<>();
        final Signal<String> signal2 = new Signal<>();

        signal1.connect(signal2);
        signal2.connect(s -> assertTrue("Foo".equals(s)));

        signal1.emit("Foo");
    }


    @Test
    public void testSetSameValue() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        final Counter c = new Counter();

        a.valueChanged().connect(b::setValue);
        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());

        a.valueChanged().connect(c::setValue);
        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(0, c.getValue());
    }

    @Test(expected = AssertionError.class)
    public void testBehaviourIdentity() {
        Counter a = new Counter();
        Slot<Integer> s1 = a::setValue;
        Slot<Integer> s2 = a::setValue;

        assertSame(s1, s2);
    }


    @Test(expected = AssertionError.class)
    public void testBehaviourEquality() {
        Counter a = new Counter();
        Slot<Integer> s1 = a::setValue;
        Slot<Integer> s2 = a::setValue;

        assertEquals(s1, s2);
    }

    @Test
    public void testDisconnect() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        final Counter c = new Counter();
        a.valueChanged().connect(b::setValue);
        final Slot<Integer> slotSetValueOfC = c::setValue;
        a.valueChanged().connect(slotSetValueOfC);

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());
        assertEquals(12, c.getValue());

        // TODO Workaround: to disconnect a slot, we must remember the method reference
        a.valueChanged().disconnect(slotSetValueOfC);
        a.setValue(42);
        assertEquals(42, a.getValue());
        assertEquals(42, b.getValue());
        assertEquals(12, c.getValue());
    }

    @Test
    public void testDisconnect_Variant() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        final Counter c = new Counter();
        a.valueChanged().connect(b::setValue);
        a.valueChanged().connect(c.setValue());

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());
        assertEquals(12, c.getValue());

        // TODO Workaround: to disconnect a slot, we must have reference a slot instance
        a.valueChanged().disconnect(c.setValue());
        a.setValue(42);
        assertEquals(42, a.getValue());
        assertEquals(42, b.getValue());
        assertEquals(12, c.getValue());
    }

    @Test
    public void testDisconnectAll() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        final Counter c = new Counter();
        a.valueChanged().connect(b::setValue);
        a.valueChanged().connect(c::setValue);

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());
        assertEquals(12, c.getValue());

        a.valueChanged().disconnectAll();
        a.setValue(42);
        assertEquals(42, a.getValue());
        assertEquals(12, b.getValue());
        assertEquals(12, c.getValue());
    }

    @Test
    public void testBlockSignal() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        a.valueChanged().connect(b::setValue);

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());

        a.valueChanged().setBlocked(true);
        a.setValue(42);
        assertEquals(42, a.getValue());
        assertEquals(12, b.getValue());

        a.valueChanged().setBlocked(false);
        a.setValue(24);
        assertEquals(24, a.getValue());
        assertEquals(24, b.getValue());
    }

    /**
     * This class holds a integer value.
     */
    public static final class Counter {

        private final Signal<Integer> valueChanged = new Signal<>();
        private int value;
        private Slot<Integer> valueSlot = this::setValue;

        public int getValue() {
            return value;
        }

        public void setValue(final int value) {
            if (value != this.value) {
                this.value = value;
                valueChanged().emit(value);
            }
        }

        public Signal<Integer> valueChanged() {
            return valueChanged;
        }

        public Slot<Integer> setValue() {
            return valueSlot;
        }

    }

}
