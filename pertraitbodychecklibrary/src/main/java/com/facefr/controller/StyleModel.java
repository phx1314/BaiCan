package com.facefr.controller;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class StyleModel {
    public int resBackImg;	//返回按钮图片资源
    public int resContentBgColor;	//界面主体背景色
    public int resActionBackImg;	//动作栏背景图片资源
    public int resTxtNormalColor;	//动作栏里字体颜色 注:动作一，动作二，动作三 字样
    public int resTxtSuccessColor;	//成功提示字体色
    public int resTxtErrorColor;	//失败提示字体色
    public int resTxtPercentageColor;	//动作栏里百分比字体色
    public int resActionErrorImg;	//动作栏失败图片资源
    public int resActionSuccessImg;	//动作栏成功图片资源
    public int resActionOneBackImg;	//动作1背景条图片资源
    public int resStepOneIcon;	//动作1图标图片资源
    public int resActionTwoBackImg;	//动作2背景条图片资源
    public int resStepTwoIcon;	//动作2图标图片资源
    public int resActionThreeBackImg;	//动作3背景条图片资源
    public int resStepThreeIcon;	//动作3图标图片资源
    public int actCount;  // 动作数量（1~3）(如不做修改，默认为3)
    public int actType;  //动作设置（选项见EnumInstance.java中的EActType，可多选，用 | 分隔）(如不做修改，默认为3个动作全选)
    public int actDifficult = 1;  // 动作难度（0~2）(如不做修改，默认为1)
    public int photoNum;  // 照片张数（1~3）






//    public long stepWait;
}
