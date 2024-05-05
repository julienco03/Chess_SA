package de.htwg.se.Chess

import controller.ControllerInterface
import controller.controllerComponent.Controller
import model.Board
import persistence.fileIOComponent.*
import persistence.fileIOComponent.FileIOjsonImpl.*

import com.google.inject.AbstractModule
import com.google.inject.TypeLiteral
import net.codingwell.scalaguice.ScalaModule

class ChessModule extends AbstractModule {
    override def configure(): Unit = {
        bind(classOf[ControllerInterface]).toInstance(new Controller(field = Board(), fileIO = FileIO()))
        bind[FileIOInterface](new TypeLiteral[FileIOInterface] {}).to(classOf[FileIOjsonImpl.FileIO])
    }
}