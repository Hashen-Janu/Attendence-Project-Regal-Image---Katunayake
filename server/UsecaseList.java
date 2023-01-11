package bit.project.server;

import bit.project.server.util.security.SystemModule;

public enum UsecaseList{
    @SystemModule("User") SHOW_ALL_USERS(1),
    @SystemModule("User") SHOW_USER_DETAILS(2),
    @SystemModule("User") ADD_USER(3),
    @SystemModule("User") UPDATE_USER(4),
    @SystemModule("User") DELETE_USER(5),
    @SystemModule("User") RESET_USER_PASSWORDS(6),
    @SystemModule("Role") SHOW_ALL_ROLES(7),
    @SystemModule("Role") SHOW_ROLE_DETAILS(8),
    @SystemModule("Role") ADD_ROLE(9),
    @SystemModule("Role") UPDATE_ROLE(10),
    @SystemModule("Role") DELETE_ROLE(11),
    @SystemModule("Employee") SHOW_ALL_EMPLOYEES(12),
    @SystemModule("Employee") SHOW_EMPLOYEE_DETAILS(13),
    @SystemModule("Employee") ADD_EMPLOYEE(14),
    @SystemModule("Employee") UPDATE_EMPLOYEE(15),
    @SystemModule("Employee") DELETE_EMPLOYEE(16),

    @SystemModule("Attendance") SHOW_ALL_ATTENDANCES(17),
    @SystemModule("Attendance") SHOW_ATTENDANCE_DETAILS(18),
    @SystemModule("Attendance") ADD_ATTENDANCE(19),
    @SystemModule("Attendance") UPDATE_ATTENDANCE(20),
    @SystemModule("Attendance") DELETE_ATTENDANCE(21);

    public final int value;

    UsecaseList(int value){
        this.value = value;
    }

}