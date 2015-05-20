/*
 * Copyright (c) 2013-2015 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
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
        if (isBlocked()) {
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
