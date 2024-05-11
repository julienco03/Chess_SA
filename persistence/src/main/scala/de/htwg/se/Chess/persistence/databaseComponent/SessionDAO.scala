package de.htwg.se.Chess
package persistence
package databaseComponent

import model.Board

import akka.http.scaladsl.model.Uri
import com.typesafe.config.Config
import java.sql.Date
import scala.concurrent.Future
import scala.util.Try

trait SessionDao(config: Config) {
    def createSession(userid: Int, sess: Board): Future[Try[Board]]
    def createSession(username: String, sess: Board): Future[Try[Board]]

    def readAllForUser(userid: Int, order: "DESC_DATE"): Future[Try[Seq[Tuple2[Int, Board]]]]
    def readAllForUserInInterval(userid: Int, start: Date, end: Date, order: "DESC_DATE"): Future[Try[Seq[Tuple2[Int, Board]]]]
    def readAllForUserWithName(userid: Int, displayName: String, order: "DESC_DATE"): Future[Try[Seq[Tuple2[Int, Board]]]]
    def readSession(sessionid: Int): Future[Try[Board]]

    def updateSession(sessionid: Int, session: Board): Future[Try[Board]]

    def deleteSession(sessionid: Int): Future[Try[Board]]

    def close(): Unit
}