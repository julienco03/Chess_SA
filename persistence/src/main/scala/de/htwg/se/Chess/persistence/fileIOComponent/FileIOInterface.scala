package de.htwg.se.Chess
package persistence
package fileIOComponent

import model.Board
import model.BoardInterface

trait FileIOInterface {
  def save(field: BoardInterface) : Unit
  def load() : Board
}
