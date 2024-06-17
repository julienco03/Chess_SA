package de.htwg.se.Chess
package controller
package controllerComponent

import controller.CellChanged
import controller.ControllerInterface
import controller.controllerComponent.GameState._
import model._
import persistence.PersistenceInterface
import utils.Observable

import com.google.inject.name.Names
import com.google.inject.{Guice, Inject}

import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import akka.stream.{Materializer, ActorMaterializer}
import akka.actor.ActorSystem
import scala.concurrent.ExecutionContextExecutor
import play.api.libs.json.{JsObject, Json}

import akka.kafka.scaladsl.Producer
import akka.kafka.ProducerSettings
import org.apache.kafka.common.serialization.StringSerializer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord

implicit val system: ActorSystem = ActorSystem("ChessSystem")
implicit val materializer: Materializer = ActorMaterializer()
implicit val executionContext: ExecutionContextExecutor = system.dispatcher

case class Controller @Inject() (
    var board: Board,
    var persistence: PersistenceInterface
) extends ControllerInterface:

  val producerSettings = ProducerSettings(system, new StringSerializer, new StringSerializer)
  .withBootstrapServers("localhost:9092")
  
  def produceMessages(topic: String): Unit = {
    val source = Source(1 to 100) // Example: sending numbers as messages
      .map(_.toString)
      .map(value => new ProducerRecord[String, String](topic, value))

    source.runWith(Producer.plainSink(producerSettings))(materializer)
  }

  def sendMessage(topic: String, message: String): Unit = {
    val record = new ProducerRecord[String, String](topic, message)
    val runnable = Source.single(record).runWith(Producer.plainSink(producerSettings))(materializer)
  }

  var game_state: GameState = NO_WINNER_YET
  var history_manager = new HistoryManager
  var playersystem: PlayerSystem = new PlayerSystem()

  def new_game(): Board =
    board = Board()
    this.playersystem = new PlayerSystem()
    this.history_manager = new HistoryManager
    notifyObservers
    publish(new CellChanged)
    board

  def board_to_string_c(): String = board.board_to_string()

  def move_c(pos_now: String, pos_new: String): Unit =
    history_manager.doMove(new SolveCommand(this))
    board = board.move(pos_now, pos_new)
    change_player()
    check_winner()
    notifyObservers
    publish(new CellChanged)

  def get_player_c(pos_now: String): String =
    board.get_player(pos_now)

  def change_player(): Unit =
    playersystem.changeState()

  def last_turn(): String =
    if (playersystem.previousState.isInstanceOf[PlayerOne])
      "2"
    else
      "1"

  def check_winner(): Unit = {
    val success = board.game_finished(board.board)
    if (success == 1) game_state = PLAYER1
    else if (success == 2) game_state = PLAYER2
    else game_state = NO_WINNER_YET
  }

  def load: Board = {
    board = persistence.loadGame()
    notifyObservers
    board
  }

  def save: Unit = {
    persistence.saveGame(board)
  }

  def undo(): Unit = {
    if (history_manager.undoStack != Nil)
      history_manager.undoMove
      change_player()
      check_winner()
      notifyObservers
      publish(new CellChanged)
  }

  def redo(): Unit = {
    if (history_manager.redoStack != Nil)
      history_manager.redoMove
      change_player()
      check_winner()
      notifyObservers
      publish(new CellChanged)
  }

  override def toJson: JsObject = {
    Json.obj(
      "board" ->
        board_to_string_c()
    )
  }

  override val controllerRoute: Route = concat(
    get {
      concat(
        path("new") {
          new_game()
          complete("Game reset successful")
        },
        path("move" / Segment / Segment) { (x: String, y: String) =>
          move_c(x, y)
          complete("Move successful")
        },
        path("undo") {
          undo()
          complete("Undo successful")
        },
        path("redo") {
          redo()
          complete("Redo successful")
        },
        path("save") {
          save
          complete("Save successful")
        },
        path("load") {
          load
          complete("Load successful")
        },
        path("board") {
          complete(board_to_string_c())
        },
        path("") {
          sys.error("POST Route does not exist")
        }
      )
    }
  )
