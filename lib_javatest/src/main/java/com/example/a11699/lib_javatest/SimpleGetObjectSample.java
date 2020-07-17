package com.example.a11699.lib_javatest;

/**
 * Create time 2020/6/17
 * Create Yu
 * description:
 */


import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Create time 2020/6/17
 * Create Yu
 * description:java实现的上传文件致阿里云仓库oss
 */
public class SimpleGetObjectSample {

    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAI4GAh1Kg5KvDrsH8fwcEp";
    private static String accessKeySecret = "ETz7ZHRVo0Ab4STOU5mgTGXOi9n0Y1";

    private static String bucketName = "cangkuone";
    private static String key = "lucklive/onerr.txt";

    public   void maicccn( ) throws IOException {
        /*
         * Constructs a client instance with your account for accessing OSS
         */

        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {

            /**
             * Note that there are two ways of uploading an object to your bucket, the one
             * by specifying an input stream as content source, the other by specifying a file.
             */

            /*
             * Upload an object to your bucket from an input stream
             */
            System.out.println("Uploading a new object to OSS from an input stream\n");
            String content = "Thank you for using Aliyun Object Storage Service";

            client.putObject(bucketName, key, new ByteArrayInputStream(content.getBytes()));

            /*
             * Upload an object to your bucket from a file
             */
            System.out.println("Uploading a new object to OSS from a file\n");
            client.putObject(new PutObjectRequest(bucketName, key, createSampleFile()));

            /*
             * Download an object from your bucket
             */
            System.out.println("Downloading an object");
            OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
            System.out.println("Content-Type: "  + object.getObjectMetadata().getContentType());
            displayTextInputStream(object.getObjectContent());

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
    }

    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("oss-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abc\n");
        writer.write("0123456789011234567890\n");
        writer.close();

        return file;
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

