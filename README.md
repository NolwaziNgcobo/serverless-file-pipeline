# Serverless File Processing Pipeline

An AWS Lambda function (Java) triggered by S3 uploads, which processes a file
and writes the result to a separate output bucket.

Built as part of my WeThinkCode_ Cloud Computing elective proof-of-work.

## Architecture

```
[User uploads file] -> [S3: uploads bucket] -> [Lambda: FileProcessorHandler] -> [S3: processed bucket]
                                                          |
                                                          v
                                                     [CloudWatch Logs]
```

## Why this project

After completing AWS Educate's Introduction to the AWS Management Console and
Cloud 101, I wanted to build something hands-on with the core services those
courses introduced: S3, Lambda, IAM, and CloudWatch, working together as a
real (if small) pipeline rather than isolated demos.

## Project status

Built incrementally as each piece of AWS infrastructure gets set up:

- [x] Project scaffold + Lambda handler stub
- [ ] S3 buckets created (uploads + processed)
- [ ] S3 -> Lambda trigger configured
- [ ] File processing logic implemented
- [ ] IAM role scoped to least privilege
- [ ] CloudWatch logging verified
- [ ] Unit tests
- [ ] Demo video

## Tech

Java 17, Maven, AWS Lambda, Amazon S3, IAM, CloudWatch.

## Demo video

_Link will go here once the pipeline is complete._
