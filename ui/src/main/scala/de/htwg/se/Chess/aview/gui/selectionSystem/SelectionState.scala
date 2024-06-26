package de.htwg.se.Chess
package aview
package selectionSystem

trait State {
  def changeState(): Unit
}

class First(selectionsystem: SelectionSystem, receiver: Receiver)
    extends State {
  def changeState() =
    receiver.off()
    selectionsystem.previousState = this;
    selectionsystem.currentState = selectionsystem.second
}

class Third(selectionsystem: SelectionSystem, receiver: Receiver)
    extends State {
  def changeState() =
    receiver.off()
    selectionsystem.previousState = this;
    selectionsystem.currentState = selectionsystem.first
}

class Second(selectionsystem: SelectionSystem, receiver: Receiver)
    extends State {
  def changeState() =
    receiver.off()
    selectionsystem.previousState = this;
    selectionsystem.currentState = selectionsystem.third
}
