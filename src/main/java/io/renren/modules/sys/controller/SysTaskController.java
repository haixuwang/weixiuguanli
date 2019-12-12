package io.renren.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.R;
import io.renren.modules.sys.entity.SysOrgEntity;
import io.renren.modules.sys.entity.SysTaskEntity;
import io.renren.modules.sys.service.SysOrgService;
import io.renren.modules.sys.service.SysTaskService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

	/**
	 * 所有组织结构列表
	 */
	@PostMapping("/list")
	//@RequiresPermissions("sys:task:list")
	public R list(@RequestBody Map<String,Object> params){
		try {
			Page<SysTaskEntity> taskEntityPage = sysTaskService.queryTaskPage(params);
			return R.ok().put("list",taskEntityPage);
		}catch (Exception e){
			e.printStackTrace();
			return R.error("查询任务出错！");
		}
	}

}
