package de.htwg.se.Chess
package model

import model._

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import scala.collection.immutable.VectorMap

class BoardSpec extends AnyWordSpec {

  "A Board should" should {
    val emptyBoard = Board()

    "move a piece from a valid start position to a valid destination" should {
      val initialBoard = emptyBoard
      val startPos = "A2"
      val endPos = "A3"
      val movedPiece = initialBoard.board(startPos)
      val updatedBoard = initialBoard.move(startPos, endPos)

      "return a new board with the piece moved to the destination" in {
        updatedBoard.board(endPos) shouldBe movedPiece
      }

      "leave the original start position empty" in {
        updatedBoard.board(startPos) shouldBe "  "
      }
    }

    "not move a piece from an invalid start position" should {
      val initialBoard = emptyBoard
      val startPos = "A9" // Invalid start position
      val endPos = "A3"
      val updatedBoard = initialBoard.move(startPos, endPos)

      "return the original board without any changes" in {
        updatedBoard shouldBe initialBoard
      }
    }

    "not move a piece to an invalid destination" should {
      val initialBoard = emptyBoard
      val startPos = "A2"
      val endPos = "I3" // Invalid destination
      val updatedBoard = initialBoard.move(startPos, endPos)

      "return the original board without any changes" in {
        updatedBoard shouldBe initialBoard
      }
    }

    "return 1 when the king of player one is not on the board" in {
      val board = Board(
        VectorMap(
          "A1" -> "  ",
          "B1" -> "  ",
          "C1" -> "  ",
          "D1" -> "  ",
          "E1" -> "K2",
          "F1" -> "  ",
          "G1" -> "  ",
          "H1" -> "  "
        )
      )
      board.game_finished(board.board) shouldBe 2
    }

    "return 2 when the king of player two is not on the board" in {
      val board = Board(
        VectorMap(
          "A1" -> "K1",
          "B1" -> "  ",
          "C1" -> "  ",
          "D1" -> "  ",
          "E1" -> "  ",
          "F1" -> "  ",
          "G1" -> "  ",
          "H1" -> "  "
        )
      )
      board.game_finished(board.board) shouldBe 1
    }

    "return 0 when both kings are on the board" in {
      val board = Board(
        VectorMap(
          "A1" -> "K1",
          "B1" -> "  ",
          "C1" -> "  ",
          "D1" -> "  ",
          "E1" -> "K2",
          "F1" -> "  ",
          "G1" -> "  ",
          "H1" -> "  "
        )
      )
      board.game_finished(board.board) shouldBe 0
    }

    "return 0 when the board is empty" in {
      val board = emptyBoard
      board.game_finished(board.board) shouldBe 0
    }

    "return false if the starting position is empty" in {
      val board = Board(
        VectorMap(
          "A1" -> "  ",
          "B1" -> "k1",
          "C1" -> "  ",
          "D1" -> "  ",
          "E1" -> "  ",
          "F1" -> "  ",
          "G1" -> "  ",
          "H1" -> "  "
        )
      )
      board.check_move(board.board, "A1", "B2") shouldBe false
    }

    "return true if the move is valid for king" in {
      val board = Board(VectorMap("A1" -> "K1", "B1" -> "P2"))
      board.check_move(board.board, "A1", "B1") shouldBe true
    }

    "return true if the move is valid for queen" in {
      val board = Board(
        VectorMap(
          "D4" -> "Q1",
          "D1" -> "  ",
          "D8" -> "  ",
          "A4" -> "  ",
          "H4" -> "  ",
          "D7" -> "  ",
          "D2" -> "  ",
          "D5" -> "  ",
          "D3" -> "  ",
          "D6" -> "  "
        )
      )
      board.check_move(board.board, "D4", "D8") shouldBe true
    }

    "return true if the move is valid for bishop" in {
      val board = Board(
        VectorMap(
          "D4" -> "B1",
          "A1" -> "  ",
          "G7" -> "  ",
          "G1" -> "  ",
          "A7" -> "  ",
          "C1" -> "  ",
          "F8" -> "  ",
          "B5" -> "  ",
          "E3" -> "  ",
          "H6" -> "  "
        )
      )
      board.check_move(board.board, "D4", "A1") shouldBe true
    }

    "return true if the move is valid for knight" in {
      val board = Board(
        VectorMap(
          "D4" -> "k1",
          "B3" -> "  ",
          "B5" -> "  ",
          "C6" -> "  ",
          "C2" -> "  ",
          "E6" -> "  ",
          "E2" -> "  ",
          "F3" -> "  ",
          "F5" -> "  "
        )
      )
      board.check_move(board.board, "D4", "B3") shouldBe true
    }

    "return true if the move is valid for rook" in {
      val board = Board(
        VectorMap(
          "D4" -> "R1",
          "D1" -> "  ",
          "D8" -> "  ",
          "A4" -> "  ",
          "H4" -> "  ",
          "D7" -> "  ",
          "D2" -> "  ",
          "D5" -> "  ",
          "D3" -> "  ",
          "D6" -> "  "
        )
      )
      board.check_move(board.board, "D4", "D1") shouldBe true
    }

    "return true if the move is valid for pawn" in {
      val board = Board(
        VectorMap(
          "D2" -> "P1",
          "D7" -> "P2",
          "C2" -> "  ",
          "C3" -> "  ",
          "C4" -> "  ",
          "C5" -> "  ",
          "C6" -> "  ",
          "D3" -> "  ",
          "D4" -> "  ",
          "D5" -> "  "
        )
      )
      board.check_move(board.board, "D2", "D4") shouldBe true
    }

    "return false if the move is invalid" in {
      val board = Board(VectorMap("A1" -> "K1", "B1" -> "k1"))
      board.check_move(board.board, "A1", "A2") shouldBe false
    }

    "return player 1 if there is a player 1 piece at the position" in {
      val board = Board(VectorMap("A1" -> "K1", "B1" -> "k1", "C1" -> "  "))
      board.get_player("A1") shouldBe "1"
    }

    "return player 2 if there is a player 2 piece at the position" in {
      val board = Board(VectorMap("A1" -> "  ", "B1" -> "  ", "C1" -> "k2"))
      board.get_player("C1") shouldBe "2"
    }

    "return the string value if Some(String) is provided" in {
      val board = emptyBoard
      val value = Some("Test")
      board.match_pattern(value) shouldBe "Test"
    }

    "return 'Invalid' if None is provided for Option[String]" in {
      val board = emptyBoard
      val value: Option[String] = None
      board.match_pattern(value) shouldBe "Invalid"
    }

    "return the integer value if Some(Int) is provided" in {
      val board = emptyBoard
      val value = Some(42)
      board.match_pattern(value) shouldBe 42
    }

    "return 0 if None is provided for Option[Int]" in {
      val board = emptyBoard
      val value: Option[Int] = None
      board.match_pattern(value) shouldBe 0
    }

    "get_figure_type" should {
      "return the correct figure type" in {
        val board = Board(VectorMap("A1" -> "P1", "B1" -> "  ", "C1" -> "B2"))
        board.get_figure_type("A1") shouldBe "P"
        board.get_figure_type("B1") shouldBe " "
        board.get_figure_type("C1") shouldBe "B"
      }
    }

    "different_player" should {
      "return true if the positions are occupied by different players" in {
        val board = Board(VectorMap("A1" -> "P1", "B1" -> "k1", "C1" -> "P2"))
        board.different_player("A1", "B1") shouldBe false
        board.different_player("A1", "C1") shouldBe true
      }
    }

    "empty_board" should {
      "return true if the position is empty" in {
        val board = Board(VectorMap("A1" -> "P1", "B1" -> "  ", "C1" -> "P2"))
        board.empty_board("A1") shouldBe false
        board.empty_board("B1") shouldBe true
        board.empty_board("C1") shouldBe false
      }
    }

    "forward_move" should {
      "return true if the move is forward for player 1 or backward for player 2" in {
        val board = Board(VectorMap("A1" -> "P1", "B1" -> "P2", "C1" -> "P2"))
        board.forward_move("A1", "A2") shouldBe true
        board.forward_move("B1", "B2") shouldBe false
        board.forward_move("C1", "C2") shouldBe false
      }
    }

    "x_diff" should {
      "return the absolute difference in x coordinates" in {
        val board = Board(VectorMap("A1" -> "  ", "B1" -> "  ", "C1" -> "  "))
        board.x_diff("A1", "C1") shouldBe 2
        board.x_diff("A1", "B1") shouldBe 1
      }
    }

    "y_diff" should {
      "return the absolute difference in y coordinates" in {
        val board = Board(VectorMap("A1" -> "  ", "A2" -> "  ", "A3" -> "  "))
        board.y_diff("A1", "A3") shouldBe 2
        board.y_diff("A1", "A2") shouldBe 1
      }
    }

    "xy_equal" should {
      "return true if x_diff is equal to y_diff" in {
        val board = Board(VectorMap("A1" -> "  ", "B2" -> "  ", "C3" -> "  "))
        board.xy_equal("A1", "C3") shouldBe true
        board.xy_equal("A1", "C2") shouldBe false
      }
    }

    "x_or_y" should {
      "return true if x_diff is 0 and y_diff is greater than or equal to 0, or if x_diff is greater than or equal to 0 and y_diff is 0" in {
        val board = Board(VectorMap("A1" -> "  ", "A2" -> "  ", "A3" -> "  "))
        board.x_or_y("A1", "A3") shouldBe true
        board.x_or_y("B1", "B2") shouldBe true
        board.x_or_y("A1", "B2") shouldBe false
      }
    }

    "x_y_maxlength" should {
      "return the maximum length between x_diff and y_diff" in {
        val board = Board(VectorMap("A1" -> "  ", "B2" -> "  ", "C3" -> "  "))
        board.x_y_maxlength("A1", "C3") shouldBe 2
        board.x_y_maxlength("A1", "B2") shouldBe 1
      }
    }

    "check_x" should {
      "return true if the given position's x coordinate matches the specified x value" in {
        val board = Board(VectorMap("A1" -> "  ", "B1" -> "  ", "C1" -> "  "))
        board.check_x("A1", "A") shouldBe true
        board.check_x("A1", "B") shouldBe false
      }
    }

    "king" should {
      "return true for a valid king move" in {
        val board = Board(VectorMap("A1" -> "K1", "A2" -> "  "))
        board.king("A1", "A2") shouldBe true
      }

      "return false for an invalid king move" in {
        val board = Board(VectorMap("A1" -> "K1", "B2" -> "  "))
        board.king("A1", "B2") shouldBe false
      }
    }

    "queen" should {
      "return true for a valid queen move" in {
        val board = Board(VectorMap("D1" -> "Q1", "F3" -> "  "))
        board.queen("D1", "F3") shouldBe true
      }

      "return false for an invalid queen move" in {
        val board = Board(VectorMap("D1" -> "Q1", "C3" -> "  "))
        board.queen("D1", "C3") shouldBe false
      }
    }

    "bishop" should {
      "return true for a valid bishop move" in {
        val board = Board(VectorMap("C1" -> "B1", "E3" -> "  "))
        board.bishop("C1", "E3") shouldBe true
      }

      "return false for an invalid bishop move" in {
        val board = Board(VectorMap("C1" -> "B1", "C3" -> "  "))
        board.bishop("C1", "C3") shouldBe false
      }
    }

    "knight" should {
      "return true for a valid knight move" in {
        val board = Board(VectorMap("B1" -> "k1", "C3" -> "  "))
        board.knight("B1", "C3") shouldBe true
      }

      "return false for an invalid knight move" in {
        val board = Board(VectorMap("B1" -> "k1", "A6" -> "  "))
        board.knight("B1", "A6") shouldBe false
      }
    }

    "rook" should {
      "return true for a valid rook move" in {
        val board = Board(VectorMap("A1" -> "R1", "A4" -> "  "))
        board.rook("A1", "A4") shouldBe true
      }

      "return false for an invalid rook move" in {
        val board = Board(VectorMap("A1" -> "R1", "B2" -> "  "))
        board.rook("A1", "B2") shouldBe false
      }
    }

    "pawn" should {
      "return true for a valid pawn move" in {
        val board = Board(VectorMap("A2" -> "P1", "A4" -> "  "))
        board.pawn("A2", "A4") shouldBe true
      }

      "return false for an invalid pawn move" in {
        val board = Board(VectorMap("A2" -> "P1", "A5" -> "  "))
        board.pawn("A2", "A5") shouldBe false
      }
    }

  }
}
