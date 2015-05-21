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
 * Ported Qt simple example of signals and slots to Java observer pattern variant 2. This variant
 * use separate member for source and target value.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public final class CounterObserverSourceAndTargetLearningTest {

    @Test
    public void testCounter() {
        final Counter a = new Counter();
        final Counter b = new Counter();
        a.source().addObserver(b.target());

        a.setValue(12);
        assertEquals(12, a.getValue());
        assertEquals(12, b.getValue());

        b.setValue(48);
        assertEquals(12, a.getValue());
        assertEquals(48, b.getValue());
    }

    @Test(expected = ClassCastException.class)
    public void testTypeSafety() {
        final Counter intValue = new Counter();
        final Name stringValue = new Name();

        stringValue.source().addObserver(intValue.target());
        stringValue.setValue("Foo");
    }

    /**
     * This class holds a integer value.
     */
    public static final class Counter {

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

        public void setValue(final int value) {
            if (this.value != value) {
                this.value = value;
                source.setChanged();
                source.notifyObservers(value);
            }
        }

        /**
         * This observable act as source of notifications.
         */
        private final class SourceObservable extends Observable {

            public void setChanged() {
                // make protected method public
                super.setChanged();
            }

        }

        /**
         * This observer act as target of notifications.
         */
        private final class TargetObserver implements Observer {

            @Override
            public void update(final Observable o, final Object arg) {
                setValue((Integer) arg);
            }

        }

    }

    /**
     * This class holds a string value.
     */
    public static final class Name {

        private SourceObservable source = new SourceObservable();
        private Observer target = new TargetObserver();
        private String value;

        public Observable source() {
            return source;
        }

        public Observer target() {
            return target;
        }

        public String getValue() {
            return value;
        }

        public void setValue(final String value) {
            if (this.value != value) {
                this.value = value;
                source.setChanged();
                source.notifyObservers(value);
            }
        }

        /**
         * This observable act as source of notifications.
         */
        private final class SourceObservable extends Observable {

            public void setChanged() {
                // make protected method public
                super.setChanged();
            }

        }

        /**
         * This observer act as target of notifications.
         */
        private final class TargetObserver implements Observer {

            @Override
            public void update(final Observable o, final Object arg) {
                setValue((String) arg);
            }

        }

    }

}
