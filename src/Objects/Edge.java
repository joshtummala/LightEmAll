package Objects;

import java.util.Random;

class Edge {
  GamePiece fromNode;
  GamePiece toNode;
  int weight;
  Random rand;
  
  // EXTRA CREDIT: bias towards horizontal wires by lowering the maximum weight of them
  // generates a random weight for this
  Edge(GamePiece fromNode, GamePiece toNode, boolean isHorizontal) {
    this.rand = new Random();
    this.fromNode = fromNode;
    this.toNode = toNode;
    if (isHorizontal) {
      this.weight = this.rand.nextInt(80);
    }
    else {
      this.weight = this.rand.nextInt(100);
    }
  }
  
  // constructor for testing
  Edge(GamePiece fromNode, GamePiece toNode, int weight) {
    this.fromNode = fromNode;
    this.toNode = toNode;
    this.weight = weight;
  }
  
}