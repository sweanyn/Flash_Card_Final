**Project Title:** Flashcard App

**Project Description:**

The Flashcard App for Android devices was a comprehensive study tool, developed using Java on the Android
Studio platform, utilizing the Room Persistence Library for embedded database operations. The Git version control
system was employed to allow for streamlined and collaborative development.

This app encompasses three main Activities: Main Menu, Flashcard Book, and Flashcard, each serving unique
purposes that collectively contributed to an efficient and engaging learning experience.

1. **Main Menu Activity:** The home page of the app, where users could see all of their custom-made flashcard books.
    This activity permitted users to Create, Update, and Delete (CUD) their books, giving them full control over their
    study materials. The implementation of an intuitive toolbar ensured smooth navigation throughout the app.
2. **Flashcard Book Activity:** This segment presented all the flashcards contained in a selected book. Similar to the
    Main Menu, this activity also supported CUD operations, granting the user the flexibility to tailor their flashcards
    according to their study requirements.
3. **Flashcard Activity:** Here, each flashcard was displayed, presenting the question with a 'tap to reveal' feature for
    the answer. This interactive design facilitated an engaging study experience.

A key aspect of the app was its careful consideration of edge cases. For instance, when a user created a new
flashcard book, they could instantly add flashcards, each composed of a tailored question and answer. Provisions were
made to ensure that even if a book with the same name existed in the database, the flashcard was added to the existing
book rather than creating a duplicate. Furthermore, if the user tried to delete a book or a flashcard, a confirmation dialog
box was presented to prevent accidental deletion, thereby safeguarding the user's study resources.

Newly created flashcard books were immediately available for study, allowing users to start their learning session
seamlessly.


**Requirements Specifications:**

- Main Menu Activity will have the following functionalities:
  
    o A toolbar with buttons to allow for –
  
       ▪ Creation of Flashcard Books
       ▪ Books can either be created with or without an initial flashcard.
  
    o A recycler view showing all current flashcard books with their titles. (Scrollable)

        ▪ Each individual Book has an Edit button in the top right corner, that allows for changing the
          Book’s name.
  
    o A floating action button at the bottom for creating a new flashcard book.
  
       ▪ This button is a more user friendly version of the toolbar above.
  
- Flashcard Book Activity will have the following functionalities:
  
    o A toolbar with buttons to allow for –
  
       ▪ Back to main menu
       ▪ Creation, editing, and deletion of flashcards.
  
    o A content view showing all the current flashcards in the book.
  
       ▪ This view contains both a Question-and-Answer text view.
       ▪ Each field (Question and Answer) has an edit button to edit the information of the current
          flashcard.
       ▪ The “Answer” field has a [Tap to Reveal] feature, which by default hides the answer from the
          user initially.
  
    o Two individual floating action buttons, which allow for continuous scrolling of all the flashcards related to
       the books (Circular list traversal).
  
- Flashcard Activity will have the following functionalities:
  
    o Editing of current flashcard (Both question and answer).
    o A “reveal” feature, that will reveal the answer to the question when pressed.
  
       - This feature was made to be a simple text transition.


**Technical Description:**

**1. Activities:**
    - **MainActivity.java:** This serves as the home page of the app, displaying all the custom-made flashcard
       books in a RecyclerView with items evenly spaced by using **SpaceItemDecoration.java**. Users could
       create and update books as required. A Floating Action Button (FAB) was also present for users to create
       a new book. The layout for this Activity was defined in **activity_main.xml** , and **content_main.xml**
       contained the RecyclerView.
    - **BookActivity.java** : Once a book was selected, this Activity displayed all the flashcards of the chosen
       book. It also supports _Create, Update,_ and _Delete_ operations for flashcards. A question-and-answer field
       was displayed on each card with a 'tap to reveal' feature for the answer. Two Floating Action Buttons
       (FABs) were placed at the bottom for users to navigate between flashcards. The layout for this Activity
       was defined in **activity_book.xml** , and **content_book.xml** handled the flashcard's question and answer
       display.
       
**2. Dialogs:**
    - **AddBookDialog:** This dialog was initiated when a user wanted to create a new book from the main
       activity. It captured user inputs for book title and the first flashcard's question and answer. The layout for
       this dialog was defined in dialog_add_book.xml.
    - **AddCardDialog:** This dialog allowed users to add new flashcards to an existing book. It accepted user
       inputs for the flashcard's question and answer, as defined in **dialog_add_card.xml**.
       
**3. Database classes:**
    - **FlashcardDatabase.java:** This class encapsulated the database's creation and provided DAO instances.
    - **FlashcardDao** and **BookDao** : These were Data Access Objects (DAOs) that contained SQL queries to
       interact with the database.
    - **Flashcard and Book** : These were Entity classes that represented the database's tables. The Flashcard
       class contained fields like question, answer, and bookId, while the Book class had bookName.
       
**4. Adapter:**
    - **BookAdapter.java** : This adapter linked the RecyclerView in MainActivity with the data. Each item in the
       RecyclerView was a flashcard book, represented by a CardView as defined in item_flashcard_book.xml.
       
**5. Menus:**
    - **menu_main.xml** : This was the menu for MainActivity, which contained a button to add a new book.
    - **menu_book.xml** : This was the menu for BookActivity. It contained options to edit the book name, add a
       new flashcard, delete the current flashcard, delete the book, and navigate back to the home.


**Instructions for Using the App:**

1. On opening the app, you will find all your flashcard books displayed in MainActivity. Here, you can create a new
    book using the button in the top right corner or the FAB at the bottom. You can also use the Edit icon on any
    flashcard book to change it’s name.

2. To create a new book, enter the book name, and the first flashcard's question and answer, then click save.
   
4. Click on a book to open it in BookActivity, where all the flashcards of that book will be displayed.

5. Inside a book, you can navigate through flashcards using the two FABs at the bottom. The flashcard will display
    the question, and tapping on it will reveal the answer.

6. In the BookActivity menu, you can edit the book name, add a new flashcard, delete the current flashcard, or
    delete the book. To add a new flashcard, provide the question and answer, then click save.

7. If you wish to delete a flashcard or book, a confirmation dialog box will appear to prevent accidental deletion.
    Confirm your choice by clicking "Delete".


