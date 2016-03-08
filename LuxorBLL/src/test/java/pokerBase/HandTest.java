package pokerBase;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.DeckException;
import exceptions.HandException;
import pokerEnums.eCardNo;
import pokerEnums.eHandStrength;
import pokerEnums.eRank;
import pokerEnums.eSuit;

public class HandTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	private Hand SetHand(ArrayList<Card> setCards, Hand h)
	{
		Object t = null;
		
		try {
			//	Load the Class into 'c'
			Class<?> c = Class.forName("pokerBase.Hand");
			//	Create a new instance 't' from the no-arg Deck constructor
			t = c.newInstance();
			//	Load 'msetCardsInHand' with the 'Draw' method (no args);
			Method msetCardsInHand = c.getDeclaredMethod("setCardsInHand", new Class[]{ArrayList.class});
			//	Change the visibility of 'setCardsInHand' to true *Good Grief!*
			msetCardsInHand.setAccessible(true);
			//	invoke 'msetCardsInHand'
			Object oDraw = msetCardsInHand.invoke(t, setCards);
			
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		h = (Hand)t;
		return h;
		
	}
	/**
	 * This test checks to see if a HandException is throw if the hand only has two cards.
	 * @throws Exception
	 */
	@Test(expected = HandException.class)
	public void TestEvalShortHand() throws Exception {
		
		Hand h = new Hand();
		
		ArrayList<Card> ShortHand = new ArrayList<Card>();
		ShortHand.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		ShortHand.add(new Card(eSuit.CLUBS,eRank.ACE,0));

		h = SetHand(ShortHand,h);
		
		//	This statement should throw a HandException
		h = Hand.EvaluateHand(h);	
	}	
			
	@Test
	public void TestFourOfAKind() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> FourOfAKind = new ArrayList<Card>();
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));		
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.KING,0));
		
		Hand h = new Hand();
		h = SetHand(FourOfAKind,h);
		
		boolean bActualIsHandFourOfAKind = Hand.isHandFourOfAKind(h, hs);
		boolean bExpectedIsHandFourOfAKind = true;
		
		//	Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandFourOfAKind,bExpectedIsHandFourOfAKind);		
		//	Was the four of a kind an Ace?
		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
		//	FOAK has one kicker.  Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		//	FOAK has one kicker.  Was it a King?		
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
	}
	@Test
	public void TestFourOfAKindTwo() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> FourOfAKind = new ArrayList<Card>();
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.KING,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.TWO,0));		
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(FourOfAKind,h);
		
		boolean bActualIsHandFourOfAKind = Hand.isHandFourOfAKind(h, hs);
		boolean bExpectedIsHandFourOfAKind = true;
		
		//	Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandFourOfAKind,bExpectedIsHandFourOfAKind);		
		//	Was the four of a kind an Ace?
		assertEquals(hs.getHiHand(),eRank.TWO.getiRankNbr());		
		//	FOAK has one kicker.  Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		//	FOAK has one kicker.  Was it a King?		
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
	}
	
	@Test
	public void TestRoyalFlush() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> RoyalFlush = new ArrayList<Card>();
		RoyalFlush.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		RoyalFlush.add(new Card(eSuit.CLUBS,eRank.KING,0));
		RoyalFlush.add(new Card(eSuit.CLUBS,eRank.QUEEN,0));		
		RoyalFlush.add(new Card(eSuit.CLUBS,eRank.JACK,0));
		RoyalFlush.add(new Card(eSuit.CLUBS,eRank.TEN,0));
		
		Hand h = new Hand();
		h = SetHand(RoyalFlush,h);
		
		boolean bActualIsHandRoyalFlush = Hand.isHandRoyalFlush(h, hs);
		boolean bExpectedIsHandRoyalFlush = true;
		
		assertEquals(bActualIsHandRoyalFlush,bExpectedIsHandRoyalFlush);		
		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());	

		

	}
	
	@Test
	public void TestRoyalFlushFail() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> RoyalFlush = new ArrayList<Card>();
		RoyalFlush.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		RoyalFlush.add(new Card(eSuit.CLUBS,eRank.SEVEN,0));
		RoyalFlush.add(new Card(eSuit.CLUBS,eRank.NINE,0));		
		RoyalFlush.add(new Card(eSuit.CLUBS,eRank.THREE,0));
		RoyalFlush.add(new Card(eSuit.HEARTS,eRank.FOUR,0));
		
		Hand h = new Hand();
		h = SetHand(RoyalFlush,h);
		
		boolean bActualIsHandRoyalFlush = Hand.isHandRoyalFlush(h, hs);
		boolean bExpectedIsHandRoyalFlush = false;
		
		assertEquals(bActualIsHandRoyalFlush,bExpectedIsHandRoyalFlush);		
	}
	
	@Test
	public void TestFourOfAKindEval() {
		
		ArrayList<Card> FourOfAKind = new ArrayList<Card>();
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FourOfAKind.add(new Card(eSuit.CLUBS,eRank.KING,0));
		
		Hand h = new Hand();
		h = SetHand(FourOfAKind,h);
		
		try {
			h = Hand.EvaluateHand(h);
		} catch (HandException e) {			
			e.printStackTrace();
			fail("TestFourOfAKindEval failed");
		}
		HandScore hs = h.getHandScore();
		boolean bActualIsHandFourOfAKind = Hand.isHandFourOfAKind(h, hs);
		boolean bExpectedIsHandFourOfAKind = true;
		
		//	Did this evaluate to Four of a Kind?
		assertEquals(bActualIsHandFourOfAKind,bExpectedIsHandFourOfAKind);		
		//	Was the four of a kind an Ace?
		assertEquals(hs.getHiHand(),eRank.ACE.getiRankNbr());		
		//	FOAK has one kicker.  Was it a Club?
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteSuit(), eSuit.CLUBS);
		//	FOAK has one kicker.  Was it a King?		
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.KING);
	}	
	@Test
	public void TestStraightFlush() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> StraightFlush = new ArrayList<Card>();
		StraightFlush.add(new Card(eSuit.CLUBS,eRank.NINE,0));
		StraightFlush.add(new Card(eSuit.CLUBS,eRank.EIGHT,0));
		StraightFlush.add(new Card(eSuit.CLUBS,eRank.SEVEN,0));		
		StraightFlush.add(new Card(eSuit.CLUBS,eRank.SIX,0));
		StraightFlush.add(new Card(eSuit.CLUBS,eRank.FIVE,0));
		
		Hand h = new Hand();
		h = SetHand(StraightFlush,h);
		
		boolean bActualIsStraightFlush = Hand.isHandStraightFlush(h, hs);
		boolean bExpectedIsHandFlush = true;
		
		assertEquals(bActualIsStraightFlush,bExpectedIsHandFlush);		
		assertEquals(hs.getHiHand(),eRank.NINE.getiRankNbr());	

		

	}
	
	@Test
	public void TestStraightFlushFail() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> StraightFlush = new ArrayList<Card>();
		StraightFlush.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		StraightFlush.add(new Card(eSuit.CLUBS,eRank.SEVEN,0));
		StraightFlush.add(new Card(eSuit.CLUBS,eRank.NINE,0));		
		StraightFlush.add(new Card(eSuit.CLUBS,eRank.THREE,0));
		StraightFlush.add(new Card(eSuit.HEARTS,eRank.FOUR,0));
		
		Hand h = new Hand();
		h = SetHand(StraightFlush,h);
		
		boolean bActualIsStraightFlush = Hand.isHandStraightFlush(h, hs);
		boolean bExpectedIsHandFlush = false;
		
		assertEquals(bActualIsStraightFlush,bExpectedIsHandFlush);		
	}
	@Test
	public void TestFullHouse() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> FullHouse = new ArrayList<Card>();
		FullHouse.add(new Card(eSuit.CLUBS,eRank.TEN,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.TEN,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.TEN,0));		
		FullHouse.add(new Card(eSuit.CLUBS,eRank.NINE,0));
		FullHouse.add(new Card(eSuit.HEARTS,eRank.NINE,0));
		
		Hand h = new Hand();
		h = SetHand(FullHouse,h);
		
		boolean bActualFullHouse = Hand.isHandFullHouse(h, hs);
		boolean bExpectedIsFullHouse = true;
		
		assertEquals(bActualFullHouse,bExpectedIsFullHouse);		
		assertEquals(hs.getHiHand(),eRank.TEN.getiRankNbr());
		assertEquals(hs.getLoHand(),eRank.NINE.getiRankNbr());

		

	}
	@Test
	public void TestFullHouseTwo() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> FullHouse = new ArrayList<Card>();
		FullHouse.add(new Card(eSuit.CLUBS,eRank.TEN,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.TEN,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.NINE,0));		
		FullHouse.add(new Card(eSuit.CLUBS,eRank.NINE,0));
		FullHouse.add(new Card(eSuit.HEARTS,eRank.NINE,0));
		
		Hand h = new Hand();
		h = SetHand(FullHouse,h);
		
		boolean bActualFullHouse = Hand.isHandFullHouse(h, hs);
		boolean bExpectedIsFullHouse = true;
		
		assertEquals(bActualFullHouse,bExpectedIsFullHouse);		
		assertEquals(hs.getHiHand(),eRank.TEN.getiRankNbr());
		assertEquals(hs.getLoHand(),eRank.NINE.getiRankNbr());

		

	}
	
	@Test
	public void TestFullHouseFail() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> FullHouse = new ArrayList<Card>();
		FullHouse.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.SEVEN,0));
		FullHouse.add(new Card(eSuit.CLUBS,eRank.NINE,0));		
		FullHouse.add(new Card(eSuit.CLUBS,eRank.THREE,0));
		FullHouse.add(new Card(eSuit.HEARTS,eRank.FOUR,0));
		
		Hand h = new Hand();
		h = SetHand(FullHouse,h);
		
		boolean bActualIsHandFullHouse = Hand.isHandFullHouse(h, hs);
		boolean bExpectedIsHandFullHouse = false;
		
		assertEquals(bActualIsHandFullHouse,bExpectedIsHandFullHouse);		
	}
		


	@Test
	public void TestFlush() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> Flush = new ArrayList<Card>();
		Flush.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Flush.add(new Card(eSuit.CLUBS,eRank.JACK,0));
		Flush.add(new Card(eSuit.CLUBS,eRank.FOUR,0));		
		Flush.add(new Card(eSuit.CLUBS,eRank.NINE,0));
		Flush.add(new Card(eSuit.CLUBS,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(Flush,h);
		
		boolean bActualFlush = Hand.isHandFlush(h, hs);
		boolean bExpectedIsFlush = true;
		
		assertEquals(bActualFlush,bExpectedIsFlush);		
		assertEquals(hs.getHiHand(),eRank.KING.getiRankNbr());
		assertEquals(hs.getKickers().get(eCardNo.FirstCard.getCardNo()).geteRank(), eRank.JACK);
	}

	@Test	
	public void TestFlushFail() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> Flush = new ArrayList<Card>();
		Flush.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Flush.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Flush.add(new Card(eSuit.CLUBS,eRank.FOUR,0));		
		Flush.add(new Card(eSuit.HEARTS,eRank.NINE,0));
		Flush.add(new Card(eSuit.SPADES,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(Flush,h);
		
		boolean bActualFlush = Hand.isHandFlush(h, hs);
		boolean bExpectedIsFlush = false;
		
		assertEquals(bActualFlush,bExpectedIsFlush);		

	}
	
	@Test	
	public void TestStraight() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> Straight = new ArrayList<Card>();
		Straight.add(new Card(eSuit.CLUBS,eRank.TEN,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.NINE,0));
		Straight.add(new Card(eSuit.CLUBS,eRank.EIGHT,0));		
		Straight.add(new Card(eSuit.HEARTS,eRank.SEVEN,0));
		Straight.add(new Card(eSuit.SPADES,eRank.SIX,0));
		
		Hand h = new Hand();
		h = SetHand(Straight,h);
		
		boolean bActualStraight = Hand.isHandStraight(h, hs);
		boolean bExpectedIsStraight = true;
		
		assertEquals(bActualStraight,bExpectedIsStraight);		

	}
	
	@Test	
	public void TestThreeOfAKind() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> ThreeOfAKind = new ArrayList<Card>();
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.KING,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.KING,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.KING,0));		
		ThreeOfAKind.add(new Card(eSuit.HEARTS,eRank.NINE,0));
		ThreeOfAKind.add(new Card(eSuit.SPADES,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(ThreeOfAKind,h);
		
		boolean bThree = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedThree = true;
		
		assertEquals(bThree,bExpectedThree);		

	}
	@Test	
	public void TestThreeOfAKindTwo() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> ThreeOfAKind = new ArrayList<Card>();
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.KING,0));
		ThreeOfAKind.add(new Card(eSuit.SPADES,eRank.KING,0));		
		ThreeOfAKind.add(new Card(eSuit.HEARTS,eRank.KING,0));
		ThreeOfAKind.add(new Card(eSuit.SPADES,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(ThreeOfAKind,h);
		
		boolean bThree = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedThree = true;
		
		assertEquals(bThree,bExpectedThree);		

	}
	@Test	
	public void TestThreeOfAKindThree() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> ThreeOfAKind = new ArrayList<Card>();
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.ACE,0));
		ThreeOfAKind.add(new Card(eSuit.CLUBS,eRank.KING,0));
		ThreeOfAKind.add(new Card(eSuit.SPADES,eRank.TWO,0));		
		ThreeOfAKind.add(new Card(eSuit.HEARTS,eRank.TWO,0));
		ThreeOfAKind.add(new Card(eSuit.SPADES,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(ThreeOfAKind,h);
		
		boolean bThree = Hand.isHandThreeOfAKind(h, hs);
		boolean bExpectedThree = true;
		
		assertEquals(bThree,bExpectedThree);		

	}
	
	@Test	
	public void TestTwoPair() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		TwoPair.add(new Card(eSuit.SPADES,eRank.NINE,0));		
		TwoPair.add(new Card(eSuit.HEARTS,eRank.TWO,0));
		TwoPair.add(new Card(eSuit.SPADES,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(TwoPair,h);
		
		boolean bTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedTwoPair = true;
		
		assertEquals(bTwoPair,bExpectedTwoPair);		

	}
	@Test	
	public void TestTwoPairTwo() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		TwoPair.add(new Card(eSuit.SPADES,eRank.NINE,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.NINE,0));		
		TwoPair.add(new Card(eSuit.HEARTS,eRank.TWO,0));
		TwoPair.add(new Card(eSuit.SPADES,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(TwoPair,h);
		
		boolean bTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedTwoPair = true;
		
		assertEquals(bTwoPair,bExpectedTwoPair);		

	}
	@Test	
	public void TestTwoPairThree() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> TwoPair = new ArrayList<Card>();
		TwoPair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		TwoPair.add(new Card(eSuit.CLUBS,eRank.NINE,0));		
		TwoPair.add(new Card(eSuit.HEARTS,eRank.NINE,0));
		TwoPair.add(new Card(eSuit.SPADES,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(TwoPair,h);
		
		boolean bTwoPair = Hand.isHandTwoPair(h, hs);
		boolean bExpectedTwoPair = true;
		
		assertEquals(bTwoPair,bExpectedTwoPair);		

	}
	@Test	
	public void TestPair() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.FIVE,0));
		Pair.add(new Card(eSuit.HEARTS,eRank.FOUR,0));		
		Pair.add(new Card(eSuit.HEARTS,eRank.THREE,0));
		Pair.add(new Card(eSuit.SPADES,eRank.THREE,0));
		
		Hand h = new Hand();
		h = SetHand(Pair,h);
		
		boolean bPair = Hand.isHandPair(h, hs);
		boolean bExpectedPair = true;
		
		assertEquals(bPair,bExpectedPair);		

	}
	@Test	
	public void TestPairTwo() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.FIVE,0));
		Pair.add(new Card(eSuit.HEARTS,eRank.FOUR,0));		
		Pair.add(new Card(eSuit.HEARTS,eRank.FOUR,0));
		Pair.add(new Card(eSuit.SPADES,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(Pair,h);
		
		boolean bPair = Hand.isHandPair(h, hs);
		boolean bExpectedPair = true;
		
		assertEquals(bPair,bExpectedPair);		

	}
	@Test	
	public void TestPairThree() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Pair.add(new Card(eSuit.CLUBS,eRank.FIVE,0));
		Pair.add(new Card(eSuit.HEARTS,eRank.FIVE,0));		
		Pair.add(new Card(eSuit.HEARTS,eRank.FOUR,0));
		Pair.add(new Card(eSuit.SPADES,eRank.THREE,0));
		
		Hand h = new Hand();
		h = SetHand(Pair,h);
		
		boolean bPair = Hand.isHandPair(h, hs);
		boolean bExpectedPair = true;
		
		assertEquals(bPair,bExpectedPair);		

	}
	@Test	
	public void TestPairFour() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> Pair = new ArrayList<Card>();
		Pair.add(new Card(eSuit.CLUBS,eRank.KING,0));
		Pair.add(new Card(eSuit.SPADES,eRank.KING,0));
		Pair.add(new Card(eSuit.HEARTS,eRank.FOUR,0));		
		Pair.add(new Card(eSuit.HEARTS,eRank.THREE,0));
		Pair.add(new Card(eSuit.SPADES,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(Pair,h);
		
		boolean bPair = Hand.isHandPair(h, hs);
		boolean bExpectedPair = true;
		
		assertEquals(bPair,bExpectedPair);		

	}
	@Test	
	public void TestHighHand() {
		
		HandScore hs = new HandScore();
		ArrayList<Card> HighHand = new ArrayList<Card>();
		HighHand.add(new Card(eSuit.CLUBS,eRank.KING,0));
		HighHand.add(new Card(eSuit.CLUBS,eRank.TEN,0));
		HighHand.add(new Card(eSuit.SPADES,eRank.SIX,0));		
		HighHand.add(new Card(eSuit.HEARTS,eRank.THREE,0));
		HighHand.add(new Card(eSuit.SPADES,eRank.TWO,0));
		
		Hand h = new Hand();
		h = SetHand(HighHand,h);
		
		boolean bHighHand = Hand.isHandHighCard(h, hs);
		boolean bExpectedHighHand = true;
		
		assertEquals(bHighHand,bExpectedHighHand);		

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
