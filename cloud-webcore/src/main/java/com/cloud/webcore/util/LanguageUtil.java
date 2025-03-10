package com.cloud.webcore.util;


import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @program:
 * @description:
 * @author:
 * @create:
 **/
@Slf4j
public class LanguageUtil {
    private LanguageUtil(){}

    private static Map<String, String> languageMap = new HashMap<>();

    static {
        languageMap.put("nr", "英语(瑙鲁)");
        languageMap.put("np", "英语|印地语(尼泊尔)");
        languageMap.put("gd", "英语(格林纳达)");
        languageMap.put("ge", "阿拉伯语(格鲁吉亚)");
        languageMap.put("nz", "英语(新西兰)");
        languageMap.put("gf", "英语(法属圭亚那)");
        languageMap.put("ga", "英语(加蓬)");
        languageMap.put("fj", "英语(斐济)");
        languageMap.put("fm", "英语(密克罗尼西亚)");
        languageMap.put("om", "阿拉伯语|英语(阿曼)");
        languageMap.put("gy", "英语(圭亚那)");
        languageMap.put("gn", "英语(几内亚)");
        languageMap.put("gm", "法语(冈比亚)");
        languageMap.put("pe", "西班牙语|英语(秘鲁)");
        languageMap.put("pg", "英语(巴布亚新几内亚)");
        languageMap.put("gh", "英语(加纳)");
        languageMap.put("gg", "法语|英语(根西)");
        languageMap.put("gt", "西班牙语|英语(危地马拉)");
        languageMap.put("ph", "菲律宾语(菲律宾)");
        languageMap.put("gr", "希腊语(希腊)");
        languageMap.put("pk", "英语|波斯语(巴基斯坦)");
        languageMap.put("ls", "英语(莱索托)");
        languageMap.put("lr", "英语(利比里亚)");
        languageMap.put("dz", "英语(阿尔及利亚)");
        languageMap.put("vu", "英语(瓦努阿图)");
        languageMap.put("lu", "德语|法语(卢森堡)");
        languageMap.put("ly", "阿拉伯语(利比亚)");
        languageMap.put("ec", "西班牙语(厄瓜多尔)");
        languageMap.put("vn", "越南语(越南)");
        languageMap.put("uy", "西班牙语(乌拉圭)");
        languageMap.put("mc", "法语(摩纳哥)");
        languageMap.put("md", "摩尔多瓦语(摩尔多瓦)");
        languageMap.put("ma", "英语(摩洛哥)");
        languageMap.put("dk", "丹麦语(丹麦)");
        languageMap.put("dj", "英语(吉布提)");
        languageMap.put("mg", "英语(马达加斯加)");
        languageMap.put("ve", "西班牙语(委内瑞拉)");
        languageMap.put("mh", "英语(马绍尔群岛)");
        languageMap.put("vc", "英语(格林纳丁斯)");
        languageMap.put("do", "西班牙语|英语(多米尼加)");
        languageMap.put("me", "波斯尼亚语|英语(黑山)");
        languageMap.put("va", "意大利语(梵蒂冈)");
        languageMap.put("ml", "英语(马里)");
        languageMap.put("mm", "缅甸语(缅甸)");
        languageMap.put("ev", "西班牙语(萨尔瓦多)");
        languageMap.put("ug", "英语(乌干达)");
        languageMap.put("mt", "马耳他语(马耳他)");
        languageMap.put("mv", "英语(马尔代夫)");
        languageMap.put("mu", "英语(毛里求斯)");
        languageMap.put("us", "英语|西班牙语(美国)");
        languageMap.put("mx", "西班牙语(墨西哥)");
        languageMap.put("mw", "英语(马拉维)");
        languageMap.put("mz", "英语(莫桑比克)");
        languageMap.put("my", "马来语(马来西亚)");
        languageMap.put("eg", "荷兰语|英语(埃及)");
        languageMap.put("tz", "英语(坦桑尼亚)");
        languageMap.put("na", "南非荷兰语|英语(纳米比亚)");
        languageMap.put("ee", "爱沙尼亚语(爱沙尼亚)");
        languageMap.put("tw", "繁体中文(中国台湾)");
        languageMap.put("ne", "法语(尼日尔)");
        languageMap.put("tv", "英语(图瓦卢)");
        languageMap.put("ng", "英语(尼日利亚)");
        languageMap.put("ua", "乌克兰语|俄罗斯语(乌克兰)");
        languageMap.put("ni", "西班牙语(尼加拉瓜)");
        languageMap.put("er", "英语(厄立特里亚)");
        languageMap.put("to", "英语(汤加)");
        languageMap.put("tn", "英语(突尼斯)");
        languageMap.put("tm", "土库曼语(土库曼斯坦)");
        languageMap.put("bz", "西班牙语|英语(伯利兹)");
        languageMap.put("bw", "英语(博茨瓦纳)");
        languageMap.put("tg", "法语(多哥)");
        languageMap.put("jp", "日语(日本)");
        languageMap.put("td", "英语(乍得)");
        languageMap.put("jo", "阿拉伯语(约旦)");
        languageMap.put("bs", "英语(巴哈马)");
        languageMap.put("jm", "英语(牙买加)");
        languageMap.put("br", "葡萄牙语|英语(巴西)");
        languageMap.put("bt", "英语(不丹)");
        languageMap.put("ki", "英语(基里巴斯)");
        languageMap.put("bo", "西班牙语(玻利维亚)");
        languageMap.put("kh", "柬埔寨语(柬埔寨)");
        languageMap.put("kg", "吉尔吉斯语(吉尔吉斯斯坦)");
        languageMap.put("bj", "法语(贝宁)");
        languageMap.put("ke", "英语(肯尼亚)");
        languageMap.put("bf", "英语(布基纳法索)");
        languageMap.put("bh", "英语|阿拉伯语(巴林)");
        languageMap.put("st", "葡萄牙语(圣多美和普林西比)");
        languageMap.put("bi", "英语(布隆迪)");
        languageMap.put("bb", "英语(巴巴多斯)");
        languageMap.put("sy", "叙利亚语(叙利亚)");
        languageMap.put("sz", "英语(斯威士兰)");
        languageMap.put("bd", "孟加拉语(孟加拉国)");
        languageMap.put("kw", "阿拉伯语(科威特)");
        languageMap.put("sn", "法语(塞内加尔)");
        languageMap.put("kz", "哈萨克语(哈萨克斯坦)");
        languageMap.put("sm", "意大利语(圣马力诺)");
        languageMap.put("so", "英语(索马里)");
        languageMap.put("sr", "英语(苏里南)");
        languageMap.put("sd", "英语(苏丹)");
        languageMap.put("cz", "捷克语(捷克)");
        languageMap.put("sc", "英语(塞舌尔)");
        languageMap.put("cy", "塞浦路斯语(塞浦路斯)");
        languageMap.put("kr", "韩语(韩国)");
        languageMap.put("se", "瑞典语(瑞典)");
        languageMap.put("cv", "英语(佛得角)");
        languageMap.put("sg", "英语|马来语|简体中文(新加坡)");
        languageMap.put("cu", "西班牙语(古巴)");
        languageMap.put("km", "英语(科摩罗)");
        languageMap.put("si", "斯洛文尼亚语(斯洛文尼亚)");
        languageMap.put("cq", "英语(赤道几内亚)");
        languageMap.put("li", "德语|英语(列支敦士登)");
        languageMap.put("cr", "西班牙语(哥斯达黎加)");
        languageMap.put("co", "西班牙语(哥伦比亚)");
        languageMap.put("lk", "英语(斯里兰卡)");
        languageMap.put("cm", "英语(喀麦隆)");
        languageMap.put("cn", "简体中文(中国大陆)");
        languageMap.put("ck", "英语(库克群岛)");
        languageMap.put("sb", "英语(所罗门群岛)");
        languageMap.put("cl", "西班牙语(智利)");
        languageMap.put("la", "老挝语(老挝)");
        languageMap.put("rs", "塞尔维亚语(塞尔维亚)");
        languageMap.put("lc", "英语(圣卢西亚)");
        languageMap.put("lb", "阿拉伯语(黎巴嫩)");
        languageMap.put("ch", "德语|意大利语|法语(瑞士)");
        languageMap.put("cf", "英语(中非)");
        languageMap.put("rw", "英语(卢旺达)");
        languageMap.put("ht", "海地语|英语(海地)");
        languageMap.put("hk", "繁体中文|英语(中国香港)");
        languageMap.put("za", "南非荷兰语|英语(南非)");
        languageMap.put("hn", "西班牙语(洪都拉斯)");
        languageMap.put("zw", "葡萄牙语(津巴布韦)");
        languageMap.put("zr", "刚果语(刚果)");
        languageMap.put("ie", "爱尔兰语|英语(爱尔兰)");
        languageMap.put("zm", "英语(赞比亚)");
        languageMap.put("iq", "阿拉伯语(伊拉克)");
        languageMap.put("ir", "波斯语(伊朗)");
        languageMap.put("ye", "阿拉伯语(也门)");
        languageMap.put("ba", "波斯尼亚语(波斯尼亚)");
        languageMap.put("at", "德语(奥地利)");
        languageMap.put("il", "阿拉伯语|英语(以色列)");
        languageMap.put("qa", "英语|阿拉伯语(卡塔尔)");
        languageMap.put("in", "印地语|英语|孟加拉语(印度)");
        languageMap.put("au", "英语(澳大利亚)");
        languageMap.put("al", "阿尔巴尼亚语(阿尔巴尼亚)");
        languageMap.put("ao", "葡萄牙语(安哥拉)");
        languageMap.put("py", "西班牙语(巴拉圭)");
        languageMap.put("an", "英语(荷属安的列斯)");
        languageMap.put("ad", "加泰尼利亚语|英语(安道尔)");
        languageMap.put("ag", "英语(安提瓜和巴布达)");
        languageMap.put("pr", "西班牙语(波多黎各)");
        languageMap.put("ae", "波斯语|英语|阿拉伯语(阿联酋)");

        languageMap.put("zh-cn", "简体中文(中国)");

        languageMap.put("zh-tw", "繁体中文(台湾地区)");

        languageMap.put("zh-hk", "繁体中文(香港)");

        languageMap.put("en-hk", "英语(香港)");

        languageMap.put("en-us", "英语(美国)");

        languageMap.put("en-gb", "英语(英国)");

        languageMap.put("en-ww", "英语(全球)");

        languageMap.put("en-ca", "英语(加拿大)");

        languageMap.put("en-au", "英语(澳大利亚)");

        languageMap.put("en-ie", "英语(爱尔兰)");

        languageMap.put("en-fi", "英语(芬兰)");

        languageMap.put("fi-fi", "芬兰语(芬兰)");

        languageMap.put("en-dk", "英语(丹麦)");

        languageMap.put("da-dk", "丹麦语(丹麦)");

        languageMap.put("en-il", "英语(以色列)");

        languageMap.put("he-il", "希伯来语(以色列)");

        languageMap.put("en-za", "英语(南非)");

        languageMap.put("en-in", "英语(印度)");

        languageMap.put("en-no", "英语(挪威)");

        languageMap.put("en-sg", "英语(新加坡)");

        languageMap.put("en-nz", "英语(新西兰)");

        languageMap.put("en-id", "英语(印度尼西亚)");

        languageMap.put("en-ph", "英语(菲律宾)");

        languageMap.put("en-th", "英语(泰国)");

        languageMap.put("en-my", "英语(马来西亚)");

        languageMap.put("en-xa", "英语(阿拉伯)");

        languageMap.put("ko-kr", "韩文(韩国)");

        languageMap.put("ja-jp", "日语(日本)");

        languageMap.put("nl-nl", "荷兰语(荷兰)");

        languageMap.put("nl-be", "荷兰语(比利时)");

        languageMap.put("pt-pt", "葡萄牙语(葡萄牙)");

        languageMap.put("pt-br", "葡萄牙语(巴西)");

        languageMap.put("fr-fr", "法语(法国)");

        languageMap.put("fr-lu", "法语(卢森堡)");

        languageMap.put("fr-ch", "法语(瑞士)");

        languageMap.put("fr-be", "法语(比利时)");

        languageMap.put("fr-ca", "法语(加拿大)");

        languageMap.put("es-la", "西班牙语(拉丁美洲)");

        languageMap.put("es-es", "西班牙语(西班牙)");

        languageMap.put("es-ar", "西班牙语(阿根廷)");

        languageMap.put("es-us", "西班牙语(美国)");

        languageMap.put("es-mx", "西班牙语(墨西哥)");

        languageMap.put("es-co", "西班牙语(哥伦比亚)");

        languageMap.put("es-pr", "西班牙语(波多黎各)");

        languageMap.put("de-de", "德语(德国)");

        languageMap.put("de-at", "德语(奥地利)");

        languageMap.put("de-ch", "德语(瑞士)");

        languageMap.put("ru-ru", "俄语(俄罗斯)");

        languageMap.put("it-it", "意大利语(意大利)");

        languageMap.put("el-gr", "希腊语(希腊)");

        languageMap.put("no-no", "挪威语(挪威)");

        languageMap.put("hu-hu", "匈牙利语(匈牙利)");

        languageMap.put("tr-tr", "土耳其语(土耳其)");

        languageMap.put("cs-cz", "捷克语(捷克共和国)");

        languageMap.put("sl-sl", "斯洛文尼亚语");
        languageMap.put("pl-pl", "波兰语(波兰)");
        languageMap.put("sv-se", "瑞典语(瑞典)");

        languageMap.put("af", "公用荷兰语");

        languageMap.put("af-ZA", "公用荷兰语(南非)");

        languageMap.put("sq", "阿尔巴尼亚");

        languageMap.put("sq-AL", "阿尔巴尼亚(阿尔巴尼亚)");

        languageMap.put("ar", "阿拉伯语");

        languageMap.put("ar-DZ", "阿拉伯语(阿尔及利亚)");

        languageMap.put("ar-BH", "阿拉伯语(巴林)");

        languageMap.put("ar-EG", "阿拉伯语(埃及)");

        languageMap.put("ar-IQ", "阿拉伯语(伊拉克)");

        languageMap.put("ar-JO", "阿拉伯语(约旦)");

        languageMap.put("ar-KW", "阿拉伯语(科威特)");

        languageMap.put("ar-LB", "阿拉伯语(黎巴嫩)");

        languageMap.put("ar-LY", "阿拉伯语(利比亚)");

        languageMap.put("ar-MA", "阿拉伯语(摩洛哥)");

        languageMap.put("ar-OM", "阿拉伯语(阿曼)");

        languageMap.put("ar-QA", "阿拉伯语(卡塔尔)");

        languageMap.put("ar-SA", "阿拉伯语(沙特阿拉伯)");

        languageMap.put("ar-SY", "阿拉伯语(叙利亚共和国)");

        languageMap.put("ar-TN", "阿拉伯语(北非的共和国)");

        languageMap.put("ar-AE", "阿拉伯语(阿拉伯联合酋长国)");

        languageMap.put("ar-YE", "阿拉伯语(也门)");

        languageMap.put("hy", "亚美尼亚");

        languageMap.put("hy-AM", "亚美尼亚的(亚美尼亚)");

        languageMap.put("az", "Azeri");

        languageMap.put("az-AZ-Cyrl", "Azeri((西里尔字母的))");

        languageMap.put("az-AZ-Latn", "Azeri(拉丁文)(阿塞拜疆)");

        languageMap.put("eu", "巴斯克");

        languageMap.put("eu-ES", "巴斯克(巴斯克)");

        languageMap.put("be", "Belarusian");

        languageMap.put("be-BY", "Belarusian(白俄罗斯)");

        languageMap.put("bg", "保加利亚");

        languageMap.put("bg-BG", "保加利亚(保加利亚)");

        languageMap.put("ca", "嘉泰罗尼亚");

        languageMap.put("ca-ES", "嘉泰罗尼亚(嘉泰罗尼亚)");

        languageMap.put("zh-HK", "中国(香港)");

        languageMap.put("zh-MO", "中国(澳门)");

        languageMap.put("zh-CN", "中国");

        languageMap.put("zh-CHS", "中国(单一化)");

        languageMap.put("zh-SG", "中国(新加坡)");

        languageMap.put("zh-TW", "中国(台湾)");

        languageMap.put("zh-CHT", "中国(传统的)");

        languageMap.put("hr", "克罗埃西亚");

        languageMap.put("hr-HR", "克罗埃西亚(克罗埃西亚)");

        languageMap.put("cs", "捷克");

        languageMap.put("cs-CZ", "捷克(捷克)");

        languageMap.put("da", "丹麦文");

        languageMap.put("da-DK", "丹麦文(丹麦)");

        languageMap.put("div", "Dhivehi");

        languageMap.put("div-MV", "Dhivehi(马尔代夫)");

        languageMap.put("nl", "荷兰");

        languageMap.put("nl-BE", "荷兰(比利时)");

        languageMap.put("nl-NL", "荷兰(荷兰)");

        languageMap.put("en", "英国");

        languageMap.put("en-AU", "英国(澳洲)");

        languageMap.put("en-BZ", "英国(伯利兹)");

        languageMap.put("en-CA", "英国(加拿大)");

        languageMap.put("en-CB", "英国(加勒比海)");

        languageMap.put("en-IE", "英国(爱尔兰)");

        languageMap.put("en-JM", "英国(牙买加)");

        languageMap.put("en-NZ", "英国(新西兰)");

        languageMap.put("en-PH", "英国(菲律宾共和国)");

        languageMap.put("en-ZA", "英国(南非)");

        languageMap.put("en-TT", "英国(千里达托贝哥共和国)");

        languageMap.put("en-GB", "英国(英国)");

        languageMap.put("en-US", "英国(美国)");

        languageMap.put("en-ZW", "英国(津巴布韦)");

        languageMap.put("et", "爱沙尼亚");

        languageMap.put("et-EE", "爱沙尼亚的(爱沙尼亚)");

        languageMap.put("fo", "Faroese");

        languageMap.put("fo-FO", "Faroese(法罗群岛)");

        languageMap.put("fa", "波斯语");

        languageMap.put("fa-IR", "波斯语(伊朗王国)");

        languageMap.put("fi", "芬兰语");

        languageMap.put("fi-FI", "芬兰语(芬兰)");

        languageMap.put("fr", "法国");

        languageMap.put("fr-BE", "法国(比利时)");

        languageMap.put("fr-CA", "法国(加拿大)");

        languageMap.put("fr-FR", "法国(法国)");

        languageMap.put("fr-LU", "法国(卢森堡)");

        languageMap.put("fr-MC", "法国(摩纳哥)");

        languageMap.put("fr-CH", "法国(瑞士)");

        languageMap.put("gl", "加利西亚");

        languageMap.put("gl-ES", "加利西亚(加利西亚)");

        languageMap.put("ka", "格鲁吉亚州");

        languageMap.put("ka-GE", "格鲁吉亚州(格鲁吉亚州)");

        languageMap.put("de", "德国");

        languageMap.put("de-AT", "德国(奥地利)");

        languageMap.put("de-DE", "德国(德国)");

        languageMap.put("de-LI", "德国(列支敦士登)");

        languageMap.put("de-LU", "德国(卢森堡)");

        languageMap.put("de-CH", "德国(瑞士)");

        languageMap.put("el", "希腊");

        languageMap.put("el-GR", "希腊(希腊)");

        languageMap.put("gu", "Gujarati");

        languageMap.put("gu-IN", "Gujarati(印度)");

        languageMap.put("he", "希伯来");

        languageMap.put("he-IL", "希伯来(以色列)");

        languageMap.put("hi", "北印度语");

        languageMap.put("hi-IN", "北印度的(印度)");

        languageMap.put("hu", "匈牙利");

        languageMap.put("hu-HU", "匈牙利的(匈牙利)");

        languageMap.put("is", "冰岛语");

        languageMap.put("is-IS", "冰岛的(冰岛)");

        languageMap.put("id", "印尼");

        languageMap.put("id-ID", "印尼(印尼)");

        languageMap.put("it", "意大利");

        languageMap.put("it-IT", "意大利(意大利)");

        languageMap.put("it-CH", "意大利(瑞士)");

        languageMap.put("ja", "日本");

        languageMap.put("ja-JP", "日本(日本)");

        languageMap.put("kn", "卡纳达语");

        languageMap.put("kn-IN", "卡纳达语(印度)");

        languageMap.put("kk", "Kazakh");

        languageMap.put("kk-KZ", "Kazakh(哈萨克)");

        languageMap.put("kok", "Konkani");

        languageMap.put("kok-IN", "Konkani(印度)");

        languageMap.put("ko", "韩国");

        languageMap.put("ko-KR", "韩国(韩国)");

        languageMap.put("ky", "Kyrgyz");

        languageMap.put("ky-KZ", "Kyrgyz(哈萨克)");

        languageMap.put("lv", "拉脱维亚");

        languageMap.put("lv-LV", "拉脱维亚的(拉脱维亚)");

        languageMap.put("lt", "立陶宛");

        languageMap.put("lt-LT", "立陶宛(立陶宛)");

        languageMap.put("mk", "马其顿");

        languageMap.put("mk-MK", "马其顿(FYROM)");

        languageMap.put("ms", "马来");

        languageMap.put("ms-BN", "马来(汶莱)");

        languageMap.put("ms-MY", "马来(马来西亚)");

        languageMap.put("mr", "马拉地语");

        languageMap.put("mr-IN", "马拉地语(印度)");

        languageMap.put("mn", "蒙古");

        languageMap.put("mn-MN", "蒙古(蒙古)");

        languageMap.put("no", "挪威");

        languageMap.put("nb-NO", "挪威(Bokm)");

        languageMap.put("nn-NO", "挪威(Nynorsk)");

        languageMap.put("pl", "波兰");

        languageMap.put("pl-PL", "波兰(波兰)");

        languageMap.put("pt", "葡萄牙");

        languageMap.put("pt-BR", "葡萄牙(巴西)");

        languageMap.put("pt-PT", "葡萄牙(葡萄牙)");

        languageMap.put("pa", "Punjab语");

        languageMap.put("pa-IN", "Punjab语(印度)");

        languageMap.put("ro", "罗马尼亚语");

        languageMap.put("ro-RO", "罗马尼亚语(罗马尼亚)");

        languageMap.put("ru", "俄国");

        languageMap.put("ru-RU", "俄国(俄国)");

        languageMap.put("sa", "梵文");

        languageMap.put("sa-IN", "梵文(印度)");

        languageMap.put("sr-SP-Cyrl", "西里尔字母(塞尔维亚()");

        languageMap.put("sr-SP-Latn", "拉丁文(塞尔维亚)");

        languageMap.put("sk", "斯洛伐克");

        languageMap.put("sk-SK", "斯洛伐克(斯洛伐克)");

        languageMap.put("sl", "斯洛文尼亚");

        languageMap.put("sl-SI", "斯洛文尼亚(斯洛文尼亚)");

        languageMap.put("es", "西班牙");

        languageMap.put("es-AR", "西班牙(阿根廷)");

        languageMap.put("es-BO", "西班牙(玻利维亚)");

        languageMap.put("es-CL", "西班牙(智利)");

        languageMap.put("es-CO", "西班牙(哥伦比亚)");

        languageMap.put("es-CR", "西班牙(哥斯达黎加)");

        languageMap.put("es-DO", "西班牙(多米尼加共和国)");

        languageMap.put("es-EC", "西班牙(厄瓜多尔)");

        languageMap.put("es-SV", "西班牙(萨尔瓦多)");

        languageMap.put("es-GT", "西班牙(危地马拉)");

        languageMap.put("es-HN", "西班牙(洪都拉斯)");

        languageMap.put("es-MX", "西班牙(墨西哥)");

        languageMap.put("es-NI", "西班牙(尼加拉瓜)");

        languageMap.put("es-PA", "西班牙(巴拿马)");

        languageMap.put("es-PY", "西班牙(巴拉圭)");

        languageMap.put("es-PE", "西班牙(秘鲁)");

        languageMap.put("es-PR", "西班牙(波多黎各)");

        languageMap.put("es-ES", "西班牙(西班牙)");

        languageMap.put("es-UY", "西班牙(乌拉圭)");

        languageMap.put("es-VE", "西班牙(委内瑞拉)");

        languageMap.put("sw", "Swahili");

        languageMap.put("sw-KE", "Swahili(肯尼亚)");

        languageMap.put("sv", "瑞典");

        languageMap.put("sv-FI", "瑞典(芬兰)");

        languageMap.put("sv-SE", "瑞典(瑞典)");

        languageMap.put("syr", "Syriac");

        languageMap.put("syr-SY", "Syriac(叙利亚共和国)");

        languageMap.put("ta", "坦米尔");

        languageMap.put("ta-IN", "坦米尔(印度)");

        languageMap.put("tt", "Tatar");

        languageMap.put("tt-RU", "Tatar(俄国)");

        languageMap.put("te", "Telugu");

        languageMap.put("te-IN", "Telugu(印度)");

        languageMap.put("th", "泰国");

        languageMap.put("th-TH", "泰国(泰国)");

        languageMap.put("tr", "土耳其语");

        languageMap.put("tr-TR", "土耳其语(土耳其)");

        languageMap.put("uk", "乌克兰");

        languageMap.put("uk-UA", "乌克兰(乌克兰)");

        languageMap.put("ur", "Urdu");

        languageMap.put("ur-PK", "Urdu(巴基斯坦)");

        languageMap.put("uz", "Uzbek");

        languageMap.put("uz-UZ-Cyrl", "Uzbek(乌兹别克斯坦)");

        languageMap.put("uz-UZ-Latn", "Uzbek(乌兹别克斯坦)");
    }

    public static String getLanguage(String key) {
        return languageMap.get(key.toLowerCase());
    }

    public static String getLanguageKeyByCountryCode(String key) {
        String result = "en";
        Set<String> strings = languageMap.keySet();
        for (String string : strings) {
            boolean ae = string.contains(key);
            if(ae){
                result = string;
            }
        }
        return result.split("-")[0];
    }
}
