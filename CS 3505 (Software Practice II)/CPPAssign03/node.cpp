/* This node class is used to build linked lists for the
 * string_set class.
 *
 * Peter Jensen & Matthew Johnsen (u1173601)
 * January 28, 2020
 */

#include <vector>
#include <string>
#include <stdlib.h>
#include "node.h"

using namespace std;

namespace cs3505 {
    /**
    * Constrcutor: Generrates a node with string s as its data and
    * a max width for its next vector of maxWidth
    */
    node::node(string s, int maxWidth)
    {
        data = s;
        generateWidth(maxWidth);
        next = vector<node*>(maxWidth);
        for (int i = 0; i < width; i++) {
            next[i] = NULL;
        }
    }

    /**
    * Destructor: Clears up memory
    */
    node::~node() {
        for (int i = 0; i < next.size(); i++) {
            next[i] = NULL;
        }
    }

    /**
    * Generate Width: Generates a random node width for next vector
    */
    int node::generateWidth(int maxWidth)
    {
        width = 1;
        int num = rand();
        while (num % 2 == 1) {
            width++;
            num = rand();
        }

        if (width > maxWidth) {
            width = maxWidth;
        }
    }
}
