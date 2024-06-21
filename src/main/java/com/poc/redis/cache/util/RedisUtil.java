package com.poc.redis.cache.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RedisUtil {

	public static String readLuaScript(String filePath) {
		try {
			byte[] bytes = Files.readAllBytes(Paths.get(filePath));
			return new String(bytes);
		} catch (IOException e) {
			throw new RuntimeException("Failed to read Lua script file: " + filePath, e);
		}
	}

}