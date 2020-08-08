package com.assignments.api.service;

import com.assignments.api.model.Group;
import com.assignments.api.model.User;
import com.assignments.api.repository.GroupRepository;
import com.assignments.api.repository.UserRepository;
import com.assignments.api.util.AuthenticationUtils;
import com.assignments.api.util.CodeGenerator;
import com.assignments.api.util.Validators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupManagementService {


    private static final int CODE_GENERATION_RETRY_COUNT = 5;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    public Group createGroup(Group group) throws Exception{
        Validators.insertGroupValidator(group);
        String code = generateUniqueCode();
        group.setInviteCode(code);
        return groupRepository.save(group);
    }

    private String generateUniqueCode() throws Exception{
        String code;
        for(int i = 0; i < CODE_GENERATION_RETRY_COUNT; i++){
            code = CodeGenerator.generateCode();
            if(isCodeUnique(code)){
                return code;
            }
        }
        throw new Exception("Failure during generating unique invite code");
    }

    private boolean isCodeUnique(String code){
        return groupRepository.findByInviteCodeAndIsActiveTrue(code).isEmpty();
    }

    public Group getGroup(String id) throws Exception{
        return groupRepository.findByIdAndIsActiveTrue(id).orElseThrow(Exception::new);
    }

    public Group addUserToGroup(String userId, String groupCode) throws Exception{
        Group target = groupRepository.findByInviteCodeAndIsActiveTrue(groupCode).orElseThrow(Exception::new);
        User user = userRepository.findByIdAndIsActiveTrue(userId).orElseThrow(Exception::new);
        if(user.getGroupId() != null)
            throw new Exception("User already in group");
        user.setGroupId(target.getId());
        userRepository.save(user);
        return target;
    }

    public void setStateOfGroup(boolean state, String id) throws Exception{
        Group target = groupRepository.findById(id).orElseThrow(Exception::new);
        target.setActive(state);
        groupRepository.save(target);
    }

    public List<Group> getGroupList(){
        return groupRepository.findAll();
    }

    public List<User> getUserListForGroup(String id) throws Exception {
        List<User> userList = userRepository.findByGroupId(id).orElseThrow(Exception::new);
        return secureUserList(userList);
    }

    private List<User> secureUserList(List<User> unsecuredList){
        List<User> securedList = new ArrayList<>();
        unsecuredList.forEach(user -> securedList.add(AuthenticationUtils.secureUserDataBeforeReturn(user)));
        return securedList;
    }
}
