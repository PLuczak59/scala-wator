import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

case class Shark(sBreed: Int, sEnergie: Int, sX: Int, sY: Int) extends Fish {
  def breed: Int = sBreed
  def posX: Int = sX
  def posY: Int = sY

  def draw(agentSize: Int): Rectangle = {
    new Rectangle {
      x = sX * agentSize
      y = sY * agentSize
      width = agentSize
      height = agentSize
      fill = Color.Red
    }
  }

  def isDead: Boolean = sEnergie <= 0

  private def reproduce(initialEnergy: Int): Shark = {
    Shark(0, initialEnergy, sX, sY)
  }

  private def moveTo(newX: Int, newY: Int, ate: Boolean): Shark = {
    val energyChange = if (ate) 0 else -1
    copy(sX = newX, sY = newY, sBreed = sBreed + 1, sEnergie = sEnergie + energyChange)
  }

  def move(boardWidth: Int, boardHeight: Int, fishMap: Map[(Int, Int), Fish], breedThreshold: Int, initialEnergy: Int): (Shark, Option[Shark]) = {
    val tunaPositions = findTunaPositions(boardWidth, boardHeight, fishMap)

    selectRandomPosition(tunaPositions) match {
      case Some((newX, newY)) =>
        if (canBreed(breedThreshold)) {
          val baby = reproduce(initialEnergy)
          val movedShark = copy(sX = newX, sY = newY, sBreed = 0, sEnergie = sEnergie)
          (movedShark, Some(baby))
        } else {
          (moveTo(newX, newY, ate = true), None)
        }
      case None =>
        val freePositions = findFreePositions(boardWidth, boardHeight, fishMap)
        selectRandomPosition(freePositions) match {
          case Some((newX, newY)) =>
            if (canBreed(breedThreshold)) {
              val baby = reproduce(initialEnergy)
              val movedShark = copy(sX = newX, sY = newY, sBreed = 0, sEnergie = sEnergie - 1)  // Coût de déplacement
              (movedShark, Some(baby))
            } else {
              (moveTo(newX, newY, ate = false), None)
            }
          case None =>
            (this, None)
        }
    }
  }
}

