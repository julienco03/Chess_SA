package de.htwg.se.Chess
package rest

import controller.ControllerInterface
import utils.Observer

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route
import scala.concurrent.{ExecutionContext, Future}

case class Rest(controller: ControllerInterface) extends Observer {
  controller.add(this)
  override def update: Unit = println()

  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContext = system.dispatcher

  private val PORT = 8080

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
      pathPrefix("chess") {
        this.controller.controllerRoute
      },
      pathPrefix("board") {
        concat(
          this.controller.field.boardRoute
        )
      },
      exitRoute
    )

  val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt("localhost", PORT).bind(route)
}
