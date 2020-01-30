package com.example.mychatappnetty.util;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.HashMap;

@Component
public class QRCodeUtil {


    private HashMap hints;

    private int width;

    private int height;

    private String format;


    private QRCodeUtil(){

        width=300;      		//image width
        height=300;     		//image height
        format="png";    	//image format
        //String content="something";     //content

        /**
         * Define QR code parameters
         */
        hints=new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");    //CHARACTER ENCODING UTF-8
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);  //ErrorCorrectionLevel : MIDDLE
        hints.put(EncodeHintType.MARGIN, 2);    //margin set to 2 px

    }

    /**
     * Generate QR CODE,
     * @param filePath target output file path
     * @param content encoding content in the qr code
     */
   /* public void createQRCode(String filePath, String content) {
        *//**
         * Generate QR code
         *//*
        try {
            BitMatrix bitMatrix=new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);
            Path file=new File(filePath).toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    /**
     * Generate QR Code and Return a MultipartFile for uploading to the FastDFS
     * @param content
     * @return
     */
    public MultipartFile createQRCode(String content,String multipartFileName){

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {

            QRCodeWriter writer = new QRCodeWriter();

            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            // convert the bitMatrix to BufferedImage
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            //Written the BufferedImage to the ByteArrayOutputStream as a png file
            ImageIO.write(bufferedImage, format, os);

            /**
             * A base64 Image String should have header as follows:
             *  data:image/png;base64,
             *  When the QR code need to be displayed, should be add by Javascript
             */
            // resultImage = new String("data:image/png;base64," + Base64.encode(os.toByteArray()));

            String base64Data =  Base64.getEncoder().encodeToString(os.toByteArray());

            return FileUtil.base64ToMultipart(base64Data,multipartFileName,"image/png");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Read content from the file path of the QR CODE
     * @param filePath
     * @return
     */
    public String getContentFromQRCode(String filePath) {
        MultiFormatReader formatReader=new MultiFormatReader();
        File file=new File(filePath);
        BufferedImage image;
        try {
            image = ImageIO.read(file);
            BinaryBitmap binaryBitmap=new BinaryBitmap(new HybridBinarizer
                    (new BufferedImageLuminanceSource(image)));
            HashMap hints=new HashMap();
            hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
            Result result=formatReader.decode(binaryBitmap,hints);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
