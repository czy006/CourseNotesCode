package com.gzczy.containerbeandemo.beanfactory;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description P8 BeanFactory实现
 * BeanFactory 本身功能并不丰富，是通过各种Utils进行扩展
 * @Author chenzhengyu
 * @Date 2022-03-26 17:43
 */

public class BeanFactoryScanTest {

  @Test
  public void beanInit() {
    DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
    AbstractBeanDefinition definition =
            BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
    factory.registerBeanDefinition("config", definition);
    /**
     * 1.2 使用 AnnotationConfigUtils 加入BeanFactory 扩展其扫描功能 打印如下
     * config
     * org.springframework.context.annotation.internalConfigurationAnnotationProcessor
     * org.springframework.context.annotation.internalAutowiredAnnotationProcessor
     * org.springframework.context.annotation.internalCommonAnnotationProcessor
     * org.springframework.context.event.internalEventListenerProcessor
     * org.springframework.context.event.internalEventListenerFactory
     *
     * 结论：可以看到容器中的一并给加载，但是我们自己定义的bean还是没有给扫描到
     */
    AnnotationConfigUtils.registerAnnotationConfigProcessors(factory);
    /**
     * 1.3 添加 bean 后处理器的一些功能从而补充Bean的定义
     *
     * config
     * org.springframework.context.annotation.internalConfigurationAnnotationProcessor
     * org.springframework.context.annotation.internalAutowiredAnnotationProcessor
     * org.springframework.context.annotation.internalCommonAnnotationProcessor
     * org.springframework.context.event.internalEventListenerProcessor
     * org.springframework.context.event.internalEventListenerFactory
     * getBeanA
     * getBeanB
     */
    factory.getBeansOfType(BeanFactoryPostProcessor.class).values().stream().forEach(beanFactoryPostProcessor -> {
      beanFactoryPostProcessor.postProcessBeanFactory(factory);
    });
    // 1.1 首先不添加AnnotationConfigUtils 进行打印，只有config注册的bean，但是我们注解上面的都无法扫描到
    for (String name : factory.getBeanDefinitionNames()) {
      System.out.println(name);
    }
  }

  @Configuration
  static class Config {
    @Bean
    public beanA getBeanA() {
      return new beanA();
    }

    @Bean
    public beanB getBeanB() {
      return new beanB();
    }
  }

  static class beanA {
  }

  static class beanB {
  }

}
