package com.shframework.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.nustaq.serialization.FSTConfiguration;
import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

import com.shframework.common.base.vo.TbsVo;

public class SerializeUtil {


	static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration(); 
	
	public TbsVo myreadMethod(InputStream stream) throws Exception  
	{  
	    FSTObjectInput in = conf.getObjectInput( stream );  
	    TbsVo result = (TbsVo) in.readObject( TbsVo.class );  
	    // DON'T: in.close(); here prevents reuse and will result in an exception        
	    stream.close();  
	    return result;  
	}  
	   
	public void mywriteMethod(OutputStream stream, TbsVo toWrite) throws IOException   
	{  
	    FSTObjectOutput out = conf.getObjectOutput( stream );  
	    out.writeObject( toWrite, TbsVo.class );  
	    // DON'T out.close() when using factory method;  
	    out.flush();  
	    stream.close();  
	}

	public static byte[] serialize(Object obj) {
		return conf.asByteArray(obj);
	}

	public static Object unserialize(byte[] sec) {
		return conf.asObject(sec);
	}
	
}
