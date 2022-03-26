package com.gzczy.containerbeandemo;

import com.gzczy.containerbeandemo.demo1.Student;
import com.gzczy.containerbeandemo.demo1.UserRegisterEvent;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * p1 - p7
 */
@SpringBootApplication
public class ContainerBeanDemoApplication {

  public static void main(String[] args) throws Exception {

    ConfigurableApplicationContext context = SpringApplication.run(ContainerBeanDemoApplication.class, args);
    Field field = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
    field.setAccessible(true);
    ConfigurableListableBeanFactory factory = context.getBeanFactory();
    Map<String, Object> map = (Map<String, Object>) field.get(factory);
    map.entrySet().stream().filter(x -> x.getKey().startsWith("demo"))
            .forEach(x -> {
              System.out.println(x.getKey() + "" + x.getValue());
            });

    // classpath* 表示可以前往jar中获取
    Resource[] resources = context.getResources("classpath*:application.properties");
    for (Resource resource : resources) {
      System.out.println(resource);
    }

    // 直接获取相关值
    ConfigurableEnvironment environment = context.getEnvironment();
    System.out.println(environment.getProperty("server.port"));
    Student student = Student.builder().name("czy").age(10).build();
    //eventsListener demo 进行解耦
    context.publishEvent(new UserRegisterEvent<>(student));

  }

}
