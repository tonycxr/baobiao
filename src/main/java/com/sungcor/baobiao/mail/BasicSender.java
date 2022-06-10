package com.sungcor.baobiao.mail;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class BasicSender implements Sender{
    private boolean excuting = false;

    @Override
    public boolean excuting() {
        return excuting;
    }

    public void setExcuting(boolean excuting){
        this.excuting = excuting;
    }

    private final List<SendCallBackListener> snedSendCallBackListeners = new CopyOnWriteArrayList<SendCallBackListener>();

    @Override
    final public void sendStart() {
        for(SendCallBackListener snedSendCallBackListener : snedSendCallBackListeners){
            snedSendCallBackListener.sendStart();
        }
    }

    @Override
    final public void sendEnd() {
        for(SendCallBackListener snedSendCallBackListener : snedSendCallBackListeners){
            snedSendCallBackListener.sendEnd();
        }
    }

    @Override
    final public void validateFailure() {
        for(SendCallBackListener snedSendCallBackListener : snedSendCallBackListeners){
            snedSendCallBackListener.validateFailure();
        }
    }

    @Override
    final public void successCallBack() {
        for(SendCallBackListener snedSendCallBackListener : snedSendCallBackListeners){
            snedSendCallBackListener.sendSuccessfully();
        }
    }

    @Override
    final public void failureCallBack() {
        for(SendCallBackListener snedSendCallBackListener : snedSendCallBackListeners){
            snedSendCallBackListener.sendFailure();
        }
    }

    @Override
    final public void addCallBackListener(SendCallBackListener sendCallBackListener) {
        snedSendCallBackListeners.add(sendCallBackListener);
    }


}
