package com.jobcho.workspace_domain;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class DomainRedirectController {

	private final WorkspaceDomainRepository workspaceDomainRepository;

	@GetMapping("/{workspaceDomain}")
	public String redirectToWorkspace(@PathVariable("workspaceDomain") String workspaceDomain) {
		System.out.println("workspaceDomain : " + workspaceDomain);
		
		Optional<WorkspaceDomains> workspaceDomainOpt = workspaceDomainRepository
				.findByWorkspaceDomain(workspaceDomain);

		if (workspaceDomainOpt.isPresent()) {
			String redirectUrl = workspaceDomainOpt.get().getRedirectWorkspaceDomain();
			if (redirectUrl == null || redirectUrl.isEmpty()) {
				return "error/404";
			}

			return "redirect:" + redirectUrl;

		} else {
			return "error/404";
		}
	}
}
