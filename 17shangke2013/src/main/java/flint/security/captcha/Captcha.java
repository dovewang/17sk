package flint.security.captcha;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

import javax.imageio.ImageIO;

import flint.security.captcha.text.producer.DefaultTextProducer;

public class Captcha {

	private int height;

	private int width;

	private String word;

	public Captcha(int height, int width) {
		this.height = height;
		this.width = width;
	}

	public BufferedImage get() throws IOException {
		Random random = new SecureRandom();
		BufferedImage background = ImageIO.read(Captcha.class.getResource("/flint/security/captcha/background/" + random.nextInt(61) + ".jpg"));
		Graphics2D g = background.createGraphics();
		DefaultTextProducer t = new DefaultTextProducer();
		this.word = t.getText();
		Font font = new Font("Arial", Font.BOLD, 22);
		g.setFont(font);
		g.drawString(word, 2, 20);
		g.dispose();
		return background;

	}

	public static void main(String[] a) throws IOException {
		for (int i = 0; i < 20; i++)
			ImageIO.write(new Captcha(28, 80).get(), "jpg", new File("c:/" + i + ".jpg"));
	}

	public String getAnswer() {
		return this.word;
	}

}
