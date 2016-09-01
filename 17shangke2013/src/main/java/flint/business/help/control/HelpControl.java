package flint.business.help.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import flint.web.WebControl;

/**
 * 帮助界面
 * 
 * @author Dove Wang
 * 
 */
@Controller
public class HelpControl extends WebControl {

	@RequestMapping("/help/index.html")
	public String index() {
		return "global:/help/index";
	}
}
