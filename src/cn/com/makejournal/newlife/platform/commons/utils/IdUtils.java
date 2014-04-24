package cn.com.makejournal.newlife.platform.commons.utils;

import java.util.Random;

public class IdUtils {
	private static Random rd = null;

	public static String getUUID() {
		if (rd == null)
			rd = new Random(System.currentTimeMillis());
		long l1 = Math.abs(rd.nextLong());
		long l2 = Math.abs(rd.nextLong());
		long l3 = Math.abs(rd.nextLong());
		long l4 = Math.abs(rd.nextLong());
		long l5 = Math.abs(rd.nextLong());
		long l6 = Math.abs(rd.nextLong());
		String result = (Long.toHexString(l1 * l2) + Long.toHexString(l3 * l4) + Long
				.toHexString(l5 * l6)).substring(0, 32);

		return result;
	}
}