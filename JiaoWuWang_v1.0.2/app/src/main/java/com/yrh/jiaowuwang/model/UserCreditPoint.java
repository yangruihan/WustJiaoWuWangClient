package com.yrh.jiaowuwang.model;

/**
 * Created by Yrh on 2015/10/21.
 */
public class UserCreditPoint {

    private String userName;//姓名
    private float[] tongShiCompul;//通识教育平台课程 必修课程
    private float[] tongShiOptiRenWen;//通识教育平台课程 选修课程  人文社科
    private float[] tongShiOptiJingJi;//通识教育平台课程 选修课程  经济管理
    private float[] tongShiOptiZiRan;//通识教育平台课程 选修课程  自然科学
    private float[] tongShiOptiYiShu;//通识教育平台课程 选修课程  艺术体育
    private float[] xueKeJiChuCompul;//学科基础平台课程 必修课程
    private float[] xueKeJiChuOpti;//学科基础平台课程 选修课程
    private float[] zhuanYeHexin;//专业课程模块 专业核心课程
    private float[] zhuanYeFangXiang;//专业课程模块 专业方向课程
    private float[] zhuanYeRenXuan;//专业课程模块 专业任选课程
    private float[] shiJianCompul;//实践教学模块 必修
    private float[] suZhiChuangXin;//素质拓展模块 创新教育
    private float[] suZhiDiEr;//素质拓展模块 第二课堂
    private float[] totalCreditPoint;//学分合计

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float[] getTongShiCompul() {
        return tongShiCompul;
    }

    public void setTongShiCompul(float[] tongShiCompul) {
        this.tongShiCompul = tongShiCompul;
    }

    public float[] getTongShiOptiRenWen() {
        return tongShiOptiRenWen;
    }

    public void setTongShiOptiRenWen(float[] tongShiOptiRenWen) {
        this.tongShiOptiRenWen = tongShiOptiRenWen;
    }

    public float[] getTongShiOptiJingJi() {
        return tongShiOptiJingJi;
    }

    public void setTongShiOptiJingJi(float[] tongShiOptiJingJi) {
        this.tongShiOptiJingJi = tongShiOptiJingJi;
    }

    public float[] getTongShiOptiZiRan() {
        return tongShiOptiZiRan;
    }

    public void setTongShiOptiZiRan(float[] tongShiOptiZiRan) {
        this.tongShiOptiZiRan = tongShiOptiZiRan;
    }

    public float[] getTongShiOptiYiShu() {
        return tongShiOptiYiShu;
    }

    public void setTongShiOptiYiShu(float[] tongShiOptiYiShu) {
        this.tongShiOptiYiShu = tongShiOptiYiShu;
    }

    public float[] getXueKeJiChuCompul() {
        return xueKeJiChuCompul;
    }

    public void setXueKeJiChuCompul(float[] xueKeJiChuCompul) {
        this.xueKeJiChuCompul = xueKeJiChuCompul;
    }

    public float[] getXueKeJiChuOpti() {
        return xueKeJiChuOpti;
    }

    public void setXueKeJiChuOpti(float[] xueKeJiChuOpti) {
        this.xueKeJiChuOpti = xueKeJiChuOpti;
    }

    public float[] getZhuanYeHexin() {
        return zhuanYeHexin;
    }

    public void setZhuanYeHexin(float[] zhuanYeHexin) {
        this.zhuanYeHexin = zhuanYeHexin;
    }

    public float[] getZhuanYeFangXiang() {
        return zhuanYeFangXiang;
    }

    public void setZhuanYeFangXiang(float[] zhuanYeFangXiang) {
        this.zhuanYeFangXiang = zhuanYeFangXiang;
    }

    public float[] getZhuanYeRenXuan() {
        return zhuanYeRenXuan;
    }

    public void setZhuanYeRenXuan(float[] zhuanYeRenXuan) {
        this.zhuanYeRenXuan = zhuanYeRenXuan;
    }

    public float[] getShiJianCompul() {
        return shiJianCompul;
    }

    public void setShiJianCompul(float[] shiJianCompul) {
        this.shiJianCompul = shiJianCompul;
    }

    public float[] getSuZhiChuangXin() {
        return suZhiChuangXin;
    }

    public void setSuZhiChuangXin(float[] suZhiChuangXin) {
        this.suZhiChuangXin = suZhiChuangXin;
    }

    public float[] getSuZhiDiEr() {
        return suZhiDiEr;
    }

    public void setSuZhiDiEr(float[] suZhiDiEr) {
        this.suZhiDiEr = suZhiDiEr;
    }

    public float[] getTotalCreditPoint() {
        return totalCreditPoint;
    }

    public void setTotalCreditPoint(float[] totalCreditPoint) {
        this.totalCreditPoint = totalCreditPoint;
    }
}
