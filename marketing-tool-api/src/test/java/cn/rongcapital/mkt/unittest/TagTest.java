package cn.rongcapital.mkt.unittest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TagTest {
	String txt1 = "天鹅舞泡芙/晴天杯/经典小方/草莓杏仁塔/圣诞纸杯/午后芭蕾/天鹅舞泡芙(两只装)/克拉情人杯/百利豆豆瑞士卷/香草瑞士卷/草莓慕斯杯/闪电泡芙/甜心/甜心x/巧克力千层蛋糕/榴莲千层蛋糕/甜品四拼/甜品八拼/香草百利/日式米卷/双层芝士/抹茶卷/薄荷慕斯杯/水果泡芙圈/香草水果蛋糕杯/芒果派/柠檬派/法式原味浮云卷/闪电泡芙/法式巧克力浮云卷/草莓甜心/经典小方/马卡龙/百香果椰子慕斯/芒果慕斯/巧克力慕斯/芝士星语/Organic奶牛/芝士星语/芝士切块/多拼切块/甜心单只/小方切块/芝士切块/奥利奥/芝士切块/芝士切块/榴莲千层/经典拿破仑/爱丽丝系列/缤纷梦境系列/黑天鹅系列/日式荞麦面蛋糕/和风榴莲蛋包饭蛋糕/伯爵茶碗蒸奶冻/大明星红天鹅绒蛋糕（切片）/百利黑醇巧克力烂泥蛋糕（切片）/纽约重磅乳酪芝士蛋糕（切片）/Devil恶魔布朗尼";
	String txt2 = "焦糖玛奇朵(热)/香草拿铁(热)/拿铁(热)/卡布奇诺(热)/摩卡(热)/美式咖啡(热)/焦糖玛奇朵(冰)/香草拿铁(冰)/拿铁(冰)/卡布奇诺(冰)/摩卡(冰)/美式咖啡(冰)/浓缩咖啡星冰乐/咖啡星冰乐/摩卡星冰乐/焦糖咖啡星冰乐/可可碎片星冰乐/香草星冰乐/巧克力星冰乐/抹茶星冰乐/焦糖星冰乐/芒果西番莲星冰乐/新鲜调制咖啡(热)/新鲜调制咖啡(冰)/英式红茶拿铁(热)/抹茶拿铁(热)/抹茶拿铁(冰)/热巧克力/冰巧克力(含奶油)/卡布奇诺（热）/卡布奇诺（冰）/拿铁（热）/拿铁（冰）/美式（热）/美式（冰）/摩卡（热）/摩卡（冰）/香草拿铁（热）/香草拿铁（冰）/浓情焦糖咖啡（热）/浓情焦糖咖啡（冰）/榛子卡布奇诺（热）/榛子卡布奇诺（冰）/绿茶拿铁（热）/绿茶拿铁（冰）/热巧克力/冰巧克力/香草咖啡酷乐/焦糖咖啡酷乐/榛子咖啡酷乐/香草浓情酷乐/薄荷香草浓情酷乐/双份巧克力浓情酷乐/混合浆果浓情酷乐/桃子酷乐/红莓酷乐/芒果百香果酷乐/冰摇芒果木槿花茶/冰摇红莓黑加仑茶/薄荷香桃冰茶/清新蔓越莓冰茶/星巴克鲜奶油/星巴克糖浆/星巴克浓缩咖啡/COSTA鲜奶油/COSTA糖浆/COSTA浓缩咖啡/英式红茶拿铁(冰)/冰巧克力(去奶油)/草莓芝士奶香星冰乐/提拉米苏星冰乐/红豆抹茶星冰乐/伯爵吉利焙茶星冰乐/缤纷焦糖星冰乐/桑德拉起泡酒/布蕾玛奇朵（热饮/冰饮/星冰乐）/啡常冰摩卡/法式香草拿铁/浓醇黑焦糖拿铁/蔓越莓白巧克力摩卡/太妃榛果拿铁/提拉米苏拿铁";
	String txt3 = "香槟玫瑰/圣诞花盒/鲜花包月套餐/蒂凡尼の清晨/永生花盒/鲜花礼盒/挚爱康乃馨/爱巢/永爱";
	String txt4 = "雀巢咖啡/万圣节礼品套餐/坚果新年礼包/来一份礼包/红豆薏米汁礼盒/经典混合口味礼盒/小黄人大眼萌海底冒险套/生日芭比/托马斯和朋友之托比寻宝/五芳大肉粽、蛋黄鲜肉粽/炫酷VR眼镜/iphone6自拍杆功能手机壳/萌萌的天气瓶";
	@Test
	public void test1() {
		String[] split = txt1.split("/");
		List<String> list = new ArrayList<>();
		for (String string : split) {
			if (!list.contains(string)) {
				list.add(string);
			}
		}
		System.out.println(list.size());
		StringBuffer sb = new StringBuffer();
		for (String string : list) {
			sb.append(string + "/");
		}
		String string = sb.toString();
		System.out.println(string.substring(0, string.length() - 1));

	}
	
	@Test
	public void test2() {
		String[] split = txt2.split("/");
		List<String> list = new ArrayList<>();
		for (String string : split) {
			if (!list.contains(string)) {
				list.add(string);
			}
		}
		System.out.println(list.size());
		StringBuffer sb = new StringBuffer();
		for (String string : list) {
			sb.append(string + "/");
		}
		String string = sb.toString();
		System.out.println(string.substring(0, string.length() - 1));

	}
	
	@Test
	public void test3() {
		String[] split = txt3.split("/");
		List<String> list = new ArrayList<>();
		for (String string : split) {
			if (!list.contains(string)) {
				list.add(string);
			}
		}
		System.out.println(list.size());
		StringBuffer sb = new StringBuffer();
		for (String string : list) {
			sb.append(string + "/");
		}
		String string = sb.toString();
		System.out.println(string.substring(0, string.length() - 1));

	}
	
	@Test
	public void test4() {
		String[] split = txt4.split("/");
		List<String> list = new ArrayList<>();
		for (String string : split) {
			if (!list.contains(string)) {
				list.add(string);
			}
		}
		System.out.println(list.size());
		StringBuffer sb = new StringBuffer();
		for (String string : list) {
			sb.append(string + "/");
		}
		String string = sb.toString();
		System.out.println(string.substring(0, string.length() - 1));

	}

}
