package org.jeecg.modules.edusport.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.edusport.entity.Athlete;
import org.jeecg.modules.edusport.entity.AthleteSelectionAthleteScore;
import org.jeecg.modules.edusport.entity.AthleteSelectionAthleteScoreDetail;
import org.jeecg.modules.edusport.entity.AthleteSelectionIndex;
import org.jeecg.modules.edusport.entity.AthleteSelectionIndexCat;
import org.jeecg.modules.edusport.entity.Coach;
import org.jeecg.modules.edusport.entity.OutlineCoach;
import org.jeecg.modules.edusport.entity.Sport;
import org.jeecg.modules.edusport.mapper.AthleteMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionAthleteScoreMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionGroupIndexMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionIndexCatMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionIndexMapper;
import org.jeecg.modules.edusport.mapper.AthleteSelectionTestMapper;
import org.jeecg.modules.edusport.mapper.CoachMapper;
import org.jeecg.modules.edusport.mapper.SportMapper;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreDetailService;
import org.jeecg.modules.edusport.service.IAthleteSelectionAthleteScoreService;
import org.jeecg.modules.edusport.service.IAthleteSelectionGroupService;
import org.jeecg.modules.edusport.service.IAthleteService;
import org.jeecg.modules.edusport.service.IOutlineCoachService;
import org.jeecg.modules.shiro.vo.DefContants;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;

 /**
 * @Description: tb_edu_outline_coach
 * @Author: jeecg-boot
 * @Date:   2020-01-04
 * @Version: V1.0
 */
@RestController
@RequestMapping("/edusport/outlineCoach")
@Slf4j
public class OutlineCoachController extends JeecgController<OutlineCoach, IOutlineCoachService> {
	@Autowired
	private IOutlineCoachService outlineCoachService;// 教练.
	@Autowired
	private IAthleteService athleteService; // 运动员.
	@Autowired
	private IAthleteSelectionGroupService athleteSelectionGroupService; // 组 .
	@Autowired
	private IAthleteSelectionAthleteScoreService athleteSelectionAthleteScoreService;
	@Autowired
	private IAthleteSelectionAthleteScoreDetailService athleteSelectionAthleteScoreDetailService;
	@Resource
	private AthleteSelectionGroupIndexMapper athleteSelectionGroupIndexMapper;
	@Resource
	private AthleteSelectionTestMapper athleteSelectionTestMapper;
	@Resource
	private AthleteMapper athleteMapper;
	@Resource
	private CoachMapper coachMapper;
	@Resource
	private SportMapper sportMapper;
	@Resource
	private AthleteSelectionAthleteScoreMapper athleteSelectionAthleteScoreMapper;
	@Autowired
	private ISysUserService sysUserService;
	@Resource
	private AthleteSelectionIndexCatMapper athleteSelectionIndexCatMapper;
	@Resource
	private AthleteSelectionIndexMapper athleteSelectionIndexMapper;
	/**
	 * 分页列表查询
	 *
	 * @param outlineCoach
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/list")
	public Result<?> queryPageList(OutlineCoach outlineCoach,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<OutlineCoach> queryWrapper = QueryGenerator.initQueryWrapper(outlineCoach, req.getParameterMap());
		Page<OutlineCoach> page = new Page<OutlineCoach>(pageNo, pageSize);
		IPage<OutlineCoach> pageList = outlineCoachService.page(page, queryWrapper);
		return Result.ok(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param outlineCoach
	 * @return
	 */
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OutlineCoach outlineCoach) {
		outlineCoachService.save(outlineCoach);
		return Result.ok("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param outlineCoach
	 * @return
	 */
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OutlineCoach outlineCoach) {
		outlineCoachService.updateById(outlineCoach);
		return Result.ok("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		outlineCoachService.removeById(id);
		return Result.ok("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.outlineCoachService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.ok("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		OutlineCoach outlineCoach = outlineCoachService.getById(id);
		if(outlineCoach==null) {
			return Result.error("未找到对应数据");
		}
		return Result.ok(outlineCoach);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param outlineCoach
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OutlineCoach outlineCoach) {
        return super.exportXls(request, outlineCoach, OutlineCoach.class, "tb_edu_outline_coach");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
//        return super.importExcel(request, response, OutlineCoach.class);
    	 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
         Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
         for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
             MultipartFile file = entity.getValue();// 获取上传文件对象
             try {
 				InputStream inputstream = file.getInputStream();
 				System.out.println();
 				Workbook book = null;
 				if (!(inputstream.markSupported())) {
 					inputstream = new PushbackInputStream(inputstream, 8);
 				}
 				book = new HSSFWorkbook(inputstream);
// 				if (POIFSFileSystem.hasPOIFSHeader(inputstream)) {
// 					book = new HSSFWorkbook(inputstream);
// 				} else if (POIXMLDocument.hasOOXMLHeader(inputstream)) {
// 					try {
// 						book = new XSSFWorkbook(OPCPackage.open(inputstream));
// 					} catch (InvalidFormatException e) {
// 						e.printStackTrace();
// 						Result.error("无效的文件！");
// 					}
// 				}
 				// 系统用户.
 				SysUser sysUser = getSystemUser(request);
 				Sheet sheet = book.getSheetAt(0);
 				// 取得当前导入行的大纲教练信息.
				OutlineCoach outlineCoach = outlineCoachService.getById("1214561679039221761");
				if(outlineCoach == null) {
					Result.error("数据信息不存在，请确认！");
				}
				
//				validatorExcel(sheet);
				// 指标类别信息.
				String indexCatName = "";
//				Sport sport = sportMapper.getSportByName(sportName);
 				for(int r = 4; r < sheet.getPhysicalNumberOfRows(); r++) {
 					Row row = sheet.getRow(r);
 					// 保存当前运动员的成绩信息.
 					Athlete athlete = athleteMapper.getAthleteByNo(row.getCell(0).getStringCellValue());// 学号.
					// 学生信息：教练、年级、出生年月、性别.
					AthleteSelectionAthleteScore athleteSelectionAthleteScore = new AthleteSelectionAthleteScore();
					athleteSelectionAthleteScore.setAthleteId(athlete.getId());// 运动员.
					athleteSelectionAthleteScore.setBirthday(athlete.getBirthDate());// 出生日期.
					athleteSelectionAthleteScore.setGender(athlete.getGender());// 性别.
					athleteSelectionAthleteScore.setGrade(athlete.getGrade());// 年级
					athleteSelectionAthleteScore.setCoachId(athlete.getMajorSportTeacherCode());// 教练员
					athleteSelectionAthleteScore.setGroupId(outlineCoach.getGroupId());// 组
					athleteSelectionAthleteScore.setTestId(outlineCoach.getOutlineId());// 大纲ID
					athleteSelectionAthleteScore.setEventCode(outlineCoach.getEventCodes());// 运动项目
					athleteSelectionAthleteScore.setTestScore(Integer.valueOf(0));// 成绩
					athleteSelectionAthleteScore.setTestGrade("");// 测试等级.
					athleteSelectionAthleteScore.setAuditState("1");// 状态
					athleteSelectionAthleteScore.setCreateTime(Calendar.getInstance().getTime());
					athleteSelectionAthleteScore.setCreateBy(sysUser.getUsername());
					athleteSelectionAthleteScore.setUpdateTime(Calendar.getInstance().getTime());
					athleteSelectionAthleteScore.setUpdateBy(sysUser.getUsername());
					athleteSelectionAthleteScoreService.save(athleteSelectionAthleteScore);
 					for(int c = 5; c < row.getPhysicalNumberOfCells(); c++) {// 从第5列开始.
 						Cell cell = row.getCell(c);
						// 指标类别.
 						indexCatName = "";
 						int rowNum = 2;// 指标类别固定行号.
 						int cellNum = c;
 						MergedRegionResult mergeRegionResult = isMergedRegion(sheet, rowNum, cellNum);
 						if(mergeRegionResult.merged) {// 合并单元格.
 							indexCatName = sheet.getRow(rowNum).getCell(mergeRegionResult.startCol).getStringCellValue();
 						} else {
 							indexCatName = sheet.getRow(rowNum).getCell(c).getStringCellValue();
 						}
 						// 指标.
 						String indexName = sheet.getRow(rowNum + 1).getCell(c).getStringCellValue(); // 指标.
 						AthleteSelectionIndex athleteSelectionIndex = athleteSelectionIndexMapper.getIndexByName(indexName, indexCatName);

 						if(athleteSelectionIndex != null) {
 							System.out.println("row = " + r + ", col = " + c + ", value = " + cell);// 当前单元格的值.
 							List<AthleteSelectionAthleteScoreDetail> athleteScoreDetailList = new ArrayList<AthleteSelectionAthleteScoreDetail>();

 							//指标信息.
 							AthleteSelectionAthleteScoreDetail athleteSelectionAthleteScoreDetail = new AthleteSelectionAthleteScoreDetail();
 							athleteSelectionAthleteScoreDetail.setAthleteScoreId(athleteSelectionAthleteScore.getId());// 成绩ID
 							athleteSelectionAthleteScoreDetail.setAthleteId(athlete.getId());// 运动员.
 							athleteSelectionAthleteScoreDetail.setTestId(outlineCoach.getOutlineId());// 大纲ID
 							athleteSelectionAthleteScoreDetail.setGroupId(outlineCoach.getGroupId());// 组
 							athleteSelectionAthleteScoreDetail.setIndexCode(athleteSelectionIndex.getL3Code());// 指标
 							athleteSelectionAthleteScoreDetail.setEventCode(outlineCoach.getEventCodes());// 运动项目
 							athleteSelectionAthleteScoreDetail.setTestValue(Double.valueOf(cell.getNumericCellValue()).toString());// 测试值
 							athleteSelectionAthleteScoreDetail.setTestScore(Integer.valueOf(0));// 测试分数
 							athleteSelectionAthleteScoreDetail.setCreateTime(Calendar.getInstance().getTime());
 							athleteSelectionAthleteScoreDetail.setCreateBy(sysUser.getUsername());
 							athleteSelectionAthleteScoreDetail.setUpdateTime(Calendar.getInstance().getTime());
 							athleteSelectionAthleteScoreDetail.setUpdateBy(sysUser.getUsername());
 							athleteScoreDetailList.add(athleteSelectionAthleteScoreDetail);
 							athleteSelectionAthleteScoreDetailService.saveBatch(athleteScoreDetailList);
 						}
 					}
 				}
 			} catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
         }
         return Result.ok("文件导入成功！" );
    }

    /**
	 * 取得当前登录用户.
	 * 
	 * @param request
	 * @return
	 */
	private SysUser getSystemUser(HttpServletRequest request) {
		String token = request.getHeader(DefContants.X_ACCESS_TOKEN);
		SysUser user = new SysUser();
		if (!oConvertUtils.isEmpty(token)) {
			String username = JwtUtil.getUsername(token);
			user = sysUserService.getUserByName(username);
		}

		return user;
	}
	
	private void validatorExcel(Sheet sheet) {
		for(int rowNum = 0; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			for(int cellNum = 0; cellNum < row.getPhysicalNumberOfCells(); cellNum++) {
				Cell cell = row.getCell(cellNum);
				// 项目判断.
				if(rowNum == 1 && cellNum == 1) {// 项目：举重.
					String sportName = row.getCell(cellNum).getStringCellValue();
					Sport sport = sportMapper.getSportByName(sportName);
					if(sport == null) {
						Result.error("导入文件模板的行：" + rowNum + "，列：" + cellNum + "的运动项目信息不存在，请确认！");
					}
				}

				// 教练判断.
				if(rowNum == 1 && cellNum == 3) {// 教练：举重.
					String coachName = row.getCell(cellNum).getStringCellValue();
					Coach coach = coachMapper.getCoachByName(coachName);
					if(coach == null) {
						Result.error("导入文件模板的行：" + rowNum + "，列：" + cellNum + "的教练信息不存在，请确认！");
					}
				}

				// 指标类别.
				if(rowNum == 2 && cellNum >= 5) {
					String indexCatName = row.getCell(cellNum).getStringCellValue();
					MergedRegionResult mergeRegionResult = isMergedRegion(sheet, rowNum, cellNum);
					if(mergeRegionResult.merged) {// 合并单元格.
						indexCatName = row.getCell(mergeRegionResult.startCol).getStringCellValue();
					} else {
						indexCatName = row.getCell(cellNum).getStringCellValue();
					}
					AthleteSelectionIndexCat athleteSelectionIndexCat = athleteSelectionIndexCatMapper.getIndexCatByName(indexCatName);
					if(athleteSelectionIndexCat == null) {
						Result.error("导入文件模板的行：" + rowNum + "，列：" + cellNum + "的指标类别信息不存在，请确认！");
					}
				}

				// 指标.
				if(rowNum == 3 && cellNum >= 5) {
					String indexCatName = "";
					MergedRegionResult mergeRegionResult = isMergedRegion(sheet, rowNum, cellNum);
					if(mergeRegionResult.merged) {// 合并单元格.
						indexCatName = sheet.getRow(rowNum-1).getCell(mergeRegionResult.startCol).getStringCellValue();
					} else {
						indexCatName = sheet.getRow(rowNum-1).getCell(cellNum).getStringCellValue();
					}
					
					String indexName = row.getCell(cellNum).getStringCellValue();// 指标.
					AthleteSelectionIndex athleteSelectionIndex = athleteSelectionIndexMapper.getIndexByName(indexName, indexCatName);
					if(athleteSelectionIndex == null) {
						Result.error("导入文件模板的行：" + rowNum + "，列：" + cellNum + "的指标信息不存在，请确认！");
					}
				}
				
				// 运动员信息.
				if(rowNum == 4) {
					String athleteNo = row.getCell(0).getStringCellValue();// 运动员学号.
					Athlete athlete = athleteMapper.getAthleteByNo(athleteNo);
					if(athlete == null) {
						Result.error("导入文件模板的行：" + rowNum + "，列：" + cellNum + "的运动员信息不存在，请确认！");
					}
				}
			}
			
		}
		
	}

	/**
	 * 判断是否是合并  单元格.
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 */
	private MergedRegionResult isMergedRegion(Sheet sheet, int row, int column) {
		int sheetMergeCount = sheet.getNumMergedRegions();
		for (int i = 0; i < sheetMergeCount; i++) {
			CellRangeAddress range = (CellRangeAddress) sheet.getMergedRegion(i);
			int firstColumn = range.getFirstColumn();
			int lastColumn = range.getLastColumn();
			int firstRow = range.getFirstRow();
			int lastRow = range.getLastRow();
			if (row >= firstRow && row <= lastRow) {
				if (column >= firstColumn && column <= lastColumn) {
					return new MergedRegionResult(true, firstRow, lastRow, firstColumn, lastColumn);
				}
			}
		}
		return new MergedRegionResult(false, 0, 0, 0, 0);
	}
}

class MergedRegionResult {
	public boolean merged;
	public int startRow;
	public int endRow;
	public int startCol;
	public int endCol;

	public MergedRegionResult(boolean merged, int startRow, int endRow, int startCol, int endCol) {
		this.merged = merged;
		this.startRow = startRow;
		this.endRow = endRow;
		this.startCol = startCol;
		this.endCol = endCol;
	}
}