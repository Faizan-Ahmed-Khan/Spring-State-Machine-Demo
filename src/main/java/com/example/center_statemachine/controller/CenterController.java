package com.example.center_statemachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.center_statemachine.enums.CenterEvents;
import com.example.center_statemachine.enums.CenterStates;

@RestController
public class CenterController {

	@Autowired
	private StateMachine<CenterStates, CenterEvents> sm;

	@PostMapping("/center")
	public String createCenter() {
		System.out.println("--------------------------------------------");
		System.out.println("Inside Controller");
		sm.start();
		sm.sendEvent(CenterEvents.CREATE_ORGANIZATION);
		String op = sm.getState().toString();
		sm.stop();
		return op;
	}

}
