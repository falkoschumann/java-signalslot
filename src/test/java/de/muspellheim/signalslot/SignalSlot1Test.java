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

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Acceptance test for signals and slots with one argument.
 *
 * @author Falko Schumann <www.muspellheim.de>
 */
public class SignalSlot1Test {

    @Test
    public void testServeTea_Connected_FillOneCup() {
        final Pot pot = new Pot();
        final Cup cup = new Cup();
        final Tea tea = new Tea();

        pot.pour().connect(cup.content());
        pot.pour().emit(tea);

        assertSame(tea, cup.content().get());
    }

    @Test
    public void testServeTea_Connected_FillTwoCups() {
        final Pot pot = new Pot();
        final Cup cup1 = new Cup();
        final Cup cup2 = new Cup();
        final Tea tea = new Tea();

        pot.pour().connect(cup1.content());
        pot.pour().connect(cup2.content());
        pot.pour().emit(tea);

        assertSame(tea, cup1.content().get());
        assertSame(tea, cup2.content().get());
    }

    @Test
    public void testServeTea_Disconnected_EmptyCup() {
        final Pot pot = new Pot();
        final Cup cup = new Cup();
        final Tea tea = new Tea();

        pot.pour().connect(cup.content());
        pot.pour().disconnect(cup.content());
        pot.pour().emit(tea);

        assertNull(cup.content().get());
    }

    @Test
    public void testCrossConnect_Connected_ReceiveEvent() throws Exception {
        final Pot pot = new Pot();
        final Tea tea = new Tea();
        final Action action = new Action();

        pot.pour().connect(new AdapterSlot<Tea>(action.doSomeThing()));
        pot.pour().emit(tea);

        assertTrue(action.isTriggered());
    }

    @Test
    public void testCrossConnect_Disconnected_DoNotReceiveEvent() throws Exception {
        final Pot pot = new Pot();
        final Tea tea = new Tea();
        final Action action = new Action();

        Slot1<Tea> teaTime = new AdapterSlot<Tea>(action.doSomeThing());
        pot.pour().connect(teaTime);
        pot.pour().disconnect(teaTime);
        pot.pour().emit(tea);

        assertFalse(action.isTriggered());
    }

    @Test
    public void testChainSignal0_Connected_ReceiveEvent() throws Exception {
        final Button button1 = new Button();
        final Button button2 = new Button();
        final Action action = new Action();

        button1.clicked().connect(button2.clicked());
        button2.clicked().connect(action.doSomeThing());
        button1.push();

        assertTrue(action.isTriggered());
    }

    @Test
    public void testChainSignal1_Connected_ReceiveData() throws Exception {
        final Pot pot1 = new Pot();
        final Pot pot2 = new Pot();
        final Cup cup = new Cup();
        final Tea tea = new Tea();

        pot1.pour().connect(pot2.pour());
        pot2.pour().connect(cup.content());
        pot1.pour().emit(tea);

        assertSame(tea, cup.content().get());
    }

    @Test
    public void testValueIsUpdated() {
        Slot1Stub<String> slot = new Slot1Stub<String>();
        slot.set("Foo");
        assertTrue(slot.valueIsUpdated);
    }

    @Test
    public void testValueIsChanged() {
        Slot1Stub<String> slot = new Slot1Stub<String>();
        slot.set("Foo");
        assertTrue(slot.valueIsChanged);
        slot.reset();
        slot.set("Bar");
        assertTrue(slot.valueIsChanged);
    }

    @Test
    public void testValueIsNotChanged() {
        Slot1Stub<String> slot = new Slot1Stub<String>();
        slot.set("Foo");
        assertTrue(slot.valueIsChanged);
        slot.reset();
        slot.set("Foo");
        assertFalse(slot.valueIsChanged);
    }

    private static class Slot1Stub<T> extends Slot1<T> {

        private boolean valueIsUpdated;
        private boolean valueIsChanged;

        @Override
        protected void valueUpdated() {
            valueIsUpdated = true;
        }

        @Override
        protected void valueChanged() {
            valueIsChanged = true;
        }

        void reset() {
            valueIsUpdated = false;
            valueIsChanged = false;
        }

    }

}
