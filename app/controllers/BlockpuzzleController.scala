package controllers

import javax.inject._
import play.api.mvc._
import de.htwg.se.blockpuzzle.BlockPuzzle
import play.api.libs.json.JsObject
import play.api.libs.json.Json._
import de.htwg.se.blockpuzzle.controller.Controller
import play.api.libs.streams.ActorFlow
import akka.actor._
import de.htwg.se.blockpuzzle.controller.FieldChanged
import akka.actor.ActorSystem
import akka.stream.Materializer

import scala.swing.Reactor

@Singleton
class BlockpuzzleController @Inject() (cc: ControllerComponents) (implicit system: ActorSystem, mat: Materializer)extends AbstractController(cc){
        val gameController = BlockPuzzle.controller

        def getText(): String ={
                val BlockPuzzleastext = "COUNT: " + gameController.returnCount + "\t HIGHSCORE: " + gameController.returnHighscore +
                  "\n" + gameController.showFieldWithCoordinates() + "\n" + gameController.showBlock(1) +
                  "\n" + gameController.showBlock(2) + "\n" + gameController.showBlock(3) +
                  "\n " + gameController.statusText
                BlockPuzzleastext
        }

        def about= Action {
                Ok(views.html.index())
        }

        def blockpuzzle = Action {
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

        def loadJason = Action {
                Ok(fieldToJson(gameController))
        }


        def websocket = WebSocket.accept[String, String] { request =>
                ActorFlow.actorRef { out =>
                        println("Connection received")
                        BlockWebSocketActorFactory.create(out)
                }

        }

        object BlockWebSocketActorFactory {
                def create(out: ActorRef) = {
                        Props(new BlockWebSocketActor(out))
                }
        }

        class BlockWebSocketActor(out: ActorRef) extends Actor with Reactor{
                listenTo(gameController)

                def receive = {
                        case msg: String =>
                                out ! (fieldToJson(gameController).toString())
                                println("Sent Json to Client"+ msg)
                }

                reactions += {
                        case event: FieldChanged => sendJsonToClient
                }

                def sendJsonToClient = {
                        println("Received event from Controller")
                        out ! (fieldToJson(gameController).toString())
                }
        }

        def blockpuzzlepolymer = Action  {
                Ok(views.html.blockpuzzlepolymer())
        }
}