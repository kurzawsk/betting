package com.kk.betting.services.admin.endpoint;

import com.kk.betting.services.common.util.Roles;

import javax.annotation.security.RolesAllowed;

/**
 * Created by KK on 2017-07-14.
 */
//@Stateless
//@LocalBean
//@Path("/admin")
@RolesAllowed({Roles.ADMIN})
public class AdminResource {

   // pub
}
