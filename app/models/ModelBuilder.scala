package models
trait ModelBuilder[A, T] {
  def apply(value:A):T
}

class JsonModelBuilder[T : Manifest] extends ModelBuilder[String, T] {
  import net.liftweb.json._

  override def apply(json:String):T = {
    implicit val formats = DefaultFormats
    parse(json).extract[T]
  }
}

object Json {
  import dispatch._
  import scala.util.control.Exception._

  def apply[T](u:String)(implicit builder:JsonModelBuilder[T]):Either[Throwable, T] = {
    allCatch either builder(Http(url(u).as_str))
  }
}
