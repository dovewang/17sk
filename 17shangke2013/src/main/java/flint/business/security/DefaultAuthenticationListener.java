package flint.business.security;

import kiss.security.Passport;
import kiss.security.authc.AuthenticationListener;
import kiss.security.authc.AuthenticationToken;
import kiss.security.exception.AuthenticationException;

import org.springframework.jdbc.core.JdbcTemplate;

public class DefaultAuthenticationListener implements AuthenticationListener {

	private JdbcTemplate JdbcTemplate;

	@Override
	public <P, C> void onSuccess(Passport passport,
			AuthenticationToken<P, C> token) {
		/* 绑定用户基本信息 */

//		JdbcTemplate.query("SELECT USER_ID,REAL_NAME,NAME,EMAIL,GRADE,USER_TYPE,ROLE,DOMAIN,STATUS,LOGINS,GRADE,FACE,ACTIVE,TEACHER_EXPERIENCE,STUDENT_EXPERIENCE,FOCUS,EXPERT FROM TB_USER",new ResultSetExtractor<Object>(){
//			
//		});  
	}

	@Override
	public <P, C> void onFailure(Passport passport,
			AuthenticationToken<P, C> token, AuthenticationException ae) {

	}

	@Override
	public void onLogout(Passport passport) {
		// TODO Auto-generated method stub

	}

}
