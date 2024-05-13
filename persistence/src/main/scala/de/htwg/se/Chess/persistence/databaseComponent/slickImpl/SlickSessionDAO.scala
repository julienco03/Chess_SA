package de.htwg.se.Chess
package persistence
package databaseComponent
package slickImpl

import slick.jdbc.MySQLProfile.api._

case class Session(id: Long, name: String, userId: Long)

trait SessionDao {
  def getGameById(id: Long): Option[Game]
  def createGame(game: Game): Long
  def updateGame(game: Game): Unit
  def deleteGame(id: Long): Unit
  def close(): Unit
  def makeMove(gameId: Long, move: Move): Unit
}

class SlickSessionDao(db: Database) extends SessionDao {
  class Sessions(tag: Tag) extends Table[Session](tag, "sessions") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def userId = column[Long]("user_id")
    def * = (id, name, userId) <> (Session.tupled, Session.unapply)
  }
  
  val sessions = TableQuery[Session]
  
  override def getGameById(id: Long): Option[Game] = {
    val query = games.filter(_.id === id).result.headOption
    db.run(query)
  }

  override def createGame(game: Game): Long = {
    val query = (games returning games.map(_.id)) += game
    db.run(query)
  }

  override def updateGame(game: Game): Unit = {
    val query = games.filter(_.id === game.id).update(game)
    db.run(query)
  }

  override def deleteGame(id: Long): Unit = {
    val query = games.filter(_.id === id).delete
    db.run(query)
  }

  override def close(): Unit = db.close

  override def makeMove(gameId: Long, move: Move): Unit = {
    val query = moves += (gameId, move)
    db.run(query)
  }

}
