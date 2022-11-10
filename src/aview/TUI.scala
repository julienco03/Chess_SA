package de.htwg.se.Chess

import scala.io.StdIn.readLine

class tui {

    var game_quit = false
    val game = new Game().newGame()

    def gameLoop()=
        println(welcomeMessage)
        while(game_quit == false) {
            val in = readLine("->")
            val commando_array = in.split(" ")
            println(commands(in))

            if (commando_array(0) == "move")
                println("Test print Gameboard")
                //println(Game().board_to_string(game))
        }
        System.exit(0)

    def commands(in: String): String =
        val commando_array = in.split(" ")
        commando_array(0) match
            case "start" => start()
            case "exit" => game_quit = true; "Goodbye :)"
            case "help" => helpString
            case "move" => println("Move Befehl")//Game().move(game, commando_array(1), commando_array//(2))
            case _ => errorMessage

    def helpString: String =
        """
        ------------------------------------
        |            HELP TABLE             |
        |-----------------------------------|
        |   start                           |
        |   help              (Display help)|
        |   exit             (Close process)|
        |                                   |
        |   move(x1 y1 x2 y2                |
        |   after start current pos x y     |
        |   then new pos x y {x y x y}      |
        -------------------------------------
        """

    def welcomeMessage: String =
        """
        ------------------------------------
        |       Schach - Chess - Game       |
        |-----------------------------------|
        |      Textbased User Interface     |
        |         HTWG Konstanz 2022        |
        |              v1.0.0               |
        -------------------------------------
        """

    def errorMessage: String =
        "ERROR! Wrong usage! Try \"help\" !"

    def start(): String =
        println("Test print Gameboard and start Game")
        //println(Game().board_to_string(game))
        //Game().board_to_string(game)
}