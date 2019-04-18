import java.awt.Color;
import java.util.*;

import tester.*;
import javalib.impworld.*;
import javalib.worldimages.*;

class LightEmAll extends World {
  // a list of columns of GamePieces,
  // i.e., represents the board in column-major order
  ArrayList<ArrayList<GamePiece>> board;
  // a list of all nodes
  ArrayList<GamePiece> nodes;
  // a list of edges of the minimum spanning tree
  ArrayList<Edge> mst;
  // the width and height of the board
  int height;
  int width;
  // the current location of the power station,
  // as well as its effective radius
  int powerRow;
  int powerCol;
  int radius;
  // the time in ticks taken so far playing this game
  int time;
  // the number of moves done in the game
  int moves;
  // the users that can play this game
  ArrayList<User> users;


  // PART 3 : creates a random grid
  LightEmAll(int width, int height) {

    if ((width <= 1 && height <= 1)
        || width < 1 || height < 1) {
      throw new IllegalArgumentException("Board must be more than 1 cell");
    }

    this.height = height;
    this.width = width;
    this.board = new ArrayList<ArrayList<GamePiece>>();
    this.nodes = new ArrayList<GamePiece>();
    this.mst = new ArrayList<Edge>();
    this.powerRow = 0;
    this.powerCol = 0;
    this.radius = 0;
    this.time = 0;
    this.moves = 0;

    for (int i = 0; i < width; i++) {
      ArrayList<GamePiece> temp = new ArrayList<GamePiece>();
      for (int x = 0; x < height; x++) {
        GamePiece add = new GamePiece(x, i);
        temp.add(add);
      }
      this.board.add(temp);
    }

    ArrayList<Edge> temp = new ArrayList<Edge>();
    for (int i = 0; i < width - 1; i++) {
      for (int x = 0; x < height; x++) {
        temp.add(new Edge(this.board.get(i).get(x), this.board.get(i + 1).get(x), true));
      }
    }

    for (int i = 0; i < width; i++) {
      for (int x = 0; x < height - 1; x++) {
        temp.add(new Edge(this.board.get(i).get(x), this.board.get(i).get(x + 1), false));
      }
    }

    for (ArrayList<GamePiece> col : this.board) {
      this.nodes.addAll(col);
    }

    this.mst.addAll(this.createMST(this.nodes, temp));

    for (Edge e : this.mst) {
      e.fromNode.addConnections(e.toNode);
      e.toNode.addConnections(e.fromNode);
    }

    int maxDiameter = 0;
    GamePiece source = this.board.get(this.powerCol).get(this.powerRow);

    for (GamePiece target : this.nodes) {
      int diameter = Math.max(maxDiameter,
          this.shortestPath(this.board.get(powerCol).get(powerRow), target));
      if (maxDiameter <= diameter) {
        maxDiameter = diameter;
        source = target;
      }
    }
    
    for (GamePiece target : this.nodes) {
      maxDiameter = Math.max(maxDiameter, this.shortestPath(source, target));
    }
    
    this.radius = (maxDiameter / 2) + 1;
    this.board.get(this.powerCol).get(this.powerRow).addPowerStation();
    
    Random rand = new Random();
    for (GamePiece g : this.nodes) {
      int numRotate = rand.nextInt(4);
      for (int i = 0; i < numRotate; i++) {
        g.rotate();
      }
    }
    this.updateBoard();
  }

  // returns the shortest distance between the given nodes through 
  // in the minimum spanning tree in this using a breadth-first search
  int shortestPath(GamePiece source, GamePiece target) {
    HashMap<GamePiece, Edge> cameFromEdge = new HashMap<GamePiece, Edge>();
    Stack<GamePiece> worklist = new Stack<GamePiece>();
    ArrayList<GamePiece> visited = new ArrayList<GamePiece>();   
    worklist.push(source);
    while (worklist.size() > 0) {
      GamePiece g = worklist.pop();
      if (!visited.contains(g)) {
        visited.add(g);
        if (g.sameGamePiece(target)) {
            return this.reconstruct(cameFromEdge, source, target);
        }
        else {
          for (Edge e : this.mst) {
            if (e.fromNode.sameGamePiece(g) && !visited.contains(e.toNode)) {
              worklist.push(e.toNode);
              cameFromEdge.put(e.toNode, e);
            }
            else if (e.toNode.sameGamePiece(g) && !visited.contains(e.fromNode)) {
              worklist.push(e.fromNode);
              cameFromEdge.put(e.fromNode, new Edge(e.toNode, e.fromNode, false));
            }
          }
        }
      }
    }
    return 0;
  }
  
  // returns the number of steps from the target to the source in the given hashmap
  int reconstruct(HashMap<GamePiece, Edge> cameFromEdge, GamePiece source, GamePiece target) {
    GamePiece node = target;
    int steps = 1;
    while (!node.sameGamePiece(source)) {
      steps++;
      node = cameFromEdge.get(node).fromNode;
    }
    return steps;
  }

  // returns a changed version the given edges to form a
  // minimum spanning tree using Kruskal's algorithm
  ArrayList<Edge> createMST(ArrayList<GamePiece> nodes, ArrayList<Edge> edges) {
    HashMap<GamePiece, GamePiece> representatives = new HashMap<GamePiece, GamePiece>();
    ArrayList<Edge> edgesInTree = new ArrayList<Edge>();
    this.sortEdges(edges);

    for (GamePiece g : nodes) {
      representatives.put(g, g);
    }

    while (edgesInTree.size() < nodes.size() - 1) {
      if (this.find(edges.get(0).toNode,
          representatives).sameGamePiece(this.find(edges.get(0).fromNode, representatives))) {
        edges.remove(0);
      }
      else {
        edgesInTree.add(edges.get(0));
        representatives.put(
            this.find(edges.get(0).fromNode, representatives),
            this.find(edges.get(0).toNode, representatives));
      }
    }
    return edgesInTree;
  }

  // finds the overall parent of the given node in the given hashmap
  GamePiece find(GamePiece g, HashMap<GamePiece, GamePiece> representatives) {
    if (g.sameGamePiece(representatives.get(g))) {
      return g;
    }
    else {
      return this.find(representatives.get(g), representatives);
    }
  }


  // sorts the given edges in an ascending order of weights
  void sortEdges(ArrayList<Edge> edges) {
    ArrayList<Edge> sorted = new ArrayList<Edge>();
    int size = edges.size();
    while (size != sorted.size()) {
      int minIndex = 0;
      for (int i = 1; i < edges.size(); i++) {
        if (edges.get(minIndex).weight > edges.get(i).weight) {
          minIndex = i;
        }
      }
      sorted.add(edges.get(minIndex));
      edges.remove(minIndex);
    }
    edges.addAll(sorted);
  }

  // creates a fractal-like grid
  LightEmAll(int width, int height, int radius) {

    if ((width <= 1 && height <= 1)
        || width < 1 || height < 1) {
      throw new IllegalArgumentException("Board must be more than 1 cell");
    }

    this.height = height;
    this.width = width;
    this.board = new ArrayList<ArrayList<GamePiece>>();
    this.nodes = new ArrayList<GamePiece>();
    this.powerRow = height / 2;
    this.powerCol = width / 2;
    this.radius = radius;
    this.time = 0;
    this.moves = 0;

    for (int i = 0; i < width; i++) {
      ArrayList<GamePiece> temp = new ArrayList<GamePiece>();
      for (int x = 0; x < height; x++) {
        GamePiece add = new GamePiece(x, i);
        temp.add(add);
      }
      this.board.add(temp);
    }

    this.board.get(this.powerCol).get(this.powerRow).addPowerStation();
    this.generateFBoard(this.board);

    for (ArrayList<GamePiece> col : this.board) {
      this.nodes.addAll(col);
    }

    this.updateBoard();
  }

  // EFFECT: changes the given board to a fractal-like grid
  void generateFBoard(ArrayList<ArrayList<GamePiece>> board) {
    for (int i = 0; i < board.get(0).size() - 1; i++) {
      board.get(0).get(i).bottom = true;
      board.get(board.size() - 1).get(i).bottom = true;
    }
    for (int i = 1; i < board.get(0).size(); i++) {
      board.get(0).get(i).top = true;
      board.get(board.size() - 1).get(i).top = true;
    }
    for (int i = 0; i < board.size() - 1; i++) {
      board.get(i).get(board.get(i).size() - 1).right = true;
    }
    for (int i = 1; i < board.size(); i++) {
      board.get(i).get(board.get(i).size() - 1).left = true;
    }

    if (board.size() > 2 && board.get(0).size() > 2) {
      ArrayList<ArrayList<ArrayList<GamePiece>>> quads = this.generateQuadrants(board);
      for (ArrayList<ArrayList<GamePiece>> quad : quads) {
        this.generateFBoard(quad);
      }
    }
  }

  // creates an arraylist of all the quadrants of the given 2-d array
  <T> ArrayList<ArrayList<ArrayList<T>>> generateQuadrants(ArrayList<ArrayList<T>> grid) {
    ArrayList<ArrayList<ArrayList<T>>> quads = new ArrayList<ArrayList<ArrayList<T>>>();
    ArrayList<ArrayList<T>> quad1 = new ArrayList<ArrayList<T>>();
    ArrayList<ArrayList<T>> quad2 = new ArrayList<ArrayList<T>>();
    ArrayList<ArrayList<T>> quad3 = new ArrayList<ArrayList<T>>();
    ArrayList<ArrayList<T>> quad4 = new ArrayList<ArrayList<T>>();
    int split1 = (grid.size() / 2) + (grid.size() % 2);
    int split2 = (grid.get(0).size() / 2) + (grid.get(0).size() % 2);

    for (int i = 0; i < split1; i++) {
      ArrayList<T> temp = new ArrayList<T>();
      for (int x = 0; x < split2; x++) {
        temp.add(grid.get(i).get(x));
      }
      quad1.add(temp);
    }
    quads.add(quad1);
    for (int i = split1; i < grid.size(); i++) {
      ArrayList<T> temp = new ArrayList<T>();
      for (int x = split2; x < grid.get(0).size(); x++) {
        temp.add(grid.get(i).get(x));
      }
      quad2.add(temp);
    }
    quads.add(quad2);
    for (int i = split1; i < grid.size(); i++) {
      ArrayList<T> temp = new ArrayList<T>();
      for (int x = 0; x < split2; x++) {
        temp.add(grid.get(i).get(x));
      }
      quad3.add(temp);
    }
    quads.add(quad3);
    for (int i = 0; i < split1; i++) {
      ArrayList<T> temp = new ArrayList<T>();
      for (int x = split2; x < grid.get(0).size(); x++) {
        temp.add(grid.get(i).get(x));
      }
      quad4.add(temp);
    }
    quads.add(quad4);

    return quads;
  }

  // creates the scene of this world
  public WorldScene makeScene() {
    WorldImage boardImg = this.draw();
    Double width = boardImg.getWidth();
    Double height = boardImg.getHeight();
    WorldScene bg = new WorldScene(width.intValue(), height.intValue());
    if (this.allConnected()) {
    }
    else {
      bg.placeImageXY(this.draw(), width.intValue() / 2, height.intValue() / 2);
    }
    return bg;
  }

  // draws all the columns and rows of the board in this
  WorldImage draw() {
    WorldImage board = new EmptyImage();
    for (ArrayList<GamePiece> col : this.board) {
      WorldImage colImg = new EmptyImage();
      for (GamePiece g : col) {
        colImg = new AboveImage(colImg, g.draw(this.radius));
      }
      board = new BesideImage(board, colImg);
    }
    Double boardWidth = board.getWidth();
    WorldImage timeCount = new TextImage("Time: " +
        Integer.toString(this.time) + "   ", this.width * 2 + 3, Color.DARK_GRAY);
    WorldImage moveCount = new TextImage("Moves: " +
        Integer.toString(this.moves), this.width * 2 + 3, Color.DARK_GRAY);
    board = new AboveImage(board, new OverlayImage(
        new BesideImage(timeCount, moveCount),
        new RectangleImage(boardWidth.intValue(), 40, OutlineMode.SOLID, Color.gray)));
    return board;
  }

  // rotates the game piece of the board that correlates with the given posn if it exists
  public void onMousePressed(Posn pos, String buttonName) {
    int row = pos.x / 50;
    int col = pos.y / 50;
    if (this.board != null && col < this.board.size() && row < this.board.get(0).size()) {
      if (buttonName.equals("RightButton")) {
        for (int i = 0; i < 3; i++) {
          this.board.get(row).get(col).rotate();
        }
      }
      else {
        this.board.get(row).get(col).rotate();
      }
      ArrayList<GamePiece> temp = new ArrayList<GamePiece>();
      temp.add(this.board.get(row).get(col));
      this.updateGamePiece(row, col, this.radius + 1, temp);
      this.updateBoard();
      this.moves++;
    }
  }

  // EXTRA CREDIT: restarts the game when r is pressed
  // moves the power generator depending on the given key if it is up, down, right or left
  public void onKeyEvent(String key) {
    if (key.equals("up") 
        && this.isValidIndex(this.powerCol, this.powerRow - 1)
        && this.board.get(this.powerCol).get(this.powerRow).top
        && this.board.get(this.powerCol).get(this.powerRow - 1).bottom) {
      this.board.get(this.powerCol).get(this.powerRow).removePowerStation();
      this.powerRow--;
      this.board.get(this.powerCol).get(this.powerRow).addPowerStation();
      this.moves++;
    }
    else if (key.equals("down") 
        && this.isValidIndex(this.powerCol, this.powerRow + 1)
        && this.board.get(this.powerCol).get(this.powerRow).bottom
        && this.board.get(this.powerCol).get(this.powerRow + 1).top) {
      this.board.get(this.powerCol).get(this.powerRow).removePowerStation();
      this.powerRow++;
      this.board.get(this.powerCol).get(this.powerRow).addPowerStation();
      this.moves++;
    }
    else if (key.equals("left") 
        && this.isValidIndex(this.powerCol - 1, this.powerRow)
        && this.board.get(this.powerCol).get(this.powerRow).left
        && this.board.get(this.powerCol - 1).get(this.powerRow).right) {
      this.board.get(this.powerCol).get(this.powerRow).removePowerStation();
      this.powerCol--;
      this.board.get(this.powerCol).get(this.powerRow).addPowerStation();
      this.moves++;
    }
    else if (key.equals("right") 
        && this.isValidIndex(this.powerCol + 1, this.powerRow)
        && this.board.get(this.powerCol).get(this.powerRow).right
        && this.board.get(this.powerCol + 1).get(this.powerRow).left) {
      this.board.get(this.powerCol).get(this.powerRow).removePowerStation();
      this.powerCol++;
      this.board.get(this.powerCol).get(this.powerRow).addPowerStation();
      this.moves++;
    }
    else if (key.equals("r")) {
      LightEmAll temp = new LightEmAll(this.width, this.height);
      this.board = temp.board;
      this.mst = temp.mst;
      this.nodes = temp.nodes;
      this.powerRow = temp.powerRow;
      this.powerCol = temp.powerCol;
    }
    this.updateBoard();
  }

  // are the given numbers valid indexes of the board in this
  boolean isValidIndex(int x, int y) {
    return this.width > x
        && x >= 0
        && this.height > y
        && y >= 0;
  }

  // updates the distance of all the game pieces in the board depending
  // on the distance from the power generator
  void updateBoard() {
    ArrayList<GamePiece> visited = new ArrayList<GamePiece>();
    this.updateGamePiece(this.powerCol, this.powerRow, 0, visited);
    for (ArrayList<GamePiece> col : this.board) {
      for (GamePiece g : col) {
        if (!visited.contains(g)) {
          g.distance = this.radius + 1;
        }
      }
    }
  }

  // updates the distance of the game piece correlating to the given col and row in this board
  // and the game pieces connected to it
  // ACCUMULATOR : accumulates the visited game pieces and the distance to the power generator
  void updateGamePiece(int col, int row, int count, ArrayList<GamePiece> visited) {
    this.board.get(col).get(row).distance = count;
    visited.add(this.board.get(col).get(row));
    if (this.isValidIndex(col - 1, row)
        && this.board.get(col).get(row).left
        && this.board.get(col - 1).get(row).right
        && !visited.contains(this.board.get(col - 1).get(row))) {
      this.updateGamePiece(col - 1, row, count + 1, visited);
    }
    if (this.isValidIndex(col + 1, row)
        && this.board.get(col).get(row).right
        && this.board.get(col + 1).get(row).left
        && !visited.contains(this.board.get(col + 1).get(row))) {
      this.updateGamePiece(col + 1, row, count + 1, visited);
    }
    if (this.isValidIndex(col, row + 1)
        && this.board.get(col).get(row).bottom
        && this.board.get(col).get(row + 1).top
        && !visited.contains(this.board.get(col).get(row + 1))) {
      this.updateGamePiece(col, row + 1, count + 1, visited);
    }
    if (this.isValidIndex(col, row - 1)
        && this.board.get(col).get(row).top
        && this.board.get(col).get(row - 1).bottom
        && !visited.contains(this.board.get(col).get(row - 1))) {
      this.updateGamePiece(col, row - 1, count + 1, visited);
    }
  }

  // adds one to the time in this every tick
  public void onTick() {
    this.time++;
  }
  
  // are all the nodes in this connected
  boolean allConnected() {
    boolean connected = true;
    for (GamePiece g : this.nodes) {
      connected = g.isConnected(this.radius) && connected;
    }
    return connected;
  }
  
}


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



  void initData() {
    this.test = new LightEmAll(7,7,10);
    this.test3 = new LightEmAll(2,2,2);
    this.test2 = new LightEmAll(3,2,1);
    this.test4 = new LightEmAll(1,2,-1);
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
    this.five = new GamePiece(1,1);
    this.five.top = true;
    this.five.left = true;
    this.four = new GamePiece(1,1);
    this.four.top = true;
    this.four.right = true;
    this.six = new GamePiece(1,1);
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


    this.star1 = new OverlayImage(new StarImage(20, 7, OutlineMode.OUTLINE,
        Color.lightGray), new StarImage(20, 7, OutlineMode.SOLID,
            Color.getHSBColor(0.7f, 0.8f, 1f)));

    this.square =  new FrameImage(new RectangleImage(50, 50,
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
  
  void testReconstruct(Tester t) {
    
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
    t.checkExpect(this.test, new LightEmAll(7,7,10));
    GamePiece g = this.test.nodes.get(0);
    g.rotate();
    this.test.onMousePressed(new Posn(0, 0));
    t.checkExpect(this.test.nodes.get(0), g);
    this.initData();
    GamePiece x = this.test.nodes.get(1);
    this.test.onMousePressed(new Posn(0, 0));
    t.checkExpect(this.test.nodes.get(1), x);
  }

  // generates a 7x7 LightEmAll game
  void testBigBang(Tester t) {
    this.initData();
    this.test5.bigBang(this.test5.makeScene().width, this.test5.makeScene().height, 1);
  }

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
    WorldScene test = new WorldScene(50,140);
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
}