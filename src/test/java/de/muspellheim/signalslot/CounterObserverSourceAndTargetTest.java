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

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertEquals;

/**
 * Ported Qt simple example of signals and slots to Java observer pattern variant 2. This variant
 * use separate member for source and target value.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public class CounterObserverSourceAndTargetTest {

    @Test
    public void testCounter() {
        Counter a = new Counter();
        Counter b = new Counter();
        a.source().addObserver(b.target());

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());

        b.setValue(48);
        assertEquals(12, a.getValue());
        assertEquals(48, b.getValue());
    }

    public static class Counter {

        private SourceObservable source = new SourceObservable();
        private Observer target = new TargetObserver();
        private int value;

        public Observable source() {
            return source;
        }

        public Observer target() {
            return target;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            if (this.value != value) {
                this.value = value;
                source.setChanged();
                source.notifyObservers(value);
            }
        }

        private class SourceObservable extends Observable {

            public void setChanged() {
                // make protected method public
                super.setChanged();
            }

        }

        private class TargetObserver implements Observer {

            @Override
            public void update(Observable o, Object arg) {
                setValue((Integer) arg);
            }

        }

    }

}
