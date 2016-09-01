package flint.security.captcha;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CaptchaServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7317105607173035867L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Captcha captcha = new Captcha(28, 80);
		ImageIO.write(captcha.get(), "jpg", resp.getOutputStream());
		req.getSession().setAttribute("captcha", captcha.getAnswer());
	}
}
