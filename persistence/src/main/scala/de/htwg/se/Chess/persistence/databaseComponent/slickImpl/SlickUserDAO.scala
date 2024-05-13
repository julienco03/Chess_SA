package de.htwg.se.Chess
package persistence
package databaseComponent
package slickImpl

import slick.jdbc.MySQLProfile.api._

case class User(id: Long, username: String, email: String)

trait UserDao {
  def getUserById(id: Long): Option[User]
}

class SlickUserDao(db: Database) extends UserDao {
  class Users(tag: Tag) extends Table[User](tag, "users") {
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def email = column[String]("email")
    def * = (id, username, email) <> (User.tupled, User.unapply)
  }
  
  val users = TableQuery[Users]
  
  override def getUserById(id: Long): Option[User] = {
    val query = users.filter(_.id === id).result.headOption
    db.run(query)
  }
  
}
