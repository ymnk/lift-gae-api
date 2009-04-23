package com.jcraft.lift.snippet

import net.liftweb.http.S
import net.liftweb.util._
import com.google.appengine.api.users.{User, UserService, UserServiceFactory}

class HelloWorld {
  def howdy = {
    val userService = UserServiceFactory.getUserService
    userService.getCurrentUser match{
      case null => 
        S.param("login") match{
          case Empty => 
            <span><a href="?login=">Log in</a></span>  
          case _ => 
            val myuri = S.request.open_!.request.getRequestURI
            val url = userService.createLoginURL(myuri)
            S.redirectTo(url)
        } 
      case user => 
        val logout = userService.createLogoutURL("/")
        <span>
          Hello, {user.getNickname}<br/>
          <a href={logout}>Log out</a>
        </span>
    }
  } 
}
