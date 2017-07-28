package com.water.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ComFuncs {

    public static void printLog(Class context, String info) {
        Log logger = LogFactory.getLog(context);
        logger.info(info);
    }

    public static void printError(Class context, String error) {
        Log logger = LogFactory.getLog(context);
        logger.error(error);
    }

    public static long getTimeMilli(String oldTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setLenient(false);
        Date oldDate = null;
        try {
            oldDate = formatter.parse(oldTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return oldDate != null ? oldDate.getTime() : 0;
    }
}
