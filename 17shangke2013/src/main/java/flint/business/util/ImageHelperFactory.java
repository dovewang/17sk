package flint.business.util;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import flint.io.image.Clipper;
import flint.io.image.ImageHelper;
import flint.io.image.Scaler;

public class ImageHelperFactory extends ImageHelper {

	/**
	 * 上传后的文件缩放
	 * 
	 * @param in
	 * @param height
	 * @param width
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static boolean resize(InputStream in, int height, int width,
			String path) throws IOException {
		try {
			write(Scaler.resize(in, height, width), new FileOutputStream(path));
		} catch (IOException e) {
			throw e;
		} finally {
			in.close();
		}
		return true;
	}

	public static boolean face(int x, int y, int h, int w, String path)
			throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream(path + ".o.jpg");
			BufferedImage source = Clipper.clip(in, x, y, h, w);
			BufferedImage large = Scaler.resize(source, 180, 180);
			write(large, new FileOutputStream(path + ".jpg"));
		} catch (IOException e) {
			throw e;
		} finally {
			in.close();
		}
		return true;
	}

}
