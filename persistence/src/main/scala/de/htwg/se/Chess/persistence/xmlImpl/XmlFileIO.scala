package de.htwg.se.Chess
package persistence
package xmlImpl

import model._
import persistence.PersistenceInterface

import java.io._
import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.io.Source
import scala.collection.immutable.VectorMap
import scala.languageFeature.postfixOps
import scala.xml._


class XmlFileIO extends PersistenceInterface {

  private val xmlFilePath: String = "board.xml"

  def createFileIfNotExists(): Unit = {
    if (!Files.exists(Paths.get(xmlFilePath))) {
      val pw = new PrintWriter(new File(xmlFilePath))
      val emptyBoard: Board = Board()
      pw.write(vectorMapToXml(emptyBoard).toString())
      pw.close()
    }
  }

  override def loadGame(): Board = {
    createFileIfNotExists()

    val source = scala.xml.XML.loadFile(xmlFilePath)

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
    createFileIfNotExists()

    val pw = new PrintWriter(new File(xmlFilePath))
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
  
  override def updateGame(board: Board): Unit = ???
  
  override def deleteGame(): Unit = ???
}