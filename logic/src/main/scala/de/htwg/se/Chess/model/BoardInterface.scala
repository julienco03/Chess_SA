package de.htwg.se.Chess
package model

import scala.collection.immutable.VectorMap

trait BoardInterface(){
    val board: VectorMap[String, String]
    def move(pos_now: String, pos_new: String): Board
    def game_finished(game_map: VectorMap[String, String]): Int
    def board_to_string(): String
    def get_player(pos: String): String
}
