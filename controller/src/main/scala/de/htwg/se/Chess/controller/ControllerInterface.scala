package de.htwg.se.Chess
package controller

import utils.Observable
import controller.controllerComponent.GameState._
import model._

import scala.swing.event.Event
import scala.swing.Publisher
import play.api.libs.json.JsObject

trait ControllerInterface extends Observable with Publisher{
    var field: Board
    def game_state: GameState
    def board_to_string_c(): String
    def move_c(pos_now : String, pos_new : String) : Unit
    def domove(): Unit
    def get_player_c(pos_now: String): String
    def change_player(): Unit
    def last_turn(): String
    def check_winner(): Unit
    def load:Board
    def save:Unit
    def undo(): Unit
    def redo(): Unit
    def toJson: JsObject
}

class CellChanged extends Event
