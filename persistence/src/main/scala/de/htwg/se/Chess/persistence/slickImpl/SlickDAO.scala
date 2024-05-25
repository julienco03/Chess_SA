package de.htwg.se.Chess
package persistence
package slickImpl

import model.Board
import persistence.slickImpl.table.Boards
import persistence.serializer.BoardSerializer

import com.typesafe.config.ConfigFactory
import concurrent.ExecutionContext.Implicits.global
import scala.collection.immutable.VectorMap
import scala.concurrent.{Await, ExecutionContext}
import scala.concurrent.duration._
import slick.jdbc.MySQLProfile.api._

class SlickDao extends PersistenceInterface {
    implicit val ec: ExecutionContext = ExecutionContext.global

    val config = ConfigFactory.load()
    val db = Database.forConfig("slick.dbs.default", config)

    val boards: TableQuery[Boards] = TableQuery[Boards]
    
    createTablesIfNotExist()

    private def createTablesIfNotExist(): Unit = {
        val setup = DBIO.seq((boards.schema).createIfNotExists)
        val setupFuture = db.run(setup)
        Await.result(setupFuture, 10.seconds)
    }

    override def loadGame(): Board = {
        val queryAction = boards.result.headOption
        val queryFuture = db.run(queryAction)
        val maybeSerializedBoard: Option[(Int, String)] = Await.result(queryFuture, 10.seconds)
        val maybeBoard = maybeSerializedBoard.map(row => BoardSerializer.deserializeBoard(row._2))
        maybeBoard.getOrElse(Board())
    }

    override def saveGame(board: Board): Unit = {
        val serializedBoard = BoardSerializer.serializeBoard(board)
        val insertAction = boards += (0, serializedBoard)
        val insertFuture = db.run(insertAction)
        Await.result(insertFuture, 10.seconds)
    }
}
