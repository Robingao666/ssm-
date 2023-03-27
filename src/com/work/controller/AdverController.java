package com.work.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.work.common.utils.BaseUtil;
import com.work.common.utils.JsonUtil;
import com.work.pojo.Adver;
import com.work.pojo.User;
import com.work.service.AdverService;

@Controller
@RequestMapping("/server/adver")
public class AdverController extends BaseUtil{

	@Autowired
	private AdverService adverService;
	
	//ȥ����б�
	@RequestMapping("/goAdverList")
	public ModelAndView goAdverList(ModelAndView mv){
		mv.setViewName("server/adverManage/adverList");
		return mv;
	}
	
	//��ȡ����б�
	@RequestMapping("/getAdverList")
	public void getAdverList(HttpServletResponse response,Adver adver,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 10;
		}
		int totalCount = adverService.getAdverListCount(adver);
		List<Adver> list = adverService.getAdverList(adver,(page-1) * limit, limit);
		output(response,JsonUtil.buildJsonByTotalCount(list, totalCount));
	}
	
	//ȥ��ӹ��
	@RequestMapping("/goAddAdver")
	public ModelAndView goAddAdver(ModelAndView mv){
		mv.setViewName("server/adverManage/addAdver");
		return mv;
	}
	
	//��ӹ��
	@RequestMapping("/addAdver")
	public void addAdver(HttpServletRequest request,HttpServletResponse response,Adver adver){
		User serverUser = (User) request.getSession().getAttribute("serverUser");
		adver.setCreateTime(new Date());
		adver.setUserId(serverUser.getUserId());
		adverService.addAdver(adver);
		output(response,JsonUtil.buildFalseJson(0, "��ӳɹ���"));
	}
	
	//ȥ�༭���
	@RequestMapping("/goUpdateAdver")
	public ModelAndView goUpdateAdver(ModelAndView mv,Integer id){
		Adver adver = adverService.getAdverById(id);
		mv.addObject("adver", adver);
		mv.setViewName("server/adverManage/updateAdver");
		return mv;
	}
	
	//�༭���
	@RequestMapping("/updateAdver")
	public void updateAdver(HttpServletResponse response,Adver adver){
		adverService.updateAdver(adver);
		output(response,JsonUtil.buildFalseJson(0, "�༭�ɹ���"));
	}

	//ɾ�����
	@RequestMapping("/deleteAdver")
	public void deleteAdver(HttpServletResponse response,Integer id){
		adverService.deleteAdverById(id);
		output(response,JsonUtil.buildFalseJson(0, "ɾ���ɹ���"));
	}

	
}
