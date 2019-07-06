package com.aiparadigms.nowtv.resources

import akka.http.scaladsl.server.Route
import com.aiparadigms.nowtv.entities.{Customer, CustomerUpdate, Watchlist}
import com.aiparadigms.nowtv.routing.MyResource
import com.aiparadigms.nowtv.services.CustomerService

import scala.concurrent.Future

trait CustomerResource extends MyResource {

    val customerService: CustomerService

    def customerRoutes: Route = pathPrefix("customers") {
        pathEnd {
            post {
                entity(as[Customer]) { customer =>
                    completeWithLocationHeader(
                        resourceId = customerService.createCustomer(customer),
                        ifDefinedStatus = 201, ifEmptyStatus = 409)
                }
            }
        } ~
            path(Segment) { id =>
                get {
                    complete(customerService.getCustomer(id))
                } ~
                    put {
                        entity(as[CustomerUpdate]) { update => {

                            complete(customerService.updateCustomer(id, update))
                        }
                        }
                    } ~
                    delete {
                        complete(customerService.deleteCustomer(id))
                    }
            }

    }

    def watchlistRoutes: Route = pathPrefix("watchlist") {
        pathEnd {
            post {
                entity(as[Customer]) { customer =>
                    completeWithLocationHeader(
                        resourceId = customerService.createCustomer(customer),
                        ifDefinedStatus = 201, ifEmptyStatus = 409)
                }
            }
        } ~
            path(Segment) { id =>
                get {
                    complete({
                        Future {
                            customerService.getCustomerWatchlist(id)
                        }
                        //  customerService.getCustomer(id)
                    })
                } ~
                    put {
                        entity(as[Watchlist]) { update => {
                            complete(customerService.updateWatchlist(id, update))
                        }
                        }
                    } ~
                    delete {
                        parameters('crid) { (crid) =>

                            complete(customerService.deleteContentId(id, crid))
                        }


                    }
            }

    }
}

