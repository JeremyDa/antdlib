package com.jeremy.antdlib.controller;

import com.jeremy.core.exception.ExceptionCenter;
import com.jeremy.core.service.FirstService;
import com.jeremy.upload.UploadService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private FirstService firstService;

  @Autowired
  private UploadService uploadService;

  @PostMapping("/api/uploadFile")
  public Object uploadFile(@RequestParam("file") MultipartFile file)
      throws ExceptionCenter {
//  {
//        "id",id,
//        "mc",fileName,
//        "name",fileName,
//        "status","done",
//        "thumbUrl",fileFullUrl,
//        "url",fileFullUrl,
//        "type",file.getContentType(),
//        "size",file.getSize()
//  }

    return uploadService.saveFile(file);

  }

  @PostMapping("/api/uploadMultipleFiles")
  public List<Object> uploadMultipleFiles(HttpServletRequest request) throws ExceptionCenter {
    return uploadService.uploadMultipleFiles(request);
  }

}