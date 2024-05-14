package de.htwg.se.Chess
package persistence
package databaseComponent

import scala.concurrent.Future

case class User(id: Int, name: String)

trait UserDao {
  def getUserById(id: Int): Future[Option[User]]
  def createUser(user: User): Future[Int]
  def updateUser(user: User): Future[Int]
  def deleteUser(id: Int): Future[Int]
}
