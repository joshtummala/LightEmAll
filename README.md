# LightEmAll

/* Extra Credits:
1) Gradient
2) Timer
3) Move Counter
4) Horizontal Bias
5) Additional screens depending on the state (login, login fail, game or leader board) of the game (with database)
5) Database Connection to store all the users and results
6) Ability to create new users in application
7) Ability to choose size of board in application


How to run the program:

In order to create a game that is simply in accordance with the assignment, the constructor 
LightEmAll(int width, int height) must be used. This will demonstrate the first 4 extra credit implementations
as well.
In order to use the database and the additional extra credit implementations there are multiple requirements.
Firstly, MySQL must be installed on the computer, and the attached sql file must be setup in the localhost port 3306
according to the jdbc:mysql://localhost:3306/LightEmAll?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=EST5EDT".
A connection with all privileges to the LightEmAll schema with username "cs2510" and password "fundies123" must be
created in order to use the default constructor of DBUtils "DBUtils()"The attached JDBC connector library must also be imported.




Untestable Methods:

The method drawLeaderBoard is untestable because it is dependant on the database connection. This means that each time
a user plays the game and his result is created the leaderboard may change, hence making it impossible to test.
