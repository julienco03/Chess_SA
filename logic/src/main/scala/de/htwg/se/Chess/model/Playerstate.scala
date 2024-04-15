package de.htwg.se.Chess
package model

class PlayerSystem {
    val player_one:State = new PlayerOne(this)
    val player_two:State = new PlayerTwo(this)
    var currentState:State = new PlayerOne(this)
    var previousState:State = new PlayerTwo(this)

    def changeState(): Unit = currentState.changeState()
}

trait State {
  def changeState(): Unit
}

class PlayerOne(playersystem: PlayerSystem) extends State {
    def changeState() =
        playersystem.previousState = this;
        playersystem.currentState = playersystem.player_two
}

class PlayerTwo(playersystem: PlayerSystem) extends State {
    def changeState() =
        playersystem.previousState = this;
        playersystem.currentState = playersystem.player_one
}
