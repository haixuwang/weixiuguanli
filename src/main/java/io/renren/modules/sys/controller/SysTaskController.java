package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.*;
import io.renren.modules.sys.service.SysOrgService;
import io.renren.modules.sys.service.SysRecordService;
import io.renren.modules.sys.service.SysTaskService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 组织结构管理
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/sys/task")
public class SysTaskController extends AbstractController {

	@Autowired
	private SysTaskService sysTaskService;

	@Autowired
	private SysRecordService sysRecordService;

	/**
	 * 所有组织结构列表
	 */
	@PostMapping("/list")
	//@RequiresPermissions("sys:task:list")
	public R list(@RequestBody Map<String,Object> params){
		Page<SysTaskEntity> taskEntityPage = sysTaskService.queryTaskPage(params);
		return  R.ok().put("page",taskEntityPage);
	}


	@PostMapping("info")
	public R info(@RequestBody Map<String,Object> params){
		try {
			SysTaskEntity sysTaskEntity = sysTaskService.getTaskEntity(params);
			return R.ok().put("task",sysTaskEntity);
		}catch (Exception e){
			e.printStackTrace();
			return R.error(1,"获取录入信息出错!");
		}
	}

	@PostMapping("saveTask")
	public R saveTask(@RequestBody SysTask systask){
		try {

			//获取任务
			SysTaskEntity sysTaskEntity = systask.getSysTaskEntity();
			sysTaskEntity.setId(UUID.randomUUID().toString());

			sysTaskService.saveTaskEntity(sysTaskEntity);
			//获取照片
			List<TaskPicEntity> picEntityList = systask.getPicList();
			//新建记录表
			SysRecordEntity sysRecordEntity = new SysRecordEntity();
			sysRecordEntity.setId(UUID.randomUUID().toString());
			sysRecordEntity.setPid(sysTaskEntity.getId());
			sysRecordEntity.setCreuser(sysTaskEntity.getCreuserid());
			sysRecordEntity.setUser(sysTaskEntity.getCreuserid());
			sysRecordEntity.setForuser(sysTaskEntity.getAssigner());
			sysRecordEntity.setStatus(0L);
			sysRecordService.saveRecord(sysRecordEntity);

			return R.ok();
		}catch (Exception e){
			e.printStackTrace();
			return R.error(1,"保存任务出错！");
		}
	}


}
