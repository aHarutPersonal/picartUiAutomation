package com.picsart.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import javax.imageio.ImageIO;

import lombok.SneakyThrows;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class ImageUtils {

    @SneakyThrows
    public static BufferedImage decodeBase64ToImage(String base64) {
        byte[] imageBytes = Base64.getDecoder().decode(base64);
        return ImageIO.read(new ByteArrayInputStream(imageBytes));
    }

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        resizedImage
            .createGraphics()
            .drawImage(originalImage.getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH), 0, 0,
                       null);
        return resizedImage;
    }

    public static ImageDiff getImageDifference(BufferedImage image1, BufferedImage image2) {
        ImageDiffer imageDiffer = new ImageDiffer();
        return imageDiffer.makeDiff(image1, image2);
    }
}
