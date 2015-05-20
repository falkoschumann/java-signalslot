Signal and Slot for Java [![Build Status](https://travis-ci.org/falkoschumann/signalslot4java.svg?branch=develop)](https://travis-ci.org/falkoschumann/signalslot4java)
========================

Introduce signals and slots for Java. Signals and slots provide a mechanism to
loose coupling of classes for notification. The classes are connected without to
know about each other. The connection is typed and do not need any interface
implementation.


Analysis of the current Patterns
--------------------------------

Java knows two basic patterns for notification: Observer and Listener. Both
patterns transfer a value from an object to another without strong coupling
between this two objects.

The unit test `CounterObserverTest` shows the usage of observer pattern.

The unit test `CounterListenerTest` shows the usage of listener pattern without
event object. The value is passed directly by the listener. The unit test
`CounterListenerAndEventTest` shows the the usage of listener pattern with event
object. The value is passed wrapped by event object.

The observer pattern is more general than the listener pattern, because there
is no need to implement a special interface. Both patterns force the receiver of
notification to implement an interface (`Observer`, `EventListener`). The
observer pattern force the sender of notification to extends a class
(`Observable`).


Diversify the Observer Pattern
------------------------------

To avoid extend `Observable` or implement `Observer` using observer patter, you
can introduce two member getting the observable as sender and the observer as
target of notification. The unit test `CounterObserverSourceAndTargetTest`show
this approach.

An other approach introduce a new value class holding the transferable value.
The value class extends `Observable` and implements `Observer`. The unit test
`CounterObserverValueClassTest` show this.

Both approaches are not type safe. The `addObserver` method do not check the
value type, producing a `ClassCastException` while notifying or getting a value.
The source and target approach needs additional internal classes.


Introduce Signals and Slots
---------------------------

The observer and listener pattern can implement in any object oriented language.
The signal and slot pattern of Qt offers a third pattern for C++. In C++ you can
connect methods each other. One method acts as sender of the notification, the
signal, and an other method act as receiver, the slot.

Because in Java you can not connect methods each other, this library use objects
instead of methods to create signals and slots. There are two pairs of signal
and slot, with and without an argument. The signal and slot without an argument
can use for notification without transfer a value.

The unit test `CounterSignalSlot` shows the usage of the signal and slot
pattern. This pattern usage is shorter than listener and observer. It allows a
looser coupling, because no interfaces must be implemented or classes must be
extended.
