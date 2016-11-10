package com.transparent.automationfactory.base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 网络参数工具类，可用于判断是否连接网络�?当前网络是可用以及获取当前IP
 *
 * @author mady
 * @version 2.0
 * @email yangxiao.hu521@163.com yangxiaohu521@Gmail.com
 * @since 2014-04-17 17:50
 */

public class NetInfoParams {

    private static NetInfoParams netInfoParams = null;
    private ConnectivityManager cwjManager = null;
    private NetworkInfo networkInfo = null;

    private NetInfoParams(Context context) {
        cwjManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = cwjManager.getActiveNetworkInfo();// 当未设置网络时为�?
    }

    public static NetInfoParams getInstacne(Context context) {
        // if (netInfoParams == null) {
        netInfoParams = null;
        netInfoParams = new NetInfoParams(context);
        // }
        return netInfoParams;
    }

    /**
     * 网络是否已经连接(网络已连接未必可用，未连接必不可�?
     *
     * @return
     */
    public boolean isConnected() {
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnected();
    }

    /**
     * 当前网络是否可用
     *
     * @return
     */
    public boolean isAvailable() {
        if (!isConnected()) {
            return false;
        }
        return networkInfo.isAvailable();
    }

    /**
     * 返回网络类型
     *
     * @return
     */
    public int getType() {
        if (networkInfo == null) {
            return -100;
        }
        return cwjManager.getActiveNetworkInfo().getType();
    }

    /**
     * 返回网络名称
     *
     * @return
     */
    public String getTypeName() {
        if (networkInfo == null) {
            return "null";
        }
        return cwjManager.getActiveNetworkInfo().getTypeName();
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public String getIp() {
        try {
            for (Enumeration<?> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements(); ) {
                NetworkInterface intface = (NetworkInterface) en.nextElement();
                for (Enumeration<?> enumIpAddr = intface.getInetAddresses(); enumIpAddr
                        .hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr
                            .nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
//			MyDebug.e("TAG", e.toString());
        }
        return "";
    }

    public String getCurrentNetType() {
        String strNetworkType = "none";

        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                networkInfo.getSubtypeName();
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = "none";//_strSubTypeName;
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }


    public String getCurrentNetStatus() {
        String strNetworkStatus = "network_unknown";

        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkStatus = "network_wifi";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                strNetworkStatus = "network_cell";
            }
        } else {
            strNetworkStatus = "network_disconnected";
        }
        return strNetworkStatus;
    }
}
