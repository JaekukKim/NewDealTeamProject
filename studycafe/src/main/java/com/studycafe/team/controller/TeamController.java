package com.studycafe.team.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studycafe.member.entity.MemberEntity;
import com.studycafe.member.service.MemberService;
import com.studycafe.team.entity.TeamEntity;
import com.studycafe.team.service.TeamService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TeamController {

	@Autowired
	private TeamService teamService;

	@Autowired
	private MemberService memberService;

	@GetMapping("/myteam/{teamNumber}")
	public String myTeam(@PathVariable("teamNumber") long teamNumber, Model model) {
		TeamEntity teamEntity = new TeamEntity();
		teamEntity.setTeamNumber(teamNumber);
		List<MemberEntity> member = memberService.getMyTeamMember(teamEntity);
		log.info("MEMBER : {}", member);
		TeamEntity team = teamService.getMyTeam(teamNumber);
		log.info("team : {}", team);
		model.addAttribute("teamMember", member);
		model.addAttribute("team", team);

		return "/team/myTeam";

	}

	@GetMapping("/team/teamNameRegis")
	public String teamNameRegistPage() {
		return "/team/teamNameRegis";
	}

	@GetMapping("/team/teamMemberRegis")
	public String teamMemberRegistPage() {
		return "/team/teamMemberRegis";
	}

	@PostMapping("/team/nameCheck")
	@ResponseBody
	public boolean teamNameCheck(@RequestParam("teamName") String name) {
		boolean nameCheck = teamService.findTeamByName(name);
		return nameCheck;
	}

	@PostMapping("/team/getoutteam")
	@ResponseBody
	public boolean getOutTeam(@RequestParam("teamNumber") long teamNumber, @RequestParam("username") String username) {

		try {
			MemberEntity member = memberService.getOutTeam(username, teamNumber);
			log.info("member : {}", member);
			if (member == null) {
				return false;
			}

			member.setTeamNumber(null);

			memberService.getOutTeamSave(member);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
