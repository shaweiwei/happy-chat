import java.io.Serializable;
import java.util.Vector;

public class Message implements Serializable {

	private static final long serialVersionUID = -3831507106408529855L;

	/**
	 * 用户在线对象集
	 */
	public Vector userOnLine;

	/**
	 * 聊天信息集
	 */

	public Vector chat;

	/**
	 * 被踢者姓名
	 */

	public String ti;

	/**
	 * 公告
	 */

	public String serverMessage;

}