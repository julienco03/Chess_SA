package de.htwg.se.Chess.model.FileIOComponent.FileIOjsonImpl

import de.htwg.se.Chess.model.FileIOComponent.FileIOInterface
import scala.collection.immutable.VectorMap

import scala.io.Source

import de.htwg.se.Chess.model.*

import scalafx.stage.FileChooser
import scalafx.stage.FileChooser.ExtensionFilter

import java.io._
import play.api.libs.json._
import scala.languageFeature.postfixOps
import scala.collection.immutable.ListMap

class FileIO extends FileIOInterface {

  override def load(): Board = {
    // load game from file
    val source: String = Source.fromFile("board.json").getLines.mkString
    val json: JsValue = Json.parse(source)

    def updateField(index: Int, field: VectorMap[String, String]): VectorMap[String, String] = {
      if (index < 8 * 8) {
        val pos = (json \\ "pos")(index).as[String]
        val figure = (json \\ "figure")(index).as[String]
        val updatedField = field.updated(pos, figure)
        updateField(index + 1, updatedField)
      } else {
        field
      }
    }

    val initialField: VectorMap[String, String] = VectorMap[String, String]()
    val finalField = updateField(0, initialField)
    Board(finalField)
  }

  override def save(game: BoardInterface) =
    // write game to file
    val pw = new PrintWriter(new File("board.json"))
    pw.write(vectorMapToJson(game))
    pw.close

  def vectorMapToJson(board_object: BoardInterface): String = {
    val tmp: Seq[(String, String)] = board_object.board.toSeq // Convert to Seq of tuples

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

    val fieldEntries = convertEntriesToJson(tmp, Nil)
    val jsonData = Json.obj(
      "field" -> Json.obj(
        "field_entry" -> Json.toJson(fieldEntries)
      )
    )
    Json.stringify(jsonData)
  }

}
