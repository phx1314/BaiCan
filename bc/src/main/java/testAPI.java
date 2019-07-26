/**
 * @Title: testAPI.java
 * @Package china.sd.ching.test
 * @Description: TODO(用一句话描述该文件做什么)
 * @author xm
 * @date 2017年10月9日 下午5:30:04
 * @version V1.0
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

//import china.sd.ching.utils.EncryptionHMAC;

/**
 * @ClassName: testAPI
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author: xm
 * @date 2017年10月9日 下午5:30:04
 *
 */
public class testAPI {

	private static String encoding = "UTF-8";
	private static String algorithm = "HmacSHA256";
	private static String projectId = "1111563517";
	private static String projectSecret = "95439b0863c241c63a861b87d1e647b7";

	public static void main(String[] args) {
		try {
			//个人实名认证-请求
			requestPerson();

			// String code = "445575";// 接收到的短信验证码
			// String serviceId = "8002d845-acde-4113-a1f0-bd6ee19683b2";
			//个人实名认证-验证
			//verifyPerson(code,serviceId);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/***
	 * 个人实名认证-请求
	 * @throws Exception
	 */
	public static void requestPerson() throws Exception {
		String signature = null;
		String resultJSON = null;
		String urlPath = null;
		// 设置运营商三要素请求参数
		String personApply = setPersonJSONStr();
//		signature = EncryptionHMAC.getHMACHexString(personApply, projectSecret, algorithm, encoding);
		System.out.println("个人实名认证-请求开始..." +signature);
		// 运营商三要素认证请求地址（测试环境）
		urlPath = "http://smlrealname.tsign.cn:8080/realname/rest/external/person/telecomAuth";
		//模拟请求
		resultJSON = sendPost(urlPath, signature, personApply);
		System.out.println("个人实名认证-请求完成：" + resultJSON);
		/* 至此，已完成个人运营商三要素认证请求 */
	}


	/***
	 * 设置运营商三要素请求参数
	 *
	 * @return
	 */
	public static String setPersonJSONStr() throws JSONException {
		JSONObject obj = new JSONObject();
//		obj.put("mobile", "16456755452");
//		obj.put("name", "何名");
//		obj.put("idno", "421123199302127687");

		obj.put("mobile", "真实手机号");
		obj.put("name", "真实姓名");
		obj.put("idno", "真实身份证");

		return obj.toString();
	}


	/***
	 * 模拟发送POST请求
	 *
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static String sendPost(String urlPath, String signature, String jsonStr) throws Exception {
		String result = null;
		// 建立连接
		URL url = new URL(urlPath);
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		// 设置Headers参数
		httpConn.setDoOutput(true); // 需要输出
		httpConn.setDoInput(true); // 需要输入
		httpConn.setUseCaches(false); // 不允许缓存
		httpConn.setRequestMethod("POST"); // 设置POST方式连接
		// 设置Headers属性
		for (Entry<String, String> entry : getHeaders(signature, jsonStr).entrySet()) {
			httpConn.setRequestProperty(entry.getKey(), entry.getValue());
		}

		// 连接会话
		httpConn.connect();

		// 建立输入流，向指向的URL传入参数
		DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
		// 设置请求参数
		dos.write(jsonStr.toString().getBytes("UTF-8"));
		System.out.println("ceshi+++++::::" + jsonStr);
		dos.flush();
		dos.close();
		// 获得响应状态
		int resultCode = httpConn.getResponseCode();
		if (HttpURLConnection.HTTP_OK == resultCode) {
			StringBuffer sb = new StringBuffer();
			String readLine = new String();
			BufferedReader responseReader = new BufferedReader(
					new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			while ((readLine = responseReader.readLine()) != null) {
				sb.append(readLine);
			}
			// System.out.println(httpConn.get.getRequestURI());
			responseReader.close();
			result = sb.toString();
		}
		return result;
	}

	/***
	 * 设置Headers报头信息
	 *
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getHeaders(String signature, String jsonStr) throws Exception {
		Map<String, String> headersMap = new LinkedHashMap<String, String>();
//		headersMap.put("X-timevale-project-id", projectId);
//		headersMap.put("X-timevale-signature", signature); // 请求参数和projectSecret参数通过HmacSHA256加密的16进制字符串
//		headersMap.put("signature-algorithm", algorithm);
//		headersMap.put("Content-Type", "application/json");
//		headersMap.put("Charset", encoding);


		headersMap.put("X-timevale-project-id", "1111563517");
		headersMap.put("X-timevale-signature", "97fbe84b6ad804ec1f0d2b2cbae66bba42efddae7137d7196975b31912294313"); // 请求参数和projectSecret参数通过HmacSHA256加密的16进制字符串
		headersMap.put("signature-algorithm", "HmacSHA256");
		headersMap.put("Content-Type", "application/json");
		headersMap.put("Charset", "UTF-8");

		return headersMap;
	}
}
