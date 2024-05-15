package de.htwg.se.Chess
package persistence
package slickImpl
package table

import slick.jdbc.MySQLProfile.api._

class Boards(tag: Tag) extends Table[(Int, String)](tag, "boards") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def board = column[String]("board")
    def * = (id, board)
}
