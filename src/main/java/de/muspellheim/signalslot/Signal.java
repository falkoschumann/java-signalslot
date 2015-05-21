/*
 * Copyright (c) 2013-2015 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.signalslot;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A signal act as source of data and can connect to any compatible slot.
 *
 * @param <T> value type
 * @author Falko Schumann &lt;www.muspellheim.de&gt;
 */
public class Signal<T> implements Slot<T> {

    private List<Slot<T>> receivers = new CopyOnWriteArrayList<>();
    private boolean blocked;

    public final void connect(final Slot<T> receiver) {
        Objects.requireNonNull(receiver, "receiver");
        receivers.add(receiver);
    }

    public final void disconnect(final Slot<T> receiver) {
        Objects.requireNonNull(receiver, "receiver");
        receivers.remove(receiver);
    }

    public final void disconnectAll() {
        receivers.clear();
    }

    public final void emit(final T value) {
        if (isBlocked()) {
            return;
        }

        for (Slot<T> e : receivers) {
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
