package com.zl.template.SPI;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        ServiceLoader<MyService> serviceLoader = ServiceLoader.load(MyService.class);
        for (MyService myService : serviceLoader) {
            myService.execute();
        }
    }
}
