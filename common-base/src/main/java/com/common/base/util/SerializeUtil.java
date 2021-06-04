package com.common.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SerializeUtil {

    private static final Logger logger= LoggerFactory.getLogger(SerializeUtil.class);

    //序列化(jdk自身)
    public static byte[] serialize(Object object) {
        byte[] result = null;
        if (object == null) {
            return new byte[0];
        }
        try  {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);
            if (!(object instanceof Serializable)) {
                String objectClassName =object.getClass().getName();
                logger.error("SerializeUtil serialize error,object is not Serializable,objectClassName={}",objectClassName);
                throw new IllegalArgumentException("SerializeUtil serialize object is not Serializable,objectClassName=" + objectClassName);
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            result =  byteStream.toByteArray();
        } catch (Throwable e) {
            logger.error("SerializeUtil serialize error.",e);
            throw new IllegalArgumentException("SerializeUtil serialize error," ,e);
        }
        return result;
    }

    //反序列化(jdk自身)
    public static Object deserialize(byte[] bytes) {
        Object result = null;
        if (bytes== null || bytes.length<=0) {
            return null;
        }
        try {
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
            try {
                result = objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                logger.error("SerializeUtil deserialize error,occur ClassNotFoundException");
                throw e;
            }
        } catch (Throwable e) {
            logger.error("SerializeUtil deserialize error,",e);
            throw new IllegalArgumentException("SerializeUtil deserialize error," ,e);
        }
        return result;
    }

}
