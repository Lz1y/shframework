package com.shframework.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.shframework.modules.sys.entity.User;
import com.shframework.modules.sys.mapper.UserMapper;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class TagDirective implements TemplateDirectiveModel {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void execute(Environment environment, @SuppressWarnings("rawtypes") Map map, TemplateModel[] templateModel,
			TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {

        String name = map.get("name").toString();
        
        System.out.println(name);
        
        User user1 = userMapper.getUser(1);
        User user2 = userMapper.getUser(2);
        User user3 = userMapper.getUser(3);
        
        
        System.out.println("TagDirective -> execute -> name -> " + name);
        List<User> list = new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        
 
        environment.setVariable("userList", getBeansWrapper().wrap(list));
        templateDirectiveBody.render(environment.getOut());
	}

    public static BeansWrapper getBeansWrapper(){
        BeansWrapper beansWrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_21).build();
        return beansWrapper;
    }
    
}
