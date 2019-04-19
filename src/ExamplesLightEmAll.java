
import javalib.impworld.WorldScene;
import javalib.worldimages.*;
import tester.Tester;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class ExamplesLightEmAll {
  LightEmAll test;
  LightEmAll test2;
  LightEmAll test3;
  LightEmAll test4;
  LightEmAll test5;

  WorldImage star1;

  GamePiece empty;
  GamePiece one;
  GamePiece two;
  GamePiece three;
  GamePiece four;
  GamePiece five;
  GamePiece six;
  GamePiece nine;
  GamePiece ten;
  GamePiece eleven;
  GamePiece seven;
  GamePiece eight;

  ArrayList<ArrayList<GamePiece>> board1;
  ArrayList<ArrayList<GamePiece>> board2;
  ArrayList<ArrayList<GamePiece>> board3;
  ArrayList<ArrayList<GamePiece>> board4;

  ArrayList<ArrayList<ArrayList<GamePiece>>> quads1;
  ArrayList<ArrayList<ArrayList<GamePiece>>> quads2;

  ArrayList<Edge> edges1;

  WorldImage square;
  WorldImage square1;
  WorldImage square2;
  WorldImage square3;
  WorldImage power;
  WorldImage link;
  WorldImage link1;
  WorldImage link2;

  HashMap<GamePiece, GamePiece> representatives;
  ArrayList<GamePiece> nodes;
  HashMap<GamePiece, Edge> cameFromEdge;
  ArrayList<Edge> edges2 = new ArrayList<Edge>();


  Result jack1;
  Result jack2;
  Result henry1;
  Result henry2;
  User Jack;
  User Henry45;


  void initData() {
    this.test = new LightEmAll(7, 7, 10);
    this.test3 = new LightEmAll(2, 2, 2);
    this.test2 = new LightEmAll(3, 2, 1);
    this.test4 = new LightEmAll(1, 2, -1);
    this.test5 = new LightEmAll(5, 5);
    this.empty = new GamePiece(1, 1);
    this.one = new GamePiece(0, 0);
    this.two = new GamePiece(0, 1);
    this.three = new GamePiece(1, 0);
    this.nine = new GamePiece(0, 0);
    this.ten = new GamePiece(0, 0);
    this.eleven = new GamePiece(0, 0);
    this.seven = new GamePiece(0, 0);
    this.eight = new GamePiece(0, 0);
    this.one.left = true;
    this.two.right = true;
    this.two.top = true;
    this.three.left = true;
    this.three.right = true;
    this.three.top = true;
    this.three.bottom = true;
    this.five = new GamePiece(1, 1);
    this.five.top = true;
    this.five.left = true;
    this.four = new GamePiece(1, 1);
    this.four.top = true;
    this.four.right = true;
    this.six = new GamePiece(1, 1);
    this.six.bottom = true;

    this.board1 = new ArrayList<ArrayList<GamePiece>>();
    this.board2 = new ArrayList<ArrayList<GamePiece>>();
    this.board3 = new ArrayList<ArrayList<GamePiece>>();
    this.board4 = new ArrayList<ArrayList<GamePiece>>();

    ArrayList<GamePiece> temp1 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> temp2 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> temp3 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> temp4 = new ArrayList<GamePiece>();
    temp1.add(this.empty);
    temp1.add(this.nine);
    temp2.add(this.ten);
    temp2.add(this.eleven);
    this.board1.add(temp1);
    this.board1.add(temp2);
    temp3.add(this.empty);
    temp3.add(this.nine);
    temp3.add(this.ten);
    temp4.add(this.eleven);
    temp4.add(this.seven);
    temp4.add(this.eight);
    this.board2.add(temp3);
    this.board2.add(temp4);

    GamePiece empty = new GamePiece(1, 1);
    GamePiece one = new GamePiece(0, 0);
    GamePiece two = new GamePiece(0, 0);
    GamePiece three = new GamePiece(0, 0);
    GamePiece four = new GamePiece(0, 0);
    GamePiece five = new GamePiece(0, 0);
    empty.bottom = true;
    one.top = true;
    one.right = true;
    two.bottom = true;
    three.top = true;
    three.left = true;
    five.top = true;
    five.bottom = true;
    four.top = true;
    four.bottom = true;


    ArrayList<GamePiece> temp5 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> temp6 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> temp7 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> temp8 = new ArrayList<GamePiece>();
    temp5.add(empty);
    temp5.add(one);
    temp6.add(two);
    temp6.add(three);
    this.board3.add(temp5);
    this.board3.add(temp6);
    temp7.add(empty);
    temp7.add(five);
    temp7.add(one);
    temp8.add(two);
    temp8.add(four);
    temp8.add(three);
    this.board4.add(temp7);
    this.board4.add(temp8);

    empty = new GamePiece(1, 1);
    one = new GamePiece(0, 0);
    two = new GamePiece(0, 0);
    three = new GamePiece(0, 0);
    four = new GamePiece(0, 0);
    five = new GamePiece(0, 0);

    this.quads1 = new ArrayList<ArrayList<ArrayList<GamePiece>>>();
    this.quads2 = new ArrayList<ArrayList<ArrayList<GamePiece>>>();

    temp5 = new ArrayList<GamePiece>();
    temp6 = new ArrayList<GamePiece>();
    temp7 = new ArrayList<GamePiece>();
    temp8 = new ArrayList<GamePiece>();
    ArrayList<ArrayList<GamePiece>> temp9 = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<ArrayList<GamePiece>> temp10 = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<ArrayList<GamePiece>> temp11 = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<ArrayList<GamePiece>> temp12 = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<GamePiece> temp13 = new ArrayList<GamePiece>();
    ArrayList<GamePiece> temp14 = new ArrayList<GamePiece>();
    ArrayList<ArrayList<GamePiece>> temp15 = new ArrayList<ArrayList<GamePiece>>();
    ArrayList<ArrayList<GamePiece>> temp16 = new ArrayList<ArrayList<GamePiece>>();

    temp5.add(empty);
    temp9.add(temp5);
    temp6.add(one);
    temp10.add(temp6);
    temp7.add(two);
    temp11.add(temp7);
    temp8.add(three);
    temp12.add(temp8);

    this.quads1.add(temp9);
    this.quads1.add(temp10);
    this.quads1.add(temp11);
    this.quads1.add(temp12);

    temp13.add(empty);
    temp13.add(four);
    temp14.add(two);
    temp14.add(five);
    temp15.add(temp13);
    temp16.add(temp14);

    this.quads2.add(temp15);
    this.quads2.add(temp10);
    this.quads2.add(temp16);
    this.quads2.add(temp12);

    this.edges1 = new ArrayList<Edge>();
    this.edges1.add(new Edge(this.one, this.two, 91));
    this.edges1.add(new Edge(this.two, this.four, 10));
    this.edges1.add(new Edge(this.one, this.three, 41));
    this.edges1.add(new Edge(this.three, this.four, 21));
    this.edges2 = new ArrayList<Edge>();
    this.edges2.add(new Edge(this.one, this.two, 91));
    this.edges2.add(new Edge(this.two, this.three, 10));
    this.edges2.add(new Edge(this.three, this.four, 41));
    this.cameFromEdge = new HashMap<GamePiece, Edge>();
    this.cameFromEdge.put(this.two, this.edges2.get(0));
    this.cameFromEdge.put(this.three, this.edges2.get(1));
    this.cameFromEdge.put(this.four, this.edges2.get(2));

    this.star1 = new OverlayImage(new StarImage(20, 7, OutlineMode.OUTLINE,
            Color.lightGray), new StarImage(20, 7, OutlineMode.SOLID,
            Color.getHSBColor(0.7f, 0.8f, 1f)));

    this.square = new FrameImage(new RectangleImage(50, 50,
            OutlineMode.SOLID, Color.DARK_GRAY));
    this.power = new OverlayImage(new StarImage(20, 7, OutlineMode.OUTLINE,
            Color.lightGray), new StarImage(20, 7, OutlineMode.SOLID,
            Color.getHSBColor(0.7f, 0.8f, 1f)));
    this.link = new RectangleImage(24, 5, OutlineMode.SOLID,
            Color.getHSBColor(0.7f, 0.99f / 2.0f, 0.7f)).movePinhole(12, 0);
    this.link1 = new RectangleImage(24, 5, OutlineMode.SOLID,
            Color.gray).movePinhole(12, 0);
    this.link2 = new RectangleImage(24, 5, OutlineMode.SOLID,
            Color.getHSBColor(1 / 6f, 0.3f, 1f)).movePinhole(12, 0);

    this.square1 = new OverlayImage(new RotateImage(this.link1, 90), this.square);
    this.square2 = new OverlayImage(new RotateImage(new RectangleImage(24, 5, OutlineMode.SOLID,
            Color.getHSBColor(0, 0, 0.7f)).movePinhole(12, 0), 270), square);
    this.square3 = new OverlayImage(new RotateImage(this.link1, 270), this.square);


    this.representatives = new HashMap<GamePiece, GamePiece>();
    this.representatives.put(this.empty, this.empty);
    this.representatives.put(this.one, this.one);
    this.representatives.put(this.two, this.two);
    this.representatives.put(this.three, this.three);
    this.nodes = new ArrayList<GamePiece>();
    this.nodes.add(this.empty);
    this.nodes.add(this.one);
    this.nodes.add(this.two);
    this.nodes.add(this.three);


    this.jack1 = new Result("Jack", 5, 5, 100, 20);
    this.jack2 = new Result("Jack", 3, 3, 60, 15);
    this.henry1 = new Result("Henry45", 4, 4, 40, 10);
    this.henry2 = new Result("Henry45", 5, 5, 120, 34);

    ArrayList<Result> jack = new ArrayList<Result>();
    jack.add(this.jack1);
    jack.add(this.jack2);
    ArrayList<Result> henry = new ArrayList<Result>();
    henry.add(this.henry1);
    henry.add(this.henry2);
    this.Jack = new User("Jack", "password", jack);
    this.Henry45 = new User("Henry45", "0803", henry);


  }

  void testIsConnected(Tester t) {
    this.initData();
    t.checkExpect(this.empty.isConnected(0), true);
    t.checkExpect(this.empty.isConnected(-1), false);
    t.checkExpect(this.empty.isConnected(1), true);
  }

  void testShortestPath(Tester t) {
    this.initData();
    t.checkExpect(this.test5.shortestPath(this.test5.board.get(0).get(0),
            this.test5.board.get(0).get(0)), 1);
    t.checkExpect(this.test5.shortestPath(this.test5.board.get(0).get(0),
            this.test5.board.get(0).get(1)) > 0, true);
    t.checkExpect(this.test5.shortestPath(this.test5.board.get(0).get(0),
            this.test5.board.get(3).get(3)) > 3, true);
  }

  // this is used to test the connection between the database and the application
  // using JDBC
  // this will only pass if the database has been setup and the connection described in
  // DBUtils has been created
  // this will throw an exception if the connection does not work
  void testConnection(Tester t) {
    this.initData();
    LightEmAllAPI api = new LightEmAllAPI(new DBUtils());
    ArrayList<User> users = api.retrieveUsers();
    t.checkExpect(users.contains(this.Jack), false);
    t.checkExpect(users.contains(this.Henry45), false);

  }

  void testReconstruct(Tester t) {
    this.initData();
    t.checkExpect(this.test.reconstruct(cameFromEdge, this.one, this.two), 2);
    t.checkExpect(this.test.reconstruct(cameFromEdge, this.one, this.three), 3);
    t.checkExpect(this.test.reconstruct(cameFromEdge, this.three, this.three), 1);
  }

  void testOnTick(Tester t) {
    this.initData();
    t.checkExpect(this.test.time, 0);
    this.test.onTick();
    t.checkExpect(this.test.time, 1);
    this.test.onTick();
    t.checkExpect(this.test.time, 2);
  }

  void testAllConnected(Tester t) {
    this.initData();
    t.checkExpect(this.test4.allConnected(), false);
    t.checkExpect(this.test5.allConnected(), false);
    t.checkExpect(this.test3.allConnected(), true);
  }

  void testFind(Tester t) {
    this.initData();
    t.checkExpect(this.test.find(this.empty, this.representatives), this.empty);
    t.checkExpect(this.test.find(this.one, this.representatives), this.one);
    this.representatives.put(this.empty, this.one);
    t.checkExpect(this.test.find(this.empty, this.representatives), this.one);
    t.checkExpect(this.test.find(this.two, this.representatives), this.two);
    this.representatives.put(this.two, this.three);
    t.checkExpect(this.test.find(this.two, this.representatives), this.three);
    this.representatives.put(this.three, this.one);
    t.checkExpect(this.test.find(this.three, this.representatives), this.one);
    t.checkExpect(this.test.find(this.two, this.representatives), this.one);
  }

  void testSortEdges(Tester t) {
    this.initData();
    ArrayList<Edge> edges1 = new ArrayList<Edge>();
    edges1.add(new Edge(this.two, this.four, 10));
    edges1.add(new Edge(this.three, this.four, 21));
    edges1.add(new Edge(this.one, this.three, 41));
    edges1.add(new Edge(this.one, this.two, 91));
    this.test.sortEdges(this.edges1);
    t.checkExpect(this.edges1, edges1);
  }


  void testGenerateBoard(Tester t) {
    this.initData();
    this.test.generateFBoard(this.board1);
    t.checkExpect(this.board1, this.board3);
    this.initData();
    this.test.generateFBoard(this.board2);
    t.checkExpect(this.board2, this.board4);
  }

  void testGenerateQuadrants(Tester t) {
    this.initData();
    t.checkExpect(this.test.generateQuadrants(this.board1), this.quads1);
    this.initData();
    t.checkExpect(this.test.generateQuadrants(this.board2), this.quads2);
  }

  void testUpdateBoard(Tester t) {
    this.initData();
    t.checkExpect(this.test2.board.get(0).get(0).distance, 2);
    this.test2.board.get(0).get(0).rotate();
    this.test2.updateBoard();
    t.checkExpect(this.test2.board.get(0).get(0).distance, 2);
    this.initData();
    t.checkExpect(this.test2.board.get(0).get(1).distance, 1);
    this.test2.board.get(0).get(1).rotate();
    this.test2.updateBoard();
    t.checkExpect(this.test2.board.get(0).get(0).distance, 2);
    this.initData();
    t.checkExpect(this.test2.board.get(1).get(1).distance, 0);
    t.checkExpect(this.test2.board.get(0).get(1).distance, 1);
    this.test2.board.get(1).get(1).rotate();
    this.test2.updateBoard();
    t.checkExpect(this.test2.board.get(1).get(1).distance, 0);
    t.checkExpect(this.test2.board.get(0).get(0).distance, 2);

  }

  void testUpdateGamePiece(Tester t) {
    this.initData();
    t.checkExpect(this.test2.board.get(0).get(1).distance, 1);
    this.test2.updateGamePiece(test2.powerCol, test2.powerRow, 1, new ArrayList<GamePiece>());
    t.checkExpect(this.test2.board.get(0).get(1).distance, 2);
    this.initData();
    this.test2.updateGamePiece(test2.powerCol, test2.powerRow, 1,
            new ArrayList<GamePiece>(Arrays.asList(this.test2.board.get(0).get(1))));
    t.checkExpect(this.test2.board.get(0).get(1).distance, 1);
    this.initData();
    this.test2.board.get(1).get(1).rotate();
    this.test2.updateGamePiece(test2.powerCol, test2.powerRow, 1,
            new ArrayList<GamePiece>(Arrays.asList(this.test2.board.get(0).get(1))));
    t.checkExpect(this.test2.board.get(1).get(1).distance, 1);
    t.checkExpect(this.test2.board.get(0).get(1).distance, 1);
  }

  void testIsValidIndex(Tester t) {
    this.initData();
    t.checkExpect(this.test.isValidIndex(0, 0), true);
    t.checkExpect(this.test.isValidIndex(-1, 0), false);
    t.checkExpect(this.test.isValidIndex(7, 7), false);
    t.checkExpect(this.test.isValidIndex(6, 6), true);
    t.checkExpect(this.test.isValidIndex(4, 3), true);
  }

  void testRotate(Tester t) {
    this.initData();
    t.checkExpect(this.empty, this.empty);
    this.empty.rotate();
    t.checkExpect(this.empty, this.empty);
    t.checkExpect(this.one.left, true);
    this.one.rotate();
    t.checkExpect(this.one.left, false);
    t.checkExpect(this.one.top, true);
    this.two.rotate();
    t.checkExpect(this.two.top, false);
    t.checkExpect(this.two.right, true);
    t.checkExpect(this.two.bottom, true);
    this.three.rotate();
    t.checkExpect(this.three.top, true);
    t.checkExpect(this.three.bottom, true);
    t.checkExpect(this.three.left, true);
    t.checkExpect(this.three.right, true);
  }

  void testAddPowerStation(Tester t) {
    this.initData();
    t.checkExpect(this.empty.powerStation, false);
    this.empty.addPowerStation();
    t.checkExpect(this.empty.powerStation, true);
    t.checkExpect(this.three.powerStation, false);
    this.three.addPowerStation();
    t.checkExpect(this.three.powerStation, true);
  }

  void testDrawGamePiece(Tester t) {
    this.initData();
    t.checkExpect(this.empty.draw(1), this.square);
    this.empty.addPowerStation();
    t.checkExpect(this.empty.draw(1), new OverlayImage(this.power, this.square));
    t.checkExpect(this.one.draw(1), new OverlayImage(this.link, this.square));
    this.one.rotate();
    t.checkExpect(this.one.draw(1), new OverlayImage(new RotateImage(this.link, 90), this.square));
    t.checkExpect(this.two.draw(1), new OverlayImage(new RotateImage(this.link, 90),
            new OverlayImage(new RotateImage(this.link, 180), this.square)));
    t.checkExpect(this.three.draw(1), new OverlayImage(new RotateImage(this.link, 270),
            new OverlayImage(new RotateImage(this.link, 90), new OverlayImage(this.link,
                    new OverlayImage(new RotateImage(this.link, 180), this.square)))));
    t.checkExpect(this.six.draw(0), new OverlayImage(new RotateImage(this.link2, 270),
            this.square));
    t.checkExpect(this.six.draw(-1), new OverlayImage(new RotateImage(this.link1, 270),
            this.square));
  }

  void testOnMousePressed(Tester t) {
    this.initData();
    this.test.onMousePressed(new Posn(2000, 2000));
    t.checkExpect(this.test, new LightEmAll(7, 7, 10));
    GamePiece g = this.test.nodes.get(0);
    g.rotate();
    this.test.onMousePressed(new Posn(0, 0));
    t.checkExpect(this.test.nodes.get(0), g);
    this.initData();
    GamePiece x = this.test.nodes.get(1);
    this.test.onMousePressed(new Posn(0, 0));
    t.checkExpect(this.test.nodes.get(1), x);
  }

  // generates a 7x7 Objects.Objects.LightEmAll game
  // void testBigBang(Tester t) {
  //   this.initData();
  //  LightEmAll run = new LightEmAll();
  // run.bigBang(450, 450, 1);
  //  }

  void testDraw(Tester t) {
    this.initData();
    WorldImage board = new BesideImage(new EmptyImage(), new AboveImage(new AboveImage(new EmptyImage(),
            this.square3), new OverlayImage(this.star1, this.square1)));
    WorldImage timeCount = new TextImage("Time: " +
            Integer.toString(0) + "   ", 5, Color.DARK_GRAY);
    WorldImage moveCount = new TextImage("Moves: " +
            Integer.toString(0), 5, Color.DARK_GRAY);
    board = new AboveImage(board, new OverlayImage(
            new BesideImage(timeCount, moveCount),
            new RectangleImage(50, 40, OutlineMode.SOLID, Color.gray)));
    t.checkExpect(this.test4.draw(), board);
  }

  void testMakeScene(Tester t) {
    this.initData();
    WorldImage board = new BesideImage(new EmptyImage(), new AboveImage(new AboveImage(new EmptyImage(),
            this.square2), new OverlayImage(this.star1, this.square1)));
    WorldImage timeCount = new TextImage("Time: " +
            Integer.toString(0) + "   ", 20, Color.DARK_GRAY);
    WorldImage moveCount = new TextImage("Moves: " +
            Integer.toString(0), 20, Color.DARK_GRAY);
    board = new AboveImage(board, new OverlayImage(
            new BesideImage(timeCount, moveCount),
            new RectangleImage(50, 40, OutlineMode.SOLID, Color.gray)));
    WorldScene test = new WorldScene(450, 450);
    test.placeImageXY(this.test4.draw(), 25, 70);
    t.checkExpect(this.test4.makeScene(), test);

  }

  void testLightEmAll(Tester t) {
    this.initData();
    t.checkExpect(this.test3.height, 2);
    t.checkExpect(this.test3.width, 2);
    t.checkExpect(this.test3.board.size(), 2);
    t.checkExpect(this.test3.board.get(0).size(), 2);
    t.checkExpect(this.test3.nodes.size(), 4);
    t.checkExpect(this.test3.powerRow, 1);
    t.checkExpect(this.test3.powerCol, 1);
    t.checkConstructorException(new IllegalArgumentException("Board must be more than 1 cell"),
            "LightEmAll", 1, 1, 1);
    t.checkConstructorException(new IllegalArgumentException("Board must be more than 1 cell"),
            "LightEmAll", 2, 0, 1);
    t.checkConstructorException(new IllegalArgumentException("Board must be more than 1 cell"),
            "LightEmAll", 0, 2, 1);
    t.checkConstructorException(new IllegalArgumentException("Board must be more than 1 cell"),
            "LightEmAll", -1, 2, 1);
    t.checkConstructorException(new IllegalArgumentException("Board must be more than 1 cell"),
            "LightEmAll", 2, -1, 1);
  }

  void testSameGamePiece(Tester t) {
    this.initData();
    t.checkExpect(this.empty.sameGamePiece(this.one), false);
    t.checkExpect(this.one.sameGamePiece(this.two), false);
    t.checkExpect(this.one.sameGamePiece(this.one), true);
  }

  void testDrawLogIn(Tester t) {
    this.initData();

    LightEmAll empty1 = new LightEmAll();

    WorldImage box = new RectangleImage(200, 30, OutlineMode.OUTLINE, Color.DARK_GRAY);
    WorldImage userBox = new OverlayImage(new TextImage("Username", 15, Color.darkGray), box);
    WorldImage pwdBox = new OverlayImage(new TextImage("Password", 15, Color.darkGray), box);
    WorldImage rowBox = new OverlayImage(new TextImage("Number of rows", 15, Color.darkGray), box);
    WorldImage colBox = new OverlayImage(new TextImage("Number of columns", 15, Color.darkGray), box);
    WorldImage text = new AboveImage(new TextImage("Enter all the fields", 15, Color.darkGray),
            new TextImage("Press \'tab\' to move between fields", 15, Color.darkGray),
            new TextImage("Press \'enter\' after all fields are filled", 15, Color.darkGray));

    WorldImage board = new OverlayImage(new AboveImage(text,
            new AboveImage(new AboveImage(new AboveImage(userBox,
                    pwdBox), rowBox), colBox)), new RectangleImage(450, 450, OutlineMode.SOLID, Color.gray));

    WorldImage box2 = new RectangleImage(200, 30, OutlineMode.OUTLINE, Color.DARK_GRAY);
    WorldImage userBox2 = new OverlayImage(new TextImage("Username", 15, Color.darkGray), box);
    WorldImage pwdBox2 = new OverlayImage(new TextImage("Password", 15, Color.darkGray), box);
    WorldImage rowBox2 = new OverlayImage(new TextImage("5", 15, Color.darkGray), box);
    WorldImage colBox2 = new OverlayImage(new TextImage("5", 15, Color.darkGray), box);
    WorldImage text2 = new AboveImage(new TextImage("Enter all the fields", 15, Color.darkGray),
            new TextImage("Press \'tab\' to move between fields", 15, Color.darkGray),
            new TextImage("Press \'enter\' after all fields are filled", 15, Color.darkGray));

    WorldImage board2 = new OverlayImage(new AboveImage(text,
            new AboveImage(new AboveImage(new AboveImage(userBox2,
                    pwdBox2), rowBox2), colBox2)), new RectangleImage(450, 450, OutlineMode.SOLID, Color.gray));

    WorldImage box3 = new RectangleImage(200, 30, OutlineMode.OUTLINE, Color.DARK_GRAY);
    WorldImage userBox3 = new OverlayImage(new TextImage("Jason", 15, Color.darkGray), box);
    WorldImage pwdBox3 = new OverlayImage(new TextImage("********", 15, Color.darkGray), box);
    WorldImage rowBox3 = new OverlayImage(new TextImage("5", 15, Color.darkGray), box);
    WorldImage colBox3 = new OverlayImage(new TextImage("5", 15, Color.darkGray), box);
    WorldImage text3 = new AboveImage(new TextImage("Enter all the fields", 15, Color.darkGray),
            new TextImage("Press \'tab\' to move between fields", 15, Color.darkGray),
            new TextImage("Press \'enter\' after all fields are filled", 15, Color.darkGray));

    WorldImage board3 = new OverlayImage(new AboveImage(text,
            new AboveImage(new AboveImage(new AboveImage(userBox3,
                    pwdBox3), rowBox3), colBox3)), new RectangleImage(450, 450, OutlineMode.SOLID, Color.gray));



    t.checkExpect(empty1.drawLogin(), board);
    t.checkExpect(this.test5.drawLogin(), board2);

    test5.username = "Jason";
    test5.password = "hello123";

    t.checkExpect(this.test5.drawLogin(), board3);
  }

  void testDrawLoginFail(Tester t) {
    this.initData();
    LightEmAll empty1 = new LightEmAll();

    WorldImage board = new AboveImage(new TextImage(
            "Login failed",25, Color.red),
            new TextImage(
                    "Press \'r\' to retry or",20, Color.darkGray),
            new TextImage(
                    "Press \'enter\' to create entry as new account",20, Color.darkGray));

    t.checkExpect(this.test5.drawLoginFail(), new OverlayImage(board,
            new RectangleImage(450, 450, OutlineMode.SOLID, Color.gray)));

    t.checkExpect(empty1.drawLoginFail(), new OverlayImage(board,
            new RectangleImage(450, 450, OutlineMode.SOLID, Color.gray)));
  }

    void testOnKeyEvent(Tester t) {
    this.initData();

    this.test5.loginFail = true;
    this.test5.username = "Jackkkskks";
    this.test5.password = "lollol";
    this.test5.width = 12;
    this.test5.height = 24;

    this.test5.onKeyEvent("r");

    t.checkExpect(this.test5.loginFail, false);
    t.checkExpect(this.test5.username,  "");
    t.checkExpect(this.test5.password , "");
    t.checkExpect(this.test5.width , 0);
    t.checkExpect(this.test5.height,  0);



}

void testLoginKeyEvent(Tester t) {

    this.initData();

    this.test5.username = "aloknath";
    this.test5.tempUse = 0;

    this.test5.loginKeyEvent("backspace");

    t.checkExpect(this.test5.username, "aloknat");



    this.test5.username = "";
    this.test5.tempUse = 1;
    this.test5.password = "lmaoHelpme";

    this.test5.loginKeyEvent("backspace");

    t.checkExpect(this.test5.password , "lmaoHelpm");

  this.test5.username = "";
  this.test5.tempUse = 2;
  this.test5.password = "";

  this.test5.loginKeyEvent("backspace");

  t.checkExpect(this.test5.height , 0);

  this.test5.username = "";
  this.test5.tempUse = 3;
  this.test5.password = "";

  this.test5.loginKeyEvent("backspace");

  t.checkExpect(this.test5.width , 0);

  this.test5.tempUse = 0;

  this.test5.loginKeyEvent("tab");

  t.checkExpect(this.test5.tempUse , 1);

  this.test5.username = "a";
  this.test5.tempUse = 0;
  this.test5.loginKeyEvent("b");

  t.checkExpect(this.test5.username ,"ab");


  this.test5.password = "s";
  this.test5.tempUse = 1;
  this.test5.loginKeyEvent("a");

  t.checkExpect(this.test5.password , "sa");

  this.test5.tempUse = 2;
  this.test5.loginKeyEvent("2");

  t.checkExpect(this.test5.height , 2);

  this.test5.loginKeyEvent("a");

  t.checkExpect(this.test5.height, 2);

  this.test5.tempUse = 3;
  this.test5.loginKeyEvent("3");

  t.checkExpect(this.test5.width , 3);

  this.test5.loginKeyEvent("2");

  t.checkExpect(this.test5.width , 2);

  this.test5.loginKeyEvent("a");

  t.checkExpect(this.test5.width , 2);

}
  }
