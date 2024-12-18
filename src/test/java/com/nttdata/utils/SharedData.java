package com.nttdata.utils;

public class SharedData {
    private static long orderId;

    public static long getOrderId() { return orderId; }
    public static void setOrderId(long id) { orderId = id; }

}
