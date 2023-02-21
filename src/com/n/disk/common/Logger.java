package com.n.disk.common;

public class Logger {
    public static final void error(String message) {
        System.err.println(message);
    }

    public static final void error(Object message) {
        if (message != null)
            System.err.println(message.toString());
    }

    public static final void error(String message, Object...args) {
        System.err.printf((message) + "%n",args);
    }


    public static final void info(String message) {
        System.out.println(message);
    }

    public static final void info(String message, Object...args) {
        System.err.printf((message) + "%n",args);
    }

    public static final void info(Object message) {
        if (message != null)
            System.out.println(message.toString());
    }
}
