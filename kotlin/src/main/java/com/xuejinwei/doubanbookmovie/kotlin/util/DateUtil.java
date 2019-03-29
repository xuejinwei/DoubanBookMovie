package com.xuejinwei.doubanbookmovie.kotlin.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observable;

/**
 * Created by twiceYuan on 9/15/15.
 * <p>
 * 时间机器
 */
public class DateUtil {

    /**
     * 最短的时间格式，非当日的显示月日，当日的显示时分
     */
    public static String shortTime(long time) {
        Calendar today = Calendar.getInstance();
        Calendar target = Calendar.getInstance();
        target.setTime(new Date(time));
        if (today.get(Calendar.YEAR) == target.get(Calendar.YEAR)
                && today.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)) {
            return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(time);
        } else {
            return (target.get(Calendar.MONTH) + 1) + "月" + target.get(Calendar.DAY_OF_MONTH) + "日";
        }
    }

    public static String char8(String char8Date) {
        int year = Integer.parseInt(char8Date.substring(0, 4));
        int month = Integer.parseInt(char8Date.substring(4, 6));
        int day = Integer.parseInt(char8Date.substring(6, 8));
        return String.format("%s-%s-%s", year, month, day);
    }

    /**
     * 格式化完整格式日期
     *
     * @param date      时间戳
     * @param separator 分隔符号
     */
    public static String fullDate(long date, String separator) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd", Locale.getDefault());
        return format.format(new Date(date));
    }

    /**
     * 最全的时间格式
     *
     * @return 返回这种时间格式 2016-12-12 21:12:12
     */
    public static String fullTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(new Date(time));
    }

    /**
     * 转换日期 转换为更为人性化的时间
     *
     * @param time 给定时间 13位，包含毫秒
     */
    public static String translateDate(long time) {
        time = time / 1000;
        long curTime = System.currentTimeMillis() / 1000;
        long oneDay = 24 * 60 * 60;
        Calendar today = Calendar.getInstance();    //今天
        today.setTimeInMillis(curTime * 1000);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        long todayStartTime = today.getTimeInMillis() / 1000;
        if (time >= todayStartTime) {
            long d = curTime - time;
            if (d <= 60) {
                return "1分钟前";
            } else if (d <= 60 * 60) {
                long m = d / 60;
                if (m <= 0) {
                    m = 1;
                }
                return m + "分钟前";
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("今天 HH:mm", Locale.getDefault());
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        } else {
            if (time < todayStartTime && time > todayStartTime - oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("昨天 HH:mm", Locale.getDefault());
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {

                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else if (time < todayStartTime - oneDay && time > todayStartTime - 2 * oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("前天 HH:mm", Locale.getDefault());
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        }
    }

    /**
     * 计算剩余时间（还剩 3 天，还剩 3 小时，还剩 3 分钟，还剩 3 秒）
     */
    public static String remain(long target) {
        long duration = target - System.currentTimeMillis();
        long minute = 1000 * 60;
        long hour = minute * 60;
        long day = hour * 24;

        if (duration > day) {
            return "还剩 " + (duration / day) + " 天";
        }
        if (duration > hour) {
            return "还剩 " + (duration / hour) + " 小时";
        }
        if (duration > minute) {
            return "还剩 " + (duration / minute) + " 分钟";
        }
        if (duration > 0) {
            return "还剩 " + duration / 1000 + " 秒";
        } else {
            return "已过期";
        }
    }

    @SuppressWarnings("ConstantConditions") public static Observable<String> remainDynamic(long target) {

        final boolean DEBUG = false; // BuildConfig.DEBUG
        final Timer timer = new Timer();
        int duration = DEBUG ? 10 : 1000;

        final long[] TEST_CURRENT = {System.currentTimeMillis()};

        Observable<String> observable = Observable.create(subscriber -> timer.schedule(new TimerTask() {
            @Override
            public void run() {
                subscriber.onNext(fullRemain(target - TEST_CURRENT[0]));
                if (target - TEST_CURRENT[0] == 0) {
                    subscriber.onCompleted();
                }
                if (DEBUG) {
                    TEST_CURRENT[0] += 10000;
                } else {
                    TEST_CURRENT[0] = System.currentTimeMillis();
                }
            }
        }, 0, duration));
        return observable.doOnUnsubscribe(timer::cancel);
    }

    /**
     * 完整格式的剩余时间
     *
     * @return 还剩 xx 天 xx 小时 xx 分钟 xx 秒
     */
    public static String fullRemain(long duration) {

        long minute = 1000 * 60;
        long hour = minute * 60;
        long day = hour * 24;

        if (duration > day) {
            return (duration / day) + " 天 " + fullRemain(duration % day);
        }
        if (duration > hour) {
            return (duration / hour) + " 小时 " + fullRemain(duration % hour);
        }
        if (duration > minute) {
            return (duration / minute) + " 分钟 " + fullRemain(duration % minute);
        }
        if (duration > 0) {
            return duration / 1000 + " 秒";
        } else {
            return "";
        }
    }
}
