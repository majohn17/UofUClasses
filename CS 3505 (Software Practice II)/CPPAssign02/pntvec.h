#include <iostream>
#include <iomanip>
#include <cmath>

#ifndef PNTVEC_H
#define PNTVEC_H
class pntvec
{
private:
    double x, y, z;

public:
    pntvec();
    pntvec(double x, double y, double z);
    pntvec(const pntvec &point);
    pntvec& operator= (const pntvec &rhs);
    const pntvec operator+ (const pntvec &rhs) const;
    const pntvec operator- (const pntvec &rhs) const;
    const pntvec operator* (const double &scale) const;
    const pntvec operator- () const;
    double get_x() const;
    double get_y() const;
    double get_z() const;
    double distance_to(const pntvec &point2);
    friend std::ostream &operator<< (std::ostream &out, const pntvec &p);
    friend std::istream &operator>> (std::istream &in, pntvec &p);
};
#endif
