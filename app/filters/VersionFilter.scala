package filters

import akka.stream.Materializer
import javax.inject._
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}

/**
 * Add api version to all requests
 *
 * @param mat This object is needed to handle streaming of requests
 * and responses.
 * @param exec This class is needed to execute code asynchronously.
 * It is used below by the `map` method.
 */
@Singleton
class VersionFilter @Inject()(
    implicit override val mat: Materializer,
    exec: ExecutionContext) extends Filter {

  override def apply(nextFilter: RequestHeader => Future[Result])
           (requestHeader: RequestHeader): Future[Result] = {
    // Run the next filter in the chain. This will call other filters
    // and eventually call the action. Take the result and modify it
    // by adding a new header.
    // val version: String = getClass.getPackage.getImplementationVersion
    // play.api.Logger.info("Version: " + version)
    nextFilter(requestHeader).map { result =>
      result.withHeaders("X-API-VERSION" -> "0.1")
    }
  }

}
