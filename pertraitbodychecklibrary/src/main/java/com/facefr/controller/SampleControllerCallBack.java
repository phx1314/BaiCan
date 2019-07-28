package com.facefr.controller;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public abstract class SampleControllerCallBack implements ControllerCallBack {

    @Override
    public abstract void onAllStepCompleteCallback(boolean isSuccess, String dataPage);

    @Override
    public abstract void onBack();

//    public void onXXXXXXX(){};
}
