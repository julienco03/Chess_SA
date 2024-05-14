package de.htwg.se.Chess

import controller.ControllerInterface
import controller.controllerComponent.Controller
import model.Board
import persistence._
import persistence.jsonImpl._
import persistence.slickImpl._

import com.google.inject.AbstractModule
import com.google.inject.TypeLiteral
import net.codingwell.scalaguice.ScalaModule

class ChessModule extends AbstractModule {
    override def configure(): Unit = {
        // bind[PersistenceInterface](new TypeLiteral[PersistenceInterface] {}).to(classOf[jsonImpl.JsonFileIO])
        // bind(classOf[ControllerInterface]).toInstance(new Controller(board = Board(), persistence = JsonFileIO()))

        bind[PersistenceInterface](new TypeLiteral[PersistenceInterface] {}).to(classOf[slickImpl.SlickDao])
        bind(classOf[ControllerInterface]).toInstance(new Controller(board = Board(), persistence = SlickDao()))
    }
}