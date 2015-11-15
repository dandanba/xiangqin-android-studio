package com.xiangqin.app.utils;

import java.util.List;

/**
 * List Utils
 */
public class ListUtils {
	public static <T> int getSize(List<T> source) {
		return source == null ? 0 : source.size();
	}

	public static <T> boolean isEmpty(List<T> source) {
		return (source == null || source.size() == 0);
	}
}
