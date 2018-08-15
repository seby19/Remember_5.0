package remember_dto;

import org.springframework.stereotype.Component;

@Component
public class GroupsDto {
	String groupName;
	String groupDesc;
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupDesc() {
		return groupDesc;
	}
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	
}
