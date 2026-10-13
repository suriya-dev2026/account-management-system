package com.accountmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(tags = {
                @Tag(name = "AuthController", description = "auth APIs"),
                @Tag(name = "OrganizationController", description = "Organization APIs"),
                @Tag(name = "UserController", description = "User APIs"),
                @Tag(name = "SubscriptionController", description = "subscription plan APIs"),
                @Tag(name = "MasterController", description = "Master country APIs"),
                @Tag(name = "MemberController", description = "Member APIs"),
                @Tag(name = "StaffController", description = "Staff and attendance APIs"),
                @Tag(name = "VbsController", description = "Vbs Class,Student,Year,Teacher,Attendance APIs"),
                @Tag(name = "SundaySchoolController", description = "Sunday School Student,Teacher,Class,Attendance,Transition APIs"),
                @Tag(name = "MeetingController", description = "Meeting and Meeting Type APIs"),
                @Tag(name = "EventController", description = "Event and Event Participant and Attendance APIs"),
                @Tag(name = "BroadcastController", description = "Broadcast and Broadcast Recipient APIs"),
                @Tag(name = "BulletinController", description = "Bulletin APIs"),
                @Tag(name = "PastoralCareController", description = "Pastoralcare APIs"),
                @Tag(name = "EquipmentController", description = "Equipment and Type APIs"),
                @Tag(name = "AccessControlController", description = "Access Control APIs"),
                @Tag(name = "CertificateController", description = "Certificate APIs"),
                @Tag(name = "AccessControlController", description = "Access Control APIs"),
                @Tag(name = "AccountingController", description = "Accounting offering, expenses APIs")

})
public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenApi() {
                final String securitySchemeName = "bearerAuth";
                return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                                .components(new Components()
                                                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                                                .name(securitySchemeName)
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")));
        }
}
