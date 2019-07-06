package com.aiparadigms.nowtv.entities

import com.aiparadigms.nowtv.entities.model.UserId

object model {
    type ContentID = String
    type UserId = String
}

case class Customer(id: UserId, var watchlist: List[model.ContentID])

case class Watchlist(watchlist: List[model.ContentID])


