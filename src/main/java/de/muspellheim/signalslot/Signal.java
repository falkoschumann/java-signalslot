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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A signal act as source of data and can connect to any compatible slot.
 *
 * @param <T> value type
 * @author Falko Schumann &lt;www.muspellheim.de&gt;
 */
public class Signal<T> implements Slot<T> {

    private List<Slot<T>> slots = Collections.synchronizedList(new ArrayList<Slot<T>>());
    private boolean blocked;

    public final void connect(final Slot<T> slot) {
        if (slot == null) {
            throw new NullPointerException("slot");
        }
        slots.add(slot);
    }

    public final void disconnect(final Slot<T> slot) {
        if (slot == null) {
            throw new NullPointerException("slot");
        }
        slots.remove(slot);
    }

    public final void disconnectAll() {
        slots.clear();
    }

    public final void emit(final T value) {
        if (blocked) {
            return;
        }

        Slot<T>[] arrLocal;
        synchronized (this) {
            arrLocal = slots.toArray(new Slot[slots.size()]);
        }
        for (Slot<T> e : arrLocal) {
            e.receive(value);
        }
    }

    public final boolean isBlocked() {
        return blocked;
    }

    public final void setBlocked(final boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public final void receive(final T value) {
        emit(value);
    }

}
