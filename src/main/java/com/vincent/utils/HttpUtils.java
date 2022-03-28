package com.vincent.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>在这里说明当前类/接口/枚举的业务用途</p>
 *
 * @author VincentHo
 * @version 1.0.0
 * <p>
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2022-03-17    VincentHo       v1.0.0        create
 * @date 2022-03-17
 */
@Slf4j
public class HttpUtils {

    /**
     * <p>调用连接并获取头部信息</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param httpUrl
     * @param headers
     * @return java.lang.String
     */
    public static Map<String, String> connectAndGetHeaders(String httpUrl, Map<String, String> headers){
        //链接
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        Map<String, String> responseHeaders = new HashMap<>();
        try {
            //创建连接
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(false);
            // 设置请求头
            for(String key : headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }
            //设置请求方式
            connection.setRequestMethod("GET");
            //设置连接超时时间
            connection.setReadTimeout(15000);
            //开始连接
            connection.connect();

            log.info("调用完成，结果码：resposneCode={}", connection.getResponseCode());

            // 获取headers
            Map<String, List<String>> headerFields = connection.getHeaderFields();
            for(String headerKey : headerFields.keySet()) {
                List<String> values = headerFields.get(headerKey);
                responseHeaders.put(headerKey, values.stream().collect(Collectors.joining(",")));
            }
        } catch (Exception e) {
            log.error("连接异常", e);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("字节流关闭异常", e);
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("输入流关闭异常", e);
                }
            }
            //关闭远程连接
            connection.disconnect();
        }
        return responseHeaders;
    }

    /**
     * <p>get请求</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param urlStr
     * @param headers
     * @return java.lang.String
     */
    public static String doGet(String urlStr, Map<String, String> headers) {
        String result = null;
        // 创建Get请求
        HttpGet httpGet = new HttpGet(urlStr);
        setHeaders(httpGet, headers);
        try(CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = httpClient.execute(httpGet)) {
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity);
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * <p>get请求</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param urlStr
     * @param headers
     * @param resultClass
     * @return T
     */
    public static<T> T doGet(String urlStr, Map<String, String> headers, Class<T> resultClass) {
        String resultJson = doGet(urlStr, headers);
        return jsonToObject(resultJson, resultClass);
    }

    /**
     * <p>get请求获取集合结果</p>
     * @author VincentHo
     * @date 2022/3/18
     * @param urlStr
     * @param headers
     * @param resultClass
     * @return java.util.List<T>
     */
    public static<T> List<T> doGetList(String urlStr, Map<String, String> headers, Class<T> resultClass) {
        List<T> result = null;

        String resultJson = doGet(urlStr, headers);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, resultClass);
        try {
            result = objectMapper.readValue(resultJson, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * <p>get请求</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param urlStr
     * @param headers
     * @param params
     * @return java.lang.String
     */
    public static String doGet(String urlStr, Map<String, String> headers, Map<String, Object> params) {
        urlStr = setGetParams(urlStr, params);
        return doGet(urlStr, headers);
    }

    /**
     * <p>get请求</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param urlStr
     * @param headers
     * @param params
     * @return java.lang.String
     */
    public static<T> T doGet(String urlStr, Map<String, String> headers, Map<String, Object> params, Class<T> resultClass) {
        urlStr = setGetParams(urlStr, params);
        return doGet(urlStr, headers, resultClass);
    }

    /**
     * <p>get请求获取集合结果</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param urlStr
     * @param headers
     * @param params
     * @return java.lang.String
     */
    public static<T> List<T> doGetList(String urlStr, Map<String, String> headers, Map<String, Object> params, Class<T> resultClass) {
        urlStr = setGetParams(urlStr, params);
        return doGetList(urlStr, headers, resultClass);
    }

    /**
     * <p>post调用</p>
     * @author VincentHo
     * @date 2022/3/26
     * @param httpPost
     * @return java.lang.String
     */
    private static String callPost(HttpPost httpPost) {

        String result = null;

        try(CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = httpClient.execute(httpPost)) {

            log.info("开始post调用，入参：{}", EntityUtils.toString(httpPost.getEntity()));

            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();

            if (responseEntity != null) {
                result = EntityUtils.toString(responseEntity);
            } else {
                result = "";
            }
            log.info("post调用结束，结果：{}", result);

        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;

    }

    /**
     * <p>post请求</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param urlStr
     * @param headers
     * @param params
     * @return java.lang.String
     */
    public static String doPost(String urlStr, Map<String, String> headers, String params) {

        HttpPost httpPost = new HttpPost(urlStr);
        setHeaders(httpPost, headers);
        setPostParams(httpPost, params);

        return callPost(httpPost);

    }

    /**
     * <p>post请求</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param urlStr
     * @param headers
     * @param params
     * @return java.lang.String
     */
    public static String doPost(String urlStr, Map<String, String> headers, Object params) {

        String paramsJson = objectToJson(params);

        return doPost(urlStr, headers, paramsJson);

    }

    /**
     * <p>post请求</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param urlStr
     * @param headers
     * @param params
     * @param resultClass
     * @return T
     */
    public static<T> T doPost(String urlStr, Map<String, String> headers, Object params, Class<T> resultClass) {
        T result = null;
        String resultJson = doPost(urlStr, headers, params);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.readValue(resultJson, resultClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * <p>post请求</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param urlStr
     * @param headers
     * @param params
     * @param resultClass
     * @return T
     */
    public static<T> T doPost(String urlStr, Map<String, String> headers, String params, Class<T> resultClass) {
        String resultJson = doPost(urlStr, headers, params);
        return jsonToObject(resultJson, resultClass);
    }

    /**
     * <p>表单方式post请求</p>
     * @author VincentHo
     * @date 2022/3/26
     * @param urlStr
     * @param headers
     * @param params 
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public static Map<String, Object> postUrlencoded(String urlStr, Map<String, String> headers, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(urlStr);
        ArrayList<BasicNameValuePair> paramList = new ArrayList<>();
        params.forEach((key, value) -> paramList.add(new BasicNameValuePair(key, value)));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            if(CollectionUtils.isEmpty(headers)) {
                headers = new HashMap<>();
            }
            headers.put("Content-Type", "application/x-www-form-urlencoded");
            setHeaders(httpPost, headers);
            String json = callPost(httpPost);
            ObjectMapper objectMapper = new ObjectMapper();
            HashMap result = objectMapper.readValue(json, HashMap.class);
            return result;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <p>post请求获取集合结果</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param urlStr
     * @param headers
     * @param params
     * @param resultClass
     * @return T
     */
    public static<T> List<T> doPostList(String urlStr, Map<String, String> headers, Object params, Class<T> resultClass) {
        List<T> result = null;
        String resultJson = doPost(urlStr, headers, params);
        ObjectMapper objectMapper = new ObjectMapper();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, resultClass);
        try {
            result = objectMapper.readValue(resultJson, javaType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * <p>get请求设置参数</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param urlStr
     * @param params
     * @return java.lang.String
     */
    private static String setGetParams(String urlStr, Map<String, Object> params) {
        if(!CollectionUtils.isEmpty(params)) {
            StringBuilder paramStr = new StringBuilder("?");
            params.forEach((key, value) -> paramStr.append(key + "=" + value));
            urlStr += paramStr.toString();
        }
        return urlStr;
    }

    /**
     * <p>post请求设置参数</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param params
     */
    private static String objectToJson(Object params) {
        String jsonString = null;
        if(!ObjectUtils.isEmpty(params)) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                jsonString = objectMapper.writeValueAsString(params);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return jsonString;
    }

    /**
     * <p>json转对象</p>
     * @author VincentHo
     * @date 2022/3/26
     * @param json
     * @param targetClass
     * @return T
     */
    public static<T> T jsonToObject(String json, Class<T> targetClass) {
        T result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            result = objectMapper.readValue(json, targetClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * <p>post请求设置参数</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param httpPost
     * @param paramsJson
     */
    private static void setPostParams(HttpPost httpPost, String paramsJson) {
        if(!ObjectUtils.isEmpty(paramsJson)) {
            StringEntity entity = new StringEntity(paramsJson, "UTF-8");
            httpPost.setEntity(entity);
        }
    }

    /**
     * <p>设置请求头</p>
     * @author VincentHo
     * @date 2022/3/17
     * @param requestBase
     * @param headers
     */
    private static void setHeaders(HttpRequestBase requestBase, Map<String, String> headers) {
        if(!CollectionUtils.isEmpty(headers)) {
            headers.forEach((key, value) -> requestBase.setHeader(key, value));
        }
    }

}
