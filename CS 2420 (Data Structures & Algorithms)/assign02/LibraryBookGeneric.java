package assign02;

import java.util.GregorianCalendar;

/**
 * This class represents a generic library book, in which the ISBN, author, and
 * title cannot change once the book is created. Note that ISBNs are unique.
 * 
 * @author Matthew Larsen, Matthew Johnsen
 * @version January 16, 2019
 */
public class LibraryBookGeneric<Type> extends Book {

	private Type holder;

	private GregorianCalendar dueDate;

	/**
	 * Creates a libary book from the given ISBN, author, and title.
	 * 
	 * @param isbn
	 * @param author
	 * @param title
	 */
	public LibraryBookGeneric(long isbn, String author, String title) {
		super(isbn, author, title);
		this.holder = null;
		this.dueDate = null;
	}

	/**
	 * Accessor method for the holder field.
	 * 
	 * @return the holder
	 */
	public Type getHolder() {
		return this.holder;
	}

	/**
	 * Accessor method for the due date field.
	 * 
	 * @return the due date
	 */
	public GregorianCalendar getDueDate() {
		return this.dueDate;
	}

	/**
	 * Method to check out a book using a given person and due date.
	 * 
	 * @param holder
	 * @param date
	 */
	public void checkOut(Type holder, GregorianCalendar date) {
		this.holder = holder;
		this.dueDate = date;
	}

	/**
	 * Method to check in a book removing the current holder and due date.
	 */
	public void checkIn() {
		this.holder = null;
		this.dueDate = null;
	}
}