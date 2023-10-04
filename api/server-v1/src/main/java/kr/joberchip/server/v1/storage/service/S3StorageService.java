package kr.joberchip.server.v1.storage.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import kr.joberchip.server.v1._errors.ErrorMessage;
import kr.joberchip.server.v1._errors.exceptions.ApiClientException;
import kr.joberchip.server.v1._errors.exceptions.ApiServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class S3StorageService {
  private final AmazonS3Client amazonS3Client;
  private final String bucket;
  private final String location;

  public S3StorageService(
      @Autowired AmazonS3Client amazonS3Client,
      @Value("${cloud.aws.s3.bucket}") String bucket,
      @Value("${cloud.aws.region.static}") String location) {
    this.amazonS3Client = amazonS3Client;
    this.bucket = bucket;
    this.location = location;
  }

  public String store(MultipartFile file) {
    String ext =
        Objects.requireNonNull(StringUtils.getFilenameExtension(file.getOriginalFilename()));

    if (!"PNG".equalsIgnoreCase(ext)
        && !"JPEG".equalsIgnoreCase(ext)
        && !"MP4".equalsIgnoreCase(ext)) {
      throw new ApiClientException(ErrorMessage.FORBIDDEN);
    }

    String s3key = generateRandomFileNameWithExtension(ext);

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());

    try {
      amazonS3Client.putObject(bucket, s3key, file.getInputStream(), metadata);
    } catch (IOException e) {
      throw new ApiClientException("File 읽기 오류.");
    } catch (SdkClientException sce) {
      throw new ApiServerException("S3 서버 연결 오류.");
    }

    return generateUrl(s3key);
  }

  public void delete(String url) {
    String s3key = decodeUrl(url);
    try {
      amazonS3Client.deleteObject(bucket, s3key);
    } catch (SdkClientException sce) {
      throw new ApiServerException("S3 서버 연결 오류.");
    }
  }

  private String generateUrl(String s3key) {
    return "https://"
        + bucket
        + ".s3."
        + location
        + ".amazonaws.com/"
        + URLEncoder.encode(s3key, StandardCharsets.UTF_8);
  }

  private String decodeUrl(String encodedUrl) {
    int lastIndexOfSlash = encodedUrl.lastIndexOf('/');
    if (lastIndexOfSlash == -1) {
      throw new ApiServerException("Invalid URL.");
    }
    return URLDecoder.decode(encodedUrl.substring(lastIndexOfSlash + 1), StandardCharsets.UTF_8);
  }

  private String generateRandomFileNameWithExtension(String ext) {
    return UUID.randomUUID() + "." + ext;
  }
}
