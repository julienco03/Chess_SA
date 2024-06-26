package de.htwg.se.Chess
package persistence
package serializer

import model.Board
import persistence.serializer.BoardSerializer
import persistence.xmlImpl.XmlFileIO

import java.nio.file.{Files, Paths}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import scala.collection.immutable.VectorMap
import java.io.File
import scala.io.Source
import scala.util.Try
import java.io.PrintWriter

class XmlSpec extends AnyWordSpec {
  val io = new XmlFileIO()
  val emptyBoard = Board()
  val filePath = "board.xml"
  val testBoard =
    """<game><pos>A1</pos><figure>R1</figure><pos>B1</pos><figure>k1</figure><pos>C1</pos><figure>B1</figure><pos>D1</pos><figure>Q1</figure><pos>E1</pos><figure>K1</figure><pos>F1</pos><figure>B1</figure><pos>G1</pos><figure>k1</figure><pos>H1</pos><figure>R1</figure><pos>A2</pos><figure>P1</figure><pos>B2</pos><figure>P1</figure><pos>C2</pos><figure>P1</figure><pos>D2</pos><figure>P1</figure><pos>E2</pos><figure>P1</figure><pos>F2</pos><figure>P1</figure><pos>G2</pos><figure>P1</figure><pos>H2</pos><figure>P1</figure><pos>A3</pos><figure>__</figure><pos>B3</pos><figure>__</figure><pos>C3</pos><figure>__</figure><pos>D3</pos><figure>__</figure><pos>E3</pos><figure>__</figure><pos>F3</pos><figure>__</figure><pos>G3</pos><figure>__</figure><pos>H3</pos><figure>__</figure><pos>A4</pos><figure>__</figure><pos>B4</pos><figure>__</figure><pos>C4</pos><figure>__</figure><pos>D4</pos><figure>__</figure><pos>E4</pos><figure>__</figure><pos>F4</pos><figure>__</figure><pos>G4</pos><figure>__</figure><pos>H4</pos><figure>__</figure><pos>A5</pos><figure>__</figure><pos>B5</pos><figure>__</figure><pos>C5</pos><figure>__</figure><pos>D5</pos><figure>__</figure><pos>E5</pos><figure>__</figure><pos>F5</pos><figure>__</figure><pos>G5</pos><figure>__</figure><pos>H5</pos><figure>__</figure><pos>A6</pos><figure>__</figure><pos>B6</pos><figure>__</figure><pos>C6</pos><figure>__</figure><pos>D6</pos><figure>__</figure><pos>E6</pos><figure>__</figure><pos>F6</pos><figure>__</figure><pos>G6</pos><figure>__</figure><pos>H6</pos><figure>__</figure><pos>A7</pos><figure>P2</figure><pos>B7</pos><figure>P2</figure><pos>C7</pos><figure>P2</figure><pos>D7</pos><figure>P2</figure><pos>E7</pos><figure>P2</figure><pos>F7</pos><figure>P2</figure><pos>G7</pos><figure>P2</figure><pos>H7</pos><figure>P2</figure><pos>A8</pos><figure>R2</figure><pos>B8</pos><figure>k2</figure><pos>C8</pos><figure>B2</figure><pos>D8</pos><figure>Q2</figure><pos>E8</pos><figure>K2</figure><pos>F8</pos><figure>B2</figure><pos>G8</pos><figure>k2</figure><pos>H8</pos><figure>R2</figure></game>"""

  "XmlFileIO" should {
    "correctly load a board from XML file" in {
      val loadedBoard = io.loadGame()

      val file = new File(filePath)
      file.exists() shouldBe true

      val expectedBoard = emptyBoard
      loadedBoard shouldBe expectedBoard
    }

    "correctly save a board to XML file" in {
      io.saveGame(emptyBoard)

      val file = new File(filePath)
      file.exists() shouldBe true

      val fileContent = Source.fromFile(file).getLines.mkString
      fileContent shouldBe testBoard
    }
  }
}
