package cn.com.bluemoon.manager.file;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.com.bluemoon.service.file.service.FileService;
import cn.com.bluemoon.service.handler.FileConverHandler;
import cn.com.bluemoon.util.FileExtConstant;
import cn.com.bluemoon.util.SystemResourceLocator;


@Controller
@RequestMapping("/file")
public class FileController {
	private static Logger logger = LoggerFactory.getLogger(FileConverHandler.class);
	@Autowired
    private FileService fileService;
	@Autowired
	private FileConverHandler fileConverHandler;
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
		
		//文档上传到服务器的路径
		String BASE_PATH = request.getSession().getServletContext().getRealPath("") + "/upload/";
		
        Map<String, Object> result = new HashMap<>();
        try {
        	String fileName = file.getOriginalFilename();
        	String path = BASE_PATH + fileName;
        	fileService.uploadDoc(file, path);
        	//TODO
        	
        	String docPath = BASE_PATH + fileName;
        	String pdfPath = BASE_PATH + fileName.substring(0, fileName.lastIndexOf(".")) + "." + FileExtConstant.FILETYPE_PDF;
        	this.converFile(docPath, pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
	/**
	 *  转换文件
	 * @param docPath 上传的文件全路径 xxxxx/xxx/xx.doc
	 * @param pdfPath pdf的存放全路径 路径+文件名
	 */
    private void converFile(String docPath,  String pdfPath) {

       // fileConverHandler = new FileConverHandler(); // (FileConverHandler) SystemResourceLocator.getBean("fileConverHandler", FileConverHandler.class);
        //转换pdf
        if (!FileExtConstant.FILETYPE_PDF.equalsIgnoreCase(FilenameUtils.getExtension(docPath))) {
            try {
                //docFileInfo.setPdfPath("upload" + FileUtils.getFileUriGeneratedPart(StringUtils.getUUIDString(), FileExtConstant.FILETYPE_PDF));
                fileConverHandler.office2PDF(docPath, pdfPath);
            } catch (IOException e) {
                logger.error("转换pdf发生异常", e);
            }
        }

    }
}
