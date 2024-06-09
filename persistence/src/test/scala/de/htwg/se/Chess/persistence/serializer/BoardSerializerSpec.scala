package de.htwg.se.Chess
package persistence
package serializer

import model.Board
import persistence.serializer.BoardSerializer

import scala.collection.immutable.VectorMap
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class BoardSerializerSpec extends AnyWordSpec {
  val emptyBoard = Board()
  val emptySerializedBoard =
    "A1:R1;B1:k1;C1:B1;D1:Q1;E1:K1;F1:B1;G1:k1;H1:R1;A2:P1;B2:P1;C2:P1;D2:P1;E2:P1;F2:P1;G2:P1;H2:P1;A3:  ;B3:  ;C3:  ;D3:  ;E3:  ;F3:  ;G3:  ;H3:  ;A4:  ;B4:  ;C4:  ;D4:  ;E4:  ;F4:  ;G4:  ;H4:  ;A5:  ;B5:  ;C5:  ;D5:  ;E5:  ;F5:  ;G5:  ;H5:  ;A6:  ;B6:  ;C6:  ;D6:  ;E6:  ;F6:  ;G6:  ;H6:  ;A7:P2;B7:P2;C7:P2;D7:P2;E7:P2;F7:P2;G7:P2;H7:P2;A8:R2;B8:k2;C8:B2;D8:Q2;E8:K2;F8:B2;G8:k2;H8:R2"

  "A BoardSerializer" should {
    "serialize an empty board to an empty string" in {
      val serialized = BoardSerializer.serializeBoard(emptyBoard)
      serialized shouldBe emptySerializedBoard
    }

    "serialize a non-empty board to a correctly formatted string" in {
      val nonEmptyBoard = Board(VectorMap("A1" -> "R1", "A2" -> "P1"))
      val serialized = BoardSerializer.serializeBoard(nonEmptyBoard)
      serialized shouldBe "A1:R1;A2:P1"
    }

    "deserialize an empty string to an empty board" in {
      val serialized = emptySerializedBoard

      val deserializedBoard = BoardSerializer.deserializeBoard(serialized)
      deserializedBoard shouldBe emptyBoard
    }

    "deserialize a correctly formatted string to the corresponding board" in {
      val serialized = "A1:R1;A2:P1"
      val deserializedBoard = BoardSerializer.deserializeBoard(serialized)
      deserializedBoard shouldBe Board(VectorMap("A1" -> "R1", "A2" -> "P1"))
    }
  }
}
