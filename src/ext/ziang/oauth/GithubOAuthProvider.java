package ext.ziang.oauth;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.MediaType;
import wt.util.WTException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
     * @param code 法典
     * @param url  网址
     * @return {@link String}
     */
    public static String getAccessTokenByCodeAndUrl(String code, String url) throws WTException {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id", OAuthConfigConstant.CLIENT_ID));
        params.add(new BasicNameValuePair("redirect_uri", url));
        params.add(new BasicNameValuePair("code", code));
        params.add(new BasicNameValuePair("client_secret", OAuthConfigConstant.CLIENT_SECRET));
        String response = buildCommonRequest(OAuthConfigConstant.ACCESS_TOKEN_URL, null, false, params);
        System.out.println("response = " + response);
        if (StrUtil.isBlank(response)) {
            throw new WTException("登录失败");
        }
        JSONObject result = JSONObject.parseObject(response);
        return result.getString("access_token");
    }

    /**
     * 获取用户信息
     *
     * @param token 令 牌
     * @return {@link JSONObject}
     */
    public static JSONObject getUserInfo(String token) {
        token = "Bearer " + token;
        String response = buildCommonRequest(OAuthConfigConstant.GET_USER_INFO_URL, token, true, null);
        return JSON.parseObject(response);
    }


    /**
     * 公共发送接口
     *
     * @param path 路径
     * @return {@link String}
     */
    public static synchronized String buildCommonRequest(String path, String token, boolean isSendUserInfo, List<BasicNameValuePair> params) {
        System.out.println("GithubOAuthProvider.buildCommonRequest INTO " + LocalDateTime.now());
        String responseString = null;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            // start build 构建用户名密码
            URIBuilder builder = new URIBuilder(path);
            httpclient = HttpClients.createDefault();
            // create POST
            HttpPost httpPost = new HttpPost(builder.build());
            httpPost.setHeader(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name());
            if (isSendUserInfo) {
                httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                httpPost.setHeader(HttpHeaders.AUTHORIZATION, token);
            } else {
                httpPost.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                httpPost.setEntity(new UrlEncodedFormEntity(params));
            }
            response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            if (statusCode != 200) {
                System.out.println("接口调用失败！");
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
        System.out.println("GithubOAuthProvider.buildCommonRequest END " + LocalDateTime.now());
        return responseString;
    }
}
