package ru.minicom.itphone.client;

public class DataSubscribers {

    private static String SubscriberSIP_A = "5098";
    private static String SubscriberSIP_B = "5099";
    private static String login = "admin";
    private static String passwordWeb = "123456";
    private static String password = "o0cJ2uas";
    private static String server = "172.22.50.100";
    private static String portNotify = "2210";
    private static String portSip = "5060";
    private static String portDx_Subscribers_A = "0,199";
    private static String portDx_Subscribers_B = "0,200";

    public String getSubscriberSIP_A(){ return SubscriberSIP_A; }

    public String getSubscriberSIP_B(){ return SubscriberSIP_B; }

    public String getLoginSubscribers(){ return login; }

    public String getPasSubscribers(){ return password; }

    public String getPasswordWeb(){ return passwordWeb; }

    public String getServer(){ return server; }

    public String getPortNotify(){ return portNotify; }

    public String getPortSip(){ return portSip; }

    public String genPortDx_Subscribers_A(){ return portDx_Subscribers_A; }

    public String genPortDx_Subscribers_B(){ return portDx_Subscribers_B; }
}
