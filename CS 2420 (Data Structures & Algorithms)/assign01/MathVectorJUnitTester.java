package assign01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This tester class assesses the correctness of the Vector class.
 * 
 * IMPORTANT NOTE: The tests provided to get you started rely heavily on a
 * correctly implemented equals method. Be careful of false positives (i.e.,
 * tests that pass because your equals method incorrectly returns true).
 * 
 * @author Erin Parker & Matthew Johnsen
 * @version January 9, 2019
 */
class MathVectorJUnitTester
{

	private MathVector rowVec, rowVec2, rowVecTranspose, unitVec, sumVec, colVec, colVec2;

	@BeforeEach
	void setUp() throws Exception
	{
		// Creates a row vector with three elements: 3.0, 1.0, 2.0
		rowVec = new MathVector(new double[][] { { 3, 1, 2 } });

		// Creates a row vector with different elements
		rowVec2 = new MathVector(new double[][] { { 7, 3, -8, 9 } });

		// Creates a column vector with three elements: 3.0, 1.0, 2.0
		rowVecTranspose = new MathVector(new double[][] { { 3 }, { 1 }, { 2 } });

		// Creates a row vector with three elements: 1.0, 1.0, 1.0
		unitVec = new MathVector(new double[][] { { 1, 1, 1 } });

		// Creates a row vector with three elements: 4.0, 2.0, 3.0
		sumVec = new MathVector(new double[][] { { 4, 2, 3 } });

		// Creates a column vector with five elements: -11.0, 2.5, 36.0, -3.14, 7.1
		colVec = new MathVector(new double[][] { { -11 }, { 2.5 }, { 36 }, { -3.14 }, { 7.1 } });

		// Creates a column vector with five elements: -11.0, 2.5, 36.0, -3.14, 7.1
		colVec2 = new MathVector(new double[][] { { -7 }, { -17 }, { 5 }, { 3 } });
	}

	@AfterEach
	void tearDown() throws Exception
	{
	}

	@Test
	public void createVectorFromBadArray()
	{
		double arr[][] = new double[0][1];
		assertThrows(IllegalArgumentException.class, () ->
		{
			new MathVector(arr);
		});
	}

	@Test
	public void createVectorFromBadArray2()
	{
		double arr[][] = new double[1][0];
		assertThrows(IllegalArgumentException.class, () ->
		{
			new MathVector(arr);
		});
	}

	@Test
	public void createVectorFromBadArray3()
	{
		double arr[][] = { { 1, 2 }, { 3, 4 } };
		assertThrows(IllegalArgumentException.class, () ->
		{
			new MathVector(arr);
		});
	}

	@Test
	void smallRowVectorEquality()
	{
		assertTrue(rowVec.equals(new MathVector(new double[][] { { 3, 1, 2 } })));
	}

	@Test
	void smallRowVectorEquality2()
	{
		assertTrue(rowVec2.equals(new MathVector(new double[][] { { 7, 3, -8, 9 } })));
	}

	@Test
	void smallColVectorEquality()
	{
		assertTrue(colVec.equals(new MathVector(new double[][] { { -11 }, { 2.5 }, { 36 }, { -3.14 }, { 7.1 } })));
	}

	@Test
	void smallColVectorEquality2()
	{
		assertTrue(colVec2.equals(new MathVector(new double[][] { { -7 }, { -17 }, { 5 }, { 3 } })));
	}

	@Test
	void smallRowVectorInequality()
	{
		assertFalse(rowVec.equals(unitVec));
	}

	@Test
	void smallRowVectorInquality2()
	{
		assertFalse(rowVec.equals(rowVecTranspose));
	}

	@Test
	void smallRowVectorInquality3()
	{
		MathVector test = new MathVector(new double[][] { { 3, 1, 2, 4 } });
		assertFalse(rowVec2.equals(test));
	}

	@Test
	void transposeSmallRowVector()
	{
		MathVector transposeResult = rowVec.transpose();
		assertTrue(transposeResult.equals(rowVecTranspose));
	}

	@Test
	void transposeSmallRowVector2()
	{
		MathVector transposeResult = rowVec2.transpose();
		assertTrue(transposeResult.equals(new MathVector(new double[][] { { 7 }, { 3 }, { -8 }, { 9 } })));
	}

	@Test
	void transposeSmallColVector()
	{
		MathVector transposeResult = colVec.transpose();
		assertTrue(transposeResult.equals(new MathVector(new double[][] { { -11, 2.5, 36, -3.14, 7.1 } })));
	}

	@Test
	void transposeSmallColVector2()
	{
		MathVector transposeResult = colVec2.transpose();
		assertTrue(transposeResult.equals(new MathVector(new double[][] { { -7, -17, 5, 3 } })));
	}

	@Test
	public void addRowAndColVectors()
	{
		assertThrows(IllegalArgumentException.class, () ->
		{
			rowVec.add(colVec);
		});
	}

	@Test
	public void addDifferentSizedVectors()
	{
		assertThrows(IllegalArgumentException.class, () ->
		{
			rowVec.add(rowVec2);
		});
	}

	@Test
	void addSmallRowVectors()
	{
		MathVector addResult = rowVec.add(unitVec);
		assertTrue(addResult.equals(sumVec));
	}

	@Test
	void addSmallRowVectors2()
	{
		MathVector addResult = rowVec.add(sumVec);
		assertTrue(addResult.equals(new MathVector(new double[][] { { 7, 3, 5 } })));
	}

	@Test
	void addSmallColVectors()
	{
		MathVector addResult = colVec.add(new MathVector(new double[][] { { 1 }, { 1 }, { 1 }, { 1 }, { 1 } }));
		assertTrue(addResult
				.equals(new MathVector(new double[][] { { -10 }, { 3.5 }, { 37 }, { -2.14 }, { 8.1 } })));
	}

	@Test
	void addSmallColVectors2()
	{
		MathVector addResult = colVec2.add(new MathVector(new double[][] { { 7 }, { 3 }, { -8 }, { 9 } }));
		assertTrue(addResult.equals(new MathVector(new double[][] { { 0 }, { -14 }, { -3 }, { 12 } })));
	}

	@Test
	public void dotProductRowAndColVectors()
	{
		assertThrows(IllegalArgumentException.class, () ->
		{
			rowVec.dotProduct(colVec);
		});
	}

	@Test
	public void dotProductDifferentSizedVectors()
	{
		assertThrows(IllegalArgumentException.class, () ->
		{
			rowVec.dotProduct(rowVec2);
		});
	}

	@Test
	void dotProductSmallRowVectors()
	{
		double dotProdResult = rowVec.dotProduct(unitVec);
		assertEquals(dotProdResult, 3.0 * 1.0 + 1.0 * 1.0 + 2.0 * 1.0);
	}

	@Test
	void dotProductSmallRowVectors2()
	{
		double dotProdResult = rowVec.dotProduct(sumVec);
		assertEquals(dotProdResult, 3.0 * 4.0 + 1.0 * 2.0 + 2.0 * 3.0);
	}

	@Test
	void dotProductSmallColVectors()
	{
		double dotProdResult = colVec
				.dotProduct(new MathVector(new double[][] { { 2 }, { 3 }, { 2 }, { 2 }, { 2 } }));
		assertEquals(dotProdResult, -11.0 * 2.0 + 2.5 * 3.0 + 36.0 * 2.0 + -3.14 * 2.0 + 7.1 * 2.0);
	}

	@Test
	void dotProdcutSmallColVectors2()
	{
		double dotProdResult = colVec2.dotProduct(new MathVector(new double[][] { { 7 }, { 3 }, { -8 }, { 9 } }));
		assertEquals(dotProdResult, -7.0 * 7.0 + -17.0 * 3.0 + 5.0 * -8.0 + 3.0 * 9.0);
	}

	@Test
	void smallRowVectorLength()
	{
		double vecLength = rowVec.magnitude();
		assertEquals(vecLength, Math.sqrt(3.0 * 3.0 + 1.0 * 1.0 + 2.0 * 2.0));
	}

	@Test
	void smallRowVectorLength2()
	{
		double vecLength = rowVec2.magnitude();
		assertEquals(vecLength, Math.sqrt(7.0 * 7.0 + 3.0 * 3.0 + -8.0 * -8.0 + 9.0 * 9.0));
	}

	@Test
	void smallColVectorLength()
	{
		double vecLength = colVec.magnitude();
		assertEquals(vecLength, Math.sqrt(-11.0 * -11.0 + 2.5 * 2.5 + 36.0 * 36.0 + -3.14 * -3.14 + 7.1 * 7.1));
	}

	@Test
	void smallColVectorLength2()
	{
		double vecLength = colVec2.magnitude();
		assertEquals(vecLength, Math.sqrt(-7.0 * -7.0 + -17.0 * -17.0 + 5.0 * 5.0 + 3.0 * 3.0));
	}

	@Test
	void smallRowVectorNormalize()
	{
		MathVector normalVec = rowVec.normalize();
		double length = Math.sqrt(3.0 * 3.0 + 1.0 * 1.0 + 2.0 * 2.0);
		assertTrue(normalVec
				.equals(new MathVector(new double[][] { { 3.0 / length, 1.0 / length, 2.0 / length } })));
	}

	@Test
	void smallRowVectorNormalize2()
	{
		MathVector normalVec = rowVec2.normalize();
		double length = Math.sqrt(7.0 * 7.0 + 3.0 * 3.0 + -8.0 * -8.0 + 9.0 * 9.0);
		assertTrue(normalVec.equals(new MathVector(
				new double[][] { { 7.0 / length, 3.0 / length, -8.0 / length, 9.0 / length } })));
	}

	@Test
	void smallColVectorNormalize()
	{
		MathVector normalVec = colVec.normalize();
		double length = Math.sqrt(-11.0 * -11.0 + 2.5 * 2.5 + 36.0 * 36.0 + -3.14 * -3.14 + 7.1 * 7.1);
		assertTrue(normalVec.equals(new MathVector(new double[][] { { -11.0 / length }, { 2.5 / length },
				{ 36.0 / length }, { -3.14 / length }, { 7.1 / length } })));
	}

	@Test
	void smallColVectorNormalize2()
	{
		MathVector normalVec = colVec2.normalize();
		double length = Math.sqrt(-7.0 * -7.0 + -17.0 * -17.0 + 5.0 * 5.0 + 3.0 * 3.0);
		assertTrue(normalVec.equals(new MathVector(
				new double[][] { { -7.0 / length }, { -17.0 / length }, { 5.0 / length }, { 3.0 / length } })));
	}

	@Test
	void smallRowVectorToString()
	{
		String resultStr = "3.0 1.0 2.0";
		assertEquals(resultStr, rowVec.toString());
	}

	@Test
	void smallRowVectorToString2()
	{
		String resultStr = "7.0 3.0 -8.0 9.0";
		assertEquals(resultStr, rowVec2.toString());
	}

	@Test
	void smallColVectorToString()
	{
		String resultStr = "-11.0\n2.5\n36.0\n-3.14\n7.1";
		assertEquals(resultStr, colVec.toString());
	}

	@Test
	void smallColVectorToString2()
	{
		String resultStr = "-7.0\n-17.0\n5.0\n3.0";
		assertEquals(resultStr, colVec2.toString());
	}
}