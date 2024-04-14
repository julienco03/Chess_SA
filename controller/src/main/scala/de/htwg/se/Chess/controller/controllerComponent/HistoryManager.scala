package de.htwg.se.Chess
package controller
package controllerComponent

import utils.Command

class HistoryManager {
  var undoStack: List[Command]= Nil
  var redoStack: List[Command]= Nil

  def doMove(command: Command) = {
    undoStack = command::undoStack
    command.doMove
  }

  def undoMove = {
    undoStack match {
      case Nil =>
      case head::stack => {
        head.undoMove
        undoStack=stack
        redoStack= head::redoStack
      }
    }
  }
  def redoMove = {
    redoStack match {
      case Nil =>
      case head::stack => {
        head.redoMove
        redoStack=stack
        undoStack=head::undoStack
      }
    }
  }
}
