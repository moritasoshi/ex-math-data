package com.example.common;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtils {
  protected final static Logger logger = Logger.getLogger(LogUtils.class.getName());

  public static void info(String subject, Integer id, String name) {
    logger.setLevel(Level.INFO);
    // subject added or changed : [id]id, [name]name
    String msg = subject + " added or changed : [id]" + id + ", [name]" + name;
    logger.info(msg);
  }

}