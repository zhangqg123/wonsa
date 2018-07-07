package com.jeecg.xzkx.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

public class ImageUtil {
	/**
	 * 按指定高度 等比例缩放图片
	 * 
	 * @param imageFile
	 * @param newPath
	 * @param newWidth
	 *            新图的宽度
	 * @throws IOException
	 */
	public static void zoomImageScale(File imageFile, String newPath,
			int newWidth) throws IOException {
		if (!imageFile.canRead())
			return;
		BufferedImage bufferedImage = ImageIO.read(imageFile);
		if (null == bufferedImage)
			return;

		int originalWidth = bufferedImage.getWidth();
		int originalHeight = bufferedImage.getHeight();
		double scale = (double) originalWidth / (double) newWidth; // 缩放的比例

		int newHeight = (int) (originalHeight / scale);

		zoomImageUtils(imageFile, newPath, bufferedImage, newWidth, newHeight);
	}

	private static void zoomImageUtils(File imageFile, String newPath,
			BufferedImage bufferedImage, int width, int height)
			throws IOException {

		String suffix = StringUtils
				.substringAfterLast(imageFile.getName(), ".");

		BufferedImage newImage = new BufferedImage(width, height,
				bufferedImage.getType());
		Graphics g = newImage.getGraphics();
		g.drawImage(bufferedImage, 0, 0, width, height, null);
		g.dispose();
		ImageIO.write(newImage, suffix, new File(newPath));
	}

}
