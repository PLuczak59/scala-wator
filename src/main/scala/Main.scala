import scalafx.animation.{KeyFrame, Timeline}
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import scalafx.stage.Screen
import scalafx.util.Duration
import scala.language.postfixOps

object Main extends JFXApp3 {

  private final val agentSize: Int      = 9
  private final val numberOfTunas: Int  = 500
  private final val numberOfSharks: Int = 50
  private final val tunaBreed: Int      = 2
  private final val sharkBreed: Int     = 9
  private final val sharkEnergy: Int    = 3

  override def start(): Unit = {
    val (screenWidth, screenHeight) = (Screen.primary.visualBounds.width.toInt, Screen.primary.visualBounds.height.toInt)
    val (boardWidth, boardHeight)   = (screenWidth / agentSize, screenHeight / agentSize)
    
    stage = new PrimaryStage {
      title = "Wator"
      width = boardWidth
      height = boardHeight
      scene = new Scene {
        content = List.empty
        List.onChange {
          content = List.empty
        }
      }
    }
  
    new Timeline {
      keyFrames = List(
        KeyFrame(
          time = Duration(50),
          onFinished = _ => {
            // Update logic for tunas and sharks would go here
          }
        )
      )
      cycleCount = Timeline.Indefinite
    }.play()
    
  }
}

