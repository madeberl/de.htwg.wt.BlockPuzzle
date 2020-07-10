package controllers

import javax.inject._
import play.api.mvc._
import de.htwg.se.blockpuzzle.BlockPuzzle

@Singleton
class BlockpuzzleController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
        val gameController = BlockPuzzle.controller


        def getText(): String ={
                var BlockPuzzleastext = "COUNT: "+ gameController.returnCount + "\t HIGHSCORE: "+ gameController.returnHighscore +
                  "\n" + gameController.showFieldWithCoordinates() + "\n" + gameController.showBlock(1) +
                  "\n" + gameController.showBlock(2) + "\n" + gameController.showBlock(3)+
                  "\n " + gameController.statusText
                BlockPuzzleastext
        }

        def about= Action {
                Ok(views.html.index())
        }

        def woodblock = Action {
                Ok(views.html.BlockPuzzle(gameController))
        }

        def reset = Action {
                gameController.reset
                Ok(views.html.BlockPuzzle(gameController))
        }
        def add(b:Int, x:Int, y:Int) = Action{
                gameController.addBlock(b,x,y)
                Ok(views.html.BlockPuzzle(gameController))
        }

        def undo = Action {
                gameController.reverse()
                Ok(views.html.BlockPuzzle(gameController))
        }
        def giveup = Action {
                gameController.giveup
                Ok(views.html.BlockPuzzle(gameController))
        }
}
