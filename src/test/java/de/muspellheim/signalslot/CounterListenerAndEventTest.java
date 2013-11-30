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
