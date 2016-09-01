package flint.business.util;

import kiss.io.check.FileTyper;
import flint.io.check.FileTypeChecker;
import flint.util.DateHelper;
import flint.util.StringHelper;

public class FreeMarkerHelper {

	public int and(int a, int b) {
		return a & b;
	}

	public int or(int a, int b) {
		return a | b;
	}

	/**
	 * 计算课程与当前时间差
	 * 
	 * @param endTime
	 * @return
	 */
	public String getTimeDiffer(int endTime) {
		int day = (24 * 60 * 60);
		int hour = 60 * 60;
		int minute = 60;
		int[] time = new int[3];
		String[] suffix = { "天", "时", "分" };
		int diff = endTime - DateHelper.getNowTime();
		time[0] = diff / day;
		time[1] = (diff - time[0] * day) / hour;
		time[2] = (diff - time[0] * day - time[1] * hour) / minute;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < time.length; i++) {
			int t = time[i];
			if (t > 0) {
				sb.append("<font class=\"time-number\">").append(t)
						.append("</font>").append(suffix[i]);
			}
		}
		return sb.toString();
	}

	public String passTime(int time) {
		return DateHelper.passTime(time);
	}

	public String linkReplace(String str, String seprator, int index,
			String value) {
		return StringHelper.replace(str, seprator, index, value);
	}

	public String fileSize(String sizestr) {
		return StringHelper.fileSize(sizestr);
	}

	public String fileSize(long size) {
		return StringHelper.fileSize(size);
	}

	public boolean isImage(String name) {
		return FileTyper.isImage(name);
	}

	public boolean isDocument(String name) {
		return FileTyper.isDocument(name);
	}

	public String imageResize(String path, int w, int h) {
		String v = "";
		if (w != 0 || h != 0) {
			v += "_s" + w + "," + h;
		}

		return StringHelper.replaceLast(path, ".", v + ".");
	}

	public static void main(String[] a) {
		//System.out.print(imageResize("213123123123.jpg", 200, 150));
	}

}
