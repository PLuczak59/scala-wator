import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle
import scala.util.Random

case class Tuna(tBreed: Int, tX: Int, tY: Int) {
  def draw: Rectangle = {
    new Rectangle {
      x = tX
      y = tY
      width = 1
      height = 1
      fill = Color.Blue
    }
  }
}

object Tuna {
  def randomDirection: Direction = Random.nextInt(8) match {
    case 0 => Direction.NORTH
    case 1 => Direction.SOUTH
    case 2 => Direction.EAST
    case 3 => Direction.WEST
    case 4 => Direction.NORTHEAST
    case 5 => Direction.NORTHWEST
    case 6 => Direction.SOUTHEAST
    case _ => Direction.SOUTHWEST
  }
}
