package org.jeecg.modules.oss.controller;

import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.config.oss.OSSProperties;
import org.jeecg.modules.oss.entity.OSSFile;
import org.jeecg.modules.oss.service.IOSSFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/oss/file")
public class OSSFileController {

	@Autowired
	private IOSSFileService ossFileService;
	
	@Autowired
	private OSSProperties properties;
	
	@ResponseBody
	@GetMapping("/list")
	public Result<IPage<OSSFile>> queryPageList(OSSFile file,
			@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
		Result<IPage<OSSFile>> result = new Result<>();
		QueryWrapper<OSSFile> queryWrapper = QueryGenerator.initQueryWrapper(file, req.getParameterMap());
		Page<OSSFile> page = new Page<>(pageNo, pageSize);
		IPage<OSSFile> pageList = ossFileService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

	@ResponseBody
	@PostMapping("/upload")
	public Result upload(@RequestParam("file") MultipartFile multipartFile) {
		Result result = new Result();
		try {
			ossFileService.upload(multipartFile);
			result.success("上传成功！");
		}
		catch (Exception ex) {
			log.info(ex.getMessage(), ex);
			result.error500("上传失败");
		}
		return result;
	}

	@ResponseBody
	@DeleteMapping("/delete")
	public Result delete(@RequestParam(name = "id") String id) {
		Result result = new Result();
		OSSFile file = ossFileService.getById(id);
		if (file == null) {
			result.error500("未找到对应实体");
		}
		else {
			boolean ok = ossFileService.delete(file);
			if (ok) {
				result.success("删除成功!");
			}
		}
		return result;
	}

	/**
	 * 通过id查询.
	 */
	@ResponseBody
	@GetMapping("/queryById")
	public Result<OSSFile> queryById(@RequestParam(name = "id") String id) {
		Result<OSSFile> result = new Result<>();
		OSSFile file = ossFileService.getById(id);
		if (file == null) {
			result.error500("未找到对应实体");
		}
		else {
			result.setResult(file);
			result.setSuccess(true);
		}
		return result;
	}
	
	/**
	 * 通过id查询.
	 */
	@ResponseBody
	@GetMapping("/replay")
	public Result<OSSFile> replay(@RequestParam(name = "id") String id) {
		Result<OSSFile> result = new Result<>();
		OSSFile file = ossFileService.getById(id);
		if (file == null) {
			result.error500("未找到对应实体");
		}
		else {
			// 创建OSSClient实例。
			OSS ossClient = new OSSClientBuilder().build(properties.getEndpoint(), properties.getAccessKey(), properties.getSecretKey());
			// 判断文件是否存在。doesObjectExist还有一个参数isOnlyInOSS，如果为true则忽略302重定向或镜像；如果为false，则考虑302重定向或镜像。
			boolean found = ossClient.doesObjectExist(properties.getBucketName(), file.getFileName());
			if (found) {
				// 设置URL过期时间为24小时。
				Date expiration = new Date(new Date().getTime() + 24 * 3600 * 1000);
				// 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
				URL url = ossClient.generatePresignedUrl(properties.getBucketName(), file.getFileName(), expiration);
				file.setUrl(url.toString());
			    System.out.println("getUrl>>>:" + url.toString());
			} else {
				result.error500("未找到OSS对应实体");
			}
			// 关闭OSSClient。
			ossClient.shutdown();	
			
			result.setResult(file);
			result.setSuccess(true);
		}
		return result;
	}
	

}
