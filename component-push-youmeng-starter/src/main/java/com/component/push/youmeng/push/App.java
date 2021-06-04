package push;


public class App {
    public static void main(String[] args) throws Exception {
        String appKey = "5da6d0284ca357228a00002b";
        String secret = "cmmq9eawe520ujxnh6otoxrz9n5nzhjf";
        Demo demo = new Demo(appKey,secret);
        demo.sendAndroidUnicast();
    }
}
