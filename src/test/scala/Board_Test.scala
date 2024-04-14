package de.htwg.se.Chess

import model._

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatest.matchers.must.Matchers
import scala.collection.immutable.VectorMap

class BoardSpec extends AnyWordSpec:
  val Test = Board()
  val eol = sys.props("line.separator")

  val test_map = VectorMap("A1"->"R1", "B1"-> "k1", "C1"->"B1", "D1"->"Q1", "E1"->"K1", "F1"->"B1", "G1"->"k1", "H1"->"R1", "A2"->"P1", "B2"->"P1", "C2"->"P1", "D2"->"P1", "E2"->"P1", "F2"->"P1", "G2"->"P1", "H2"->"P1","A7"->"P2", "B7"->"P2", "C7"->"P2", "D7"->"P2", "E7"->"P2", "F7"->"P2", "G7"->"P2", "H7"->"P2", "A8"->"R2", "B8"->"k2", "C8"->"B2", "D8"->"Q2", "E8"->"K2", "F8"->"B2", "G8"->"k2", "H8"->"R2")

  val empty_map = VectorMap("A1"->"  ", "B1"->"  ", "C1"->"  ", "D1"->"  ", "E1"->"  ", "F1"->"  ", "G1"->"  ", "H1"->"  ", "A2"->"  ", "B2"->"  ", "C2"->"  ", "D2"->"  ", "E2"->"  ", "F2"->"  ", "G2"->"  ", "H2"->"  ","A3"->"  ", "B3"->"  ", "C3"->"  ", "D3"->"  ", "E3"->"  ", "F3"->"  ", "G3"->"  ", "H3"->"  ", "A4"->"  ", "B4"->"  ", "C4"->"  ", "D4"->"  ", "E4"->"  ", "F4"->"  ", "G4"->"  ", "H4"->"  ", "A5"->"  ", "B5"->"  ", "C5"->"  ", "D5"->"  ", "E5"->"  ", "F5"->"  ", "G5"->"  ", "H5"->"  ", "A6"->"  ", "B6"->"  ", "C6"->"  ", "D6"->"  ", "E6"->"  ", "F6"->"  ", "G6"->"  ", "H6"->"  ", "A7"->"  ", "B7"->"  ", "C7"->"  ", "D7"->"  ", "E7"->"  ", "F7"->"  ", "G7"->"  ", "H7"->"  ", "A8"->"  ", "B8"->"  ", "C8"->"  ", "D8"->"  ", "E8"->"  ", "F8"->"  ", "G8"->"  ", "H8"->"  ")

  val test_field = empty_map.++(test_map)

  val expected_field = "   A    B    C    D    E    F    G    H  " + """
/----+----+----+----+----+----+----+----\
| R1 | k1 | B1 | Q1 | K1 | B1 | k1 | R1 | 1
+----+----+----+----+----+----+----+----+
| P1 | P1 | P1 | P1 | P1 | P1 | P1 | P1 | 2
+----+----+----+----+----+----+----+----+
|    |    |    |    |    |    |    |    | 3
+----+----+----+----+----+----+----+----+
|    |    |    |    |    |    |    |    | 4
+----+----+----+----+----+----+----+----+
|    |    |    |    |    |    |    |    | 5
+----+----+----+----+----+----+----+----+
|    |    |    |    |    |    |    |    | 6
+----+----+----+----+----+----+----+----+
| P2 | P2 | P2 | P2 | P2 | P2 | P2 | P2 | 7
+----+----+----+----+----+----+----+----+
| R2 | k2 | B2 | Q2 | K2 | B2 | k2 | R2 | 8
\----+----+----+----+----+----+----+----/""" + eol

  /**
  * Playfield Tests
  */
  "have a top_row as string of form " +
  "   A    B    C    D    E    F    G    H  " in {
    Test.top_row() shouldBe("   A    B    C    D    E    F    G    H  " + eol)
  }

  "have a border_row as string of form " +
  "+----+----+----+----+----+----+----+----+" in {
    Test.border_row() shouldBe("+----+----+----+----+----+----+----+----+" +eol)
  }

  "have a First row as String of form " +
  """/----+----+----+----+----+----+----+----\""" in {
    Test.first_last_row() shouldBe(
      """/----+----+----+----+----+----+----+----\""" + eol)
  }

  "have a Border row as string of form |----+----+----+----+----+----+---+----|" in {
    Test.border_row() shouldBe("+----+----+----+----+----+----+----+----+" +eol)
  }

  //"board_to_string(test_field)" in {
  //  Test.board_to_string() shouldBe (expected_field)
  //}

  "empty_field(test_field, 'A5') should be true (Empty Field)" in {
    Test.empty_field("A5") shouldBe (true)
  }

  "match_pattern() Invalid" in {
    Test.match_pattern(Option(null)) shouldBe ("Invalid")
  }

  "match_pattern() P1" in {
    Test.match_pattern(Option("P1")) shouldBe ("P1")
  }

  def combine_map_test(test_map: Board, pos_old: String, pos_new: String): Board =
    val figure = test_map.board.get(pos_old)
    val map_new = test_map.board + (pos_old -> "  ", pos_new -> Test.match_pattern(figure))
    val map_test_combined = empty_map.++(map_new)
    return Board(map_test_combined)

  val test_map_0 = combine_map_test(Board(test_field), "A2", "A4")

  // Valid Moves

  "move(map, A2, A4) (P1=> empty field)" in {
    Test.move("A2", "A4") shouldBe (test_map_0)
  }

  val test_map_1 = combine_map_test(test_map_0, "A1", "A3")

  "move(map, A1, A3) (R1=> empty field)" in {
    test_map_0.move("A1", "A3") shouldBe (test_map_1)
  }

  val test_map_2 = combine_map_test(test_map_1, "G1", "H3")

  "move(map, G1, H3) (k1=> empty field)" in {
    test_map_1.move("G1", "H3") shouldBe (test_map_2)
  }

  val test_map_3 = combine_map_test(test_map_2, "B2", "B3")

  "move(map, B2, B3) (P1=> empty field)" in {
    test_map_2.move("B2", "B3") shouldBe (test_map_3)
  }

  val test_map_4 = combine_map_test(test_map_3, "C1", "B2")

  "move(map, C1, B2) (B1=> empty field)" in {
    test_map_3.move("C1", "B2") shouldBe (test_map_4)
  }

  val test_map_5 = combine_map_test(test_map_4, "C2", "C4")

  "move(map, C2, C4) (P1=> empty field)" in {
    test_map_4.move("C2", "C4") shouldBe (test_map_5)
  }

  val test_map_6 = combine_map_test(test_map_5, "D1", "C2")

  "move(map, D1, C2) (Q1=> empty field)" in {
    test_map_5.move("D1", "C2") shouldBe (test_map_6)
  }

  val test_map_7 = combine_map_test(test_map_6, "D2", "D3")

  "move(map, D2, D3) (P1=> empty field)" in {
    test_map_6.move("D2", "D3") shouldBe (test_map_7)
  }

  val test_map_8 = combine_map_test(test_map_7, "E1", "D1")

  "move(map, E1, D1) (K1=> empty field)" in {
    test_map_7.move("E1", "D1") shouldBe (test_map_8)
  }

  val test_map_9 = combine_map_test(test_map_8, "A7", "A6")

  "move(map, A7, A6) (P2 one forward)" in {
    test_map_8.move("A7", "A6") shouldBe (test_map_9)
  }

  val test_map_10 = combine_map_test(test_map_9, "B7", "B5")

  "move(map, B7, B5) (P2 two forward)" in {
    test_map_9.move("B7", "B5") shouldBe (test_map_10)
  }

  val test_map_11 = combine_map_test(test_map_10, "A4", "B5")

  "move(map, A4, B5) (P1 Attack P2)" in {
    test_map_10.move("A4", "B5") shouldBe (test_map_11)
  }

  val test_map_12 = combine_map_test(test_map_11, "A6", "B5")

  "move(map, A6, B5) (P2 Attack P1)" in {
    test_map_11.move("A6", "B5") shouldBe (test_map_12)
  }

  val test_map_13 = combine_map_test(test_map_12, "C2", "C3")

  "move(map, C2, C3) (Q1 y-axis)" in {
    test_map_12.move("C2", "C3") shouldBe (test_map_13)
  }

  val test_map_14 = combine_map_test(test_map_13, "D1", "D2")

  "move(map, D1, D2) (K1 y-axis)" in {
    test_map_13.move("D1", "D2") shouldBe (test_map_14)
  }


  val test_map_15 = combine_map_test(test_map_14, "H3", "F4")

  "move(map, H3, F4) (k1 horizontal)" in {
    test_map_14.move("H3", "F4") shouldBe (test_map_15)
  }

// Invalid Moves

  val start_field = Board(test_field)

  "move(map, D1, C2) (K1=> Blocked/Invalid Move)" in {
    start_field.move("D1", "C2") shouldBe (start_field)
  }

  "move(map, C3, D2) (Q1=> Blocked/Invalid Move)" in {
    start_field.move("C3", "D2") shouldBe (start_field)
  }

  "move(map, B1, D2) (k1=> Blocked/Invalid Move)" in {
    start_field.move("B1", "D2") shouldBe (start_field)
  }

  "move(map, A3, B3) (R1=> Blocked/Invalid Move)" in {
    start_field.move("A3", "B3") shouldBe (start_field)
  }

  "move(map, C1, B2) (B1=> Blocked/Invalid Move)" in {
    start_field.move("C1", "B2") shouldBe (start_field)
  }

  "move(map, E2, E5) (k1=> Blocked/Invalid Move)" in {
    start_field.move("E2", "E5") shouldBe (start_field)
  }

// Single Methode Tests

  val x_map = Map("A"->1)
  val option_I = x_map.get("B")

  "match_pattern(null) should be 0" in {
    Test.match_pattern(option_I) should be (0)
  }
