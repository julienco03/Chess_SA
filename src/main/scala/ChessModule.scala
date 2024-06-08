package de.htwg.se.Chess

import controller.ControllerInterface
import controller.controllerComponent.Controller
import model.Board
import persistence._
import persistence.xmlImpl.XmlFileIO
import persistence.jsonImpl.JsonFileIO
import persistence.slickImpl.SlickDao
import persistence.mongoImpl.MongoDao

import com.google.inject.AbstractModule
import com.google.inject.TypeLiteral
import net.codingwell.scalaguice.ScalaModule

class ChessModule extends AbstractModule {
  override def configure(): Unit = {
    /* XML */
    // bind[PersistenceInterface](new TypeLiteral[PersistenceInterface] {})
    //   .to(classOf[xmlImpl.XmlFileIO])
    // bind(classOf[ControllerInterface]).toInstance(
    //   new Controller(board = Board(), persistence = XmlFileIO())
    // )

    /* JSON */
    bind[PersistenceInterface](new TypeLiteral[PersistenceInterface] {})
      .to(classOf[jsonImpl.JsonFileIO])
    bind(classOf[ControllerInterface]).toInstance(
      new Controller(board = Board(), persistence = JsonFileIO())
    )

    /* Slick - MySQL */
    // bind[PersistenceInterface](new TypeLiteral[PersistenceInterface] {})
    //   .to(classOf[slickImpl.SlickDao])
    // bind(classOf[ControllerInterface]).toInstance(
    //   new Controller(board = Board(), persistence = SlickDao())
    // )

    /* MongoDB */
    // bind[PersistenceInterface](new TypeLiteral[PersistenceInterface] {})
    //   .to(classOf[mongoImpl.MongoDao])
    // bind(classOf[ControllerInterface]).toInstance(
    //   new Controller(board = Board(), persistence = MongoDao())
    // )
  }
}
