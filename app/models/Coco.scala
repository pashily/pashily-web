package models

case class Coco(
  here_id:          String,
  here_body_text:   String,
  here_created_on:  Int,
  author_entity:    AuthorEntry,
  images:           Images,
  spot:             Spot
) extends Model

case class AuthorEntry(
  url_name:         String,
  display_name:    String
) extends Model

case class Images(
  original:            String,
  decorated:           String,
  decorated_thumbnail: String
) extends Model

case class Spot(
  spot_name: String,
  spot_key: String
) extends Model

object Coco extends HttpModelMapper with DefaultBuild {
  import play.api.libs.concurrent._

  def find(author:String, hereId:String) = {
    get[Coco]("http://c.hatena.com/%s/h/%s.json".format(author, hereId))
  }
}
