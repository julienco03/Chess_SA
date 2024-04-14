package de.htwg.se.Chess
package controller
package controllerComponent

object GameState extends Enumeration{
  type GameState = Value
  val PLAYER1, PLAYER2, NO_WINNER_YET = Value

  val map = Map[GameState, String](
    PLAYER1 ->"Player 1 wins!!!",
    PLAYER2 ->"Player 2 wins!!!",
    NO_WINNER_YET ->"")

  def message(game_state: GameState): String = {
    map(game_state)
  }
}
