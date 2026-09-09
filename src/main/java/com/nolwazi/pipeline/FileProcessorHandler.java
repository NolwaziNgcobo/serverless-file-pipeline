package com.nolwazi.pipeline;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

/**
 * Lambda handler triggered when a file is uploaded to the "uploads" S3 bucket.
 *
 * Plan:
 *   1. Read the S3 event to find which file was uploaded
 *   2. Download/process the file (exact processing TBD - see README)
 *   3. Write the result to the "processed" S3 bucket
 *   4. Log progress to CloudWatch
 *
 * Each step becomes real code over the next few commits, once the AWS
 * environment (buckets, IAM role) is set up.
 */
public class FileProcessorHandler implements RequestHandler<S3Event, String> {

    @Override
    public String handleRequest(S3Event event, Context context) {
        context.getLogger().log("Received S3 event: " + event.toJson());
        // TODO: extract bucket/key from event, process file, write to output bucket
        return "OK";
    }
}
