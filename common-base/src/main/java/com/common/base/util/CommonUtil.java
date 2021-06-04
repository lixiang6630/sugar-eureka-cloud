package com.common.base.util;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;


/**
 * 公共工具类
 *
 * @author LX
 */
@Slf4j
public class CommonUtil {

    private static final BigDecimal BASE_MONEY = BigDecimal.valueOf(0.01);
    private static final Double BASE_MONEY_DOUBLE = 0.01;
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);

    /**
     * @param rpMoney    红包金额
     * @param userNumber 分配人数
     * @description: 计算每人获得红包金额;最小每人0.01元
     * 先根据人数给每个用户分配随机数,并累加这个随机数;再
     * 根据用户随机数/随机总数计算每个用户百分比,根据此百
     * 分比进行红包分配,钱不够置为0.01元;
     * @author: LX
     */
    public static List<BigDecimal> allotRedPacket(BigDecimal rpMoney, int userNumber) {
        if (rpMoney.doubleValue() < userNumber * BASE_MONEY_DOUBLE) {
            return new ArrayList<>();
        }
        //当平均金额=0.01的时候所有人都一样;
        BigDecimal divideMoney = rpMoney.divide(BigDecimal.valueOf(userNumber), 2, BigDecimal.ROUND_DOWN);
        if (divideMoney.compareTo(BASE_MONEY) == 0) {
            List<BigDecimal> arrMoney = new ArrayList<>(userNumber);
            for (int i = 0; i < userNumber; i++) {
                arrMoney.add(divideMoney);
            }
            return arrMoney;
        }
        Random random = new Random();
        // 发放金额总额:单位分
        int money = rpMoney.multiply(BigDecimal.valueOf(100)).intValue();
        // 随机数总额
        double randomCount = 0;
        // 每人获得随机点数
        double[] arrRandom = new double[userNumber];
        // 每人获得钱数
        List<BigDecimal> arrMoney = new ArrayList<>(userNumber);
        // 分配随机数
        for (int i = 0; i < arrRandom.length; i++) {
            int randomNum = random.nextInt((userNumber) * 99) + 1;
            randomCount += randomNum;
            arrRandom[i] = randomNum;
        }
        // 已分配金额
        int sendMoney = 0;
        for (int i = 0; i < arrRandom.length; i++) {
            // 计算每人百分比
            Double percent = new Double(arrRandom[i] / randomCount);
            // 计算每人获得金额
            int userPreMoney = (int) Math.floor(percent * money);
            if (userPreMoney == 0) {
                userPreMoney = 1;
            }
            sendMoney += userPreMoney;

            BigDecimal everyMoney;
            //不是最后一个人,正常发放;
            if (i < arrRandom.length - 1) {
                everyMoney = BigDecimal.valueOf(userPreMoney).divide(ONE_HUNDRED);
                arrMoney.add(everyMoney);
            } else {
                //最后一个用户,直接获得剩余的钱,并且判断金额是否小于1分钱,小于了就从之前分配的钱多的里面扣除;
                everyMoney = new BigDecimal(money - sendMoney + userPreMoney).divide(ONE_HUNDRED);
                if (everyMoney.compareTo(BigDecimal.ZERO) <= 0) {
                    BigDecimal maxMoney = arrMoney.stream().reduce(BigDecimal::max).get();
                    if (maxMoney != null && maxMoney.compareTo(BASE_MONEY) > 0) {
                        everyMoney = BASE_MONEY;
                        BigDecimal remainMoney = maxMoney.subtract(everyMoney);
                        arrMoney.remove(maxMoney);
                        arrMoney.add(remainMoney);
                    }
                }
                arrMoney.add(everyMoney);
            }
        }
        Collections.shuffle(arrMoney);
        return arrMoney;
    }

    /**
     * 发放单个红包;
     *
     * @param rpMoney    分配金额;
     * @param userNumber 总共分配人数;
     * @return
     */
    public static BigDecimal allotRedPacketSingle(BigDecimal rpMoney, int userNumber) {
        Random random = new Random();
        // 发放金额总额:单位分
        int money = rpMoney.multiply(BigDecimal.valueOf(100)).intValue();
        // 随机数总额
        double randomCount = 0;
        // 每人获得随机点数
        double[] arrRandom = new double[userNumber];
        // 分配随机数
        for (int i = 0; i < userNumber; i++) {
            int randomNum = random.nextInt((userNumber) * 99) + 1;
            randomCount += randomNum;
            arrRandom[i] = randomNum;
        }
        // 计算百分比
        Double percent = new Double(arrRandom[0] / randomCount);
        // 计算获得金额
        int userPreMoney = (int) Math.floor(percent * money);
        if (userPreMoney == 0) {
            userPreMoney = 1;
        }
        return BigDecimal.valueOf(userPreMoney).divide(ONE_HUNDRED);
    }

    /**
     * 参数加密
     *
     * @param param 需要加密的参数
     * @param key   加密秘钥 16位秘钥;
     * @return
     */
    public static String encryptResult(String param, String key) {
        String result;
        try {
            if (StrKit.isEmpty(param)) {
                return "";
            }
            result = AESUtil.aesEncrypt(param, key);
            result = URLEncoder.encode(result, "UTF-8");
        } catch (Exception e) {
            log.info("【参数加密错误】->返回未加密参数");
            result = param;
        }
        return result;
    }

    /**
     * 参数解密
     *
     * @param param 需要解密的参数
     * @param key   加密秘钥 16位秘钥;
     * @return
     */
    public static String decryptResult(String param, String key) {
        String result;
        try {
            if (StrKit.isEmpty(param)) {
                return "";
            }
            param = URLDecoder.decode(param, "UTF-8").replaceAll(" ", "+");
            result = AESUtil.aesDecrypt(param, key);
        } catch (Exception e) {
            log.info("参数解密错误");
            result = "";
        }
        return result;
    }

    /**
     * redis 分页  返回 开始与结束信息 [0] 页 [1] 行
     *
     * @param page 原始页数
     * @param rows 原始行数
     * @return
     */
    public static int[] redisPage(int page, int rows) {
        int tempPage = page;
        int tempRows = rows;
        if (page <= 0) {
            tempRows -= 1;
        }
        if (page == 1) {
            tempRows -= 1;
            tempPage -= 1;
        }
        if (page > 1) {
            tempPage = (page - 1) * rows;
            tempRows = page * rows - 1;
        }
        return new int[]{tempPage, tempRows};
    }

}
