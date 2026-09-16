package com.nolwazi.pipeline;

import org.junit.jupiter.api.Test;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileProcessorHandlerTest {
    @Test
    void summaryContainsFileNameSizeAndContentType(){
        FileProcessorHandler handler = new FileProcessorHandler();

        HeadObjectResponse fakeMetadata = HeadObjectResponse.builder().
                contentLength(1024L).
                contentType("image/png").
                build();

        String summary = handler.buildSummary("photo.png", fakeMetadata);

        assertTrue(summary.contains("photo.png"));
        assertTrue(summary.contains("1024"));
        assertTrue(summary.contains("image/png"));
    }
}
