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
 *   - Neither the name of the <ORGANIZATION> nor the names of its contributors
 *     may be used to endorse or promote products derived from this software
 *     without specific prior written permission.
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

import static org.junit.Assert.*;

import org.junit.*;

/**
 * Akzeptanztest für die API der Signale und Slots.
 * 
 * @author Falko Schumann <www.muspellheim.de>
 */
public class SignalSlotTest {

	@Test
	public void testServeCoffee_Connected_FillCup() {
		final Pot pot = new Pot();
		final Cup cup = new Cup();
		final Coffee coffee = new Coffee();

		pot.pour().connect(cup.infuse());
		pot.pour().emit(coffee);

		assertSame(coffee, cup.getContent());
	}

	@Test
	public void testServeTea_Disconnected_EmptyCup() {
		final Pot pot = new Pot();
		final Cup cup = new Cup();
		final Tea tea = new Tea();

		pot.pour().connect(cup.infuse());
		pot.pour().disconnect(cup.infuse());
		pot.pour().emit(tea);

		assertNull(cup.getContent());
	}

	@Test
	public void testServeTea_Blocked_EmptyCup() {
		final Pot pot = new Pot();
		final Cup cup = new Cup();
		final Tea tea = new Tea();

		pot.pour().connect(cup.infuse());
		pot.pour().blockSignals(true);
		pot.pour().emit(tea);

		assertNull(cup.getContent());
	}

}
