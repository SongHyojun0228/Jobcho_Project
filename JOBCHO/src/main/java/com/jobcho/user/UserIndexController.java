package com.jobcho.user;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jobcho.alarm.AlarmRepository;
import com.jobcho.alarm.AlarmService;
import com.jobcho.alarm.Alarms;
import com.jobcho.cs.CS;
import com.jobcho.cs.CSService;
import com.jobcho.cs.CsChatroomService;
import com.jobcho.member.MemberService;
import com.jobcho.workspace.Workspaces;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserIndexController {

	private final UserService userService;
	private final MemberService memberService;
	private final AlarmRepository alarmRepository;
	private final AlarmService alarmService;
	private final CsChatroomService csChatroomService;
	private final CSService csService;

	// 🌿 메인화면 페이지 GET
	@GetMapping("/index")
	public String getIndex(Principal principal, Model model) {
		if (principal == null) {
			return "user/login";
		}

		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		model.addAttribute("user", user);

		List<Workspaces> workspaces = this.memberService.findWorkspacesByUserUserId(user.getUserId());
		model.addAttribute("workspaces", workspaces);

		Map<Integer, Integer> alarmCountMap = alarmService.getWorkspaceAlarmCountMap(user.getUserId());
		model.addAttribute("alarmCountMap", alarmCountMap);

		if (user.getIsAdmin() == 1) {
			List<CS> allCsChatrooms = this.csChatroomService.findAllCsChatroom();
			model.addAttribute("allCsChatrooms", allCsChatrooms);
		}

		return "user/index";
	}

	@GetMapping("/alarm/count")
	@ResponseBody
	public Integer getAlarmCount(Principal principal) {
		if (principal == null)
			return 0;

		Optional<Users> _user = this.userService.getUser(principal.getName());
		Users user = _user.get();
		List<Alarms> alarms = this.alarmRepository.findByUserIdAndIsNotRead(user.getUserId());

		return alarms.size();
	}

	// 🌿 메인화면의 워크스페이스 알람 수 GET
	@GetMapping("/alarm/workspace-count")
	@ResponseBody
	public Map<Integer, Integer> getWorkspaceAlarmCounts(Principal principal) {
		if (principal == null)
			return Map.of();

		Optional<Users> _user = userService.getUser(principal.getName());
		if (_user.isEmpty())
			return Map.of();

		Users user = _user.get();
		return this.alarmService.getWorkspaceAlarmCountMap(user.getUserId());
	}

	// 🌿 메인화면의 새로운 고객문의 메세지 수 GET
	@GetMapping("/csChat/count")
	@ResponseBody
	public Map<Integer, Long> getCsChatCount(Principal principal) {
		Users user = userService.getUser(principal.getName()).orElseThrow();
		List<Object[]> resultList = csChatroomService.countUnreadByChatroom(user.getUserId());

		Map<Integer, Long> countMap = new HashMap<>();
		for (Object[] result : resultList) {
			Integer chatroomId = (Integer) result[0];
			Long count = (Long) result[1];
			countMap.put(chatroomId, count);
		}

		return countMap;
	}

	// 🌿 메인화면의 마지막 메시지 내용 GET
	@GetMapping("/csChat/last")
	@ResponseBody
	public List<Map<String, Object>> getLastMessages(Principal principal) {
		List<CS> lastMessages = csService.findLatestMessagesPerChatroom();
		List<Map<String, Object>> result = new ArrayList<>();

		
		// java.lang.NullPointerException: Cannot invoke "com.jobcho.cs.CsChatroom.getCsChatroomId()" because the return value of "com.jobcho.cs.CS.getCsChatroom()" is null

		// 채팅방아이디, 마지막 메시지 내용, 보낸 시각을 반환
		for (CS cs : lastMessages) {
			Map<String, Object> map = new HashMap<>();
			map.put("chatroomId", cs.getCsChatroom().getCsChatroomId());
			map.put("content", cs.getContent());
			map.put("sentDate", cs.getSentDate());
			result.add(map);
		}
		return result;
	}

}
