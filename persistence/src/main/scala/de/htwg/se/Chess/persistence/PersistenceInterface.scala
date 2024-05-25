package de.htwg.se.Chess
package persistence

import model.Board

trait PersistenceInterface {
    def loadGame() : Board              // GET
    def saveGame(board: Board) : Unit   // POST
    def updateGame(board: Board): Unit  // PUT
    def deleteGame(): Unit              // DELETE
}
