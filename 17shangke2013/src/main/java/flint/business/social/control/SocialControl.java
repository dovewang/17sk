package flint.business.social.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kiss.security.Passport;
import kiss.security.authc.Authenticator;
import kiss.security.authc.OauthToken;
import kiss.security.exception.AuthenticationException;
import kiss.social.authz.AuthorizeFacoty;
import kiss.social.authz.SocialUser;
import kiss.social.exception.ShareException;
import kiss.social.share.ShareFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import flint.business.social.service.SocialService;
import flint.exception.ApplicationException;
import flint.web.WebControl;

@Controller
public class SocialControl extends WebControl {

	@Autowired
	private SocialService socialService;

	@Autowired
	private Authenticator authenticator;

	/**
	 * 视频分享
	 * 
	 * @param url
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/social/share/video.html", method = RequestMethod.POST)
	public ModelAndView shareVideo(String url) throws ApplicationException {
		try {
			return success("解析成功", "video", ShareFactory.getVideo(url));
		} catch (ShareException e) {
			return failure("很抱歉，目前暂不支持该地址解析");
		}
	}

	/**
	 * oauth授权登录
	 * 
	 * @param webName
	 * @param request
	 * @return
	 * @throws ApplicationException
	 */
	@RequestMapping(value = "/social/{webname}/authz.html", method = RequestMethod.GET)
	public String login(@PathVariable("webname") String webName,
			HttpServletRequest request) throws ApplicationException {
		String url = request.getParameter("url");
		return "redirect:"
				+ AuthorizeFacoty.getAuthorizationUrl(webName, null, url);
	}

	/**
	 * oauth授权回调
	 * 
	 * @param webName
	 * @param code
	 * @param request
	 * @param response
	 * @return
	 * @throws ApplicationException
	 * @throws AuthenticationException
	 */
	@RequestMapping(value = "/social/{webname}/callback.html")
	public String callback(@PathVariable("webname") String webName,
			String code, HttpServletRequest request,
			HttpServletResponse response) throws ApplicationException,
			AuthenticationException {
		String url = request.getParameter("url");
		/* 有code返回才代表有验证 */
		if (code != null) {
			SocialUser su = AuthorizeFacoty.getSocialUser(webName, code);
			final long userId = socialService.getUser(su,
					this.getSchoolId(request));
			Passport user = authenticator.authenticate(
					this.getPassport(request), new OauthToken() {

						@Override
						public String getPrincipal() {
							return String.valueOf(userId);
						}

						@Override
						public String getCredentials() {
							return null;
						}

					});
		}
		/* 无论授权是否成功都返回用户上次访问的页面 */
		return "redirect:" + url;
	}
}
