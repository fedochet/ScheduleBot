package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.schedulebot.commands.UserCommand;
import org.anstreth.schedulebot.commands.UserCommandParser;
import org.anstreth.schedulebot.model.UserRoute;
import org.anstreth.schedulebot.response.BotResponse;
import org.anstreth.schedulebot.response.PossibleReplies;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerbotcommandshandler.SchedulerBotCommandsHandler;
import org.anstreth.schedulebot.schedulerbotcommandshandler.request.ScheduleRequest;
import org.anstreth.schedulebot.schedulerrepository.UserGroupRepository;
import org.anstreth.schedulebot.schedulerrepository.UserRouteRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerBotHomeTest {

    @InjectMocks
    private SchedulerBotHome schedulerBotHome;
    @Mock
    private UserCommandParser parser;
    @Mock
    private UserRouteRepository routeRepository;
    @Mock
    private SchedulerBotCommandsHandler commandsHandler;
    @Mock
    private UserGroupRepository userGroupRepository;

    @Test
    public void if_user_command_parsed_as_MENU_then_his_route_is_changed_to_menu_and_menu_response_returned() {
        long userId = 1;
        String command = "command";
        UserRequest userRequest = new UserRequest(userId, command);
        doReturn(UserCommand.MENU).when(parser).parse(command);
        BotResponse menuResponse = new BotResponse("What do you want to do?", PossibleReplies.MENU_REPLIES);

        assertThat(schedulerBotHome.handleRequest(userRequest), is(menuResponse));

        then(routeRepository).should().save(userId, UserRoute.MENU);
    }

    @Test
    public void if_user_command_parsed_as_scheduler_command_then_his_request_is_passed_to_commandsHandler() {
        long userId = 1;
        int groupId = 2;
        String command = "command";
        UserCommand parsedCommand = UserCommand.TODAY;
        UserRequest userRequest = new UserRequest(userId, command);
        ScheduleRequest expectedScheduleRequest = new ScheduleRequest(groupId, parsedCommand);
        List<String> messagesFromHandler = Collections.singletonList("response");
        BotResponse commandHandledResponse = new BotResponse(messagesFromHandler, PossibleReplies.WITH_GROUP_REPLIES);
        doReturn(groupId).when(userGroupRepository).get(userId);
        doReturn(parsedCommand).when(parser).parse(command);
        doReturn(messagesFromHandler).when(commandsHandler).handleRequest(expectedScheduleRequest);

        assertThat(schedulerBotHome.handleRequest(userRequest), is(commandHandledResponse));
    }
}