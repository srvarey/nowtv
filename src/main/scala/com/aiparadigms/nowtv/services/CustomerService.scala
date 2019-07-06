package com.aiparadigms.nowtv.services

import com.aiparadigms.nowtv.entities.{Customer, CustomerUpdate, Watchlist}

import scala.concurrent.{ExecutionContext, Future}

class
CustomerService(implicit val executionContext: ExecutionContext) {

    var customers = Vector.empty[Customer]

    def createCustomer(customer: Customer): Future[Option[String]] = Future {
        customers.find(_.id == customer.id) match {
            case Some(q) => None // Conflict! id is already taken
            case None =>
                customers = customers :+ customer
                Some(customer.id)
        }
    }

    def getCustomerWatchlist(id: String): Option[Watchlist] = {
        val cust: Option[Customer] = customers.find(_.id == id)
        cust match {
            case Some(c) => Some(Watchlist(c.watchlist))
            case None => None
        }
    }

    def getCustomer(id: String): Future[Option[Customer]] = Future {
        customers.find(_.id == id)
    }

    def updateCustomer(id: String, update: CustomerUpdate): Future[Option[Customer]] = {

        def updateEntity(customer: Customer): Customer = {

            println("updateEntity OLD:  " + customer.watchlist + "    NEW:  " + update.watchlist)

            val watchlist = update.watchlist
            Customer(id, watchlist)
        }


        getCustomer(id).flatMap { maybeCustomer =>
            maybeCustomer match {
                case None => Future {
                    None
                } // No watchlist found, nothing to update
                case Some(customer) =>
                    val updateCustomer = updateEntity(customer)
                    deleteCustomer(id).flatMap { _ =>
                        createCustomer(updateCustomer).map(_ => Some(updateCustomer))
                    }
            }
        }
    }


    def updateWatchlist(id: String, update: Watchlist): Future[Option[Customer]] = {

        val cust = customers.find(_.id == id)
        cust match {
            case Some(c) => for (x <- update.watchlist) {
                if (!c.watchlist.contains(x))
                    c.watchlist = c.watchlist :+ x
            }
            case None => None
        }


        Future {
            cust
        }
    }

    def deleteCustomer(id: String): Future[Unit] = Future {
        customers = customers.filterNot(_.id == id)
    }

    def deleteContentId(id: String, contentID: String): Future[Unit] = Future {
        val cust: Option[Customer] = customers.find(_.id == id)
        cust match {
            case Some(c) => {

                c.watchlist = c.watchlist.filter(_ != contentID)
            }

            case None => None
        }
    }


}

