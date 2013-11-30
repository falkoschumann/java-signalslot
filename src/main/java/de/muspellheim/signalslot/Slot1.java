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

/**
 * A slot with one argument.
 * <p/>
 * <p>This slot act as receiver of data.</p>
 *
 * @param <T> value type
 * @author Falko Schumann <www.muspellheim.de>
 */
public class Slot1<T> {

    private T value;

    public Slot1() {
        this(null);
    }

    public Slot1(final T value) {
        this.value = value;
    }

    public final T get() {
        return value;
    }

    public final void set(final T newValue) {
        final T oldValue = this.value;
        this.value = newValue;
        valueUpdated();
        if ((oldValue != newValue) && !(oldValue != null && oldValue.equals(newValue))) {
            valueChanged();
        }
    }

    /**
     * Called every time the value is set.
     */
    protected void valueUpdated() {
        // do nothing
    }

    /**
     * Called only if the value changed by setting.
     */
    protected void valueChanged() {
        // do nothing
    }

}
