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

import java.util.Vector;

/**
 * A signal with one argument.
 * <p/>
 * <p>This signal act as source of data and can connect to any compatible slot.</p>
 *
 * @author Falko Schumann <www.muspellheim.de>
 */
public class Signal1<T> extends Slot1<T> {

    private Vector<Slot1<T>> slots = new Vector<Slot1<T>>();

    public Signal1() {
        this(null);
    }

    public Signal1(T value) {
        super(value);
    }

    public void emit(T value) {
        Slot1[] arrLocal;
        synchronized (this) {
            arrLocal = slots.toArray(new Slot1[0]);
        }
        for (Slot1 e : arrLocal) {
            e.set(value);
        }
    }

    public void connect(Slot1<T> slot) {
        if (slot == null) throw new NullPointerException("slot");
        slots.add(slot);
    }

    public void disconnect(Slot1<T> slot) {
        if (slot == null) throw new NullPointerException("slot");
        slots.remove(slot);
    }

    public void set(T value) {
        super.set(value);
        emit(value);
    }

}
