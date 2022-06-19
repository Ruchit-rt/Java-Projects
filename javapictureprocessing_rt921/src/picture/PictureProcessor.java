package picture;

public class PictureProcessor {

  public static void main(String[] args) {
    switch (args[0]) {
      case "invert" -> {
        Picture pic = new Picture(args[1]);
        String output = args[2];
        pic.invert();
        pic.saveAs(output);
      }
      case "grayscale" -> {
        Picture pic = new Picture(args[1]);
        String output = args[2];
        pic.grayscale();
        pic.saveAs(output);
      }
      case "rotate" -> {
        int angle = Integer.parseInt(args[1]);
        Picture pic = new Picture(args[2]);
        String output = args[3];
        while (angle > 0) {
          pic = pic.rotate90();
          angle = angle - 90;
        }
        pic.saveAs(output);
      }
      case "flip" -> {
        String cmd = args[1];
        Picture pic = new Picture(args[2]);
        String output = args[3];
        pic = pic.flip(cmd);
        pic.saveAs(output);
      }
      case "blur" -> {
        Picture pic = new Picture(args[1]);
        String output = args[2];
        pic = pic.blur();
        pic.saveAs(output);
      }
      case "blend" -> {
        int n = args.length;
        String output = args[n - 1];
        final var pics = new Picture[n - 2];
        for (int i = 1; i < n - 1; i++) {
          pics[i - 1] = new Picture(args[i]);
        }
        Picture blended = Picture.blend(pics, n - 2);
        blended.saveAs(output);
      }
      case "mosaic" -> {
        int n = args.length;
        String output = args[n - 1];
        int tileSize = Integer.parseInt(args[1]);
        final var pics = new Picture[n - 3];
        for (int i = 2; i < n - 1; i++) {
          pics[i - 2] = new Picture(args[i]);
        }
        Picture mosaic = Picture.mosaic(pics, n - 3, tileSize);
        mosaic.saveAs(output);
      }
      default -> throw new IllegalStateException("Unexpected value: " + args[0]);
    }
  }

}
