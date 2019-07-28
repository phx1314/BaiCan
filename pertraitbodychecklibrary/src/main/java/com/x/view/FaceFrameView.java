package com.x.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.facefr.controller.Controller;

import static android.graphics.Paint.Style.STROKE;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class FaceFrameView extends View {


    //构造方法1
    public FaceFrameView(Context context) {
        super(context);
    }
    //构造方法2.这个构造方法必须有,否则自定义的view在xml中添加后不会显示
    public FaceFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int horizontalCenter  =  getWidth() / 2;
        float strokeWidth       = getHeight();//为了让除了中间的圆之外，其他地方都是空白，则画笔宽度设置为整个View的高度
        float lf_width        = (getWidth()/40);//圆的左右两侧的留白宽度
        float top_width       = (getHeight()/15.15f);//圆的上方留白宽度
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setStyle(STROKE); //空心效果
        paint.setStrokeWidth(strokeWidth); //线宽
        paint.setColor(Controller.getInstance().mStyle.resContentBgColor);
        canvas.drawCircle( horizontalCenter, horizontalCenter + top_width  , horizontalCenter + (strokeWidth/2)-lf_width, paint);
    }
}


