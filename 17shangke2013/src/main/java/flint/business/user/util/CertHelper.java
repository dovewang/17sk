package flint.business.user.util;

import kiss.security.Passport;
import flint.business.constant.UserConst;

public class CertHelper {
	public static int getActive(Passport user) {
		return user.getAttribute("active");
	}

	private static boolean v(int active, byte type) {
		return (active & type) != 0;
	}

	public static boolean vEmail(int active) {
		return v(active, UserConst.ACTIVE_EMAIL);
	}

	public static boolean vRealName(int active) {
		return v(active, UserConst.ACTIVE_REALNAME);
	}

	public static boolean vTel(int active) {
		return v(active, UserConst.ACTIVE_TEL);
	}

	public static boolean vDegree(int active) {
		return v(active, UserConst.ACTIVE_DEGREE);
	}
}
