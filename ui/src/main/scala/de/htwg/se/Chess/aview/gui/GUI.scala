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

import java.util.Properties
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.jdk.CollectionConverters._

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source, Flow, Keep}
import akka.stream.{ActorMaterializer, Materializer, OverflowStrategy}
import akka.{Done, NotUsed}
import akka.kafka.{ProducerSettings, ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.{Producer, Consumer}
import org.apache.kafka.clients.admin.{AdminClient, AdminClientConfig, NewTopic}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{StringSerializer, StringDeserializer}

class GUI(controller: ControllerInterface) extends Frame with Observer:
  controller.add(this)

  var pos1: String = ""
  var pos2: String = ""
  val selection_system: SelectionSystem = new SelectionSystem()

  // Create the Actor System and Materializer
  implicit val system: ActorSystem = ActorSystem("ChessActorSystem")
  implicit val materializer: Materializer = Materializer(system)

  def createTopic(topic: String, partitions: Int, replicationFactor: Short): Unit = {
    val props = new Properties()
    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
    
    val adminClient = AdminClient.create(props)

    val topicExists = adminClient.listTopics().names().get().contains(topic)

    if (!topicExists) {
      val newTopic = new NewTopic(topic, partitions, replicationFactor)
      adminClient.createTopics(List(newTopic).asJava).all().get()
      println(s"Topic $topic created")
    } else {
      println(s"Topic $topic already exists")
    }

    adminClient.close()
  }

  // Ensure the topic exists before consuming
  createTopic("chess-moves", 1, 1)

  val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
    .withBootstrapServers("127.0.0.1:9092")
    .withGroupId("group1")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  def startConsuming(topic: String): Unit = {
    Consumer
      .plainSource(consumerSettings, Subscriptions.topics(topic))
      .map(record => record.value())
      .runWith(Sink.foreach(handleKafkaMessage))(materializer)
  }

  def handleKafkaMessage(message: String): Unit = {
    apiClient.exit()
  }

  // Start consuming messages from Kafka
  startConsuming("chess-moves")

  // Initialize the API Client
  val apiClient = new ApiClient

  // Define the Source, Flow, and Sink
  val (queue, source) = Source
    .queue[(String, String)](
      bufferSize = 10,
      overflowStrategy = OverflowStrategy.backpressure
    )
    .preMaterialize()(materializer)

  val flow = Flow[(String, String)].map { case (oldPos, newPos) =>
    (oldPos, newPos)
  }

  val sink: Sink[(String, String), _] =
    Sink.foreach[(String, String)] { case (oldPos, newPos) =>
      apiClient.processMove(oldPos, newPos)
      selection_system.changeState()
      pos1 = ""
      pos2 = ""
    }

  // Connect the Source, Flow, and Sink
  val runnableGraph = source.via(flow).toMat(sink)(Keep.right)
  runnableGraph.run()(materializer)

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
        update
      })
      contents += new MenuItem(Action("Redo") {
        apiClient.redoMove()
        update
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
    reactions += { case ButtonClicked(_) =>
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
