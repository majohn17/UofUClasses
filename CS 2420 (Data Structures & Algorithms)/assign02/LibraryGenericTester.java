package assign02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * This class contains tests for LibraryGeneric.
 * 
 * @author Erin Parker, Matthew Larsen, Matthew Johnsen
 * @version January 16, 2019
 */
public class LibraryGenericTester {

	private LibraryGeneric<String> nameLib; // library that uses names to identify patrons (holders)
	private LibraryGeneric<PhoneNumber> phoneLib; // library that uses phone numbers to identify patrons
	private LibraryGeneric<Double> idLib; // library that uses id's (double) to identify patrons
	private LibraryGeneric<String> testLib; // library used to test code

	@BeforeEach
	void setUp() throws Exception {
		nameLib = new LibraryGeneric<String>();
		nameLib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		nameLib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		nameLib.add(9780446580342L, "David Baldacci", "Simple Genius");

		phoneLib = new LibraryGeneric<PhoneNumber>();
		phoneLib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		phoneLib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		phoneLib.add(9780446580342L, "David Baldacci", "Simple Genius");

		idLib = new LibraryGeneric<Double>();
		idLib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
		idLib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
		idLib.add(9780446580342L, "David Baldacci", "Simple Genius");
		
		testLib = new LibraryGeneric<String>();
		testLib.add(9999999999991L, "John", "One");
		testLib.add(9999999999992L, "John2", "Two");
		testLib.add(9999999999993L, "John3", "Three");
		testLib.add(9999999999994L, "John4", "Four");
		testLib.add(9999999999995L, "John5", "Five");
		testLib.add(9999999999996L, "John6", "Six");
		testLib.add(9999999999997L, "John7", "seven");
	}

	@Test
	public void testNameLibCheckout() {
		String patron = "Jane Doe";
		assertTrue(nameLib.checkout(9780330351690L, patron, 1, 1, 2008));
		assertTrue(nameLib.checkout(9780374292799L, patron, 1, 1, 2008));
	}

	@Test
	public void testNameLibLookup() {
		String patron = "Jane Doe";
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);
		nameLib.checkout(9780374292799L, patron, 1, 1, 2008);
		ArrayList<LibraryBookGeneric<String>> booksCheckedOut = nameLib.lookup(patron);

		assertNotNull(booksCheckedOut);
		assertEquals(2, booksCheckedOut.size());
		assertTrue(booksCheckedOut.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
		assertTrue(booksCheckedOut.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
		assertEquals(patron, booksCheckedOut.get(0).getHolder());
		assertEquals(patron, booksCheckedOut.get(1).getHolder());
	}

	@Test
	public void testNameLibCheckin() {
		String patron = "Jane Doe";
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);
		nameLib.checkout(9780374292799L, patron, 1, 1, 2008);
		assertTrue(nameLib.checkin(patron));
	}

	@Test
	public void testPhoneLibCheckout() {
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		assertTrue(phoneLib.checkout(9780330351690L, patron, 1, 1, 2008));
		assertTrue(phoneLib.checkout(9780374292799L, patron, 1, 1, 2008));
	}

	@Test
	public void testPhoneLibLookup() {
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		phoneLib.checkout(9780330351690L, patron, 1, 1, 2008);
		phoneLib.checkout(9780374292799L, patron, 1, 1, 2008);
		ArrayList<LibraryBookGeneric<PhoneNumber>> booksCheckedOut = phoneLib.lookup(patron);

		assertNotNull(booksCheckedOut);
		assertEquals(2, booksCheckedOut.size());
		assertTrue(booksCheckedOut.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
		assertTrue(booksCheckedOut.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
		assertEquals(patron, booksCheckedOut.get(0).getHolder());
		assertEquals(patron, booksCheckedOut.get(1).getHolder());
	}

	@Test
	public void testPhoneLibCheckin() {
		PhoneNumber patron = new PhoneNumber("801.555.1234");
		phoneLib.checkout(9780330351690L, patron, 1, 1, 2008);
		phoneLib.checkout(9780374292799L, patron, 1, 1, 2008);
		assertTrue(phoneLib.checkin(patron));
	}

	@Test
	public void testIdLibCheckout() {
		Double patron = 17.0;
		assertTrue(idLib.checkout(9780330351690L, patron, 1, 1, 2008));
		assertTrue(idLib.checkout(9780374292799L, patron, 1, 1, 2008));
	}

	@Test
	public void testIdLibLookup() {
		Double patron = 17.0;
		idLib.checkout(9780330351690L, patron, 1, 1, 2008);
		idLib.checkout(9780374292799L, patron, 1, 1, 2008);
		ArrayList<LibraryBookGeneric<Double>> booksCheckedOut = idLib.lookup(patron);

		assertNotNull(booksCheckedOut);
		assertEquals(2, booksCheckedOut.size());
		assertTrue(booksCheckedOut.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
		assertTrue(booksCheckedOut.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
		assertEquals(patron, booksCheckedOut.get(0).getHolder());
		assertEquals(patron, booksCheckedOut.get(1).getHolder());
	}

	@Test
	public void testIdLibCheckin() {
		Double patron = 17.0;
		idLib.checkout(9780330351690L, patron, 1, 1, 2008);
		idLib.checkout(9780374292799L, patron, 1, 1, 2008);
		assertTrue(idLib.checkin(patron));
	}

	@Test
	public void testSortByDueDate() {
		String patron = "Jane Doe";
		nameLib.checkout(9780330351690L, patron, 1, 1, 2008);
		nameLib.checkout(9780374292799L, patron, 1, 1, 2007);
		ArrayList<LibraryBookGeneric<String>> sortedBooks = nameLib.getOverdueList(1, 1, 2009);

		assertEquals(2, sortedBooks.size());
		assertEquals(new GregorianCalendar(2007, 1, 1), sortedBooks.get(0).getDueDate());
	}
	
	@Test
	public void testSortByDueDate2() {
		String patron = "Jane";
		testLib.checkout(9999999999991L, patron, 1, 1, 2007);
		testLib.checkout(9999999999992L, patron, 1, 1, 2008);
		testLib.checkout(9999999999993L, patron, 1, 1, 20012);
		testLib.checkout(9999999999994L, patron, 1, 1, 2028);
		testLib.checkout(9999999999995L, patron, 1, 1, 123);
		testLib.checkout(9999999999996L, patron, 1, 2, 123);
		ArrayList<LibraryBookGeneric<String>> sortedBooks = testLib.getOverdueList(1, 1, 2009);

		assertEquals(4, sortedBooks.size());
		assertEquals("Five", sortedBooks.get(0).getTitle());
		assertEquals("One", sortedBooks.get(2).getTitle());
	}
	
	@Test
	public void testSortByTitle() {
		ArrayList<LibraryBookGeneric<String>> sortedBooks = nameLib.getOrderedByTitle();

		assertEquals(3, sortedBooks.size());
		assertEquals("Into the Wild", sortedBooks.get(0).getTitle());
	}
	
	@Test
	public void testSortByTitle2() {
		ArrayList<LibraryBookGeneric<String>> sortedBooks = testLib.getOrderedByTitle();

		assertEquals(7, sortedBooks.size());
		assertEquals("Five", sortedBooks.get(0).getTitle());
		assertEquals("Six", sortedBooks.get(3).getTitle());
		assertEquals("seven", sortedBooks.get(6).getTitle());
		
	}
}
