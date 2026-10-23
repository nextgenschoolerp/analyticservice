package com.kvs.analytics.config;

/**
 * RBAC authorization expressions for Module 10 Enterprise Integrations, API Webhooks, Compliance & Analytics.
 * User stories: US-10000 to US-10019.
 */
public final class AnalyticsRoles {

    private AnalyticsRoles() {
    }

    /** Integration and external developer suite management */
    public static final String INTEGRATION_MANAGE = "hasAnyRole('CHIEF_TECHNOLOGY_OFFICER', 'SYSTEM_INTEGRATOR', 'SUPER_ADMIN', 'ADMIN')";

    /** Webhook ingestion and real-time settlement */
    public static final String WEBHOOK_MANAGE = "hasAnyRole('CHIEF_TECHNOLOGY_OFFICER', 'DATA_ANALYST', 'SUPER_ADMIN', 'ADMIN', 'SYSTEM_AUTOMATION')";

    /** Regulatory board compliance and data exports */
    public static final String COMPLIANCE_MANAGE = "hasAnyRole('COMPLIANCE_OFFICER', 'PRINCIPAL', 'SCHOOL_PRINCIPAL', 'EXAM_CONTROLLER', 'DATABASE_ADMINISTRATOR', 'SUPER_ADMIN', 'ADMIN')";

    /** System health monitoring and immutable audit query */
    public static final String AUDIT_VIEW = "hasAnyRole('SECURITY_AUDITOR', 'AUDITOR', 'CHIEF_TECHNOLOGY_OFFICER', 'PRINCIPAL', 'SCHOOL_PRINCIPAL', 'SUPER_ADMIN', 'ADMIN')";

    /** Cloud backup, snapshot retention and DR failover */
    public static final String BACKUP_MANAGE = "hasAnyRole('CHIEF_TECHNOLOGY_OFFICER', 'DATABASE_ADMINISTRATOR', 'SUPER_ADMIN', 'ADMIN')";

    /** Executive KPI reports and trustee dashboards */
    public static final String ANALYTICS_VIEW = "hasAnyRole('DATA_ANALYST', 'TRUSTEE', 'PRINCIPAL', 'SCHOOL_PRINCIPAL', 'SUPER_ADMIN', 'ADMIN')";

    /** Custom SQL report builder execution */
    public static final String REPORT_BUILDER = "hasAnyRole('DATA_ANALYST', 'SYSTEM_INTEGRATOR', 'SUPER_ADMIN', 'ADMIN')";

    /** Biometric and external hardware ingestion */
    public static final String GATEWAY_INGEST = "hasAnyRole('SYSTEM_AUTOMATION', 'SYSTEM_INTEGRATOR', 'SUPER_ADMIN', 'ADMIN')";

    /** Any authenticated user */
    public static final String ANY_AUTHENTICATED = "isAuthenticated()";
}
