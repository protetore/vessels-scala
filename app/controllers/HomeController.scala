package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * 
   */
  def index = Action {
    Ok("Vessels API")
  }

  /**
   * A simple Action to be used by health checks
   * Simply returns as text and 200 status but
   * can have more complex tests like database
   * access
   */
  def health = Action {
  	Ok("ok")
  }

}
