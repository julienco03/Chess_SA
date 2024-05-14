package de.htwg.se.Chess
package persistence
package databaseComponent
package slickImpl

import persistence.databaseComponent.{User, UserDao}

import slick.jdbc.MySQLProfile.api._
import scala.concurrent.{Future, ExecutionContext}

class SlickUserDao(db: Database)(implicit ec: ExecutionContext) extends UserDao {
  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def * = (id, name).mapTo[User]
  }

  private val users = TableQuery[Users]

  override def getUserById(id: Int): Future[Option[User]] = {
    val query = users.filter(_.id === id).result.headOption
    db.run(query)
  }

  override def createUser(user: User): Future[Int] = {
    val query = (users returning users.map(_.id)) += user
    db.run(query).map(_.toInt)
  }

  override def updateUser(user: User): Future[Int] = {
    val query = users.filter(_.id === user.id.toInt).update(user)
    db.run(query)
  }

  override def deleteUser(id: Int): Future[Int] = {
    val query = users.filter(_.id === id).delete
    db.run(query)
  }
}
