package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.ruzapi.response.Groups;
import org.anstreth.ruzapi.ruzapirepository.GroupsRepository;
import org.anstreth.schedulebot.exceptions.NoGroupForUserException;
import org.anstreth.schedulebot.exceptions.NoGroupFoundException;
import org.anstreth.schedulebot.exceptions.NoSuchUserException;
import org.anstreth.schedulebot.model.User;
import org.anstreth.schedulebot.schedulebotservice.request.UserRequest;
import org.anstreth.schedulebot.schedulerrepository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserGroupManagerTest {

    @InjectMocks
    private UserGroupManager userGroupManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageWithRepliesSender messageSender;

    @Mock
    private GroupsRepository groupRepository;

    private final List<String> repliesToAdd = Arrays.asList("Today", "Tomorrow", "Week");

    private final long userId = 1L;
    private final String requestMessage = "request message";
    private final UserRequest userRequest = new UserRequest(userId, requestMessage);
    private final String noGroupFoundMessage = "No group by name 'request message' is found! Try again.";
    private final User userWithoutGroup = new User(userId, User.NO_GROUP_SPECIFIED);

    @Test
    public void ifUserRepositoryReturnsNullGetGroupOfUserReturnsEmptyOptional() throws Exception {
        when(userRepository.getUserById(userId)).thenReturn(null);

        assertThat(userGroupManager.getGroupIdOfUser(userId), is(Optional.empty()));
    }

    @Test
    public void ifUserRepositoryReturnsUserWithoutGroupThenGetGroupOfUserReturnsEmptyOptional() throws Exception {
        when(userRepository.getUserById(userId)).thenReturn(userWithoutGroup);

        assertThat(userGroupManager.getGroupIdOfUser(userId), is(Optional.empty()));
    }

    @Test
    public void ifUserRepositoryReturnsUserWithGroupItsIdIsReturned() throws Exception {
        int groupId = 2;
        when(userRepository.getUserById(userId)).thenReturn(new User(userId, groupId));

        assertThat(userGroupManager.getGroupIdOfUser(userId), is(Optional.of(groupId)));
    }

    @Test
    public void ifUserRepositoryReturnsNullNewUserWithoutGroupIsCreatedAndUserIsAskedToSendHisGroup() throws Exception {
        String noGroupSpecifiedMessage = "Send me your group number like '12345/6' to get your schedule.";
        when(userRepository.getUserById(userId)).thenReturn(null);

        userGroupManager.handleUserAbsense(userRequest, messageSender);

        verify(userRepository).save(userWithoutGroup);
        verify(messageSender).sendMessage(noGroupSpecifiedMessage);
    }

    @Test
    public void ifUserGroupIsNotSpecifiedAndGroupRepositoryReturnsSomeGroupByQueryThenItsGroupIsSetAsUserGroupAndUserIsUpdated() throws Exception {
        int foundGroupId = 2;
        String foundGroupName = "found group name";
        Group group = getGroupWithIdAndName(foundGroupId, foundGroupName);
        Groups groups = createGroupsWithGroupInside(group);
        User userWithoutGroup = this.userWithoutGroup;
        when(userRepository.getUserById(userId)).thenReturn(userWithoutGroup);
        when(groupRepository.findGroupsByName(requestMessage)).thenReturn(groups);

        userGroupManager.handleUserAbsense(userRequest, messageSender);

        verify(userRepository).save(new User(userId, foundGroupId));
        verify(messageSender).sendMessage("Your group is set to 'found group name'.", repliesToAdd);
    }

    @Test
    public void ifUserGroupIsNotSpecifiedAndGroupRepositoryReturnsGroupsWithNullThenUserIsAskedAgain() throws Exception {
        Groups groupsWithNull = new Groups();
        when(userRepository.getUserById(userId)).thenReturn(userWithoutGroup);
        when(groupRepository.findGroupsByName(requestMessage)).thenReturn(groupsWithNull);

        userGroupManager.handleUserAbsense(userRequest, messageSender);

        verify(messageSender).sendMessage(noGroupFoundMessage);
    }

    @Test
    public void ifUserGroupIsNotSpecifiedAndGroupRepositoryReturnsGroupsWithNoGroupsThenUserIsAskedAgain() throws Exception {
        Groups groupsWithZeroGroups = new Groups();
        groupsWithZeroGroups.setGroups(Collections.emptyList());
        when(userRepository.getUserById(userId)).thenReturn(userWithoutGroup);
        when(groupRepository.findGroupsByName(requestMessage)).thenReturn(groupsWithZeroGroups);

        userGroupManager.handleUserAbsense(userRequest, messageSender);

        verify(messageSender).sendMessage(noGroupFoundMessage);
    }

    @Test(expected = NoSuchUserException.class)
    public void ifThereAreNoSuchUserInRepoThen_NoSuchUserException_isThrown() throws Exception {
        doReturn(null).when(userRepository).getUserById(userId);

        userGroupManager.getGroupIdOfUserWithExceptions(userId);
    }

    @Test(expected = NoGroupForUserException.class)
    public void ifUser_groupId_isNotSetThen_NoGroupForUser__isThrown() throws Exception {
        User userWithNoGroup = new User(userId, User.NO_GROUP_SPECIFIED);
        doReturn(userWithNoGroup).when(userRepository).getUserById(userId);

        userGroupManager.getGroupIdOfUserWithExceptions(userId);
    }

    @Test
    public void ifUserIsInRepoAndGroupIdIsOk_ThenGroupIdIsReturned() throws Exception {
        int groupId = 2;
        User userWithNoGroup = new User(userId, groupId);
        doReturn(userWithNoGroup).when(userRepository).getUserById(userId);

        int receivedId = userGroupManager.getGroupIdOfUserWithExceptions(userId);

        assertThat(receivedId, is(groupId));
    }

    @Test
    public void createUserWithNoGroup_createsNewUserInRepository() {
        userGroupManager.saveUserWithoutGroup(userId);

        then(userRepository).should().save(new User(userId, User.NO_GROUP_SPECIFIED));
    }

    @Test
    public void findAndSetGroupForUser_setsGroupIdIfFindsGroupIn_groupsRepository_andSavesUser() {
        int groupId = 2;
        Groups groups = new Groups();
        Group group = new Group();
        group.setId(groupId);
        groups.setGroups(Collections.singletonList(group));
        String groupName = "group name";
        doReturn(groups).when(groupRepository).findGroupsByName(groupName);
        doReturn(new User(userId, User.NO_GROUP_SPECIFIED)).when(userRepository).getUserById(userId);

        userGroupManager.findAndSetGroupForUser(userId, groupName);

        then(userRepository).should().save(new User(userId, groupId));
    }

    @Test(expected = NoGroupFoundException.class)
    public void if_groupsRepository_returnsGroupsWithNullThen_NoGroupFoundException_isThrown() {
        String groupName = "group name";
        doReturn(new User(userId, User.NO_GROUP_SPECIFIED)).when(userRepository).getUserById(userId);
        doReturn(new Groups()).when(groupRepository).findGroupsByName(groupName);

        userGroupManager.findAndSetGroupForUser(userId, groupName);
    }

    @Test(expected = NoGroupFoundException.class)
    public void if_groupsRepository_returnsGroupsWithNoGroupsThen_NoGroupFoundException_isThrown() {
        String groupName = "group name";
        Groups groups = new Groups();
        groups.setGroups(Collections.emptyList());
        doReturn(new User(userId, User.NO_GROUP_SPECIFIED)).when(userRepository).getUserById(userId);
        doReturn(groups).when(groupRepository).findGroupsByName(groupName);

        userGroupManager.findAndSetGroupForUser(userId, groupName);
    }

    private Groups createGroupsWithGroupInside(Group group) {
        Groups groups = new Groups();
        groups.setGroups(Collections.singletonList(group));
        return groups;
    }

    private Group getGroupWithIdAndName(int foundGroupId, String foundGroupName) {
        Group group = new Group();
        group.setName(foundGroupName);
        group.setId(foundGroupId);
        return group;
    }

}