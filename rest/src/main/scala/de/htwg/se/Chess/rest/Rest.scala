package de.htwg.se.Chess
package rest

import controller.ControllerInterface
import utils.Observer

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import scala.concurrent.{ExecutionContext, Future}

case class Rest(controller: ControllerInterface) extends Observer {
  controller.add(this)
  override def update: Unit = println() // nothing to do

  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContext = system.dispatcher

  private val PORT = 8080

  val WELCOME_STRING =
    """
      <h1>Welcome to Chess API!</h1>
      <hr>
      <h3>Available routes:</h3>
      <ul>
        <li><a href="/chess/new">GET           ->     chess/new</a></li>
        <li><a href="/chess/move/A2/A4">POST           ->     chess/move/A2/A4</a></li>
        <li><a href="/chess/undo">GET           ->     chess/undo</a></li>
        <li><a href="/chess/redo">GET           ->     chess/redo</a></li>
        <li><a href="/chess/load">GET           ->     chess/load</a></li>
        <li><a href="/chess/save">GET           ->     chess/save</a></li>
        <li><a href="/board/">GET           ->     board/</a></li>
        <li><a href="/exit">GET           ->     exit</a></li>
      </ul>
      <br>
    """.stripMargin

  val homeRoute =
    pathSingleSlash {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, WELCOME_STRING))
      }
    }

  val exitRoute =
    path("exit") {
      get {
          bindingFuture
              .flatMap(_.unbind()) // trigger unbinding from the port
              .onComplete(_ => system.terminate()) // and shutdown when done
              System.exit(0) // terminate TUI and GUI
          complete("Chess API terminated.")
      }
    }

  val route = concat(
    homeRoute,
    exitRoute,
    pathPrefix("chess") {
      this.controller.controllerRoute
    },
    pathPrefix("board") {
      concat(
        this.controller.field.boardRoute
      )
    }
  )

  val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt("localhost", PORT).bind(route)
}
