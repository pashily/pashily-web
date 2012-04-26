package models

case class Coco(
  here_id:          String,
  here_body_text:   String,
  here_created_on:  Int,
  author_entity:    AuthorEntry,
  images:           Images,
  spot:             Spot
)

case class AuthorEntry(
  url_name:         String,
  display_name:    String
)

case class Images(
  original:            String,
  decorated:           String,
  decorated_thumbnail: String
)

case class Spot(
  spot_name: String,
  spot_key: String
)

object Coco {
  implicit def builder = new JsonModelBuilder[Coco]


  def get(author:String, hereId:String):Option[Coco] = {
    Json("http://c.hatena.com/%s/h/%s.json".format(author, hereId)) match {
      case Right(c) => Some(c)
      case Left(e) => println(e.getMessage); None
    }
  }
}

