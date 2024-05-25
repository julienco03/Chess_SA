package de.htwg.se.Chess
package persistence
package serializer

import model.Board
import scala.collection.immutable.VectorMap

object BoardSerializer {

  def serializeBoard(board: Board): String = {
    board.board.map { case (key, value) => s"$key:$value" }.mkString(";")
  }

  def deserializeBoard(serialized: String): Board = {
    if (serialized.isEmpty) Board(VectorMap.empty)
    else {
      val keyValuePairs = serialized.split(";").map { entry =>
        val Array(key, value) = entry.split(":")
        key -> value
      }
      val boardMap = VectorMap(keyValuePairs: _*)
      Board(boardMap)
    }
  }
}
