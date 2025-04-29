package com.abdulrahman.cityxcore.dto;

import java.time.Instant;
import java.util.List;

public class CaseImportDTO {
    private String case_number;
    private String case_name;
    private String description;
    private String area;
    private String city;
    private CreatorDTO created_by;
    private Instant created_at;
    private String case_type;
    private String level;
    private List<AssigneeDTO> assignees;
    private List<PersonDTO> persons;
    private List<ReportDTO> reported_by;

    // Getters and setters
    public String getCase_number() {
        return case_number;
    }
    public void setCase_number(String case_number) {
        this.case_number = case_number;
    }
    public String getCase_name() {
        return case_name;
    }
    public void setCase_name(String case_name) {
        this.case_name = case_name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public CreatorDTO getCreated_by() {
        return created_by;
    }
    public void setCreated_by(CreatorDTO created_by) {
        this.created_by = created_by;
    }
    public Instant getCreated_at() {
        return created_at;
    }
    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }
    public String getCase_type() {
        return case_type;
    }
    public void setCase_type(String case_type) {
        this.case_type = case_type;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }
    public List<AssigneeDTO> getAssignees() {
        return assignees;
    }
    public void setAssignees(List<AssigneeDTO> assignees) {
        this.assignees = assignees;
    }
    public List<PersonDTO> getPersons() {
        return persons;
    }
    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }
    public List<ReportDTO> getReported_by() {
        return reported_by;
    }
    public void setReported_by(List<ReportDTO> reported_by) {
        this.reported_by = reported_by;
    }

    // Nested DTO classes

    public static class CreatorDTO {
        private String id;
        private String name;
        private String role;
        // Getters and setters
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class AssigneeDTO {
        private String id;
        private String name;
        private String role;
        // Getters and setters
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class PersonDTO {
        private String type;
        private String name;
        private int age;
        private String gender;
        private String role;
        // Getters and setters
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getAge() {
            return age;
        }
        public void setAge(int age) {
            this.age = age;
        }
        public String getGender() {
            return gender;
        }
        public void setGender(String gender) {
            this.gender = gender;
        }
        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }
    }

    public static class ReportDTO {
        private int report_id;
        private String email;
        private String civil_id;
        private String name;
        private String role;
        // Getters and setters
        public int getReport_id() {
            return report_id;
        }
        public void setReport_id(int report_id) {
            this.report_id = report_id;
        }
        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getCivil_id() {
            return civil_id;
        }
        public void setCivil_id(String civil_id) {
            this.civil_id = civil_id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
        }
    }
}
