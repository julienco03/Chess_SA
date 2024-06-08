package de.htwg.se.Chess
package aview

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.Materializer

import scala.concurrent.{ExecutionContext, Future}
import com.typesafe.config.ConfigFactory

class ApiClient(implicit
    val system: ActorSystem,
    val materializer: Materializer
) {
  implicit val executionContext: ExecutionContext = system.dispatcher

  val config = ConfigFactory.load()
  val SERVER_PORT: String = config.getString("server.port")
  val SERVER_HOST: String = config.getString("server.host")
  val BASE_URL = s"http://$SERVER_HOST:$SERVER_PORT"

  private def sendRequest(httpRequest: HttpRequest): Future[HttpResponse] = {
    Http().singleRequest(httpRequest)
  }

  def newGame(): Future[HttpResponse] = {
    val url = s"$BASE_URL/chess/new"
    val httpRequest = HttpRequest(method = HttpMethods.GET, uri = url)
    sendRequest(httpRequest)
  }

  def processMove(pos1: String, pos2: String): Future[HttpResponse] = {
    val url = s"$BASE_URL/chess/move/$pos1/$pos2"
    val httpRequest = HttpRequest(method = HttpMethods.GET, uri = url)
    sendRequest(httpRequest)
  }

  def undoMove(): Future[HttpResponse] = {
    val url = s"$BASE_URL/chess/undo"
    val httpRequest = HttpRequest(method = HttpMethods.GET, uri = url)
    sendRequest(httpRequest)
  }

  def redoMove(): Future[HttpResponse] = {
    val url = s"$BASE_URL/chess/redo"
    val httpRequest = HttpRequest(method = HttpMethods.GET, uri = url)
    sendRequest(httpRequest)
  }

  def saveGame(): Future[HttpResponse] = {
    val url = s"$BASE_URL/chess/save"
    val httpRequest = HttpRequest(method = HttpMethods.GET, uri = url)
    sendRequest(httpRequest)
  }

  def loadGame(): Future[HttpResponse] = {
    val url = s"$BASE_URL/chess/load"
    val httpRequest = HttpRequest(method = HttpMethods.GET, uri = url)
    sendRequest(httpRequest)
  }

  def exit(): Future[HttpResponse] = {
    val url = s"$BASE_URL/exit"
    val httpRequest = HttpRequest(method = HttpMethods.GET, uri = url)
    sendRequest(httpRequest)
  }
}
