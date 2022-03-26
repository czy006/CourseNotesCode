package com.gzczy.containerbeandemo.demo1;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author chenzhengyu
 * @Date 2022-03-26 15:09
 */
@Component
public class demoB {

  @EventListener
  public void printListen(UserRegisterEvent studentUserRegisterEvent){
    Student source = (Student)studentUserRegisterEvent.getSource();
    System.out.println(source.getName());
  }
}
