package com.aiparadigms.nowtv.entities

import model.UserId

case class CustomerUpdate(id: UserId, watchlist: List[model.ContentID])
