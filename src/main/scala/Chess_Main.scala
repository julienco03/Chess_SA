package de.htwg.se.Chess

import controller.ControllerInterface
import model._
import aview.tui
import aview.SwingGUI

import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}
import com.google.inject.Guice

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