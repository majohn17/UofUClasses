/* This node class is used to build drop lists for the
 * string_set class.
 *
 * Peter Jensen  & Matthew Johnsen (u1173601)
 * January 28, 2020
 */

#ifndef NODE_H
#define NODE_H

#include <vector>
#include <string>
#include <stdlib.h>

using namespace std;

namespace cs3505
{
    // We're in a namespace - declarations will be within this CS3505 namespace.
    // (There are no definitions here - see node.cpp.)

    /* Node class for holding elements. */
    class node
    {
        friend class string_set; //Allows access to private fucntions and data

        private:
            node(string s, int maxWidth);       //Constructor
            ~node();                            //Destructor
            int generateWidth(int maxWidth);    //Generates a random width for the vector<node*>
            string data;                        //Holds the data of the node
            vector <node*> next;                //Holds node pointers to the next node
            int width;                          //The Width of the node
    };
}

#endif
