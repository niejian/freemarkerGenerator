package cn.com.bluemoon.service.file.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.com.bluemoon.service.file.service.FileService;

@Service(value="fileService")
public class FileServiceImpl implements FileService {

	@Override
	public void uploadDoc(MultipartFile file, String path) throws Exception {

        File realFile = new File(path);
        if (!realFile.getParentFile().exists()) {
            if (!realFile.getParentFile().mkdirs()) {
                throw new Exception("创建文件上传目录失败");
            }
        }
		 //转存文件
        file.transferTo(realFile);

	}

}
