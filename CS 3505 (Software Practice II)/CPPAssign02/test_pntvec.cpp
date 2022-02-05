/* This utility tests the pntvec class for correctness
 *
 * Matthew Johnsen (u1173601)
 * January 25, 2020
 */

#include <sstream>
#include "pntvec.h"

using namespace std;

//Defining functions
int runTests();
string testConstructorDefault();
string testConstructorPoint();
string testConstructorCopy();
string testOperatorAssignment();
string testOperatorAddition();
string testOperatorSubtraction();
string testOperatorMultiplication();
string testOperatorNegation();
string testGetX();
string testGetY();
string testGetZ();
string testOperatorOutput();
string testOperatorInput();
bool checkDoubles (const pntvec &p, double x, double y, double z);

/* Main -- the application entry point.
 *
 * Parameters:
 *      none
 *
 * Returns:
 *      an integer exit code, we return non-zero if an error occurred
 */
int main() {
    return runTests();
}

/* Run Tests -- runs all of tht tests for the pntvec class
 *
 * Parameters:
 *      none
 * Returns:
 *      non-zero if any of the tests failed
 */
int runTests() {
    string s = "";

    //Tests Default Constructor
    s = testConstructorDefault();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Point Constructor
    s = testConstructorPoint();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Copy Constructor
    s = testConstructorCopy();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Assignment Operator
    s = testOperatorAssignment();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Addition Operator
    s = testOperatorAddition();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Subtraction Operator
    s = testOperatorSubtraction();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Multiplication Operator
    s = testOperatorMultiplication();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Negation Operator
    s = testOperatorNegation();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Get X
    s = testGetX();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Get Y
    s = testGetY();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Get Z
    s = testGetZ();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Output Operator
    s = testOperatorOutput();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }

    //Tests Input Operator
    s = testOperatorInput();
    if (s != "") {
        cout << "Error in " << s << "." << endl;
        return -1;
    }
    return 0;
}

//All test functions retun an empty string if the test pases and the name of the function if it doesn't

//Tests to see if the default constructor initialized all variables as 0.
string testConstructorDefault() {
    pntvec p = pntvec();
    //Checks if doubles match
    bool result = checkDoubles(p, 0, 0, 0);
    //Checks if test passes
    if (!result) {
        return __func__;
    }
    return "";
}

//Tests to see if the point constructor initialized all variables to the correct inputs.
string testConstructorPoint() {
    pntvec p = pntvec(1,2,3);
    bool result = checkDoubles(p, 1, 2, 3);
    if (!result) {
        return __func__;
    }
    return "";
}

//Tests copy constructor for correctly copied varibles
string testConstructorCopy() {
    pntvec p = pntvec();
    pntvec p2 = pntvec(4,7.3234, 6.11);
    p = pntvec(p2);
    bool result = checkDoubles(p, 4, 7.3234, 6.11);
    if (!result) {
        return __func__;
    }
    return "";
}

//Tests assignment operator for correctness
string testOperatorAssignment() {
    pntvec p = pntvec();
    pntvec p2 = pntvec(0.02, 9.9991, 7.242);
    p = p2;
    bool result = checkDoubles(p, 0.02, 9.9991, 7.242);
    if (!result) {
        return __func__;
    }
    return "";
}

//Tests addition operator for correctness
string testOperatorAddition() {
    pntvec p = pntvec(2.2, 5.75, 9.1);
    pntvec p2 = pntvec(1.52, 3.1, 7.777);
    pntvec p3 = p + p2;
    bool result = checkDoubles(p3, 3.72, 8.85, 16.877);
    if (!result) {
        return __func__;
    }
    return "";
}

//Tests subtraction operator for correctness
string testOperatorSubtraction() {
    pntvec p = pntvec(10.3, 1777.333, 975.62);
    pntvec p2 = pntvec(5.1, 2354, 124.2);
    pntvec p3 = p - p2;
    bool result = checkDoubles(p3, 5.2, -576.667, 851.42);
    if (!result) {
        return __func__;
    }
    return "";
}

//Tests multiplication operator for correctness
string testOperatorMultiplication() {
    pntvec p = pntvec(10, 17.7, 222.2);
    pntvec p2 = p * 5.5;
    bool result = checkDoubles(p2, 55, 97.35, 1222.1);
    if (!result) {
        return __func__;
    }
    return "";
}

//Tests negation operator for correctness
string testOperatorNegation() {
    pntvec p = pntvec(22.2, -123542.1241, -3.37);
    pntvec p2 = -p;
    bool result = checkDoubles(p2, -22.2, 123542.1241, 3.37);
    if (!result) {
        return __func__;
    }
    return "";
}

//Tests getting x for correctness
string testGetX() {
    pntvec p = pntvec(111, 222, 333.333);
    bool result = abs(p.get_x() - 111) < 1e-10;
    if (!result) {
        return __func__;
    }
    return "";
}

//Tests getting y for correctness
string testGetY() {
    pntvec p = pntvec(111, 222, 333.333);
    bool result = abs(p.get_y() - 222) < 1e-10;
    if (!result) {
        return __func__;
    }
    return "";
}

//Tests getting z for correctness
string testGetZ() {
    pntvec p = pntvec(111, 222, 333.333);
    bool result = abs(p.get_z() - 333.333) < 1e-10;
    if (!result) {
        return __func__;
    }
    return "";
}

//Tests output operator for correctness
string testOperatorOutput() {
    pntvec p = pntvec(3.3, 55.55, 777.777);
    ostringstream ss;
    ss << p;
    string s = "(3.3, 55.55, 777.777)";
    if (s == ss.str()) {
        return "";
    }
    return __func__;
}

//Tests input operator for correctness
string testOperatorInput() {
    pntvec p = pntvec(3.3, 55.55, 777.777);
    istringstream ss("5.2 1.22 3.55");
    ss >> p;
    bool result = checkDoubles(p, 5.2, 1.22, 3.55);
    if (!result) {
        return __func__;
    }
    return "";
}
/* Check Doubles -- Helper method to determine if a point's variables are equal to different doubles
 *
 * Parameters:
 *      p - the point being checked
 *      x - the x value the point is being checked against
 *      y - the y value the point is being checked against
 *      z - the z value the point is being checked against
 * Returns:
 *      a bool that repesents whether or not the point matches the passed values
 */
bool checkDoubles (const pntvec &p, double x, double y, double z) {
    bool xDiff = abs(p.get_x() - x) < 1e-10;
    bool yDiff = abs(p.get_y() - y) < 1e-10;
    bool zDiff = abs(p.get_z() - z) < 1e-10;
    return xDiff && yDiff && zDiff;
}
