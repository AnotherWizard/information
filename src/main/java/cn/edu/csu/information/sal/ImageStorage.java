package cn.edu.csu.information.sal;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * @author liuchengsheng
 */
@Component
public class ImageStorage {
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;
    @Value("${qiniu.domainName}")
    private String domainName;

    public String storage(InputStream inputStream) {

        Configuration cfg = new Configuration(Zone.zone0());

        UploadManager uploadManager = new UploadManager(cfg);



        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(inputStream, null, upToken, null, null);

            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

            return String.format("%s/%s", domainName, putRet.key);
        } catch (QiniuException ex) {
            Response r = ex.response;

        }

        return null;

    }

}
