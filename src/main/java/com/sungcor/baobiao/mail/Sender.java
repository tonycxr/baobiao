package com.sungcor.baobiao.mail;

public interface Sender {
    /**
     * 是否正在执行
     * @return
     */
    boolean excuting();

    /**
     * 发送接口
     *
     * @return
     */
    boolean send();

    /**
     * 发送完毕，不管成功和失败
     */
    void sendStart();

    /**
     * 发送完毕，不管成功和失败
     */
    void sendEnd();

    /**
     * 校验接口
     *
     * @return
     */
    boolean validate();

    /**
     * 检验失败回执
     */
    void validateFailure();

    /**
     * 发送成功回执
     */
    void successCallBack();

    /**
     * 发送失败回执
     */
    void failureCallBack();

    void addCallBackListener(SendCallBackListener sendCallBackListener);

    /**
     *
     * 回执监听，用于非sender回执处理
     *
     * @author wangming
     *
     */
    public interface SendCallBackListener {

        void sendStart();

        void sendEnd();

        void sendFailure();

        void sendSuccessfully();

        void validateFailure();

    }
}
