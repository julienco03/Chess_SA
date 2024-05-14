// package de.htwg.se.Chess
// package persistence
// package persistence
// package jsonImpl

// import model.*
// import persistence.PersistenceInterface

// import java.io._
// import scala.io.Source
// import scala.collection.immutable.ListMap
// import scala.collection.immutable.VectorMap
// import scala.languageFeature.postfixOps
// import scalafx.stage.FileChooser
// import scalafx.stage.FileChooser.ExtensionFilter
// import play.api.libs.json._

// class FileIO extends PersistenceInterface {

//   override def load(): Board = {
//     // load game from file
//     val source: String = Source.fromFile("board.json").getLines.mkString
//     val json: JsValue = Json.parse(source)

//     def updateBoard(index: Int, board: VectorMap[String, String]): VectorMap[String, String] = {
//       if (index < 8 * 8) {
//         val pos = (json \\ "pos")(index).as[String]
//         val figure = (json \\ "figure")(index).as[String]
//         val updatedBoard = board.updated(pos, figure)
//         updateBoard(index + 1, updatedBoard)
//       } else {
//         board
//       }
//     }

//     val initialBoard: VectorMap[String, String] = VectorMap[String, String]()
//     val finalBoard = updateBoard(0, initialBoard)
//     Board(finalBoard)
//   }

//   override def save(game: BoardInterface) =
//     // write game to file
//     val pw = new PrintWriter(new File("board.json"))
//     pw.write(vectorMapToJson(game))
//     pw.close

//   def vectorMapToJson(board_object: BoardInterface): String = {
//     val tmp: Seq[(String, String)] = board_object.board.toSeq // Convert to Seq of tuples

//     def convertEntriesToJson(entries: Seq[(String, String)], acc: List[JsObject]): List[JsObject] = {
//       entries match {
//         case Nil => acc.reverse
//         case (pos, figure) :: tail =>
//           val entryJson = Json.obj(
//             "pos" -> pos,
//             "figure" -> figure
//           )
//           convertEntriesToJson(tail, entryJson :: acc)
//       }
//     }

//     val boardEntries = convertEntriesToJson(tmp, Nil)
//     val jsonData = Json.obj(
//       "board" -> Json.obj(
//         "board_entry" -> Json.toJson(boardEntries)
//       )
//     )
//     Json.stringify(jsonData)
//   }

// }
