Matthew Johsen (u1173601)
(9/19/2019): 
- I'm going to follow the API as closely as possible to implement the Spreadsheet class, and I will create a separate class to represent cells.
- I will be using newer versions of the PS2 and PS3 libries than were graded because I was able to make them more efficient and fixed bugs that showed up in the grading tests.
- N/A (No comments for grader at this time)
(9/26/2019):
- Following the guidelines for PS5 to update PS4 to use the updated AbstractSpreadsheet. Haven't looked into the saving and versions aspects of the spreadsheet yet but have hopefully
finished the rest of the methods. The only other things I am still working on are the lookup method to provide to the evaluate part of GetCellValue and determining whether I should
add a value field to the cell class to store values and make it more efficient.
- It's later now and I'm still having trouble with the lookup method. However, I believe I have successfully implemented saveing, now I just need to understand the versioning and
reading of the file so I can read the saved file.
(9/27/2019):
- It's now 2am and I just finished implementing the Save and GetVersion methods to read and write the XML for the spreadsheet. All I need to do now is to implement the CellLookup for
the Get value method which involves finding a way to make it constant and incorporate Changed into the spreadsheet. I know that I need to use the return list of the set contents methods
for the optimal retrieval of the value.
- Finished Spreadsheet class and most of testing.
- One thing to note is I wasn't able to test 2 of the GetSavedVersions thrown exceptions because the XML writer wouldn't write things that would throw XMLExceptions.