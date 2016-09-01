package flint.search.filter;

import java.io.IOException;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.util.Bits;

import flint.search.UserContext;

public class UserOnlineFilter extends Filter {
	private static final long serialVersionUID = 4470551074756202615L;

	public final static String fieldName = "UID";

	private UserContext context;

	public UserOnlineFilter(UserContext context) {
		this.context = context;
	}

	@Override
	public DocIdSet getDocIdSet(AtomicReaderContext context, Bits acceptDocs)
			throws IOException {
//		FixedBitSet res = null;
//		final AtomicReader reader = context.reader();
//		OpenBitSet bits = new OpenBitSet(reader.maxDoc());
//		Term startTerm = new Term(fieldName);
//		TermEnum te = reader.terms(startTerm);
//		if (te != null) {
//			Term currTerm = te.term();
//			int i = 0;
//			while ((currTerm != null)
//					&& (currTerm.field() == startTerm.field())) {
//				TermDocs td = reader.termDocs(currTerm);
//				/* 在这里过滤用户在线的数据 */
//				while (td.next()) {
//					if (context.isOnline(currTerm.text())) {
//						bits.set(td.doc());
//					}
//				}
//
//				if (!te.next()) {
//					break;
//				}
//				currTerm = te.term();
//			}
//			System.out.println(i);
//		}
//
//		return bits;
		
		return null;
	}
}
