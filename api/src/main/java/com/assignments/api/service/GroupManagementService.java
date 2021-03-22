package com.assignments.api.service;

import com.assignments.api.error.exception.CodeGenerationException;
import com.assignments.api.error.exception.DocumentNotFoundException;
import com.assignments.api.error.exception.UserAlreadyInGroupException;
import com.assignments.api.model.Assignment;
import com.assignments.api.model.Group;
import com.assignments.api.model.User;
import com.assignments.api.repository.AssignmentRepository;
import com.assignments.api.repository.GroupRepository;
import com.assignments.api.repository.UserRepository;
import com.assignments.api.util.AuthenticationUtils;
import com.assignments.api.util.CodeGenerator;
import com.assignments.api.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupManagementService {

    private static final int CODE_GENERATION_RETRY_COUNT = 5;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssignmentRepository assignmentRepository;
    @Autowired
    private UserManagementService userManagementService;

    public Group createGroup(Group group) {
        Validators.insertGroupValidator(group);
        String code = generateUniqueCode();
        group.setInviteCode(code);
        return groupRepository.save(group);
    }

    private String generateUniqueCode() {
        String code;
        for (int i = 0; i < CODE_GENERATION_RETRY_COUNT; i++) {
            code = CodeGenerator.generateCode();
            if (isCodeUnique(code)) {
                return code;
            }
        }
        throw new CodeGenerationException(CODE_GENERATION_RETRY_COUNT);
    }

    private boolean isCodeUnique(String code) {
        return groupRepository.findByInviteCodeAndIsActiveTrue(code).isEmpty();
    }

    public Group getGroup(String id) {
        return groupRepository.findByIdAndIsActiveTrue(id).orElseThrow(DocumentNotFoundException::new);
    }

    public Group addUserToGroup(String userId, String groupCode) {
        Group target = groupRepository.findByInviteCodeAndIsActiveTrue(groupCode).orElseThrow(DocumentNotFoundException::new);
        User user = userRepository.findByIdAndIsActiveTrue(userId).orElseThrow(DocumentNotFoundException::new);
        if (user.getGroupId() != null) {
            throw new UserAlreadyInGroupException();
        }
        user.setGroupId(target.getId());
        userRepository.save(user);
        return target;
    }

    public void setStateOfGroup(boolean state, String id) {
        Group target = groupRepository.findById(id).orElseThrow(DocumentNotFoundException::new);
        target.setActive(state);
        groupRepository.save(target);
    }

    public List<Group> getGroupList() {
        return groupRepository.findAll();
    }

    public List<User> getUserListForGroup(String id) {
        List<User> userList = userRepository.findByGroupId(id).orElseThrow(DocumentNotFoundException::new);
        return secureUserList(userList);
    }

    private List<User> secureUserList(List<User> unsecuredList) {
        List<User> securedList = new ArrayList<>();
        unsecuredList.forEach(user -> securedList.add(AuthenticationUtils.secureUserDataBeforeReturn(user)));
        return securedList;
    }

    public List<Map<String, Object>> getUserListWithAssignments(String id) {
        List<User> userList = getUserListForGroup(id);
        List<Map<String, Object>> result = new ArrayList<>();
        userList.forEach(user -> result.add(tryToGetUserWithAssignments(user.getId())));
        return result;
    }

    public Map<String, Integer> getRanking(String id) {
        return getUserListForGroup(id).stream()
        .collect(Collectors.toMap(
                User::getLogin,
                user -> assignmentRepository.findByAssignee(user.getId()).orElse(Collections.emptyList())
                        .stream().mapToInt(Assignment::getPoints).reduce(0, Integer::sum)
        ));
    }

    private Map<String, Object> tryToGetUserWithAssignments(String id) {
        try {
            return userManagementService.getUserWithAssignments(id);
        } catch (DocumentNotFoundException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
