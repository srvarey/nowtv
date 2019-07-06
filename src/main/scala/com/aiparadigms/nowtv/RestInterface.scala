package com.aiparadigms.nowtv

import akka.http.scaladsl.server.Route
import com.aiparadigms.nowtv.resources.CustomerResource
import com.aiparadigms.nowtv.services.CustomerService

import scala.concurrent.ExecutionContext

trait RestInterface extends Resources {

    implicit def executionContext: ExecutionContext

    lazy val customerService = new CustomerService

    val routes: Route = customerRoutes ~ watchlistRoutes

}

trait Resources extends CustomerResource

