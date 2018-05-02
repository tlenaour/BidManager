package com.tln.app

import com.tln.domain.{Bid, BidWinner}

object BidManager {

  def getBidWinner(bids : List[Bid], reservePrice : Int) : Option[BidWinner]  = {

    val bidsHigherOrEqualsThanReservePrice : List[Bid] = bids.sortBy(_.value).dropWhile(_.value <= reservePrice)

    bidsHigherOrEqualsThanReservePrice match {
      case Nil => None
      case _ =>
        val bidWinnerBuyer: String = bidsHigherOrEqualsThanReservePrice.last.buyer
        val bidsWithoutWinner: List[Bid] = bidsHigherOrEqualsThanReservePrice.filter(_.buyer != bidWinnerBuyer)
        val finalBidPrice = getFinalBidPrice(bidsWithoutWinner,reservePrice)
        Some(BidWinner(bidWinnerBuyer, finalBidPrice))
    }
  }

  def getFinalBidPrice(bidsWithoutWinner : List[Bid], reservePrice : Float) : Float = {
    bidsWithoutWinner match {
      case Nil => reservePrice
      case _  => bidsWithoutWinner.last.value
    }
  }
}
