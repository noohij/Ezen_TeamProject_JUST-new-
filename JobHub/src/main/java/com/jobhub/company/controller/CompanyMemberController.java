package com.jobhub.company.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jobhub.company.dto.CompanyMemberDto;
import com.jobhub.company.service.CompanyMemberService;
import com.jobhub.company.util.SearchPersonPaging;
import com.jobhub.personal.dto.PersonalMemberDto;
import com.jobhub.review.util.CompanyMemberPaging;

@Controller
public class CompanyMemberController {

	private static final Logger log 
		= LoggerFactory.getLogger(CompanyMemberController.class);

	@Autowired
	private CompanyMemberService companyMemberService;

	@RequestMapping(value = "/company/login.do", method = RequestMethod.GET)
	public String login(HttpSession session, Model model) {
		log.info("Welcome CompanyMemberController login!");

		return "company/auth/CompanyLoginForm";
	}

	// 로그인 클릭 시 이동
	@RequestMapping(value = "/company/loginCtr.do", method = RequestMethod.POST)
	public String loginCtr(String comCode, String comPwd, HttpSession session, Model model) {

		log.info("Welcome CompanyMemberController loginCtr! " + comCode + ", " + comPwd);

		CompanyMemberDto companyMemberDto
			= companyMemberService.companyMemberExist(comCode, comPwd);

		String viewUrl = "";
		if (companyMemberDto != null) {
			
			int permission = companyMemberDto.getComPermission();

			if (permission < 6) {
				viewUrl = "company/auth/emailAuthFail";
			} else {
				session.setAttribute("companyMemberDto", companyMemberDto);
				session.setAttribute("permission", permission);
			
				viewUrl = "company/myPage/CompanyMyPage";
			}
		} else {
			viewUrl = "personal/auth/LoginFail";
		}

		return viewUrl;
	}
	
		@RequestMapping(value = "/company/showCompanyInfo.do", method = RequestMethod.GET)
		public String showCompanyInfo(int comNo, HttpSession session, Model model) {

			log.info("Welcome CompanyMemberController showCompanyInfo! comNo" + comNo);

			CompanyMemberDto companyMemberDto
				= companyMemberService.showCompanyInfo(comNo);


				session.setAttribute("companyMemberDto", companyMemberDto);

			return "./company/myPage/CompanyMyPage";
		}

	// 로그아웃
	@RequestMapping(value = "/company/logout.do", method = RequestMethod.GET)
	public String logout(HttpSession session, Model model) {

		log.info("Welcome MemberController logout!");

		session.invalidate(); // 세션 종료

		return "redirect:/home.do";
	}

	@RequestMapping(value = "/company/add.do", method = RequestMethod.GET)
	public String MemberAdd(Model model) {

		log.debug("Welcome CompanyMemberController companyMemberAdd!");

		return "/company/auth/JoinCompany";
	}

	@RequestMapping(value = "/company/addCtr.do", method = RequestMethod.POST)
	public String memberAddCtr(CompanyMemberDto companyMemberDto, Model model) {
		log.debug("Welcome CompanyMemberController companyMemberAddCtr!" + companyMemberDto);

		try {
			companyMemberService.companyInsertOne(companyMemberDto);
			System.out.println(companyMemberDto);
		} catch (Exception e) {
			System.out.println("오류 처리할거 있음 한다");
			e.printStackTrace();
		}
		return "redirect:/company/login.do";
	}

	@RequestMapping(value = "/company/searchPerson.do", method = RequestMethod.GET)
	public String memberAddCtr() {
		log.debug("Welcome CompanyMemberController searchPerson!");

		return "company/search/SearchPerson";
	}

	//아이디찾기 화면으로 이동
	@RequestMapping(value = "/company/findId.do", method = RequestMethod.GET)
	public String companyFindId(Model model) {

		log.debug("Welcome CompanyMemberController personalFindId!");

		return "/company/auth/CompanyFindId";
	}
		
	//아이디 결과
	@RequestMapping(value = "/company/findIdCtr.do", method = RequestMethod.POST)
	public String companyFindIdCtr(HttpSession session, CompanyMemberDto companyMemberDto, Model model) {

		log.debug("Welcome CompanyMemberController companyFindIdCtr!");

		try {
			companyMemberDto = companyMemberService.companyMemberSearchId(companyMemberDto);
		    model.addAttribute("companyMemberDto", companyMemberDto);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/company/auth/CompanyResultId";
	}
		
	//비밀번호찾기 화면으로 이동
	@RequestMapping(value = "/company/findPwd.do", method = RequestMethod.GET)
	public String companyFindPwd(Model model) {

		log.debug("Welcome CompanyMemberController companyFindPwd!");

		return "/company/auth/CompanyFindPwd";
	}
		
	//비밀번호 결과
	@RequestMapping(value = "/company/findPwdCtr.do", method = RequestMethod.POST)
	public String companyFindPwdCtr(HttpSession session, CompanyMemberDto companyMemberDto, Model model) {

		log.debug("Welcome CompanyMemberController companyFindPwdCtr!");

		try {
			companyMemberDto = companyMemberService.companyMemberSearchPwd(companyMemberDto);
		    
		    model.addAttribute("companyMemberDto", companyMemberDto);
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/company/auth/CompanyResultPwd";
	}
		
	@RequestMapping(value = "/company/personalInfoList.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String memberList(@RequestParam(defaultValue = "1") int curPage
			, String talentScore, String careerScore, String educationScore,
			String licenseScore, String search, String searchText
			, Model model, HttpSession session) {
		// log4j
//		log.info("Welcome personalInfoController list!: {}");
		CompanyMemberDto companyMemberDto = (CompanyMemberDto)session.getAttribute("companyMemberDto");
//		System.out.println("회원 DTO : " + companyMemberDto);
		int comNo = companyMemberDto.getComNo();
		
		log.info("기업 번호" + comNo + "인재 점수" + talentScore + "경력" 
				+ careerScore + "학력" + educationScore + "자격증"
				+ licenseScore + search + searchText);

		Map<String, Object> countMap = new HashMap<String, Object>();
		countMap.put("comNo", comNo);
		countMap.put("talentScore", talentScore);
		countMap.put("careerScore", careerScore);
		countMap.put("educationScore", educationScore);
		countMap.put("licenseScore", licenseScore);
		countMap.put("search", search);
		countMap.put("searchText", searchText);
		
		int totalCount = companyMemberService.personalSearchSelectTotalCount(countMap);
		System.out.println("totalCount : " + totalCount);
		SearchPersonPaging searchPersonPaging = new SearchPersonPaging(totalCount, curPage);
		
		int start = searchPersonPaging.getPageBegin();
		int end = searchPersonPaging.getPageEnd();
		
		

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", start);
		map.put("end", end);

		map.put("comNo", comNo);
		map.put("talentScore", talentScore);
		map.put("careerScore", careerScore);
		map.put("educationScore", educationScore);
		map.put("licenseScore", licenseScore);
		map.put("search", search);
//	    searchText = " %" + searchText + "%";
		map.put("searchText", searchText);

		List<PersonalMemberDto> personalInfoList = companyMemberService.personalInfoList(map);
		
		HashMap<String, Object> pagingMap = new HashMap<>(); 
		pagingMap.put("totalCount", totalCount);
		pagingMap.put("searchPersonPaging", searchPersonPaging);
		
		model.addAttribute("personalInfoList", personalInfoList);
		model.addAttribute("pagingMap", pagingMap);
		model.addAttribute("search", search);
		model.addAttribute("searchText", searchText);
		
		return "company/search/SearchPerson";
	}


	@RequestMapping(value = "/company/companyInfoUpdate.do", method = RequestMethod.GET)
	public String companyInfoUpdate(CompanyMemberDto companyMemberDto, Model model) {
		log.info("Welecom CompanyInfoUpdate! companyMemberDto" + companyMemberDto);

		model.addAttribute("companyMemberDtoUpdate", companyMemberDto);

		return "company/myPage/CompanyInfoUpdate";
	}


	@RequestMapping(value = "/company/CompanyInfoUpdateCtr.do", method = RequestMethod.POST)
	public String CompanyInfoUpdateCtr(CompanyMemberDto companyMemberDto, HttpSession session, Model model) {
		log.info("Welecome companyInfoUpdateCtr" + companyMemberDto);


		try {
			companyMemberService.companyInfoUpdateOne(companyMemberDto);
			
			// 담아둔 personalMemberDto의 바뀐 정보를 세션에 다시 덮어쓰기
//			session.setAttribute("companyMemberDto", companyMemberDto);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return "redirect:./showCompanyInfo.do?comNo=" +  companyMemberDto.getComNo();
	}

	@RequestMapping(value = "/company/setPrefer.do", method = RequestMethod.GET)
	public String setPrefer(int comNo, HttpSession session, Model model) {
		log.debug("Welcome CompanyMemberController setPrefer!");

		CompanyMemberDto companyMemberDto = companyMemberService.companyMemberSelectOne(comNo);

		// 담아둔 personalMemberDto의 바뀐 정보를 세션에 다시 덮어쓰기
		session.setAttribute("companyPrefer", companyMemberDto);

		return "company/myPage/showPrefer";
	}

	@RequestMapping(value = "/company/preferUpdate.do", method = RequestMethod.GET)
	public String preferUpdate(HttpSession session, Model model) {
		log.info("Welecom CompanyMemberController preferUpdate");

		return "company/myPage/preferUpdate";
	}

	@RequestMapping(value = "/company/preferUpdateCtr.do", method = RequestMethod.POST)
	public String preferUpdateCtr(String prefer, int comNo, HttpSession session, Model model) {
		log.info("Welecom CompanyMemberController preferUpdateCtr! 정보:" + prefer);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prefer", prefer);
		map.put("comNo", comNo);

		companyMemberService.preferUpdateOne(map);

		return "company/myPage/CompanyMyPage";
	}
	
	@RequestMapping(value = "/company/registerEmail.do", method = RequestMethod.GET)
	public String emailConfirm(CompanyMemberDto companyMemberDto) {
		System.out.println(companyMemberDto);
		
		companyMemberService.companyUpdatePermission(companyMemberDto);
		
		return "/company/auth/emailAuthSuccess";
	}
	
}
