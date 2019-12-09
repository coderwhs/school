package org.jeecg.config.oss.aliyun;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.SimplifiedObjectMeta;

public class testAliOss {

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
		String accessKeyId = "LTAI4FcwqkMJVQ8xNHueaPHS";
		String accessKeySecret = "aiKP244ESFtKo9DQlGygtl6gjUcwC4";
		String bucketName = "springboot-oss";
//		String objectName = "video/起跑1.mp4";
		String objectName = "video/刘国梁2.png";
		
//	    type: aliyun
//	    endpoint: oss-cn-shanghai.aliyuncs.com
//	    accessKey: LTAI4FcwqkMJVQ8xNHueaPHS
//	    secretKey: aiKP244ESFtKo9DQlGygtl6gjUcwC4
//	    bucketName: springboot-oss

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		// ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
		// OSSObject ossObject = ossClient.getObject(bucketName, objectName);

		// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
//		ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File("刘国梁3.png"));

		// 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果为false，则考虑302重定向或镜像。
		boolean found = ossClient.doesObjectExist(bucketName, objectName);
		
		if (found) {
			// 设置URL过期时间为24小时。
			Date expiration = new Date(new Date().getTime() + 24 * 3600 * 1000);
			// 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
			URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
		    System.out.println("getUrl>>>:" + url.toString());
		}

		// 关闭OSSClient。
		ossClient.shutdown();		
		
		/** 
		// 读取文件内容。
		System.out.println("Object content:");
		BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
		while (true) {
		    String line = reader.readLine();
		    if (line == null) break;

		    System.out.println("\n" + line);
		}
		// 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
		reader.close();

		// 关闭OSSClient。
		ossClient.shutdown();
		**/
		
	}

	
	
    private static void displayTextInputStream(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            System.out.println("\t" + line);
        }
        System.out.println();

        reader.close();
    }
    
}
