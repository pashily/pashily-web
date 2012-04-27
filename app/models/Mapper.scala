package models

import scalaz._
import Scalaz._

trait Model

trait Mapper {
  trait Builder[T] {
    def apply(value:String):T
  }
}

trait ModelMapper extends Mapper {
  trait ModelBuilder[T <: Model] extends Builder[T]
}

trait HttpModelMapper extends ModelMapper {
  import play.api.http.{ Writeable, ContentTypeOf }
  import play.api.libs.concurrent.Promise
  import play.api.libs.ws._

  def get[R](url: String)(implicit builder: Builder[R]):Promise[R] = {
    WS.url(url).get().map(build _)
  }

  def post[T, R](url: String, body: T)(implicit builder: Builder[R], wrt: Writeable[T], ct: ContentTypeOf[T]):Promise[R] = {
    WS.url(url).post(body).map(build _)
  }

  def put[T, R](url: String, body: T)(implicit builder: Builder[R], wrt: Writeable[T], ct: ContentTypeOf[T]):Promise[R] = {
    WS.url(url).put(body).map(build _)
  }

  def delete[R](url: String)(implicit builder: Builder[R]):Promise[R] = {
    WS.url(url).delete().map(build _)
  }

  def build[R](implicit builder : Builder[R]) = { response:Response =>
    builder(response.body)
  }
}

trait StringBuild {
  self: Mapper =>
  implicit lazy val stringBuilder = new Builder[String] {
    override def apply(value:String):String = value
  }
}

trait JsonBuild {
  self: HttpModelMapper =>

  class JsonModelBuilder[T <: Model](implicit mnf : Manifest[T]) extends ModelBuilder[T] {
    import net.liftweb.json._

    override def apply(json: String):T = {
      implicit val formats = DefaultFormats
      parse(json).extract[T]
    }
  }

  implicit def jsonBuilder[R <: Model](implicit mnf: Manifest[R]) = new JsonModelBuilder[R]
}

trait DefaultBuild extends StringBuild with JsonBuild {
  self: HttpModelMapper =>
}
