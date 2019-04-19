# LightEmAll

Extra Credits:
1) Gradient
2) Timer
3) Move Counter
4) Horizontal Bias
5) Restarting the game without closing (by pressing 'r')
6) Additional screens depending on the state (login, login fail, game or leader board) of the game (with database)
7) Database Connection to store all the users and results
8) Ability to create new users in application
9) Ability to choose size of board in application


How to run the program:

In order to create a game that is simply in accordance with the assignment, the constructor 
LightEmAll(int width, int height) must be used. This will demonstrate the first 5 extra credit implementations
as well.
In order to use the database and the additional extra credit implementations there are multiple requirements.
Firstly, MySQL must be installed on the computer, and the attached sql file must be setup in the localhost port 3306
according to the jdbc:mysql://localhost:3306/LightEmAll?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=EST5EDT".
A connection with all privileges to the LightEmAll schema with username "cs2510" and password "fundies123" must be
created in order to use the default constructor of LightEmAll ('LightEmAll()'), DBUtils ('DBUtils()') and
LightEmAllAPI ('LightEmAllAPI()').
The attached mysql connector library must also be imported in order to use the JDBC.


Functionality of the program:

This program is intended to function as a more fully implemented version of LightEmAll with support for multiple users,
though not at the same time (for now), and storing of the results of each game played by a user. In order to do this,
MySQL and the Java Database Connection (JDBC) were used along with an edited version of LightEmAll to support the
functionality.
The user starts at the login page where the user enters a username, password and the desired size of the board.
If the username and password do not exist, the user can either choose to try logging in again or create a new account.
After being logged in, a LightEmAll game with the given board size is created. The set limit for the board size is 9x9.
After the user wins the game the top 5 scores are displayed. The score is calculated by adding time and moves. Lower
scores are better. The user can then start a new game with the same size board by pressing 'r'.

Untestable Methods:

Many of the methods such as drawLeaderBoard are untestable due to their dependence on the database. The output of the
methods would be dependent on the database's contents, therefore the expected output cannot be determined.
Furthermore, the methods involved with the use of the database such as the ones in LightEmAllAPI and DBUtils could not
be tested because they are dependent on the database as well.
However, it should be noted that all of the methods were extensively tested through use in the program.

Intentional program breaks:
There are some cases where the program breaks through means such as throwing an exception.
One example of the case is when the entered username exists and the user is trying to insert the username into the
database. The reason why is because the username needs to be unique.
Another case is when the given width and height are both 1 and 1 or if any of them are less than 1. This is because
this would create a board that is either already solved or it would not exist.
