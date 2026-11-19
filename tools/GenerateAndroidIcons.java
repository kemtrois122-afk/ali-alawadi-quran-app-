import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/** Convert the site's approved app icon into Android launcher resources. */
public final class GenerateAndroidIcons {
    private static BufferedImage resized(BufferedImage source, int size, boolean round) {
        BufferedImage result = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        if (round) g.setClip(new Ellipse2D.Double(0, 0, size, size));
        g.drawImage(source, 0, 0, size, size, null);
        g.dispose();
        return result;
    }

    private static void save(BufferedImage image, Path folder, String filename) throws Exception {
        Files.createDirectories(folder);
        if (!ImageIO.write(image, "png", folder.resolve(filename).toFile())) {
            throw new IllegalStateException("PNG encoder unavailable");
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: GenerateAndroidIcons <icon.png> <maskable.png> <res-directory>");
        }
        BufferedImage icon = ImageIO.read(new File(args[0]));
        BufferedImage foreground = ImageIO.read(new File(args[1]));
        if (icon == null || foreground == null) throw new IllegalArgumentException("Invalid source PNG");
        Path res = Path.of(args[2]);
        String[] densities = {"mdpi", "hdpi", "xhdpi", "xxhdpi", "xxxhdpi"};
        int[] sizes = {48, 72, 96, 144, 192};
        int[] foregroundSizes = {108, 162, 216, 324, 432};
        for (int i = 0; i < densities.length; i++) {
            Path folder = res.resolve("mipmap-" + densities[i]);
            save(resized(icon, sizes[i], false), folder, "ic_launcher.png");
            save(resized(icon, sizes[i], true), folder, "ic_launcher_round.png");
            save(resized(foreground, foregroundSizes[i], false), folder, "ic_launcher_foreground.png");
        }
        System.out.println("Android launcher icons generated from website assets.");
    }
}
