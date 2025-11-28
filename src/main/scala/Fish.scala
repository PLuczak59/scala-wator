import scalafx.scene.shape.Rectangle
import scala.util.Random

trait Fish {
  def breed: Int
  def posX: Int
  def posY: Int
  def draw(agentSize: Int): Rectangle
  
  private def getNeighbors(boardWidth: Int, boardHeight: Int): List[(Int, Int)] = {
    val directions = List(
      (-1, -1), (0, -1), (1, -1),
      (-1, 0),           (1, 0),
      (-1, 1),  (0, 1),  (1, 1)
    )
    
    directions.map { case (dx, dy) =>
      val newX = (posX + dx + boardWidth) % boardWidth
      val newY = (posY + dy + boardHeight) % boardHeight
      (newX, newY)
    }
  }
  
  protected def findFreePositions(boardWidth: Int, boardHeight: Int, fishMap: Map[(Int, Int), Fish]): List[(Int, Int)] = {
    getNeighbors(boardWidth, boardHeight).filterNot(fishMap.contains)
  }
  
  protected def findTunaPositions(boardWidth: Int, boardHeight: Int, fishMap: Map[(Int, Int), Fish]): List[(Int, Int)] = {
    getNeighbors(boardWidth, boardHeight).collect {
      case pos if fishMap.get(pos).exists(_.isInstanceOf[Tuna]) => pos
    }
  }
  
  protected def selectRandomPosition(positions: List[(Int, Int)]): Option[(Int, Int)] = {
    positions match {
      case Nil => None
      case pos => Some(pos(Random.nextInt(pos.length)))
    }
  }
  
  protected def canBreed(breedThreshold: Int): Boolean = {
    breed + 1 >= breedThreshold
  }
}
