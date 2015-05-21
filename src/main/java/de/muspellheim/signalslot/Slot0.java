/*
 * Copyright (c) 2013-2015 Falko Schumann <www.muspellheim.de>
 * Released under the terms of the MIT License.
 */

package de.muspellheim.signalslot;

/**
 * A slot act as receiver of a signal.
 *
 * @author Falko Schumann &lt;www.muspellheim.de&gt;
 */
@FunctionalInterface
public interface Slot0 {

    void receive();

}
