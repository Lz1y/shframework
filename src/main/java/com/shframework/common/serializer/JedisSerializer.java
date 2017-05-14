package com.shframework.common.serializer;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * 序列化(steam)
 * @author OneBoA
 *
 * @param <T>
 */
public class JedisSerializer<T> implements RedisSerializer<T> {

	private static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration(); 
	
	@Override
	public byte[] serialize(T t) throws SerializationException {
		return conf.asByteArray(t);
	}

	@Override
	public T deserialize(byte[] bytes) throws SerializationException {
		return (T) conf.asObject(bytes);
	}

}
