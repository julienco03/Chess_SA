package de.htwg.se.Chess
package aview

import utils.Observer
import controller.ControllerInterface
import controller.controllerComponent._

import scala.swing._
import javax.swing.border.EmptyBorder
import scala.swing.event.Key
import scala.swing.event.ButtonClicked
import scalafx.scene.input.KeyCode.R
import scalafx.scene.input.KeyCode.G
import scala.swing.event.PopupMenuCanceled
import javax.swing.JOptionPane

class SwingGUI(controller: ControllerInterface) extends Frame with Observer:
    controller.add(this)

    var pos1: String = ""
    var pos2: String = ""
    val selection_system:SelectionSystem = new SelectionSystem()

    title = "Chess Game"
    preferredSize = new Dimension(800, 800)
    resizable = true

    menuBar = new MenuBar {
        contents += new Menu("Game") {
            mnemonic = Key.F
            contents += new Separator
            contents += new MenuItem(Action("Undo") {
                controller.undo()
                update
            })
            contents += new MenuItem(Action("Redo") {
                controller.redo()
                update
            })
            contents += new Separator
            contents += new MenuItem(Action("Quit") {
                println("Bye!")
                System.exit(0)
            })
            contents += new Separator
            contents += new MenuItem(Action("Save") {
                controller.save
            })
            contents += new Separator
            contents += new MenuItem(Action("Load") {
                controller.load
            })
        }
    }
    contents = contentPanel

    def contentPanel = new BorderPanel {
        add(new Label("Welcome to Chess:"), BorderPanel.Position.North)
        add(new CellPanel(), BorderPanel.Position.Center)
        if (GameState.message(controller.game_state) != "")
            infoBox(GameState.message(controller.game_state), GameState.message(controller.game_state));
    }

    override def update: Unit =
        contents = contentPanel

    def infoBox(infoMessage: String, titleBar: String) =
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);

    class CellPanel() extends GridPanel(8, 8):
        border = EmptyBorder(20,20,20,20)
        printField
        def printField =
            controller.field.board.map(x =>
                contents += new CellButton(x._1, x._2))

    class CellButton(pos: String, figure: String) extends Button(figure):
        listenTo(mouse.clicks)
        listenTo(keys)
        reactions += {
            // handle Button Press
            case ButtonClicked(button) =>
                selectionHandler(pos, figure)
        }

    def selectionHandler(pos: String, figure: String) =
        if (pos1 != pos && pos2 != pos)
            if(selection_system.currentState.isInstanceOf[First] &&controller.last_turn() == controller.get_player_c(pos))
                pos1 = pos
                println(pos1)
                selection_system.changeState()
            else if (selection_system.currentState.isInstanceOf[Second])
                pos2 = pos
                println(pos2)
                selection_system.changeState()
                controller.domove()
                controller.move_c(pos1, pos2)
                selection_system.changeState()
                pos1 = ""
                pos2 = ""
        else
            println("Invalid Move" + pos1 + " " + pos2)

    pack()
    centerOnScreen()
    open()