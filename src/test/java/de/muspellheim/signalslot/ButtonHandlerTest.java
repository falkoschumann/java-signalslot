/*
 * Copyright (c) 2013-2015 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.signalslot;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A Button emit a clicked signal and the handler receive it.
 *
 * @author Falko Schumann &lt;falko.schumann@muspellheim.de&gt;
 */
public final class ButtonHandlerTest {

    @Test
    public void testClickHandled() {
        final Button b = new Button();
        final Handler h = new Handler();
        b.clicked().connect(h::handle);

        b.clicked().emit();

        assertTrue(h.isHandled());
    }

    @Test(expected = NullPointerException.class)
    public void testConnectNull() {
        final Button b = new Button();
        b.clicked().connect(null);
    }

    @Test(expected = NullPointerException.class)
    public void testDisconnectNull() {
        final Button b = new Button();
        b.clicked().disconnect(null);
    }

    @Test
    public void testChainSignals() {
        final Signal0 s1 = new Signal0();
        final Signal0 s2 = new Signal0();
        final Handler h = new Handler();
        s1.connect(s2);
        s2.connect(h::handle);

        s1.emit();

        assertTrue(h.isHandled());
    }

    @Test(expected = AssertionError.class)
    public void testBehaviourIdentity() {
        final Button b = new Button();
        final Slot0 s1 = b::clicked;
        final Slot0 s2 = b::clicked;

        assertSame(s1, s2);
    }


    @Test(expected = AssertionError.class)
    public void testBehaviourEquality() {
        final Button b = new Button();
        final Slot0 s1 = b::clicked;
        final Slot0 s2 = b::clicked;

        assertEquals(s1, s2);
    }

    @Test
    public void testDisconnect() {
        final Button b = new Button();
        final Handler h = new Handler();
        final Slot0 slot = h::handle;
        b.clicked().connect(slot);

        b.clicked().emit();

        assertTrue(h.isHandled());

        // TODO Workaround: to disconnect a slot, we must remember the method reference
        h.reset();
        b.clicked().disconnect(slot);
        b.clicked().emit();

        assertFalse(h.isHandled());
    }

    @Test
    public void testDisconnect_Variant() {
        final Button b = new Button();
        final Handler h = new Handler();
        b.clicked().connect(h.getHandle());

        b.clicked().emit();

        assertTrue(h.isHandled());

        // TODO Workaround: to disconnect a slot, we must have reference a slot instance
        h.reset();
        b.clicked().disconnect(h.getHandle());
        b.clicked().emit();

        assertFalse(h.isHandled());
    }

    @Test
    public void testBlockSignal() {
        final Button b = new Button();
        final Handler h = new Handler();
        b.clicked().connect(h::handle);

        b.clicked().emit();

        assertTrue(h.isHandled());

        h.reset();
        b.clicked().setBlocked(true);
        b.clicked().emit();

        assertFalse(h.isHandled());

        h.reset();
        b.clicked().setBlocked(false);
        b.clicked().emit();

        assertTrue(h.isHandled());
    }

    /**
     * This class represents a button.
     */
    private static class Button {

        private final Signal0 clicked = new Signal0();

        public Signal0 clicked() {
            return clicked;
        }

    }

    /**
     * This class represents the click handler.
     */
    private static class Handler {

        private boolean handled;
        private Slot0 handle = this::handle;

        public void handle() {
            handled = true;
        }

        public boolean isHandled() {
            return handled;
        }

        public void reset() {
            handled = false;
        }

        public Slot0 getHandle() {
            return handle;
        }

    }

}
