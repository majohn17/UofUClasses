/* A 'string set' is defined as a set of strings stored
 * in sorted order in a drop list.  See the class video
 * for details.
 *
 * For lists that do not exceed 2^(max_next_width elements+1)
 * elements, the add, remove, and contains functions are 
 * O(lg size) on average.  The operator= and get_elements 
 * functions are O(size).   
 * 
 * Peter Jensen & Matthew Johnsen (u1173601)
 * January 28, 2020
 */

#include <iostream>
#include "string_set.h"


namespace cs3505
{
    /**
     * Constructor: The parameter indicates the maximum
     * width of the next pointers in the drop list nodes.
     */
    string_set::string_set(int max_next_width)
    {
        this->max_next_width = max_next_width;
        head = new node("", max_next_width);
        size = 0;
    }

    /**
     * Copy constructor: Initialize this set
     * to contain exactly the same elements as
     * another set.
     */
    string_set::string_set (const string_set & other)
    {
        this->max_next_width = other.max_next_width;
        head = new node("", max_next_width);
        size = 0;
        node* current = other.head;
        while (current->next[0] != NULL) {         
            current = current->next[0];
            add(current->data);
        }
    }

    /**
     * Destructor: Release any memory allocated
     * for this object.
     */
    string_set::~string_set()
    {
        node* current = head;
        //Delete every node but the last
        while (current->next[0] != NULL) {
            node* prev = current;
            current = current->next[0];
            delete prev;
        }
        delete current;
    }

    /**
     * Add: Adds a string to the set
     */
    void string_set::add(const std::string & target)
    {
        //Node to be added to list
        node* toAdd = new node(target, max_next_width);

        //Get list of previous nodes
        std::vector<node*> prev;
        for (int i = 0; i < head->next.size(); i++) {
            prev.push_back(head);
        }
        findPrev(prev, target);

        //Adds only if target string not already in the list
        if (prev[0]->next[0] == NULL || prev[0]->next[0]->data != target) {
            for (int i = 0; i < toAdd->width; i++) {
                toAdd->next[i] = prev[i]->next[i];
                prev[i]->next[i] = toAdd;
            }
            size++;
        }
        else {
            delete toAdd;
        }
    }

    /**
     * Remove: Removes a string from the set if it contains it
     */
    void string_set::remove(const std::string & target)
    {
        //Gets list of previous nodes to location of where target would be
        std::vector<node*> prev;
        for (int i = 0; i < head->next.size(); i++) {
            prev.push_back(head);
        }
        findPrev(prev, target);

        //Determines if the string to be removed is in the list and removes if it is
        if (prev[0]->next[0] != NULL && prev[0]->next[0]->data == target) {
            node* toRemove = prev[0]->next[0];
            for (int i = 0; i < toRemove->width; i++) {
                prev[i]->next[i] = toRemove->next[i];
            }
            delete toRemove;
            size--;
        }
    }

    /**
     * Contains: Determines if the set contains a string
     */
    bool string_set::contains(const std::string & target) const
    {
        std::vector<node*> prev;
        for (int i = 0; i < head->next.size(); i++) {
            prev.push_back(head);
        }
        for (int i = prev.size() - 1; i > -1; i--) {
            if (prev[i]->next[i] == NULL || prev[i]->next[i]->data > target) {
                //Do nothing
            }
            else {
                node* n = prev[i]->next[i];

                if (n->data == target) // returns true if ever finds the same
                    return true;

                for (int j = i; j > -1; j--) {
                    prev[j] = n;
                }
                i++; //Repeat the same level until find the previous
            }
        }
        return false;
    }

    /**
     * Get Size: Returns the size of the string set
     */
    int string_set::get_size() const
    {
        return size;
    }

    /**
     * Assignment Operator: Copys the rhs string set to this one
     */
    string_set & string_set::operator= (const string_set & rhs)
    {
        node* current = head;
        //Delete every node but the last
        while (current->next[0] != NULL) {
            node* prev = current;
            current = current->next[0];
            delete prev;
        }
        delete current;

        this->max_next_width = rhs.max_next_width;
        head = new node("", max_next_width);
        size = 0;
        node* c = rhs.head;
        while (c->next[0] != NULL) {
            c = c->next[0];
            add(c->data);
        }
    }

    /**
     * Get Elements: Returns a vector of all the strings in this set
     */
    std::vector<std::string> string_set::get_elements()
    {
        std::vector<std::string> vals;
        node* current = head;
        while (current->next[0] != NULL) {
            current = current->next[0];
            vals.push_back(current->data);
        }
        return vals;
    }

    /**
     * Find Prev: Gets all the previous nodes to where the target string
     * would go in the list
     */
    void string_set::findPrev(std::vector<node*> & prev, std::string target)
    {
        for (int i = prev.size() - 1; i > -1; i--) {
            if (prev[i]->next[i] == NULL || prev[i]->next[i]->data > target) {
                //Do nothing
            }
            else {
                node* n = prev[i]->next[i];
                if (target != n->data) {
                    for (int j = i; j > -1; j--) {
                        prev[j] = n;
                    }
                    i++; //Repeat the same level until find the previous
                }
            }
        }
    }
}
