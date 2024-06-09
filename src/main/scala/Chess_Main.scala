package de.htwg.se.Chess

import aview.GUI
import aview.TUI
import controller.ControllerInterface
import rest.Rest

import com.google.inject.Guice

object Chess extends Thread {
  val injector = Guice.createInjector(new ChessModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  val rest = new Rest(controller)
  val tui = new TUI(controller)
  val gui = new GUI(controller)

  def main(args: Array[String]): Unit = tui.start()
}
