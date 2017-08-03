package com.water.global;

public class Global {
    public static int[] sensorId = new int[]{
            6000, 6010, 6020, 6030, 6040, 6050, 6060, 6070, 6080, 6100, 6110, 6120, 6130,
            6140, 6150, 6160, 6170, 6190, 6306, 6500, 6520, 6610, 6630, 6650, 6690
    };

    public static int[] timeInterval = new int[]{
            5 * 60 * 1000, 15 * 60 * 1000, 60 * 60 * 1000
    };

    public static boolean load = true;
    public static boolean regular = false;
}
