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

/**
 * Ported Qt simple example of signals and slots to Java observer pattern.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public final class CounterSignalSlotTest {

    @Test
    public void testCounter() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        a.getValueChanged().connect(b.value());

        a.value().set(12);
        assertEquals(12, (int) a.value().get());
        assertEquals(12, (int) b.value().get());

        b.value().set(48);
        assertEquals(12, (int) a.value().get());
        assertEquals(48, (int) b.value().get());
    }

    /**
     * This class holds a integer value.
     */
    public static final class Counter {

        private Signal1<Integer> value = new Signal1<>(0);

        public Slot1<Integer> value() {
            return value;
        }

        Signal1<Integer> getValueChanged() {
            return value;
        }

    }

}
