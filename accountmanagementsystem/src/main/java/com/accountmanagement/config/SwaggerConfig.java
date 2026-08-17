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
                @Tag(name = "SubscriptionPlanController", description = "subscription plan APIs"),
                @Tag(name = "SubscriptionFeatureController", description = "subscription feature APIs"),
                @Tag(name = "SubscriptionPlanFeatureController", description = "Subscription Plan Feature APIs"),
                @Tag(name = "SubscriptionOrganizationController", description = "Subscription Organization APIs"),
                @Tag(name = "SubscriptionPaymentController", description = "Subscription Payment APIs"),
                @Tag(name = "MemberCategoryController", description = "Member category APIs"),
                @Tag(name = "LocationController", description = "Location APIs"),
                @Tag(name = "MemberController", description = "Member APIs"),
                @Tag(name = "MasterCountryController", description = "Master country APIs"),
                @Tag(name = "MemberStateController", description = "Master state APIs"),
                @Tag(name = "MemberCityController", description = "Master city APIs"),
                @Tag(name = "AccessControlRoleController", description = "Access Control Role APIs"),
                @Tag(name = "AccessControlRouteController", description = "Access Control Route APIs"),
                @Tag(name = "AccessControlModulePresetController", description = "Access Control Role APIs"),
                @Tag(name = "AccessControlRolePresetAccessController", description = "Access Control Role Preset Access APIs"),
                @Tag(name = "AccessControlRoutePresetAccessController", description = "Access Control Route Preset Access APIs"),
                @Tag(name = "AccessControlUserRoleController", description = "Access Control User Role APIs"),

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
