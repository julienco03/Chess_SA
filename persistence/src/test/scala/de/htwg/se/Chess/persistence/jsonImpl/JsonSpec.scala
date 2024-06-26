package de.htwg.se.Chess
package persistence
package serializer

import model.Board
import persistence.serializer.BoardSerializer
import persistence.jsonImpl.JsonFileIO

import java.nio.file.{Files, Paths}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import scala.collection.immutable.VectorMap
import java.io.File
import scala.io.Source
import scala.util.Try
import play.api.libs.json.Json

class JsonSpec extends AnyWordSpec {
  val io = JsonFileIO()
  val filePath = "board.json"
  val testBoard =
    """{"board":{"board_entry":[{"pos":"A1","figure":"R1"},{"pos":"B1","figure":"k1"},{"pos":"C1","figure":"B1"},{"pos":"D1","figure":"Q1"},{"pos":"E1","figure":"K1"},{"pos":"F1","figure":"B1"},{"pos":"G1","figure":"k1"},{"pos":"H1","figure":"R1"},{"pos":"A2","figure":"P1"},{"pos":"B2","figure":"P1"},{"pos":"C2","figure":"P1"},{"pos":"D2","figure":"P1"},{"pos":"E2","figure":"P1"},{"pos":"F2","figure":"P1"},{"pos":"G2","figure":"P1"},{"pos":"H2","figure":"P1"},{"pos":"A3","figure":"  "},{"pos":"B3","figure":"  "},{"pos":"C3","figure":"  "},{"pos":"D3","figure":"  "},{"pos":"E3","figure":"  "},{"pos":"F3","figure":"  "},{"pos":"G3","figure":"  "},{"pos":"H3","figure":"  "},{"pos":"A4","figure":"  "},{"pos":"B4","figure":"  "},{"pos":"C4","figure":"  "},{"pos":"D4","figure":"  "},{"pos":"E4","figure":"  "},{"pos":"F4","figure":"  "},{"pos":"G4","figure":"  "},{"pos":"H4","figure":"  "},{"pos":"A5","figure":"  "},{"pos":"B5","figure":"  "},{"pos":"C5","figure":"  "},{"pos":"D5","figure":"  "},{"pos":"E5","figure":"  "},{"pos":"F5","figure":"  "},{"pos":"G5","figure":"  "},{"pos":"H5","figure":"  "},{"pos":"A6","figure":"  "},{"pos":"B6","figure":"  "},{"pos":"C6","figure":"  "},{"pos":"D6","figure":"  "},{"pos":"E6","figure":"  "},{"pos":"F6","figure":"  "},{"pos":"G6","figure":"  "},{"pos":"H6","figure":"  "},{"pos":"A7","figure":"P2"},{"pos":"B7","figure":"P2"},{"pos":"C7","figure":"P2"},{"pos":"D7","figure":"P2"},{"pos":"E7","figure":"P2"},{"pos":"F7","figure":"P2"},{"pos":"G7","figure":"P2"},{"pos":"H7","figure":"P2"},{"pos":"A8","figure":"R2"},{"pos":"B8","figure":"k2"},{"pos":"C8","figure":"B2"},{"pos":"D8","figure":"Q2"},{"pos":"E8","figure":"K2"},{"pos":"F8","figure":"B2"},{"pos":"G8","figure":"k2"},{"pos":"H8","figure":"R2"}]}}"""

  "A JsonFileIO" should {
    "load a correct board from JSON file" in {
      val loadedBoard = io.loadGame()

      val file = new File(filePath)
      file.exists() shouldBe true

      loadedBoard shouldBe a[Board]
    }

    "correctly save a board to JSON file" in {
      val boardToSave = Board()

      io.saveGame(boardToSave)

      val file = new File(filePath)
      file.exists() shouldBe true
      file.length() should be > 0L

      val fileContent = Source.fromFile(file).getLines.mkString
      val isValidJson = Try(Json.parse(fileContent)).isSuccess
      isValidJson shouldBe true

      val expectedJson = testBoard
      fileContent shouldBe expectedJson
    }

    "correctly convert board data to JSON" in {
      val board = Board()
      val jsonStr = io.vectorMapToJson(board)
      val expectedJsonStr = testBoard
      jsonStr shouldBe expectedJsonStr
    }
  }
}
