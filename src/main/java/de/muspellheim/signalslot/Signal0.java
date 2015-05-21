/*
 * Copyright (c) 2013-2015 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.signalslot;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A signal act as source of an event and can connect to a slot.
 *
 * @author Falko Schumann &lt;www.muspellheim.de&gt;
 */
public class Signal0 implements Slot0 {

    private List<Slot0> receivers = new CopyOnWriteArrayList<>();
    private boolean blocked;

    public final void connect(final Slot0 receiver) {
        Objects.requireNonNull(receiver, "receiver");
        receivers.add(receiver);
    }

    public final void disconnect(final Slot0 receiver) {
        Objects.requireNonNull(receiver, "receiver");
        receivers.remove(receiver);
    }

    public final void emit() {
        if (isBlocked()) {
            return;
        }

        for (Slot0 e : receivers) {
            e.receive();
        }
    }

    public final boolean isBlocked() {
        return blocked;
    }

    public final void setBlocked(final boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public final void receive() {
        emit();
    }

}
