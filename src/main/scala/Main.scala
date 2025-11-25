import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.stage.Screen
import scala.language.postfixOps


import scala.language.postfixOps

object Main extends JFXApp3 {

  override def start(): Unit = {
    val (boardWidth, boardHeight): (Int, Int) = (
      Screen.primary.visualBounds.width.intValue,
      Screen.primary.visualBounds.height.intValue
    )

    stage = new PrimaryStage {
      title = "Wator"
      width = boardWidth
      height = boardHeight
      scene = new Scene {

      }
    }
  }
}

