package de.htwg.se.Chess
package persistence
package databaseComponent

import scala.concurrent.Future
import persistence.databaseComponent.Session

case class Session(id: Int, board: String)

trait SessionDao {
  def getSessionById(id: Int): Future[Option[Session]]
  def createSession(session: Session): Future[Int]
  def updateSession(session: Session): Future[Int]
  def deleteSession(id: Int): Future[Int]
}
