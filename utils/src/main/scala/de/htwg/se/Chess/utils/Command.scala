package de.htwg.se.Chess
package utils

trait Command {
  def doMove:Unit
  def undoMove:Unit
  def redoMove:Unit
}
