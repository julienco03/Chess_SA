package de.htwg.se.Chess
package persistence
package xmlImpl

import model._
import persistence.PersistenceInterface

import java.io._
import scala.io.Source
import scala.collection.immutable.VectorMap
import scala.languageFeature.postfixOps
import scala.xml._


class XmlFileIO extends PersistenceInterface {

  override def loadGame(): Board = {
    val source = scala.xml.XML.loadFile("board.xml")

    def updateBoard(index: Int, board: VectorMap[String, String]): VectorMap[String, String] = {
      if (index < 8 * 8) {
        val pos = ((source \\ "pos")(index)).text.trim()
        val figure = ((source \\ "figure")(index)).text.trim()
        val updatedBoard = if (figure == "__") {
          board.updated(pos, "  ")
        } else {
          board.updated(pos, figure)
        }

        updateBoard(index + 1, updatedBoard)
      } else {
        board
      }
    }

    val initialBoard: VectorMap[String, String] = VectorMap[String, String]()
    val finalBoard = updateBoard(0, initialBoard)
    Board(finalBoard)
  }

  override def saveGame(board: Board): Unit =
    val pw = new PrintWriter(new File("board.xml"))
    pw.write(vectorMapToXml(board).toString())
    pw.close()

  def vectorMapToXml(board: Board): Elem = {
    def convertEntriesToXml(entries: Vector[(String, String)]): Seq[scala.xml.Node] = {
      entries.flatMap { case (pos, figure) =>
        val figureText = if (figure == "  ") "__" else figure
        Seq(<pos>{pos}</pos>, <figure>{figureText}</figure>)
      }
    }

    val entriesXml = convertEntriesToXml(board.board.toVector)
    <game>{entriesXml}</game>
  }
}