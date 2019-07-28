package com.facefr.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.facefr.activity.PictureActivity;
import com.facefr.server.in.AuthActCallBack;
import com.facefr.server.in.CollectInfoInstance;
import com.facefr.server.in.EnumInstance;
import com.facefr.server.in.PictureActCallBack;
import com.pertraitbodycheck.activity.R;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Controller implements PictureActCallBack,AuthActCallBack{

    public ControllerCallBack mCallBack;

    private static Controller mInstance = null;

    private Context mContext;
    public StyleModel mStyle;

    private Controller(Context ctx) {
        this.mContext = ctx;
    }

    public Controller setCallBack(ControllerCallBack cbk) {
        this.mCallBack = cbk;
        return mInstance;
    }


    //初始化
    public static Controller getInstance(Context ctx) {
        synchronized (Controller.class) {
            mInstance = new Controller(ctx);
        }
        return mInstance;
    }

    public static Controller getInstance() {
        return mInstance;
    }


    //开始活体
    public void show(StyleModel style){
        if (style!=null){
            mStyle = style;
        }else if (mStyle==null){
            mStyle = new StyleModel();
        }

        if (mStyle.resBackImg==0){
            mStyle.resBackImg = R.drawable.btn_back;
        }
        if (mStyle.resTxtPercentageColor == 0){
            mStyle.resTxtPercentageColor = Color.WHITE;
        }
        if (mStyle.resTxtNormalColor == 0){
            mStyle.resTxtNormalColor = Color.WHITE;
        }
        if (mStyle.resActionSuccessImg==0){
            mStyle.resActionSuccessImg = R.drawable.lamp_success;
        }
        if (mStyle.resActionErrorImg==0){
            mStyle.resActionErrorImg = R.drawable.lamp_fail;
        }
//        if (mStyle.stepWait == 0){
//            mStyle.stepWait = 1;
//        }
        if (mStyle.resActionBackImg==0){
            mStyle.resActionBackImg = R.drawable.base_map;
        }
        if (mStyle.resActionOneBackImg==0){
            mStyle.resActionOneBackImg = R.drawable.cell;
        }
        if (mStyle.resActionTwoBackImg==0){
            mStyle.resActionTwoBackImg = R.drawable.cell;
        }
        if (mStyle.resActionThreeBackImg==0){
            mStyle.resActionThreeBackImg = R.drawable.cell;
        }
        if (mStyle.resStepOneIcon==0){
            mStyle.resStepOneIcon = R.drawable.l1;
        }
        if (mStyle.resStepTwoIcon==0){
            mStyle.resStepTwoIcon = R.drawable.l2;
        }
        if (mStyle.resStepThreeIcon==0){
            mStyle.resStepThreeIcon = R.drawable.l3;
        }
        if (mStyle.actCount != 0 && mStyle.actCount > 0 && mStyle.actCount < 4){
            CollectInfoInstance.getInstance(mContext).setActCount(mStyle.actCount);//设置动作次数
        }else {
            CollectInfoInstance.getInstance(mContext).setActCount(EnumInstance.EActCount.three);//默认3次
        }
        if (mStyle.actType!=2 && mStyle.actType!=4 && mStyle.actType!=6 &&
                mStyle.actType!=8 && mStyle.actType!=10 && mStyle.actType!=12 && mStyle.actType!=14){
            CollectInfoInstance.getInstance(mContext).setActType(EnumInstance.EActType.act_head|EnumInstance.EActType.act_mouth|EnumInstance.EActType.act_shake);//默认动作有三个动作
        }else {
            CollectInfoInstance.getInstance(mContext).setActType(mStyle.actType);//设置动作类型
        }

        if (mStyle.actDifficult >=0 && mStyle.actDifficult <= 2){
            CollectInfoInstance.getInstance(mContext).setActDifficult(mStyle.actDifficult);//设置难度
        }else {
            CollectInfoInstance.getInstance(mContext).setActDifficult(EnumInstance.EActDiffi.normal);//默认普通
        }
        if (mStyle.photoNum != 0 && mStyle.photoNum >0
                && mStyle.photoNum < 4){
            CollectInfoInstance.getInstance(mContext).setPictureNum(mStyle.photoNum);//设置张数
        }else {
            CollectInfoInstance.getInstance(mContext).setPictureNum(EnumInstance.EPicNum.one);//默认1张
        }
        if (mStyle.resContentBgColor == 0){
            mStyle.resContentBgColor = Color.parseColor("#181818");
        }
        if (mStyle.resTxtSuccessColor == 0){
            mStyle.resTxtSuccessColor = Color.WHITE;
        }
        if (mStyle.resTxtErrorColor == 0){
            mStyle.resTxtErrorColor = Color.RED;
        }



        Intent intent = new Intent();
        intent.setClass(mContext.getApplicationContext(), PictureActivity.class);
        this.mContext.startActivity(intent);
    }


    @Override
    public void onBack(){
        mCallBack.onBack();
    }
    @Override
    public void onAllStepCompleteCallback(boolean isSuccess,String dataPage){
        mCallBack.onAllStepCompleteCallback(isSuccess,dataPage);
    }

}


