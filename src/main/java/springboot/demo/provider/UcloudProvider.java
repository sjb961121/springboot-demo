package springboot.demo.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.BucketAuthorization;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileBucketLocalAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import springboot.demo.exception.FileException;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Component
public class UcloudProvider {
    @Value("${ucloud.ufile.publickey}")
    private String publickey;
    @Value("${ucloud.ufile.privatekey}")
    private String privatekey;
    ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(
            publickey, privatekey);
    ObjectConfig config = new ObjectConfig("cn-bj", "ufileos.com");
    @Value("${ucloud.ufile.bucketName}")
    private String bucketName;
    @Value("${ucloud.ufile.region}")
    private String region;
    @Value("${ucloud.ufile.suffix}")
    private String suffix;


    public String upload(InputStream fileStream,String mimeType,String fileName){
        String generatedFileName="";
        String[] filePaths = fileName.split("\\.");
        if (filePaths.length > 1) {
            generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        }
        try {
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(
                    publickey, privatekey);
            ObjectConfig config = new ObjectConfig(region, suffix);
            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generatedFileName)
                    .toBucket("sunwen")
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(long bytesWritten, long contentLength) {

                        }
                    })
                    .execute();
                    if (response!=null && response.getRetCode()==0){
                        String url = UfileClient.object(objectAuthorization, config)
                                .getDownloadUrlFromPrivateBucket(generatedFileName, bucketName, 24*60*60*365*10)
                                .createUrl();
                        return url;
                    }
                    else {
                        throw new FileException("文件上传失败",2009);
                    }
        } catch (UfileClientException e) {
            throw new FileException("文件上传失败",2009);
        } catch (UfileServerException e) {
            throw new FileException("文件上传失败",2009);
        }

    }
}
