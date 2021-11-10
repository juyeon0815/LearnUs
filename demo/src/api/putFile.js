import AWS from "aws-sdk";

export default {
  async upload(fileList) {
    console.log("파일 저장!!");
    const albumBucketName = 'ovl-bucket'; // S3의 버킷 이름
    const region = 'ap-northeast-2'; // 서울
    const accessKeyId = 'AKIA36BVZDJBOE5XZDOC'; // IAM에서 생성한 사용자의 accessKeyId
    const secretAccessKey = 'Yn8hl1C8mxlT+cdQrw80PCF8ZbpM0mqq9JUCf6yL'; // IAM에서 생성한 사용자의 secretAccessKey
    
    let urlList = [];
    AWS.config.update({
      region,
      accessKeyId,
      secretAccessKey
    }); 
    
    for (let i=0;i<fileList.length;i++) {
      const upload = new AWS.S3.ManagedUpload({
        params: {
          Bucket: albumBucketName,
          Key: fileList[i].name,
          Body: fileList[i],
          ACL: "public-read"
        }
      });
      
      const promise = upload.promise();
    
      promise.then(
        function(data) {
          // console.log("Successfully uploaded photo : ", data);
          urlList.push(data.Location);
          console.log("location : ", data.Location);
          // return data.Location;
        },
        function(err) {
          return console.log("There was an error uploading your photo: ", err.message);
        }
      );
  
    }
    console.log("urlList : ", urlList);
  }
}