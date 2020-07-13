package controllers

import javax.inject._
import play.api.mvc._
import de.htwg.se.blockpuzzle.BlockPuzzle
import play.api.libs.json.{JsObject, JsValue, Json}
import play.api.libs.json.Json._
import de.htwg.se.blockpuzzle.controller.Controller

@Singleton
class BlockpuzzleController @Inject() (cc: ControllerComponents) extends AbstractController(cc){
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
        def addChosen(x:Int, y:Int)= Action{
                gameController.addBlock(gameController.getChosenBlock(),x,y)
                Ok(views.html.BlockPuzzle(gameController))
        }
        def setChosen(b:Int)= Action{
                gameController.setChosenBlock(b)
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

        def fieldToJson(controller: Controller): JsObject = {
                obj(
                        "b1" -> toJson(controller.b1.toString()),
                        "b2" -> toJson(controller.b2.toString()),
                        "b3" -> toJson(controller.b3.toString()),
                        "field" -> toJson(controller.field.toString()),
                        "count"-> toJson(controller.returnCount.toString),
                        "highscore" -> toJson(controller.returnHighscore.toString),
                        "statusText" -> toJson(controller.statusText.toString())
                )
        }

        def actionJson = Action {
                Ok(fieldToJson(gameController))
        }
}