import scalafx.scene.paint.Color
import scalafx.scene.shape.Rectangle

case class Shark(sBreed: Int, sEnergie: Int, sX: Int, sY: Int) {
  def draw: Rectangle = {
    new Rectangle {
      x = sX
      y = sY
      width = 1
      height = 1
      fill = Color.Red
    }
  }
}



