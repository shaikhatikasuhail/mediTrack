package com.listener;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.entity.Role;
import com.enums.RolesEnum;
import com.repo.RoleRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class RoleListener implements ApplicationListener<ContextRefreshedEvent> {
	
	 @Autowired
	private RoleRepo rr;
	private boolean isRoleEmpty;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadRoles();
		log.debug("Roles added");
	}

	public void loadRoles()
	{
		RolesEnum[] re=new RolesEnum[] {RolesEnum.ADMIN,RolesEnum.SUPER_ADMIN,RolesEnum.USER};
		
		Map<RolesEnum, String> map=new HashMap<RolesEnum, String>();
		map.put(RolesEnum.USER, "This is Default role");
		map.put(RolesEnum.ADMIN, "This is Admin role");
		map.put(RolesEnum.SUPER_ADMIN, "This is Super admin role");
		
		
		for (RolesEnum rolesEnum : re) {
			
			boolean rolename=rr.findByName(rolesEnum).isPresent();
			
			if(!rolename)
			{
				Role r=new Role();
				r.setName(rolesEnum);
				r.setDescription(map.get(rolesEnum));
				rr.save(r);
				log.info("role with name created",r.getName());
			}
			
		}
		

		
	}
}
