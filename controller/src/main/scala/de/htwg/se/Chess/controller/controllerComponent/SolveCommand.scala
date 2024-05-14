package de.htwg.se.Chess
package controller
package controllerComponent

import utils.Command
import model.Board

class SolveCommand(controller: Controller) extends Command {
  var memento: Board = controller.board

  override def doMove: Unit = {
    memento = controller.board
  }

  override def undoMove: Unit = {
    val new_memento = controller.board
    controller.board = memento
    memento = new_memento
  }

  override def redoMove: Unit = {
    val new_memento = controller.board
    controller.board = memento
    memento = new_memento
  }
}
