package de.htwg.se.Chess
package persistence
package databaseComponent

import persistence.User

import com.typesafe.config.Config
import scala.concurrent.Future
import scala.util.Try

trait UserDao(config: Config) {
    def createUser(name: String, passHash: String): Future[Try[User]]

    def readUser(id: Int): Future[Try[User]]
    def readUser(name: String): Future[Try[User]]
    def readHash(id: Int): Future[Try[String]]
    def readHash(name: String): Future[Try[String]]

    def updateUser(id: Int, newName: String): Future[Try[User]]
    def updateUser(name: String, newName: String): Future[Try[User]]

    def deleteUser(id: Int): Future[Try[User]]
    def deleteUser(name: String): Future[Try[User]]

    def close(): Unit
}

object UserDao {
    val maxNameLength = 50
}