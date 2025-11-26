import scala.util.Random

case class Fish(fX: Int, fY: Int, nTunas: Int, nSharks: Int) {

}

object Fish {
  def randomPosition(screenWidth: Int, screenHeight: Int, nTunas: Int, nSharks: Int): Fish =
    Fish(Random.nextInt(screenWidth), Random.nextInt(screenHeight), nTunas, nSharks)

}
