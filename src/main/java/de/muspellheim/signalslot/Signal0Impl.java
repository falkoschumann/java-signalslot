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
import java.util.List;

/**
 * Default implementation of {@link Signal0}.
 *
 * @author Falko Schumann <www.muspellheim.de>
 */
public class Signal0Impl implements Signal0 {

    private final List<Slot0> receivers = new ArrayList<Slot0>();

    @Override
    public void connect(final Slot0 slot) {
        synchronized (receivers) {
            receivers.add(slot);
        }
    }

    @Override
    public void disconnect(final Slot0 slot) {
        synchronized (receivers) {
            receivers.remove(slot);
        }
    }

    @Override
    public void emit() {
        synchronized (receivers) {
            for (final Slot0 slot : receivers) {
                slot.receive();
            }
        }
    }

    @Override
    public void receive() {
        emit();
    }

}
