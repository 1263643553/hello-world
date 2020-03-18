package com.example.wxs.serviceImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wxs.configs.Log;
import com.example.wxs.dao.UsersDao;
import com.example.wxs.entity.Users;
import com.example.wxs.mapper.UsersMapper;
import com.example.wxs.service.UsersService;
import com.example.wxs.utils.UUIDUtils;
import com.example.wxs.wxsession.entity.Wxsession;
import com.example.wxs.wxsession.service.IWxsessionService;
import com.example.wxs.wxsession.service.impl.WxsessionServiceImpl;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.hibernate.id.UUIDHexGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UsersServcieImpl implements UsersService {

    @Autowired
    private UsersDao usersDao;
    @Resource
    private UsersMapper usersMapper;
    @Autowired
    private IWxsessionService iWxsessionService;

    @Override
    public List<Users> usersList() {
        List<Users> allUsers=new ArrayList<>();
        Users users=new Users();
        users.setUserName("ceshi");
        users.setPassword("test");
        usersDao.save(users);
        allUsers.add(users);
        System.out.println(allUsers.isEmpty());
        return allUsers;
    }

    @Override
    public IPage<Users> getAllUsers(Page<Users> page) {
        return usersMapper.selectPage(page,null);
    }

    @Override
    @Log("插入人员")
    public Integer insertOne(Users users) {
        return usersMapper.insert(users);
    }

    @Override
    public List<Users> getUserIdByUsernameAndPassword(String userName) {
        QueryWrapper<Users> queryWrapper1=new QueryWrapper<>();
        queryWrapper1.like("user_name",userName);
        return usersMapper.selectList(queryWrapper1);
    }

    @Override
    public String getWxCode() {
        return null;
    }


    @Override
    public String getWxSessionKeyAndOpenId(String appId, String appSecret, String code) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
        String result="";
        UUIDUtils uuidUtils=new UUIDUtils();
        Wxsession wxsession=new Wxsession();
        try (CloseableHttpClient httpClient = createHttpClient()) {
            HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
                HttpEntity entity = httpResponse.getEntity();
                result = EntityUtils.toString(entity);
                JSONObject jsonObject=JSONObject.parseObject(result);
                JSONArray jsonArray=jsonObject.getJSONArray(result);
                EntityUtils.consume(entity);
                String[] strings=result.split(",");
                System.out.println(jsonObject.getString("openid"));
                System.out.println(result);
                System.out.println(Arrays.toString(strings));
                String thridSession=uuidUtils.toUUID(jsonObject.getString("openid")+jsonObject.getString("session_key"));
                wxsession.setThridSession(thridSession);
                iWxsessionService.insertSession(wxsession);
            }
        } catch (IOException e) {
            e.printStackTrace();
            result="获取失败";
        }
        return result;
    }


    //免验证https
    private static CloseableHttpClient createHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(null, (chain, authType) -> true)
                .build();

        SSLConnectionSocketFactory sslSf = new SSLConnectionSocketFactory(sslcontext, null, null,
                new NoopHostnameVerifier());

        return HttpClients.custom().setSSLSocketFactory(sslSf).build();
    }
}
