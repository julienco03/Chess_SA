package de.htwg.se.Chess
package aview

import aview.selectionSystem.*
import controller.ControllerInterface
import controller.controllerComponent._
import utils.Observer

import scala.swing._
import javax.swing.border.EmptyBorder
import scala.swing.event.Key
import scala.swing.event.ButtonClicked
import scalafx.scene.input.KeyCode.R
import scalafx.scene.input.KeyCode.G
import scala.swing.event.PopupMenuCanceled
import javax.swing.JOptionPane

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source, Flow, Keep}
import akka.stream.{ActorMaterializer, Materializer, OverflowStrategy}
import akka.{Done, NotUsed}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class GUI(controller: ControllerInterface) extends Frame with Observer:
  controller.add(this)

  var pos1: String = ""
  var pos2: String = ""
  val selection_system: SelectionSystem = new SelectionSystem()

  // Create the Actor System and Materializer
  implicit val system: ActorSystem = ActorSystem("ChessActorSystem")
  implicit val materializer: Materializer = ActorMaterializer()

  // Initialize the API Client
  val apiClient = new ApiClient

  // Define the Source, Flow, and Sink
  val (queue, source) = Source
    .queue[(String, String)](10, OverflowStrategy.dropNew)
    .preMaterialize()

  val flow: Flow[(String, String), (String, String), NotUsed] =
    Flow[(String, String)].mapAsync(parallelism = 4) { case (pos1, pos2) =>
      apiClient.processMove(pos1, pos2)
      Future.successful((pos1, pos2))
    }

  val sink: Sink[(String, String), Future[Done]] =
    Sink.foreach[(String, String)] { case (p1, p2) =>
      selection_system.changeState()
      pos1 = ""
      pos2 = ""
      update
    }

  // Connect the Source, Flow, and Sink
  val runnableGraph = source.via(flow).toMat(sink)(Keep.right)
  val materialized = runnableGraph.run()

  title = "Chess Game"
  preferredSize = new Dimension(800, 800)
  resizable = true

  menuBar = new MenuBar {
    contents += new Menu("Game") {
      contents += new MenuItem(Action("New") {
        apiClient.newGame()
      })
      contents += new Separator
      contents += new MenuItem(Action("Undo") {
        apiClient.undoMove()
      })
      contents += new MenuItem(Action("Redo") {
        apiClient.redoMove()
      })
      contents += new Separator
      contents += new MenuItem(Action("Save") {
        apiClient.saveGame()
      })
      contents += new MenuItem(Action("Load") {
        apiClient.loadGame()
      })
      contents += new Separator
      contents += new MenuItem(Action("Quit") {
        apiClient.exit()
      })
    }
  }
  contents = contentPanel

  def contentPanel = new BorderPanel {
    add(new Label("Welcome to Chess:"), BorderPanel.Position.North)
    add(new CellPanel(), BorderPanel.Position.Center)
    if (GameState.message(controller.game_state) != "")
      infoBox(
        GameState.message(controller.game_state),
        GameState.message(controller.game_state)
      )
  }

  override def update: Unit =
    contents = contentPanel

  def infoBox(infoMessage: String, titleBar: String) =
    JOptionPane.showMessageDialog(
      null,
      infoMessage,
      titleBar,
      JOptionPane.INFORMATION_MESSAGE
    )

  class CellPanel() extends GridPanel(8, 8):
    border = EmptyBorder(20, 20, 20, 20)
    printBoard
    def printBoard =
      controller.board.board.map(x => contents += new CellButton(x._1, x._2))

  class CellButton(pos: String, figure: String) extends Button(figure):
    listenTo(mouse.clicks)
    listenTo(keys)
    reactions += { case ButtonClicked(button) =>
      selectionHandler(pos, figure)
    }

  def selectionHandler(pos: String, figure: String) =
    if (pos1 != pos && pos2 != pos)
      if (
        selection_system.currentState.isInstanceOf[First] && controller
          .last_turn() == controller.get_player_c(pos)
      )
        pos1 = pos
        selection_system.changeState()
      else if (selection_system.currentState.isInstanceOf[Second])
        pos2 = pos
        selection_system.changeState()
        // Add the positions to the queue for asynchronous processing
        queue.offer((pos1, pos2))
    else
      println("Invalid Move" + pos1 + " " + pos2)

  pack()
  centerOnScreen()
  open()
