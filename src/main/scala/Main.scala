import javafx.animation.AnimationTimer
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.beans.property.ObjectProperty
import scalafx.scene.Scene
import scalafx.scene.layout.Pane
import scalafx.scene.paint.Color
import scalafx.stage.Screen
import scala.util.Random

object Main extends JFXApp3 {

  private final val agentSize: Int      = 9
  private final val numberOfTunas: Int  = 500
  private final val numberOfSharks: Int = 50
  private final val tunaBreed: Int      = 2
  private final val sharkBreed: Int     = 9
  private final val sharkEnergy: Int    = 3

  private type Position = (Int, Int)
  private type FishMap = Map[Position, Fish]

  private def createFishMap(tunas: List[Tuna], sharks: List[Shark]): FishMap = {
    (tunas.map(t => ((t.posX, t.posY), t: Fish)) ++
     sharks.map(s => ((s.posX, s.posY), s: Fish))).toMap
  }

  private def updateTunas(tunas: List[Tuna], fishMap: FishMap, boardWidth: Int, boardHeight: Int, breedThreshold: Int): List[Tuna] = {
    tunas.foldLeft((List.empty[Tuna], fishMap)) {
      case ((accTunas, accMap), tuna) =>
        val mapWithoutTuna = accMap - ((tuna.posX, tuna.posY))
        val (movedTuna, maybeBaby) = tuna.move(boardWidth, boardHeight, mapWithoutTuna, breedThreshold)
        val updatedMap = mapWithoutTuna + ((movedTuna.posX, movedTuna.posY) -> movedTuna)

        maybeBaby match {
          case Some(baby) =>
            val finalMap = updatedMap + ((baby.posX, baby.posY) -> baby)
            (movedTuna :: baby :: accTunas, finalMap)
          case None =>
            (movedTuna :: accTunas, updatedMap)
        }
    }._1
  }

  private def updateSharks(sharks: List[Shark], tunas: List[Tuna], fishMap: FishMap, boardWidth: Int, boardHeight: Int, breedThreshold: Int, initialEnergy: Int): (List[Shark], List[Tuna]) = {
    val (updatedSharks, updatedTunas, _) = sharks.foldLeft((List.empty[Shark], tunas, fishMap)) {
      case ((accSharks, accTunas, accMap), shark) =>
        val mapWithoutShark = accMap - ((shark.posX, shark.posY))
        val (movedShark, maybeBaby) = shark.move(boardWidth, boardHeight, mapWithoutShark, breedThreshold, initialEnergy)

        val (finalTunas, ateTuna) = mapWithoutShark.get((movedShark.posX, movedShark.posY)) match {
          case Some(_: Tuna) =>
            (accTunas.filterNot(t => t.posX == movedShark.posX && t.posY == movedShark.posY), true)
          case _ =>
            (accTunas, false)
        }

        val mapAfterMove = mapWithoutShark + ((movedShark.posX, movedShark.posY) -> movedShark)

        if (movedShark.isDead) {
          (accSharks, finalTunas, mapAfterMove - ((movedShark.posX, movedShark.posY)))
        } else {
          maybeBaby match {
            case Some(baby) =>
              (baby :: movedShark :: accSharks, finalTunas, mapAfterMove + ((baby.posX, baby.posY) -> baby))
            case None =>
              (movedShark :: accSharks, finalTunas, mapAfterMove)
          }
        }
    }
    (updatedSharks, updatedTunas)
  }

  private def createFishRectangles(tunas: List[Tuna], sharks: List[Shark], size: Int) = {
    tunas.map(_.draw(size)) ++ sharks.map(_.draw(size))
  }

  override def start(): Unit = {
    val (screenWidth, screenHeight) = (Screen.primary.visualBounds.width.toInt, Screen.primary.visualBounds.height.toInt)
    val (boardWidth, boardHeight)   = (screenWidth / agentSize, screenHeight / agentSize)

    val allPositions = (0 until boardWidth).flatMap { x =>
      (0 until boardHeight).map { y =>
        (x, y)
      }
    }.toList

    val shuffledPositions = Random.shuffle(allPositions)
    val (tunaPositions, remainingPositions) = shuffledPositions.splitAt(numberOfTunas)
    val (sharkPositions, _) = remainingPositions.splitAt(numberOfSharks)

    val tunas: List[Tuna] = tunaPositions.map { case (x, y) => Tuna(0, x, y) }
    val sharks: List[Shark] = sharkPositions.map { case (x, y) => Shark(0, sharkEnergy, x, y) }

    val pane = new Pane()
    pane.children = createFishRectangles(tunas, sharks, agentSize)

    stage = new PrimaryStage {
      title = "Wator"
      width = screenWidth
      height = screenHeight
      scene = new Scene {
        fill = Color.Black
        content = pane
      }
    }

    def updateWorld(currentTunas: List[Tuna], currentSharks: List[Shark]): (List[Tuna], List[Shark]) = {
      val currentFishMap = createFishMap(currentTunas, currentSharks)
      val updatedTunas = updateTunas(currentTunas, currentFishMap, boardWidth, boardHeight, tunaBreed)
      val updatedFishMap = createFishMap(updatedTunas, currentSharks)
      val (updatedSharks, finalTunas) = updateSharks(currentSharks, updatedTunas, updatedFishMap, boardWidth, boardHeight, sharkBreed, sharkEnergy)
      (finalTunas, updatedSharks)
    }

    val worldState = ObjectProperty((tunas, sharks))

    new AnimationTimer {
      override def handle(now: Long): Unit = {
        val (currentTunas, currentSharks) = worldState.value
        val (newTunas, newSharks) = updateWorld(currentTunas, currentSharks)
        worldState.value = (newTunas, newSharks)
        pane.children = createFishRectangles(newTunas, newSharks, agentSize)
      }
    }.start()
  }
}
