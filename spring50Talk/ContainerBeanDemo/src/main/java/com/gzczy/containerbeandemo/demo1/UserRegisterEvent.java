package com.gzczy.containerbeandemo.demo1;

import org.springframework.context.ApplicationEvent;

/**
 * @Description
 * @Author chenzhengyu
 * @Date 2022-03-26 15:29
 */
public class UserRegisterEvent<T> extends ApplicationEvent {

  public UserRegisterEvent(T source) {
    super(source);
  }
}
