/*
 * Copyright (c) 2013, Falko Schumann <www.muspellheim.de>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *   - Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package de.muspellheim.signalslot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public void testConnectNull_NullPointerException() {
        final Counter a = new Counter();
        a.valueChanged.connect(null); // must throw exception
    }

    @Test(expected = NullPointerException.class)
    public void testDisconnectNull_NullPointerException() {
        final Counter a = new Counter();
        a.valueChanged.disconnect(null); // must throw exception
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
        Signal<String> signal1 = new Signal<>();
        Signal<String> signal2 = new Signal<>();

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
