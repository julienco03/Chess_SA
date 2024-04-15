package de.htwg.se.Chess
package aview

class SelectionSystem {
    var intialReceiver:Receiver=new FirstReceiver()
    var first:State=new First(this,intialReceiver)
    var third:State=new Third(this,new ThirdReceiver)
    var second:State=new Second(this,new SecondReceiver)
    var currentState:State=new First(this,intialReceiver)
    var previousState:State=new First(this,intialReceiver)
    var pos1: String = ""
    var pos2: String = ""

    def changeState(): Unit = currentState.changeState()
}
