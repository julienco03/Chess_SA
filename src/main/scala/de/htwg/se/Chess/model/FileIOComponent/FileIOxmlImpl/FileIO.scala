package de.htwg.se.Chess.model.FileIOComponent.FileIOxmlImpl

import de.htwg.se.Chess.model.FileIOComponent.FileIOInterface
import de.htwg.se.Chess.model.*

import play.api.libs.json.*
import scala.io.Source
import java.io._
import scala.xml._
import scala.collection.immutable.VectorMap


class FileIO extends FileIOInterface {

  override def load(): Board = {
    // load game from file
    val source = scala.xml.XML.loadFile("board.xml")

    def updateField(index: Int, field: VectorMap[String, String]): VectorMap[String, String] = {
      if (index < 8 * 8) {
        val pos = ((source \\ "pos")(index)).text.trim()
        val figure = ((source \\ "figure")(index)).text.trim()

        val updatedField = if (figure == "__") {
          field.updated(pos, "  ")
        } else {
          field.updated(pos, figure)
        }

        updateField(index + 1, updatedField)
      } else {
        field
      }
    }

    val initialField: VectorMap[String, String] = VectorMap[String, String]()
    val finalField = updateField(0, initialField)
    Board(finalField)
  }

  override def save(game: BoardInterface): Unit =
    // write game to file
    val pw = new PrintWriter(new File("board.xml"))
    pw.write(vectorMapToXml(game).toString())
    pw.close()

  def vectorMapToXml(board_object: BoardInterface): Elem = {
    def convertEntriesToXml(entries: Vector[(String, String)]): Seq[scala.xml.Node] = {
      entries.flatMap { case (pos, figure) =>
        val figureText = if (figure == "  ") "__" else figure
        Seq(<pos>{pos}</pos>, <figure>{figureText}</figure>)
      }
    }

    val entriesXml = convertEntriesToXml(board_object.board.toVector)
    <game>{entriesXml}</game>
  }

}
