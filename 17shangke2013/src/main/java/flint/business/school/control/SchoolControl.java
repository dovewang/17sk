package flint.business.school.control;

import javax.servlet.http.HttpServletRequest;

import kiss.util.Q;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import entity.SchoolAddress;
import flint.business.mail.MailManager;
import flint.business.school.service.SchoolService;
import flint.exception.ApplicationException;
import flint.web.WebControl;

@Controller
public class SchoolControl extends WebControl {
	@Autowired
	private MailManager mailManager;

	@Autowired
	private SchoolService schoolService;

	/**
	 * 学校地址管理
	 * 
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping("/school/address.html")
	public ModelAndView address(HttpServletRequest request, long id)
			throws ApplicationException {
		ModelAndView mav = new ModelAndView();
		mav.addObject(RESULT, schoolService.findAddress(id));
		return mav;
	}

	@RequestMapping(value = "/school/address.add.html", method = RequestMethod.POST)
	public ModelAndView saveAddress(HttpServletRequest request,
			SchoolAddress address) throws ApplicationException {
		address.setDateline(Q.now());
		address.setStatus((byte) 1);
		long id = schoolService.saveAddress(address);
		if (id > 0) {
			return success("保存成功！", "id", String.valueOf(id));
		} else {
			return failure("保存失败！");
		}
	}

	@RequestMapping(value = "/school/address.delete.html", method = RequestMethod.POST)
	public ModelAndView deleteAddress(HttpServletRequest request, long id)
			throws ApplicationException {
		if (schoolService.deleteAddress(id) > 0) {
			return success("删除成功！");
		} else {
			return failure("删除失败！");
		}
	}
}
