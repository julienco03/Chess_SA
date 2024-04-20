package de.htwg.se.Chess
package model

import akka.http.scaladsl.server.Route
import scala.collection.immutable.VectorMap
import play.api.libs.json.JsObject

trait BoardInterface(){
    val board: VectorMap[String, String]
    val boardRoute: Route

    def move(pos_now: String, pos_new: String): Board
    def game_finished(game_map: VectorMap[String, String]): Int
    def board_to_string(): String
    def get_player(pos: String): String
    def toJson: JsObject
}
