package Objects;

import java.awt.Color;
import javalib.worldimages.*;

class GamePiece {
  // in logical coordinates, with the origin
  // at the top-left corner of the screen
  int row;
  int col;
  // whether this Objects.GamePiece is connected to the
  // adjacent left, right, top, or bottom pieces
  boolean left;
  boolean right;
  boolean top;
  boolean bottom;
  // whether the power station is on this piece
  boolean powerStation;
  // distance to the powerStation
  int distance;

  GamePiece(int row, int col) {
    this.row = row;
    this.col = col;
    this.left = false;
    this.right = false;
    this.top = false;
    this.bottom = false;
    this.powerStation = false;
    this.distance = 0;
  }
  
  // is this connected to the power station
  boolean isConnected(int radius) {
    return this.distance <= radius;
  }
  
  // adds the connections in this depending on the given Objects.GamePiece
  void addConnections(GamePiece that) {
    if (this.row == that.row && this.col < that.col) {
      this.right = true;
    }
    else if (this.row == that.row && this.col > that.col) {
      this.left = true;
    }
    else if (this.row > that.row && this.col == that.col) {
      this.top = true;
    }
    else if (this.row < that.row && this.col == that.col) {
      this.bottom = true;
    }
  }
  
  // is the given gamepiece in the same location as this
  boolean sameGamePiece(GamePiece that) {
    return this.row == that.row
        && this.col == that.col;
  }

  // changes this to a power station
  void addPowerStation() {
    this.powerStation = true;
  }

  // removes the power station from this
  void removePowerStation() {
    this.powerStation = false;
  }

  // draws this depending on the connections of this and the distance from the given radius
  // EXTRA CREDIT: changes the color of the wire depending on the distance from this to the
  // power generator
  WorldImage draw(int radius) {
    WorldImage square = new FrameImage(new RectangleImage(50, 50,
        OutlineMode.SOLID, Color.DARK_GRAY));
    Color color = Color.gray;
    if (this.distance <= radius) {
      Double temp = (radius - this.distance - 0.01) / (2.0 * radius);
      color = Color.getHSBColor(0.7f, temp.floatValue(), 0.7f);
    }
    WorldImage link = new RectangleImage(24, 5, OutlineMode.SOLID,
        color).movePinhole(12, 0);
    if (this.right) {
      square = new OverlayImage(new RotateImage(link, 180), square);
    }
    if (this.left) {
      square = new OverlayImage(link, square);
    }
    if (this.top) {
      square = new OverlayImage(new RotateImage(link, 90), square);
    }
    if (this.bottom) {
      square = new OverlayImage(new RotateImage(link, 270), square);
    }
    if (this.powerStation) {
      square = new OverlayImage(new OverlayImage(new StarImage(20, 7, OutlineMode.OUTLINE,
          Color.lightGray), new StarImage(20, 7, OutlineMode.SOLID,
              Color.getHSBColor(0.7f, 0.8f, 1f))), square);
    }
    return square;
  }

  // rotates the connections in this in a clock-wise motion
  void rotate() {
    boolean temp = this.top;
    this.top = this.left;
    this.left = this.bottom;
    this.bottom = this.right;
    this.right = temp;
  }
}
