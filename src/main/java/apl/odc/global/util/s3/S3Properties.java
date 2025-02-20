package apl.odc.global.util.s3;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.cloud.aws.s3")
public record S3Properties(
        String bucketName,
        String dataKey
) {
}