import javax.inject._
import play.api._
import play.api.http.HttpFilters
import play.api.mvc._

import filters.VersionFilter

@Singleton
class Filters @Inject() (
  env: Environment,
  versionFilter: VersionFilter) extends HttpFilters {

  override val filters = {
    if (env.mode == Mode.Dev) Seq(versionFilter) else Seq.empty
  }

}
