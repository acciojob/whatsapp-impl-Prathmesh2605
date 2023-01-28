package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private HashMap<String,User > usersMap;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.usersMap = new HashMap<String,User>();
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    //create user
    public String createUser(String mobileNumber,String name) throws Exception {
        if(userMobile.contains(mobileNumber)){
            throw new Exception("Already exists");
        }

        userMobile.add(mobileNumber);
        User user = new User(name,mobileNumber);
        return "Success";
    }

    //create new group
    public Group createGroup(List<User>users){
        if(users.size()>2){
            customGroupCount+=1;
            Group group = new Group("Group "+customGroupCount, users.size());
            groupUserMap.put(group, users);
            adminMap.put(group, users.get(0));
            groupMessageMap.put(group, new ArrayList<Message>());
            return group;
        }
        Group group = new Group(users.get(1).getName(), users.size());
        groupUserMap.put(group, users);
        adminMap.put(group, users.get(0));
        groupMessageMap.put(group, new ArrayList<Message>());
        return group;
    }
    public int createMessage(String content){
        messageId++;
        Message message=new Message(messageId,content);

        return messageId;
    }

    //Send message
    public int sendMessage(Message message, User sender, Group group) throws Exception{

        if(!groupUserMap.containsKey(group))
        {
            throw new Exception("Group Not Found");
        }
        if(!groupUserMap.get(group).contains(sender))
        {
            throw new Exception("You are not allowed to send message");
        }
        senderMap.put(message, sender);
        List<Message>temp=groupMessageMap.get(group);
        temp.add(message);
        groupMessageMap.put(group,temp);
        return temp.size();

    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{

        if(!groupUserMap.containsKey(group))
        {
            throw new Exception("Group Not Found");
        }

        if(!groupUserMap.get(group).contains(user))
        {
            throw new Exception("User not found in the group");
        }
        if(!approver.equals(adminMap.get(group)))
        {
            throw new Exception("User does not have rights");
        }
        adminMap.put(group,user);

        return "Success";
    }


}
