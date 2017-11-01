package org.anstreth.schedulebot.schedulebotservice;

import org.anstreth.ruzapi.response.Group;
import org.anstreth.ruzapi.response.Groups;
import org.anstreth.ruzapi.ruzapirepository.GroupsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class GroupSearcherTest {

    @InjectMocks
    private GroupSearcher groupSearcher;

    @Mock
    private GroupsRepository groupRepository;

    @Test
    public void when_groupRepository_returns_null_emptyIsReturned() throws Exception {
        String groupName = "name";
        doReturn(null).when(groupRepository).findGroupsByName(groupName);

        assertThat(groupSearcher.findGroupByName(groupName), is(Optional.empty()));
    }

    @Test
    public void when_groupRepository_returns_Groups_withNullList_thenEmptyIsReturned() throws Exception {
        String groupName = "name";
        doReturn(new Groups()).when(groupRepository).findGroupsByName(groupName);

        assertThat(groupSearcher.findGroupByName(groupName), is(Optional.empty()));
    }

    @Test
    public void when_groupRepository_returns_Groups_withEmptyList_thenEmptyIsReturned() throws Exception {
        String groupName = "name";
        Groups groups = new Groups();
        groups.setGroups(Collections.emptyList());
        doReturn(groups).when(groupRepository).findGroupsByName(groupName);

        assertThat(groupSearcher.findGroupByName(groupName), is(Optional.empty()));
    }

    @Test
    public void when_groupRepository_returns_Groups_withAtLeastOneGroupInside_ItIsReturned() throws Exception {
        String groupName = "name";
        Groups groups = new Groups();
        Group group = mock(Group.class);
        groups.setGroups(Collections.singletonList(group));
        doReturn(groups).when(groupRepository).findGroupsByName(groupName);

        assertThat(groupSearcher.findGroupByName(groupName), is(Optional.of(group)));
    }
}