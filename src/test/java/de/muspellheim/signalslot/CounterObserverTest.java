/*
 * Copyright (c) 2013-2015 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.signalslot;

import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertEquals;

/**
 * Ported Qt simple example of signals and slots to Java observer pattern.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public final class CounterObserverTest {

    @Test
    public void testCounter() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        a.addObserver(b);

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());

        b.setValue(48);
        assertEquals(12, a.getValue());
        assertEquals(48, b.getValue());
    }

    /**
     * This class holds a integer value.
     */
    public static final class Counter extends Observable implements Observer {

        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(final int value) {
            if (this.value != value) {
                this.value = value;
                setChanged();
                notifyObservers(value);
            }
        }

        @Override
        public void update(final Observable o, final Object arg) {
            value = (Integer) arg;
        }
    }

}
