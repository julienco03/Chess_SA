package de.htwg.se.Chess
package persistence
package mongoImpl

import model.Board
import persistence.serializer.BoardSerializer

import org.mongodb.scala._
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.model.Updates._
import org.mongodb.scala.result.{DeleteResult, InsertOneResult, UpdateResult}
import play.api.libs.json._

import scala.collection.immutable.VectorMap
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

class MongoDao extends PersistenceInterface {
  private val client = MongoClient("mongodb://localhost:27017")
  private val db: MongoDatabase = client.getDatabase("chess_db")
  private val boardCollection: MongoCollection[Document] = db.getCollection("board")

  override def loadGame(): Board = {
    val future: Future[Option[Document]] = boardCollection.find().first().toFutureOption()
    val maybeDoc: Option[Document] = Await.result(future, 10.seconds)

    maybeDoc.flatMap { doc =>
      val serializedBoard = doc.getString("board")
      Some(BoardSerializer.deserializeBoard(serializedBoard))
    }.getOrElse(Board(VectorMap.empty))
  }

  override def saveGame(board: Board): Unit = {
    val serializedBoard = BoardSerializer.serializeBoard(board)
    val document = Document("board" -> serializedBoard)
    
    val future: Future[InsertOneResult] = boardCollection.insertOne(document).toFuture()
    Await.result(future, 10.seconds)
  }
}
