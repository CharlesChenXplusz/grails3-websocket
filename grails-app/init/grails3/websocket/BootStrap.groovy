package grails3.websocket

import example.Role
import example.User
import example.UserRole
import example.enu.RoleType

class BootStrap {

    def init = { servletContext ->
        Role role = new Role(authority: RoleType.ROLE_STAFF.toString())
        role.save()
        def usera = new User(username: 'usera', password: 'usera')
        usera.save()
        def userb = new User(username: 'userb', password: 'userb')
        userb.save()
        UserRole.create(usera, role)
        UserRole.create(userb, role, true)
    }
    def destroy = {
    }
}
