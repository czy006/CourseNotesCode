package com.gzczy.containerbeandemo.demo2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Description
 * @Author chenzhengyu
 * @Date 2022-04-10 16:11
 */
public class TestBeanFactory {

  public static void main(String[] args) {
    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope(
            "singleton").getBeanDefinition();
    beanFactory.registerBeanDefinition("config", beanDefinition);

    // 给beanFactory添加了一些常用的后处理器
    AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
    // beanFactory 后处理器的主要功能 补充了一些bean定义
    beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().stream().forEach(beanFactoryPostProcessor -> {
      beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
    });

    // bean 后处理器，针对bean的生命周期的各个阶段提供了扩展，例如@AutoWried @Resource
    // 如果不添加此后处理器 获取bean2时候则会有为null 结果如下

    //16:29:53.141 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating
    // shared instance of singleton bean 'bean1'
    //16:29:53.142 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating
    // shared instance of singleton bean 'config'
    //16:29:53.159 [main] INFO com.gzczy.containerbeandemo.demo2.TestBeanFactory$Bean1 - bean1 init....
    //null

    //beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);

    // 我们可以通过比较器调整order加载的优先级次序
    beanFactory.getBeansOfType(BeanPostProcessor.class).values().stream()
            .sorted(beanFactory.getDependencyComparator())
            .forEach(beanPostProcessor -> {
              System.out.println(">>>>>>> "+beanPostProcessor);
              beanFactory.addBeanPostProcessor(beanPostProcessor);
            });

    for (String name : beanFactory.getBeanDefinitionNames()) {
      System.out.println(name);
    }

    // 准备好所有的单例 用的时候不用再创建
    beanFactory.preInstantiateSingletons();
    System.out.println("-----------单例对象默认延迟创建！！！！！-----------");
    System.out.println(beanFactory.getBean(Bean1Test.class).getBean2());
    /**
     * P09 截止
     * 从以上代码我们能获知什么信息：
     * 1、BeanFactory不会主动做这些事情
     *  不会主动的调用 BeanFactory 后置处理器
     *  不会主动的添加 Bean 后处理器
     *  不会主动初始化单例
     *  不会解析beanFactory 还不会解析${} 与 #{}
     * 2、bean 后处理器会有排序逻辑
     */

    //问题：如果我们即加了AutoWrite 又加了Resource 注解，那我们的是什么会优先生效呢  （可以看） AnnotationConfigUtils

    System.out.println(beanFactory.getBean(Bean1Test.class).getInter());
    // 添加了比较器后 结果如下
    // com.gzczy.containerbeandemo.demo2.TestBeanFactory$Bean4Test@6d4d66d2
  }
  @Configuration
  static class Config {

    @Bean
    public Bean1Test bean1Test() {
      return new Bean1Test();
    }

    @Bean
    public Bean2Test bean2Test() {return new Bean2Test();}

    @Bean
    public Bean3Test bean3Test() {return new Bean3Test();}

    @Bean
    public Bean4Test bean4Test() {return new Bean4Test();}
  }

  @Slf4j
  static class Bean1Test {
    public Bean1Test() {
      log.info("bean1 init....");
    }

    @Autowired
    private Bean2Test bean2Test;

    public Bean2Test getBean2() {
      return bean2Test;
    }

    @Autowired
    @Resource(name = "bean4Test")
    private Inter bean3Test;

    public Inter getInter() {
      return bean3Test;
    }
  }


  @Slf4j
  static class Bean2Test {
    public Bean2Test() {
      log.info("bean2 init....");
    }

  }

  interface  Inter{}

  @Slf4j
  static class Bean3Test implements Inter{
    public Bean3Test() {
      log.info("bean3 init....");
    }
  }

  @Slf4j
  static class Bean4Test implements Inter{
    public Bean4Test() {
      log.info("bean4 init....");
    }
  }
}
