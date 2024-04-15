package de.htwg.se.Chess

import controller.ControllerInterface
import controller.controllerComponent.Controller
import model.Board
import persistence.fileIOComponent.*
import persistence.fileIOComponent.FileIOInterface
import persistence.fileIOComponent.*

import com.google.inject.AbstractModule
import com.google.inject.TypeLiteral
import net.codingwell.scalaguice.ScalaModule

/*
* Uncoment the line below and in the binds down under to Save and Load
* from XML
*/
import persistence.fileIOComponent.FileIOxmlImpl.FileIO

/*
* Uncoment the line below and in the binds down under to Save and Load
* from JSON
*/
//import persistence.fileIOComponent.FileIOjsonImpl.FileIO


class ChessModule extends AbstractModule {
    override def configure(): Unit = {
        bind(classOf[ControllerInterface]).toInstance(new Controller(field = Board(), fileIO = FileIO()))

        /*
         * Uncoment the line below and in the Imports above to Save and Load
         * from XML
        */

        bind[FileIOInterface](new TypeLiteral[FileIOInterface] {}).to(classOf[FileIOxmlImpl.FileIO])

        /*
         * Uncoment the line below and in the Imports above to Save and Load
         * from JSON
        */

        //bind[FileIOInterface](new TypeLiteral[FileIOInterface] {}).to(classOf[FileIOjsonImpl.FileIO])
    }
}