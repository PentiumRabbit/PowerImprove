/*
 * Copyright:  Beijing BaoFeng Technology Co., Ltd. Copyright 2014-2114,  All rights reserved
 */

package com.android.base.common.message;

/**
 * 消息分发
 * 
 * @author Administrator
 *
 */
interface IMessageProcessor {

	/**
	 * 分发消息
	 * 
	 * @param message
	 */
	void sendMessage(IMessage message);
}
