package de.htwg.se.Chess

import controller.ControllerInterface
import rest.Rest

import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}
import com.google.inject.Guice

object Chess extends Thread{
  val injector = Guice.createInjector(new ChessModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  val rest = new Rest(controller)

  def main(args: Array[String]): Unit = {
    while {
      true
    } do()
  }
}
