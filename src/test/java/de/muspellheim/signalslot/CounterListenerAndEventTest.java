/*
 * Copyright (c) 2013-2015 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.signalslot;

import org.junit.Test;

import javax.swing.event.EventListenerList;
import java.util.EventListener;
import java.util.EventObject;

import static org.junit.Assert.assertEquals;

/**
 * Ported Qt simple example of signals and slots to Java listener pattern with event object.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public final class CounterListenerAndEventTest {

    @Test
    public void testCounter() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        a.addValueListener(b);

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
    public static final class Counter implements ValueListener {

        private final EventListenerList listeners = new EventListenerList();
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(final int value) {
            if (this.value != value) {
                this.value = value;
                final ValueEvent e = new ValueEvent(this, value);
                for (ValueListener l : listeners.getListeners(ValueListener.class)) {
                    l.valueChanged(e);
                }
            }
        }

        public void addValueListener(final ValueListener l) {
            listeners.add(ValueListener.class, l);
        }

        public void removeValueListener(final ValueListener l) {
            listeners.remove(ValueListener.class, l);
        }

        public void valueChanged(final ValueEvent e) {
            this.value = e.getValue();
        }

    }

    /**
     * Listener interface for notifying a changed integer value.
     */
    public interface ValueListener extends EventListener {

        void valueChanged(ValueEvent e);

    }

    /**
     * This Event holds a integer value.
     */
    public static final class ValueEvent extends EventObject {

        private int value;

        public ValueEvent(final Object source, final int value) {
            super(source);
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

}
