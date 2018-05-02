package com.tln.app

import com.tln.domain.{Bid, BidWinner}
import org.scalatest.{GivenWhenThen, Matchers, WordSpec}

class BidManagerTest extends WordSpec with GivenWhenThen with Matchers {

  "getBidWinner" should {
    "return the BidWinner from a List of Bid" in {
      Given("a List of Bid and a reservePrice")
      val givenListOfBid = Bid("A", 110) :: Bid("A", 130) ::
        Bid("C", 125) ::
        Bid("D", 105) :: Bid("D", 115) :: Bid("D", 90) ::
        Bid("E", 132) :: Bid("E", 135) :: Bid("E", 140) :: Nil
      val givenReservePrice = 100

      When("getBidWinner")
      val result = BidManager.getBidWinner(givenListOfBid, givenReservePrice)

      Then("The BidWinner is returned")
      result should be(Some(BidWinner("E", 130)))
    }

    "return None BidWinner" in {
      Given("a List of Bid lower than the reserve price")
      val givenListOfBid = Bid("A", 110) :: Bid("A", 130) ::
        Bid("C", 125) ::
        Bid("D", 105) :: Bid("D", 115) :: Bid("D", 90) ::
        Bid("E", 132) :: Bid("E", 135) :: Bid("E", 140) :: Nil
      val givenReservePrice = 160

      When("getBidWinner")
      val result = BidManager.getBidWinner(givenListOfBid, givenReservePrice)

      Then("No BidWinner has been found")
      result should be(None)
    }


    "return BidWinner with the reserve price when only bids from winner are higher than reserve price" in {
      Given("a List of Bid lower than the reserve price")
      val givenListOfBid = Bid("A", 110) :: Bid("A", 130) ::
        Bid("C", 125) ::
        Bid("D", 105) :: Bid("D", 115) :: Bid("D", 90) ::
        Bid("E", 132) :: Bid("E", 135) :: Bid("E", 140) :: Nil
      val givenReservePrice = 131

      When("getBidWinner")
      val result = BidManager.getBidWinner(givenListOfBid, givenReservePrice)

      Then("The BidWinner is returned with the reserve price")
      result should be(Some(BidWinner("E", 131)))
    }

    "return BidWinner with the reserve price when only bids from winner are higher than reserve price but a non winner bid equal to reserve price" in {
      Given("a List of Bid lower than the reserve price")
      val givenListOfBid = Bid("A", 110) :: Bid("A", 131) ::
        Bid("C", 125) ::
        Bid("D", 105) :: Bid("D", 115) :: Bid("D", 90) ::
        Bid("E", 132) :: Bid("E", 135) :: Bid("E", 140) :: Nil
      val givenReservePrice = 131

      When("getBidWinner")
      val result = BidManager.getBidWinner(givenListOfBid, givenReservePrice)

      Then("The BidWinner is returned with the reserve price")
      result should be(Some(BidWinner("E", 131)))
    }

    "return BidWinner with the reserve price equal to the price of bid winner" in {
      Given("a List of Bid lower than the reserve price")
      val givenListOfBid = Bid("A", 110) :: Bid("A", 131) ::
        Bid("C", 125) ::
        Bid("D", 105) :: Bid("D", 115) :: Bid("D", 90) ::
        Bid("E", 132) :: Bid("E", 135) :: Bid("E", 140) :: Nil
      val givenReservePrice = 135

      When("getBidWinner")
      val result = BidManager.getBidWinner(givenListOfBid, givenReservePrice)

      Then("The BidWinner is returned with the reserve price")
      result should be(Some(BidWinner("E", 135)))
    }
  }
  "getFinalBidPrice" should {
    "return the final price as the reserve price" in {
      Given("An empty list of non winner bid and a reserve price")
      val givenListOfNoneWinnerBid = Nil
      val givenReservePrice = 100

      When("getFinalBidPrice")
      val result = BidManager.getFinalBidPrice(givenListOfNoneWinnerBid,givenReservePrice)

      Then("The reserve price is returned")
      result should be(100)
    }
    "return the final price as the highest bid from non winner bids" in {
      Given("A list of non winner bid and a reserve price")
      val givenListOfNoneWinnerBid = Bid("A", 110) :: Bid("A", 131) :: Nil
      val givenReservePrice = 100

      When("getFinalBidPrice")
      val result = BidManager.getFinalBidPrice(givenListOfNoneWinnerBid,givenReservePrice)

      Then("The last element of the list is returned")
      result should be(131)
    }
  }
}