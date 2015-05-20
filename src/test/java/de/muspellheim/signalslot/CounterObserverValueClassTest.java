/*
 * Copyright (c) 2013-2015 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.signalslot;

import org.junit.Test;

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertEquals;

/**
 * Ported Qt simple example of signals and slots to Java observer pattern. This variant
 * use a special class for holding the value and observer logic.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public final class CounterObserverValueClassTest {

    @Test
    public void testCounter() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        a.value.addObserver(b.value);

        a.value().set(12);
        assertEquals(12, (int) a.value().get());
        assertEquals(12, (int) b.value().get());

        b.value().set(48);
        assertEquals(12, (int) a.value().get());
        assertEquals(48, (int) b.value().get());
    }

    @Test(expected = ClassCastException.class)
    public void testTypeSafety() {
        final Value<String> stringValue = new Value<>();
        final Value<Integer> integerValue = new Value<>();

        stringValue.addObserver(integerValue);
        stringValue.set("Foo");
        final Integer value = integerValue.get();
    }

    /**
     * This class holds a integer value.
     */
    public static final class Counter {

        private Value<Integer> value = new Value<>();

        public Value<Integer> value() {
            return value;
        }

    }

    /**
     * This class holds a generic value.
     *
     * @param <T> type of value
     */
    public static final class Value<T> extends Observable implements Observer {

        private T value;

        public T get() {
            return value;
        }

        public void set(final T newValue) {
            if ((value == newValue) || (value != null && value.equals(newValue))) {
                return;
            }

            value = newValue;
            setChanged();
            notifyObservers(value);
        }

        @Override
        public void update(final Observable o, final Object arg) {
            value = (T) arg;
        }

    }

}
