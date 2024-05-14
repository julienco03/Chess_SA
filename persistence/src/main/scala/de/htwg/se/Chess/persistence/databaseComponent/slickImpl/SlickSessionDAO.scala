package de.htwg.se.Chess
package persistence
package databaseComponent
package slickImpl

import model.Board
import persistence.databaseComponent.{Session, SessionDao}

import slick.jdbc.MySQLProfile.api._
import scala.concurrent.{Future, ExecutionContext}

class SlickSessionDao(db: Database)(implicit ec: ExecutionContext) extends SessionDao {
  class Sessions(tag: Tag) extends Table[Session](tag, "sessions") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def board = column[String]("board")
    def * = (id, board).mapTo[Session]
  }

  private val sessions = TableQuery[Sessions]

  override def getSessionById(id: Int): Future[Option[Session]] = {
    val query = sessions.filter(_.id === id).result.headOption
    db.run(query)
  }

  override def createSession(session: Session): Future[Int] = {
    val query = (sessions returning sessions.map(_.id)) += session
    db.run(query).map(_.toInt)
  }

  override def updateSession(session: Session): Future[Int] = {
    val query = sessions.filter(_.id === session.id.toInt).update(session)
    db.run(query)
  }

  override def deleteSession(id: Int): Future[Int] = {
    val query = sessions.filter(_.id === id).delete
    db.run(query)
  }
}
