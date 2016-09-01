package flint.security.captcha.text.renderer;

import java.awt.image.BufferedImage;

public interface WordRenderer {

	public void render(String word, BufferedImage image);

}
