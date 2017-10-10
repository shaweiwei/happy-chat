
import java.io.Serializable;

/**
 * <p>
 * Title: HappyChat聊天系统登录程序
 * </p>
 * <p>
 * Description: 聊天内容对象
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Filename: Chat.java
 * </p>
 * 
 * @author *****
 * @version 1.0
 */
public class Chat implements Serializable {

	private static final long serialVersionUID = 4058485121419391969L;
	/**
	 * 发言人用户名
	 */
	public String chatUser;
	/**
	 * 聊天内容
	 */
	public String chatMessage;
	/**
	 * 接受对象用户名
	 */
	public String chatToUser;
	/**
	 * 聊天语气
	 */
	public String emote;
	/**
	 * 是否私聊
	 */
	public boolean whisper;
}