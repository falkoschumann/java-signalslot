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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Acceptance test for signals and slots without arguments.
 *
 * @author Falko Schumann <www.muspellheim.de>
 */
public final class SignalSlot0Test {

    @Test
    public void testButtonClicked_Connected_ReceiveEvent() throws Exception {
        final Button button = new Button();
        final Action action = new Action();

        button.clicked().connect(action.doSomeThing());
        button.push();

        assertTrue(action.isTriggered());
    }

    @Test
    public void testButtonClicked_Disconnected_DoNotReceiveEvent() throws Exception {
        final Button button = new Button();
        final Action action = new Action();

        button.clicked().connect(action.doSomeThing());
        button.clicked().disconnect(action.doSomeThing());
        button.push();

        assertFalse(action.isTriggered());
    }

    @Test
    public void testCheckButtonClicked_Connected_ReceiveEvent() throws Exception {
        final CheckButton button = new CheckButton();
        final Action action = new Action();

        button.selected().connect(new AdapterSlot<Boolean>(action.doSomeThing()));
        button.selected().set(true);

        assertTrue(action.isTriggered());
    }

    @Test
    public void testCheckButtonClicked_Disconnected_DoNotReceiveEvent() throws Exception {
        final CheckButton button = new CheckButton();
        final Action action = new Action();

        final Slot1<Boolean> adapter = new AdapterSlot<>(action.doSomeThing());
        button.selected().connect(adapter);
        button.selected().disconnect(adapter);
        button.selected().set(true);

        assertFalse(action.isTriggered());
    }

}
