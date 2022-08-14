package spring.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
@Log4j2
public class FileUploader {

    private OutputStream outputStream;
    private static final String DEFAULT_EXTENSION = ".png";
    private static final String EXTENSION_FORMAT = ".%s";

    @Value("${file.upload.path}")
    private String fileUploadDir;
    @Value("${file.download.url}")
    private String fileDownloadUrl;

    public String saveBase64File(String image) {
        try {
            boolean isFullDetailsBase64 = true;

            File uploadDirectory = new File(fileUploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }


            String[] imageParts = new String[2];
            if (image == null)
                return null;

            if (image.startsWith("data:")) {
                imageParts = image.split(",");
            } else {
                isFullDetailsBase64 = false;
                imageParts[1] = image;
            }

            byte[] data = DatatypeConverter.parseBase64Binary(imageParts[1]);
            String extension = isFullDetailsBase64 ? getExtension(image) : getImageType(data);
            String fileName = UUID.randomUUID().toString() + extension;

            Path path = Paths.get(fileUploadDir, fileName);
            File file = new File(path.toString());

            outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(data);
            outputStream.close();

            return fileDownloadUrl + fileName;

        } catch (Exception e) {
            log.error("Method saveBase64File : ", e);
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("Method saveBase64File : ", e);
                }
            }
        }
    }

    private String getExtension(String base64ImageString) {
        try {
            String delims = "[,]";
            String[] parts = base64ImageString.split(delims);
            String imageString = parts[1];
            byte[] imageByteArray = Base64.decode(imageString);

            InputStream is = new ByteArrayInputStream(imageByteArray);

            //Find out image type
            String mimeType = null;
            String fileExtension = null;

            mimeType = URLConnection.guessContentTypeFromStream(is); //mimeType is something like "image/jpeg"
            String delimiter = "[/]";
            String[] tokens = mimeType.split(delimiter);
            fileExtension = tokens[1];
            return String.format(EXTENSION_FORMAT, fileExtension);
        } catch (Exception e) {
            log.error("Function getExtension : " + e.getMessage());
            return DEFAULT_EXTENSION;
        }
    }


    private boolean isMatch(byte[] pattern, byte[] data) {
        if (pattern.length <= data.length) {
            for (int idx = 0; idx < pattern.length; ++idx) {
                if (pattern[idx] != data[idx])
                    return false;
            }
            return true;
        }
        return false;
    }

    private String getImageType(byte[] data) {

//        filetype    magic number(hex)
//        jpg         FF D8 FF
//        gif         47 49 46 38
//        png         89 50 4E 47 0D 0A 1A 0A
//        bmp         42 4D
//        tiff(LE)    49 49 2A 00
//        tiff(BE)    4D 4D 00 2A

        final byte[] pngPattern = new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
        final byte[] jpgPattern = new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
        final byte[] gifPattern = new byte[]{0x47, 0x49, 0x46, 0x38};
        final byte[] bmpPattern = new byte[]{0x42, 0x4D};
        final byte[] tiffLEPattern = new byte[]{0x49, 0x49, 0x2A, 0x00};
        final byte[] tiffBEPattern = new byte[]{0x4D, 0x4D, 0x00, 0x2A};

        if (isMatch(pngPattern, data))
            return ".png";

        if (isMatch(jpgPattern, data))
            return ".jpg";

        if (isMatch(gifPattern, data))
            return ".gif";

        if (isMatch(bmpPattern, data))
            return ".bmp";

        if (isMatch(tiffLEPattern, data))
            return ".tif";

        if (isMatch(tiffBEPattern, data))
            return ".tif";

        return ".png";
    }
}
