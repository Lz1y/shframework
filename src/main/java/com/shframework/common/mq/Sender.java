package com.shframework.common.mq;

import static com.shframework.common.util.Constants.BROKER_URL;
import static com.shframework.common.util.Constants.QUEUE_NAME;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.shframework.modules.sys.entity.SysNotification;

public class Sender {
	
	private static final Logger logger = LoggerFactory.getLogger(Sender.class);
	
	public static final String ALG_SHA1 = "SHA-1";
	public static final String SIGN_KEY = "3s9rgICJaKiet8Ss";
	
	public static Gson gson = new Gson();

    public static String notify2xml(SysNotification notify) throws Exception {
		StringWriter write = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(SysNotification.class);
	  	context.createMarshaller().marshal(notify, write);  
	  	return write.toString();
    }
    
    public static SysNotification xml2notify(String xml) throws Exception {
        JAXBContext context = JAXBContext.newInstance(SysNotification.class);
    	Unmarshaller unmarshaller = context.createUnmarshaller();
    	return (SysNotification) unmarshaller.unmarshal(new StringReader(xml));
    }
    
    public static void confProducerAndSendMessage(String xml) {
        // ConnectionFactory ：连接工厂，JMS 用它创建连接
        ConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接
        Connection connection = null;
        // Session： 一个发送或接收消息的线程
        Session session;
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination;
        // MessageProducer：消息发送者
        MessageProducer producer;
        // TextMessage message;
        
        // 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
        connectionFactory = new ActiveMQConnectionFactory(
        	ActiveMQConnection.DEFAULT_USER,
        	ActiveMQConnection.DEFAULT_PASSWORD,
        	BROKER_URL
        );
        
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 获取session注意参数值是一个服务器的queue，须在在ActiveMq的console配置
            destination = session.createQueue(QUEUE_NAME);
            // 得到消息生成者【发送者】
            producer = session.createProducer(destination);
            // 设置不持久化，此处学习，实际根据项目决定
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            // 构造消息，此处写死，项目就是参数，或者方法获取
            sendMessage(session, producer, xml);
            // 提交
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("["+xml+"]", e);
        } finally {
            try {
                if (null != connection) connection.close();
            } catch (Throwable ignore) {
            	ignore.printStackTrace();
            	logger.error("[connection close error] ["+xml+"]", ignore);
            }
        }
    }

    public static void sendMessage(Session session, MessageProducer producer, String xml) throws Exception {
        TextMessage message = session.createTextMessage(xml);
        // 发送消息到配置通道
        producer.send(message);
    }
    
    /**
     * 
     * @param map
     * e.g 计算班级人数 -> {"type":Constants.ASYNC_KEY_EDU_STUDENT_COUNT,"v":"1"}
     * e.g 消息 -> {"type":Constants.ASYNC_KEY_SYS_NOTIFICATION,"notify":"1"}
     */
    public static void send2mq(Map<String, Object> map) {
    	confProducerAndSendMessage(gson.toJson(map));
    }
    
}
