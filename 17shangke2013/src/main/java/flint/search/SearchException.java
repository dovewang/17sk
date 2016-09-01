package flint.search;

public class SearchException extends RuntimeException {

	private static final long serialVersionUID = 5295389894694502656L;

	public SearchException(String message) {
		super(message);
	}

	public SearchException(String message, Throwable cause) {
		super(message, cause);
	}

	public SearchException(Throwable cause) {
		super(cause);
	}
}
