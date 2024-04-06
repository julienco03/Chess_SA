package de.htwg.se.Chess

import com.google.inject.Guice
import de.htwg.se.Chess.aview.tui
import de.htwg.se.Chess.controller.ControllerInterface
import de.htwg.se.Chess.model._

import scala.io.StdIn.readLine
import de.htwg.se.Chess.aview.SwingGUI
import scala.util.{Try, Success, Failure}

object Chess extends Thread{
  val injector = Guice.createInjector(new ChessModule)
  val field = Board()
  val controller = injector.getInstance(classOf[ControllerInterface])
  val tui = new tui(controller)
  val gui = new SwingGUI(controller)

  def main(args: Array[String]): Unit = {
    /* TUI AND GUI start */
    var input: String = ""
    while {
      input = readLine("->")
      Try(tui.process(input)) match {
        case Success(_) => true
        case Failure(exception) =>
          println(s"An error occurred: ${exception.getMessage}")
          true
      }
      input != "exit"
    } do()
  }
}