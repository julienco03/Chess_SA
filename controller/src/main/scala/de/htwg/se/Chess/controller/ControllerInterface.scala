package de.htwg.se.Chess
package controller

import controllerComponent.GameState._
import model._
import utils.Observable

import akka.http.scaladsl.server.Route
import scala.swing.event.Event
import scala.swing.Publisher
import play.api.libs.json.JsObject

trait ControllerInterface extends Observable with Publisher {
    var field: Board
    val controllerRoute: Route

    def new_game(): Board
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
