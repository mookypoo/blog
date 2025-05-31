// package com.mooky.pet_diary.global.util;

// public String createPresignedUrl(String bucketName, String keyName, Map<String, String> metadata) {
//     try (S3Presigner presigner = S3Presigner.create()) {

//         PutObjectRequest objectRequest = PutObjectRequest.builder()
//                 .bucket(bucketName)
//                 .key(keyName)
//                 .metadata(metadata)
//                 .build();

//         PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
//                 .signatureDuration(Duration.ofMinutes(10))  // The URL expires in 10 minutes.
//                 .putObjectRequest(objectRequest)
//                 .build();


//         PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
//         String myURL = presignedRequest.url().toString();
//         logger.info("Presigned URL to upload a file to: [{}]", myURL);
//         logger.info("HTTP method: [{}]", presignedRequest.httpRequest().method());

//         return presignedRequest.url().toExternalForm();
//     }
// }

// @Service public class S3Service {

//     private static final List<String> ALLOWED_TYPES = Arrays.asList(
//             "image/jpeg", "image/jpg", "image/png", "image/webp");

//     public S3Service(S3Presigner s3Presigner, S3Client s3Client) {
//         this.s3Presigner = s3Presigner;
//         this.s3Client = s3Client;
//     }

//     public UploadUrlResponse generatePresignedUrl(UploadUrlRequest request) {
//         // Validate file type
//         if (!ALLOWED_TYPES.contains(request.getFileType())) {
//             throw new IllegalArgumentException("Invalid file type: " + request.getFileType());
//         }

//         // Generate unique file name
//         String fileExtension = getFileExtension(request.getFileName());
//         String uniqueKey = String.format("pets/%d/%s.%s",
//                 request.getPetId(),
//                 UUID.randomUUID().toString(),
//                 fileExtension);

//         // Create presigned URL request
//         Map<String, String> metadata = Map.of(
//                 "pet-id", request.getPetId().toString(),
//                 "original-name", request.getFileName(),
//                 "uploaded-at", Instant.now().toString());

//         PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                 .bucket(bucketName)
//                 .key(uniqueKey)
//                 .contentType(request.getFileType())
//                 .metadata(metadata)
//                 .build();

//         PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
//                 .signatureDuration(Duration.ofMinutes(15))
//                 .putObjectRequest(putObjectRequest)
//                 .build();

//         PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

//         return new UploadUrlResponse(
//                 presignedRequest.url().toString(),
//                 uniqueKey,
//                 bucketName);
//     }

//     private String getFileExtension(String fileName) {
//         return fileName.substring(fileName.lastIndexOf('.') + 1);
//     }

//     public boolean objectExists(String key) {
//         try {
//             s3Client.headObject(HeadObjectRequest.builder()
//                     .bucket(bucketName)
//                     .key(key)
//                     .build());
//             return true;
//         } catch (NoSuchKeyException e) {
//             return false;
//         }
//     }
// }