package de.htwg.se.Chess
package controller
package controllerComponent

import utils.Command
import model.Board

class SolveCommand(controller: Controller) extends Command {
  var memento: Board = controller.field

  override def doMove: Unit = {
    memento = controller.field
  }

  override def undoMove: Unit = {
    val new_memento = controller.field
    controller.field = memento
    memento = new_memento
  }

  override def redoMove: Unit = {
    val new_memento = controller.field
    controller.field = memento
    memento = new_memento
  }
}
