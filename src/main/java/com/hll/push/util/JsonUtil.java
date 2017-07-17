package com.hll.push.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Author: huangll
 * Written on 17/7/16.
 */
public class JsonUtil {

  static private ObjectMapper objectMapper = new ObjectMapper();

  public static String toJson(Object o) {
    try {
      return objectMapper.writeValueAsString(o);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
