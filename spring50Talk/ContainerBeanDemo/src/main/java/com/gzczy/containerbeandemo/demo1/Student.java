package com.gzczy.containerbeandemo.demo1;

import lombok.Builder;
import lombok.Data;

/**
 * @Description
 * @Author chenzhengyu
 * @Date 2022-03-26 15:47
 */
@Data
@Builder
public class Student{
  private String name;
  private int age;
}
