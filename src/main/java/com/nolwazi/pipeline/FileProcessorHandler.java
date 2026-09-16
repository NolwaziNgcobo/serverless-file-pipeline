package com.nolwazi.pipeline;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class FileProcessorHandler implements RequestHandler<S3Event, String> {

    private static final String PROCESSED_BUCKET = "nolwazi-pipeline-processed";

    private S3Client s3Client;

    //It only builds the real AWS client the first time it's actually needed
    private S3Client getS3Client(){
        if( s3Client == null){
            s3Client = S3Client.builder().build();
        }
        return s3Client;
    }

    @Override
    public String handleRequest(S3Event event, Context context) {
        var logger = context.getLogger();

        for (S3Event.S3EventNotificationRecord record : event.getRecords()) {
            String bucket = record.getS3().getBucket().getName();
            String key = record.getS3().getObject().getKey();

            logger.log("Processing uploaded file: " + key + " from bucket: " + bucket);

            try {
                HeadObjectResponse metadata = s3Client.headObject(
                        HeadObjectRequest.builder().bucket(bucket).key(key).build()
                );

                String summary = buildSummary(key, metadata);
                writeSummaryToProcessedBucket(key, summary);

                logger.log("Successfully processed and wrote summary for: " + key);
            } catch (Exception e) {
                logger.log("ERROR processing " + key + ": " + e.getMessage());
                throw new RuntimeException("Failed to process file: " + key, e);
            }
        }

        return "OK";
    }

    String buildSummary(String key, HeadObjectResponse metadata) {
        return "File Processing Summary\n"
                + "------------------------\n"
                + "File: " + key + "\n"
                + "Size: " + metadata.contentLength() + " bytes\n"
                + "Content-Type: " + metadata.contentType() + "\n"
                + "Processed at: " + Instant.now() + "\n";
    }

    private void writeSummaryToProcessedBucket(String originalKey, String summary) {
        String summaryKey = originalKey + "-summary.txt";

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(PROCESSED_BUCKET)
                        .key(summaryKey)
                        .contentType("text/plain")
                        .build(),
                RequestBody.fromString(summary, StandardCharsets.UTF_8)
        );
    }
}
