Signal and Slot for Java [![Build Status](https://travis-ci.org/falkoschumann/signalslot4java.png)](https://travis-ci.org/falkoschumann/signalslot4java)
========================

Introduce signals and slots for Java.

Java knows two basic patterns for notification: Observer and Listener. Both
patterns transfer a value from an object to another without strong coupling
between this two objects.

The unit test `CounterObserverTest` show the usage of observer pattern.

The unit test `CounterListenerTest`show the usage of listener pattern without
event object. The value is passed directly by the listener. The unit test
`CounterListenerAndEventTest`show the the usage of listener pattern with event
object. The value is passed wrapped in the event object.

The observer pattern is more general than the listener pattern, because there
is no need to implement special interfaces. Both patterns force the receiver of
notification to implement an interface (`Observer`, `EventListener`). The
observer pattern force the sender of notification to extends a class
(`Observable`).

The observer and listener pattern can implement in any object oriented language.
The signal and slot pattern of Qt offers a third pattern for C++. In C++ you can
connect to methods. One method acts as signal or sender of the notification and
an other method act as slot or receiver.

Because in Java you can not connect methods each other, this library use objects
instead of method to create signals and slots. There are two pairs of signal and
slot, with and without an argument. The signal and slot without an argument can
use for notification without transfer a value.

The unit test `CounterSignalSlot`show the usage of the signal and slot pattern.
This pattern is shorter than the other. It allows a looser coupling, because no
interfaces must be implemented or classes must be extended.
