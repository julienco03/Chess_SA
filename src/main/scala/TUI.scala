package de.htwg.se.Chess

import scala.io.StdIn.readLine

val game = new Game

def playerSetup() =
    println("Player One: enter your Name: ")
    val playerOne = readLine()
    println("Player Two: enter your Name: ")
    val playerTwo = readLine()
    println(s"Welcome $playerOne and $playerTwo")

def gameLoop() =
    println(welcomeMessage)
    while(true) {
        val in = readLine("->")
        commands(in)
    }

def commands(in: String) =
    val commando_array = in.split(" ")
    println("start the game")
    commando_array(0) match
        case "start" => start()
        case "exit" => exit()
        case "help" => println(helpString)
        case "move" => game.move(commando_array(1), commando_array(2), commando_array(3), commando_array(4))
        case _ => println(errorMessage)

def exit() =
    println("Goodbye!")
    System.exit(0)

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

def errorMessage: String = "ERROR! Wrong usage! Try \"help\" !"

def start() =
    playerSetup()
    game.newGame()
    println(game.board())