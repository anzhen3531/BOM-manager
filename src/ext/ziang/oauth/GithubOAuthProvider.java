package ext.ziang.oauth;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.StrUtil;
import ext.ziang.common.util.LoggerHelper;
import wt.util.WTException;

/**
 * GitHub OAuth 提供程序
 *
 * @author anzhen
 * @date 2024/02/06
 */
public class GithubOAuthProvider {
	/**
	 * 通过代码和 URL 获取访问令牌
	 *
	 * @param code
	 *            法典
	 * @param url
	 *            网址
	 * @return {@link String}
	 */
	public static String getAccessTokenByCodeAndUrl(String code, String url) throws WTException {
		List<BasicNameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("client_id", OAuthConfigConstant.CLIENT_ID));
		params.add(new BasicNameValuePair("redirect_uri", url));
		params.add(new BasicNameValuePair("code", code));
		params.add(new BasicNameValuePair("client_secret", OAuthConfigConstant.CLIENT_SECRET));
		String response = buildCommonRequestTOPost(OAuthConfigConstant.ACCESS_TOKEN_URL, null, false, params);
		LoggerHelper.log("response = " + response);
		if (StrUtil.isBlank(response)) {
			throw new WTException("登录失败");
		}
		JSONObject result = JSONObject.parseObject(response);
		if (result == null || result.getString("access_token") == null) {
			throw new WTException("登录失败");
		}
		return result.getString("access_token");
	}

	/**
	 * 获取用户信息
	 * ·
	 * 
	 * @param token
	 *            令 牌
	 * @return {@link JSONObject}
	 */
	public static JSONObject getUserInfo(String token) {
		token = "Bearer " + token;
		String response = buildCommonRequestTOGet(OAuthConfigConstant.GET_USER_INFO_URL, token);
		return JSON.parseObject(response);
	}

	/**
	 * 公共发送接口
	 *
	 * @param path
	 *            路径
	 * @return {@link String}
	 */
	public static synchronized String buildCommonRequestTOPost(String path, String token, boolean isSendUserInfo,
			List<BasicNameValuePair> params) {
		LoggerHelper.log("GithubOAuthProvider.buildCommonRequest INTO " + LocalDateTime.now());
		String responseString = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			// start build 构建用户名密码
			URIBuilder builder = new URIBuilder(path);
			httpclient = HttpClients.custom().setDefaultCookieStore(new BasicCookieStore()).build();
			// create POST
			HttpPost httpPost = new HttpPost(builder.build());
			httpPost.setHeader(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name());
			if (isSendUserInfo) {
				httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
				httpPost.setHeader(HttpHeaders.AUTHORIZATION, token);
			} else {
				httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
				httpPost.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
				httpPost.setEntity(new UrlEncodedFormEntity(params));
			}
			response = httpclient.execute(httpPost);
			LoggerHelper.log("response = " + response);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			if (statusCode != 200) {
				LoggerHelper.log("接口调用失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LoggerHelper.log("GithubOAuthProvider.buildCommonRequest END " + LocalDateTime.now());
		return responseString;
	}

	/**
	 * 构建公共请求 to get
	 *
	 * @param path
	 *            路径
	 * @param token
	 *            令 牌
	 * @return {@link String}
	 */
	public static synchronized String buildCommonRequestTOGet(String path, String token) {
		LoggerHelper.log("GithubOAuthProvider.buildCommonRequestTOGet INTO " + LocalDateTime.now());
		String responseString = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			// start build 构建用户名密码
			URIBuilder builder = new URIBuilder(path);
			httpclient = HttpClients.createDefault();
			// create POST
			HttpGet http = new HttpGet(builder.build());
			http.setHeader(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name());
			http.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
			http.setHeader(HttpHeaders.AUTHORIZATION, token);
			response = httpclient.execute(http);
			LoggerHelper.log("response = " + response);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
			LoggerHelper.log("responseString = " + responseString);
			if (statusCode != 200) {
				LoggerHelper.log("接口调用失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LoggerHelper.log("GithubOAuthProvider.buildCommonRequestTOGet END " + LocalDateTime.now());
		return responseString;
	}
}
