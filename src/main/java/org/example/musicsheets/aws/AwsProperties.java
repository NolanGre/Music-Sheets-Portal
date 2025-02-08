package org.example.musicsheets.aws;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aws", ignoreUnknownFields = false)
@Getter
@Setter
public class AwsProperties {
    private String accessKey;
    private String secretKey;

    private S3Properties s3;

    @Getter
    @Setter
    public static class S3Properties {
        private String bucketName;
        private String region;
    }
}
