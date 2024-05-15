package de.htwg.se.Chess
package persistence
package jsonImpl

import model._
import persistence.PersistenceInterface

import java.io._
import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.io.Source
import scala.collection.immutable.VectorMap
import scala.languageFeature.postfixOps
import play.api.libs.json._

class JsonFileIO extends PersistenceInterface {

  private val jsonFilePath: String = "board.json"

  def createFileIfNotExists(): Unit = {
    if (!Files.exists(Paths.get(jsonFilePath))) {
      val pw = new PrintWriter(new File(jsonFilePath))
      val emptyBoard: Board = Board()
      pw.write(vectorMapToJson(emptyBoard))
      pw.close()
    }
  }

  override def loadGame(): Board = {
    createFileIfNotExists()

    val source: String = Source.fromFile(jsonFilePath).getLines.mkString
    val json: JsValue = Json.parse(source)

    def updateBoard(index: Int, board: VectorMap[String, String]): VectorMap[String, String] = {
      if (index < 8 * 8) {
        val pos = (json \\ "pos")(index).as[String]
        val figure = (json \\ "figure")(index).as[String]
        val updatedBoard = board.updated(pos, figure)
        updateBoard(index + 1, updatedBoard)
      } else {
        board
      }
    }

    val initialBoard: VectorMap[String, String] = VectorMap[String, String]()
    val finalBoard = updateBoard(0, initialBoard)
    Board(finalBoard)
  }

  override def saveGame(board: Board): Unit = {
    createFileIfNotExists()

    val pw = new PrintWriter(new File(jsonFilePath))
    pw.write(vectorMapToJson(board))
    pw.close()
  }

  def vectorMapToJson(board: Board): String = {
    val tmp: Seq[(String, String)] = board.board.toSeq // Convert to Seq of tuples

    def convertEntriesToJson(entries: Seq[(String, String)], acc: List[JsObject]): List[JsObject] = {
      entries match {
        case Nil => acc.reverse
        case (pos, figure) :: tail =>
          val entryJson = Json.obj(
            "pos" -> pos,
            "figure" -> figure
          )
          convertEntriesToJson(tail, entryJson :: acc)
      }
    }

    val boardEntries = convertEntriesToJson(tmp, Nil)
    val jsonData = Json.obj(
      "board" -> Json.obj(
        "board_entry" -> Json.toJson(boardEntries)
      )
    )
    Json.stringify(jsonData)
  }
}
