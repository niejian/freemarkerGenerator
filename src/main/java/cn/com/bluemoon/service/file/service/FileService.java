package cn.com.bluemoon.service.file.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {
	 /**
     * 上传文档
     * @param file
     * @throws Exception
     */
    public void uploadDoc(MultipartFile file, String path) throws Exception;

}
