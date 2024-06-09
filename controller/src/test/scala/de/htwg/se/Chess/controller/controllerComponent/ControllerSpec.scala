package de.htwg.se.Chess
package controllerComponent
package controller

import de.htwg.se.Chess.controller.controllerComponent.Controller
import de.htwg.se.Chess.controller.controllerComponent.GameState
import model._
import persistence.jsonImpl.JsonFileIO

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import scala.collection.immutable.VectorMap

class ControllerSpec extends AnyWordSpec {

  "A Controller" should {
    val emptyBoard = Board()
    val persistence = JsonFileIO()

    "start a new game" in {
      val controller = Controller(emptyBoard, persistence)
      controller.move_c("A2", "A4")
      controller.new_game()

      controller.board should be(emptyBoard)

      controller.playersystem.currentState shouldBe a[PlayerOne]

      controller.history_manager.undoStack shouldBe empty
      controller.history_manager.redoStack shouldBe empty

      controller.game_state should be(
        GameState.NO_WINNER_YET
      )
    }

    "convert the board to a string" in {
      val controller = Controller(emptyBoard, persistence)
      val boardString = controller.board_to_string_c()

      boardString shouldBe a[String]
      boardString.length should be > 0
      boardString should be(
        controller.board.board_to_string()
      )
    }

    "move a piece" in {
      val controller = Controller(emptyBoard, persistence)
      val initialBoard = emptyBoard
      val pos_now = "A2"
      val pos_new = "A4"

      controller.move_c(pos_now, pos_new)

      controller.board.board(pos_now) should be("  ")
      controller.board.board(pos_new) should be("P1")

      controller.playersystem.currentState shouldBe a[PlayerTwo]

      controller.game_state should be(GameState.NO_WINNER_YET)

      controller.history_manager.undoStack should not be empty
    }

    "get the player at a position" in {
      val controller = Controller(emptyBoard, persistence)

      controller.get_player_c("A2") should be(controller.board.get_player("A2"))
    }

    "change the player" in {
      val controller = Controller(emptyBoard, persistence)

      controller.change_player()
      controller.playersystem.currentState shouldBe a[PlayerTwo]
      controller.change_player()
      controller.playersystem.currentState shouldBe a[PlayerOne]
    }

    "check player of last turn" in {}

    "check for winner when player 1 wins" in {
      val controller = Controller(emptyBoard, persistence)
      val winningBoard = Board(
        VectorMap("A1" -> "K1", "A8" -> "  ")
      )
      controller.board = winningBoard

      controller.check_winner()
      controller.game_state should be(GameState.PLAYER1)
    }

    "check for winner when player 2 wins" in {
      val controller = Controller(emptyBoard, persistence)
      val winningBoard = Board(
        VectorMap("A1" -> "  ", "A8" -> "K2")
      )
      controller.board = winningBoard

      controller.check_winner()
      controller.game_state should be(GameState.PLAYER2)
    }

    "check for no winner yet" in {
      val controller = Controller(emptyBoard, persistence)
      val ongoingBoard = Board()
      controller.board = ongoingBoard

      controller.check_winner()
      controller.game_state should be(GameState.NO_WINNER_YET)
    }

    "load a chess board" in {
      val controller = Controller(emptyBoard, persistence)
      controller.move_c("A2", "A4")
      controller.save
      val savedBoard = controller.board

      controller.new_game()
      controller.load
      controller.board shouldBe savedBoard
    }

    "save a chess board" in {
      val controller = Controller(emptyBoard, persistence)
      val initialBoard = controller.board

      controller.move_c("A2", "A4")
      controller.save

      controller.new_game()
      controller.load

      controller.board shouldBe initialBoard.move("A2", "A4")
    }

    "undo a move" in {
      val controller = Controller(emptyBoard, persistence)
      val initialBoard = controller.board

      controller.move_c("A2", "A4")
      val afterMoveBoard = controller.board

      controller.undo()

      controller.board shouldBe initialBoard
    }

    "redo a move" in {
      val controller = Controller(emptyBoard, persistence)
      val initialBoard = controller.board

      controller.move_c("A2", "A4")
      val afterMoveBoard = controller.board

      controller.undo()
      controller.redo()

      controller.board shouldBe afterMoveBoard
    }
  }
}
