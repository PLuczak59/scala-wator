import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

case class Tuna(tBreed: Int, tX: Int, tY: Int) extends Fish {
  def breed: Int = tBreed
  def posX: Int = tX
  def posY: Int = tY

  def draw(agentSize: Int): Rectangle = {
    new Rectangle {
      x = tX * agentSize
      y = tY * agentSize
      width = agentSize
      height = agentSize
      fill = Color.Green
    }
  }
  
  private def moveTo(newX: Int, newY: Int): Tuna = {
    copy(tX = newX, tY = newY, tBreed = tBreed + 1)
  }
  
  private def reproduce(): Tuna = {
    Tuna(0, tX, tY)
  }

  def move(boardWidth: Int, boardHeight: Int, fishMap: Map[(Int, Int), Fish], breedThreshold: Int): (Tuna, Option[Tuna]) = {
    val freePositions = findFreePositions(boardWidth, boardHeight, fishMap)

    selectRandomPosition(freePositions) match {
      case None => (this, None)
      case Some((newX, newY)) =>
        if (canBreed(breedThreshold)) {
          val baby = reproduce()
          val movedTuna = copy(tX = newX, tY = newY, tBreed = 0)
          (movedTuna, Some(baby))
        } else {
          (moveTo(newX, newY), None)
        }
    }
  }
}


