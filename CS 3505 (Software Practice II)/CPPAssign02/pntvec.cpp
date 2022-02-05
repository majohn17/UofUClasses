/* This class represents a 3D point and provides functions to modify the 3d point
 *
 * Matthew Johnsen (u1173601)
 * January 23, 2020
 */

#include <iostream>
#include <cmath>
#include "pntvec.h"

using namespace std;

/** Default Constructor -- Initializes x, y, and z to 0 */
pntvec::pntvec() {
    this->x = this->y = this->z = 0;
}

/** Point Constructor -- Initializes x, y, and z to the values that are passed
 *
 *  Parameters:
 *      x - the x value of the point
 *      y - the y value of the point
 *      z - the z value of the point
 *
 *  Returns:
 *      none
 */
pntvec::pntvec(double x, double y, double z) {
    this->x = x;
    this->y = y;
    this->z = z;
}

/** Copy Constructor -- Copies the passed pntvec to this pntvec
 *
 *  Parameters:
 *      point - the pntvec being copied from
 *
 *  Returns:
 *      none
 */
pntvec::pntvec(const pntvec &point) {
    this->x = point.get_x();
    this->y = point.get_y();
    this->z = point.get_z();
}

/** Assignment Operator -- Assigns one pntvec with the values of another
 *
 *  Parameters:<< setprecision(11)
 *      rhs - the pntvec being assigned from
 *
 *  Returns:
 *      A pointer to this pntvec
 */
pntvec& pntvec::operator= (const pntvec &rhs) {
    this->x = rhs.get_x();
    this->y = rhs.get_y();
    this->z = rhs.get_z();
    return *this;
}

/** Addition Operator -- Adds two pntvec objects together
 *
 *  Parameters:
 *      rhs - the pntvec being added to this
 *  Returns:
 *      A pntvec resulting from pntvec addition
 */
const pntvec pntvec::operator+ (const pntvec &rhs) const {
    return pntvec((this->x + rhs.get_x()), (this->y + rhs.get_y()), (this->z + rhs.get_z()));
}

/** Subtraction Operator -- Subtracts two pntvec objects
 *
 *  Parameters:
 *      rhs - the pntvec being subtracted from this
 *  Returns:
 *      A pntvec resulting from pntvec subtraction
 */
const pntvec pntvec::operator- (const pntvec &rhs) const {
    return pntvec((this->x - rhs.get_x()), (this->y - rhs.get_y()), (this->z - rhs.get_z()));
}

/** Multiplication Operator -- Multiplies this pntvec by a scale
 *
 *  Parameters:
 *      scale - the amount this pntvec is being multiplied by
 *  Returns:
 *      A pntvec resulting from multiplication by the scale
 */
const pntvec pntvec::operator* (const double &scale) const {
    return pntvec((this->x * scale), (this->y * scale), (this->z * scale));
}

/** Negation Operator -- Negates this pntvec
 *
 *  Parameters:
 *      none
 *  Returns:
 *      A pntvec resulting from negating this pntvec
 */
const pntvec pntvec::operator- () const {
    return pntvec((this->x * -1), (this->y * -1), (this->z * -1));
}

/** Get X -- Returns the x value of the pntvec
 *
 *  Parameters:
 *      none
 *  Returns:
 *      The x value of the pntvec
 */
double pntvec::get_x() const {
    return this->x;
}


/** Get Y -- Returns the y value of the pntvec
 *
 *  Parameters:
 *      none
 *  Returns:
 *      The y value of the pntvec
 */
double pntvec::get_y() const {
    return this->y;
}

/** Get Z -- Returns the z value of the pntvec
 *
 *  Parameters:
 *      none
 *  Returns:
 *      The z value of the pntvec
 */
double pntvec::get_z() const {
    return this->z;
}

/** Distance To -- Finds the distance between 2 points
 *
 *  Parameters:
 *      point2 - the point that's the distance from this
 *  Returns:
 *      The distance between this point and point2
 */
double pntvec::distance_to(const pntvec &point2) {
    // Squaring the differance between point2 and this point
    double xVal = pow((point2.get_x() - this->x), 2);
    double yVal = pow((point2.get_y() - this->y), 2);
    double zVal = pow((point2.get_z() - this->z), 2);

    // Return the squareroot of those values added together
    return sqrt(xVal + yVal + zVal);
}

/** Output Operator -- Outputs the point
 *
 *  Parameters:
 *      out - the output stream
 *      p - the point being outputted
 *  Returns:
 *      An output stream of the point
 */
ostream& operator<< (ostream &out, const pntvec &p) {
    out << "(" << p.get_x() << ", " << p.get_y() << ", " << p.get_z() << ")";
    return out;
}

/** Input Operator -- Inputs a point into this pntvec
 *
 *  Parameters:
 *      in - the input stream
 *      p - the point being inputted to
 *  Returns:
 *      An input stream to a pntvec
 */
istream& operator>> (istream &in, pntvec &p) {
    in >> p.x >> p.y >> p.z;
    return in;
}
