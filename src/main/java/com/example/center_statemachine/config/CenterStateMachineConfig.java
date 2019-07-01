package com.example.center_statemachine.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import com.example.center_statemachine.enums.CenterEvents;
import com.example.center_statemachine.enums.CenterStates;

@Configuration
@EnableStateMachine
public class CenterStateMachineConfig extends StateMachineConfigurerAdapter<CenterStates, CenterEvents> {

	@Override
	public void configure(StateMachineStateConfigurer<CenterStates, CenterEvents> states) throws Exception {
		states.withStates()
			.initial(CenterStates.CENTER_CREATION_INPUT)
			.stateEntry(CenterStates.CENTER_CREATED, context -> context.getStateMachine().sendEvent(CenterEvents.CREATE_BATCH))
//			.stateDo(CenterStates.CENTER_CREATED, context -> System.out.println("^^^^^^CREATING CENTER^^^^^"))
			
			.stateEntry(CenterStates.BATCH_CREATED, context -> context.getStateMachine().sendEvent(CenterEvents.CONFIGURE))
//			.stateDo(CenterStates.BATCH_CREATED, context -> System.out.println("^^^^^^CREATING BATCH^^^^^"))
			
			.stateEntry(CenterStates.COURSE_CONFIGURED, context -> context.getStateMachine().sendEvent(CenterEvents.DONE))
//			.stateDo(CenterStates.COURSE_CONFIGURED, context -> System.out.println("^^^^^^CREATING COURSE^^^^^"))
			
			.end(CenterStates.END);
	}

	@Override
	public void configure(StateMachineTransitionConfigurer<CenterStates, CenterEvents> transitions) throws Exception {
		transitions.withExternal()
			.source(CenterStates.CENTER_CREATION_INPUT).target(CenterStates.CENTER_CREATED).event(CenterEvents.CREATE_ORGANIZATION)
			.action(context -> System.out.println("***********CENTER CREATED***********"))
			.and()
	        .withExternal()
	        .source(CenterStates.CENTER_CREATED).target(CenterStates.BATCH_CREATED).event(CenterEvents.CREATE_BATCH)
	        .action(context -> System.out.println("***********BATCH CREATED***********"))
	        .and()
	        .withExternal()
	        .source(CenterStates.BATCH_CREATED).target(CenterStates.COURSE_CONFIGURED).event(CenterEvents.CONFIGURE).and()
	        .withExternal()
	        .source(CenterStates.COURSE_CONFIGURED).target(CenterStates.END).event(CenterEvents.DONE);
	}

}
