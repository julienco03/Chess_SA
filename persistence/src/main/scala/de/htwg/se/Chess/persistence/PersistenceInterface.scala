package de.htwg.se.Chess
package persistence

import model.Board

trait PersistenceInterface {
    def saveGame(board: Board) : Unit
    def loadGame() : Board
}
