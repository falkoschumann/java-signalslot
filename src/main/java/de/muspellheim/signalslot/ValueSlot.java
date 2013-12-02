package de.muspellheim.signalslot;

/**
 * This Slot holds a value.
 *
 * @param <T> value type
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public class ValueSlot<T> extends Signal<T> {

    private T value;

    public ValueSlot() {
        this(null);
    }

    public ValueSlot(final T initialValue) {
        value = initialValue;
        connect(new Slot<T>() {

            @Override
            public void receive(T value) {
                ValueSlot.this.value = value;
            }

        });
    }

    public final T get() {
        return value;
    }

    public final void set(final T newValue) {
        final T oldValue = value;
        value = newValue;
        if ((oldValue != newValue) && (oldValue != null) && !oldValue.equals(newValue)) {
            emit(value);
        }
    }

}
