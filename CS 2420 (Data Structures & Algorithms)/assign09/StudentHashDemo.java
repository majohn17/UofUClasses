package assign09;

/**
 * This class demonstrates how to use a hash table to store key-value pairs.
 * 
 * @author Erin Parker
 * @version March 20, 2019
 */
public class StudentHashDemo {

	public static void main(String[] args) {

		StudentGoodHash alan = new StudentGoodHash(1019999, "Alan", "Turing");
		StudentGoodHash ada = new StudentGoodHash(1004203, "Ada", "Lovelace");
		StudentGoodHash edsgar = new StudentGoodHash(1010661, "Edsgar", "Dijkstra");
		StudentGoodHash grace = new StudentGoodHash(1019941, "Grace", "Hopper");

		HashTable<StudentGoodHash, Double> studentGpaTable = new HashTable<StudentGoodHash, Double>();
		studentGpaTable.put(alan, 3.2);
		studentGpaTable.put(ada, 3.5);
		studentGpaTable.put(edsgar, 3.8);
		studentGpaTable.put(grace, 4.0);

		for (MapEntry<StudentGoodHash, Double> e : studentGpaTable.entries())
			System.out.println("Student " + e.getKey() + " has GPA " + e.getValue() + ".");
	}
}