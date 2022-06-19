package picture;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class that encapsulates and provides a simplified interface for manipulating an image. The
 * internal representation of the image is based on the RGB direct colour model.
 */
public class Picture {

  /**
   * The internal image representation of this picture.
   */
  private final BufferedImage image;

  /**
   * Construct a new (blank) Picture object with the specified width and height.
   */
  public Picture(int width, int height) {
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
  }

  /**
   * Construct a new Picture from the image data in the specified file.
   */
  public Picture(String filepath) {
    try {
      image = ImageIO.read(new File(filepath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Test if the specified point lies within the boundaries of this picture.
   *
   * @param x the x co-ordinate of the point
   * @param y the y co-ordinate of the point
   * @return <tt>true</tt> if the point lies within the boundaries of the picture, <tt>false</tt>
   * otherwise.
   */
  public boolean contains(int x, int y) {
    return x >= 0 && y >= 0 && x < getWidth() && y < getHeight();
  }

  /**
   * Returns true if this Picture is graphically identical to the other one.
   *
   * @param other The other picture to compare to.
   * @return true iff this Picture is graphically identical to other.
   */
  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (!(other instanceof Picture)) {
      return false;
    }

    Picture otherPic = (Picture) other;

    if (image == null || otherPic.image == null) {
      return image == otherPic.image;
    }
    if (image.getWidth() != otherPic.image.getWidth()
        || image.getHeight() != otherPic.image.getHeight()) {
      return false;
    }

    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        if (image.getRGB(i, j) != otherPic.image.getRGB(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Return the height of the <tt>Picture</tt>.
   *
   * @return the height of this <tt>Picture</tt>.
   */
  public int getHeight() {
    return image.getHeight();
  }

  /**
   * Return the colour components (red, green, then blue) of the pixel-value located at (x,y).
   *
   * @param x x-coordinate of the pixel value to return
   * @param y y-coordinate of the pixel value to return
   * @return the RGB components of the pixel-value located at (x,y).
   * @throws ArrayIndexOutOfBoundsException if the specified pixel-location is not contained within
   *                                        the boundaries of this picture.
   */
  public Color getPixel(int x, int y) {
    int rgb = image.getRGB(x, y);
    return new Color((rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff);
  }

  /**
   * Return the width of the <tt>Picture</tt>.
   *
   * @return the width of this <tt>Picture</tt>.
   */
  public int getWidth() {
    return image.getWidth();
  }

  @Override
  public int hashCode() {
    if (image == null) {
      return -1;
    }
    int hashCode = 0;
    for (int i = 0; i < image.getWidth(); i++) {
      for (int j = 0; j < image.getHeight(); j++) {
        hashCode = 31 * hashCode + image.getRGB(i, j);
      }
    }
    return hashCode;
  }

  public void saveAs(String filepath) {
    try {
      ImageIO.write(image, "png", new File(filepath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Update the pixel-value at the specified location.
   *
   * @param x   the x-coordinate of the pixel to be updated
   * @param y   the y-coordinate of the pixel to be updated
   * @param rgb the RGB components of the updated pixel-value
   * @throws ArrayIndexOutOfBoundsException if the specified pixel-location is not contained within
   *                                        the boundaries of this picture.
   */
  public void setPixel(int x, int y, Color rgb) {

    image.setRGB(
        x,
        y,
        0xff000000
            | (((0xff & rgb.getRed()) << 16)
            | ((0xff & rgb.getGreen()) << 8)
            | (0xff & rgb.getBlue())));
  }

  /**
   * Returns a String representation of the RGB components of the picture.
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        Color rgb = getPixel(x, y);
        sb.append("(");
        sb.append(rgb.getRed());
        sb.append(",");
        sb.append(rgb.getGreen());
        sb.append(",");
        sb.append(rgb.getBlue());
        sb.append(")");
      }
      sb.append("\n");
    }
    sb.append("\n");
    return sb.toString();
  }

  /**
   * Return the tile set of pixels as an Array for a particular starting pixel (top left pixel).
   *
   * @param x        the x-coordinate of the top left pixel
   * @param y        the y-coordinate of the top left pixel
   * @param tileSize the tile size
   */
  public Color[][] tileAt(int x, int y, int tileSize) {
    Color[][] tile = new Color[tileSize][tileSize];
    for (int i = 0; i < tileSize; i++) {
      for (int j = 0; j < tileSize; j++) {
        tile[i][j] = getPixel(x + i, y + j);
      }
    }
    return tile;
  }

  // Following functions implement picture transformation of the spec

  public void invert() {
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        Color orignalColor = getPixel(x, y);
        Color newColor =
            new Color((255 - orignalColor.getRed()),
                (255 - orignalColor.getGreen()),
                (255 - orignalColor.getBlue()));
        setPixel(x, y, newColor);
      }
    }
  }

  public void grayscale() {
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        Color orignalColor = getPixel(x, y);
        int avg = (orignalColor.getRed()
            + orignalColor.getGreen()
            + orignalColor.getBlue()) / 3;
        Color newColor = new Color(avg, avg, avg);
        setPixel(x, y, newColor);
      }
    }
  }

  public Picture rotate90() {
    Picture pic = new Picture(getHeight(), getWidth());
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        Color orignalColor = getPixel(x, y);
        pic.setPixel(getHeight() - y - 1, x, orignalColor);
      }
    }
    return pic;
  }

  public Picture flip(String cmd) {
    Picture pic = new Picture(getWidth(), getHeight());
    if (cmd == "H" || cmd == "h") {
      for (int x = 0; x < getWidth(); x++) {
        for (int y = 0; y < getHeight(); y++) {
          Color orignalColor = getPixel(x, y);
          pic.setPixel(getWidth() - x - 1, y, orignalColor);
        }
      }
    } else {
      for (int x = 0; x < getWidth(); x++) {
        for (int y = 0; y < getHeight(); y++) {
          Color orignalColor = getPixel(x, y);
          pic.setPixel(x, getHeight() - y - 1, orignalColor);
        }
      }
    }
    return pic;
  }

  public Boolean edge(int x, int y) {
    return x == 0 || x == (getWidth() - 1) || y == 0 || y == (getHeight() - 1);
  }

  public Picture blur() {
    Picture pic = new Picture(getWidth(), getHeight());
    for (int x = 0; x < getWidth(); x++) {
      for (int y = 0; y < getHeight(); y++) {
        if (edge(x, y)) {
          Color original = getPixel(x, y);
          pic.setPixel(x, y, original);
        } else {
          int red = 0;
          int green = 0;
          int blue = 0;
          for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
              Color color = getPixel(i, j);
              red = red + color.getRed();
              green = green + color.getGreen();
              blue = blue + color.getBlue();
            }
          }
          Color newColor = new Color(red / 9, green / 9, blue / 9);
          pic.setPixel(x, y, newColor);
        }
      }
    }
    return pic;
  }

  public static Picture blend(Picture[] pics, int length) {
    List<Integer> heights = new ArrayList<>();
    List<Integer> widths = new ArrayList<>();
    for (int i = 0; i < length; i++) {
      widths.add(pics[i].getWidth());
      heights.add(pics[i].getHeight());
    }
    int min_height = Collections.min(heights);
    int min_width = Collections.min(widths);
    Picture pic = new Picture(min_width, min_height);
    for (int x = 0; x < min_width; x++) {
      for (int y = 0; y < min_height; y++) {
        int red = 0;
        int green = 0;
        int blue = 0;
        for (Picture picture : pics) {
          Color color = (picture.getPixel(x, y));
          red = red + color.getRed();
          green = green + color.getGreen();
          blue = blue + color.getBlue();
        }
        Color newColor = new Color(red / length, green / length, blue / length);
        pic.setPixel(x, y, newColor);
      }
    }
    return pic;
  }

  /**
   * Return the mosaic picture combination of pictures passed as parameters (Part of suggested
   * extension)
   *
   * @param pics     input pictures
   * @param length   length of picture set
   * @param tileSize the tile size
   */
  public static Picture mosaic(Picture[] pics, int length, int tileSize) {
    List<Integer> heights = new ArrayList<>();
    List<Integer> widths = new ArrayList<>();
    for (int i = 0; i < length; i++) {
      widths.add(pics[i].getWidth());
      heights.add(pics[i].getHeight());
    }
    int min_height = Collections.min(heights);
    int min_width = Collections.min(widths);
    int height = min_height - (min_height % tileSize);    //trimming the picture size
    int width = min_width - (min_width % tileSize);
    Picture pic = new Picture(width, height);

    int base = 0;
    for (int x = 0; x < width; x = x + tileSize) {
      if (base >= length) {
        base = 0;
      }
      int counter = base;
      for (int y = 0; y < height; y = y + tileSize) {
        if (counter >= length) {
          counter = 0;
        }
        Color[][] tile = pics[counter].tileAt(x, y, tileSize);
        for (int i = x; i < x + tileSize; i++) {
          for (int j = y; j < y + tileSize; j++) {
            pic.setPixel(i, j, tile[i - x][j - y]);
          }
        }
        counter++;
      }
      base++;
    }
    return pic;
  }
}
