package flint.business.security;

import kiss.security.Expression;
import kiss.security.IdentityImpl;
import kiss.security.Passport;
import kiss.util.Q;

public class Person extends IdentityImpl {

	private byte userType;

	public byte getUserType() {
		return userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}

	public Person(Passport passport) {
		super(passport);
	}

	@Override
	public <T> boolean is(T userType) {
		if (userType instanceof Byte) {
			return this.userType == (Byte) userType;
		}
		return false;
	}

	@Override
	public boolean hasRole(String roles) {
		/* 先分隔绑定当前域 */
		String[] role = roles.split(",");
		for (int i = 0; i < role.length; i++) {
			role[i] = "school" + this.getDomainId() + Expression.OBJECT
					+ role[i];
		}
		return one(Q.Array(role).join(","));
	}
}
