-- =====================================================================================================
-- Module 10 (analyticservice) Seed Data: Delhi Public School (tenant a0eebc99-…), Academic Year 2026-2027
-- =====================================================================================================
-- Run after authservice and communicationservice seeds, after analyticservice has started once:
--     powershell -ExecutionPolicy Bypass -File .agents/skills/test-ui/scripts/db/db.ps1 -Script <this file>
--
-- Story coverage:
--   US-10000: External Integration Endpoints Catalog (Canvas LMS, Moodle, Entra ID)
--   US-10001: Biometric Turnstiles & Real-Time Attendance Stream Events
--   US-10002: Inbound Payment Gateway Webhook Events (Razorpay, Stripe)
--   US-10003: WhatsApp Business Cloud API Gateway Configuration
--   US-10004: Tally Prime Financial Voucher Synchronization Batches
--   US-10005: CBSE / ICSE List of Candidates (LOC) Standardized Candidate Exports
--   US-10006: Real-Time Server Node Health Telemetry & Resource Utilization
--   US-10007: Immutable Database Modification Forensic Audit Trail Records
--   US-10008: Automated Cloud Database Backup Snapshots & SHA-256 PITR Checksums
--   US-10009: Visual Drag-and-Drop SQL Report Definitions (Finance, Attendance)
--   US-10010: API Latency Alert Rules & Operational Breaches
--   US-10011: API Rate Limiting Policies & Traffic Throttling
--   US-10012: Bank Statement Automated Reconciliation & Ledger Discrepancy Audits
--   US-10013: Production Data Anonymization Staging Pipeline Jobs
--   US-10014: Executive Trustee KPI Governance Dashboards
--   US-10015: External Partner API Key Credentials & Scoped Access Tokens
--   US-10017: Board Regulatory Compliance Certificates (Fire NOC, Building Safety, Affiliation)
--   US-10018: Database Replica Node Cluster Health & Primary Failover Promotion
--   US-10019: System Monthly Uptime SLAs & Public Status Incident Notices
--
-- Re-runnable: every seed row has created_by = 'seed:module10' and UUIDs starting with 5eed0010-.
-- =====================================================================================================

BEGIN;

-- ---------------------------------------------------------------------------------------------------
-- 0. Clean previous Module 10 seed rows
-- ---------------------------------------------------------------------------------------------------
DELETE FROM analytics_trustee_kpi_dashboards     WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_custom_reports             WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_status_incidents           WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_system_uptime_sla          WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_database_replicas          WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_cloud_backup_snapshots     WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_data_modification_audits   WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_system_health_metrics      WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_compliance_certificates    WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_anonymization_jobs         WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_cbse_loc_configs           WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_tally_sync_batches         WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_whatsapp_configs           WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_bank_reconciliation        WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_payment_webhooks           WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_attendance_stream_events   WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_biometric_devices          WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_latency_rules              WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_rate_limit_policies        WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_api_keys                   WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';
DELETE FROM analytics_integration_endpoints      WHERE created_by = 'seed:module10' OR id::text LIKE '5eed0010-%';

-- ---------------------------------------------------------------------------------------------------
-- 1. US-10000: Integration Endpoints Catalog
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_integration_endpoints (
    id, tenant_id, endpoint_name, target_url, http_method, auth_type, headers_json, is_active, description, created_by, updated_by
) VALUES
('5eed0010-0001-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Canvas LMS Gradebook Sync', 'https://canvas.partner.edu/api/v1/sync', 'POST', 'BEARER_TOKEN', '{"X-Tenant":"DPS-DELHI"}', true, 'Automated Gradebook & Assignment synchronization with Canvas LMS', 'seed:module10', 'seed:module10'),
('5eed0010-0001-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Microsoft Entra ID Roster Sync', 'https://graph.microsoft.com/v1.0/education/schools', 'POST', 'OAUTH2', '{"Scope":"EducationRoster.ReadWrite.All"}', true, 'Microsoft 365 Education student accounts automated provisioning', 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 2. US-10015: API Keys & Credentials
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_api_keys (
    id, tenant_id, key_name, client_id, api_key_prefix, secret_hash, allowed_scopes, rate_limit_tier, expires_at, is_revoked, created_by, updated_by
) VALUES
('5eed0010-0002-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'RFID Turnstile Gate Ingestion Key', 'kvs_cli_rfid_gate_01', 'kvs_sec_9948', 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855', 'ATTENDANCE_READ,ATTENDANCE_WRITE', 'PREMIUM', CURRENT_TIMESTAMP + INTERVAL '365 days', false, 'seed:module10', 'seed:module10'),
('5eed0010-0002-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Smart Cafeteria POS Integration Key', 'kvs_cli_pos_cafe_02', 'kvs_sec_8832', 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855', 'STUDENT_VERIFY,WALLET_DEBIT', 'STANDARD', CURRENT_TIMESTAMP + INTERVAL '365 days', false, 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 3. US-10011: Rate Limiting Policies
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_rate_limit_policies (
    id, tenant_id, policy_name, client_identifier, window_seconds, max_requests, enforcement_action, is_active, created_by, updated_by
) VALUES
('5eed0010-0003-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Mobile Parent App Gateway Pool', 'parent_mobile_pool', 60, 240, 'THROTTLE', true, 'seed:module10', 'seed:module10'),
('5eed0010-0003-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Public Registration Portal Throttle', 'admissions_public_pool', 60, 60, 'REJECT_429', true, 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 4. US-10010: Latency Alert Rules
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_latency_rules (
    id, tenant_id, rule_name, endpoint_pattern, threshold_ms, consecutive_breaches, alert_webhook_url, is_active, created_by, updated_by
) VALUES
('5eed0010-0004-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Payment Clearance Critical Latency SLA', '/api/v1/financeservice/transactions/**', 450, 3, 'https://opsgenie.schoolerp.internal/alerts/kvs-infra', true, 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 5. US-10001: Biometric Hardware Terminals & Attendance Stream
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_biometric_devices (
    id, tenant_id, device_serial_number, device_name, ip_address, port_number, location, device_model, is_online, last_heartbeat_at, created_by, updated_by
) VALUES
('5eed0010-0005-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'ZK-SN-849201', 'Senior Wing Turnstile 01', '192.168.10.45', 4370, 'Main Gate Turnstile A', 'ZKTeco ProCapture-X', true, CURRENT_TIMESTAMP, 'seed:module10', 'seed:module10'),
('5eed0010-0005-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'ZK-SN-849202', 'Junior Wing Turnstile 02', '192.168.10.46', 4370, 'Junior Gate Turnstile B', 'ZKTeco ProCapture-X', true, CURRENT_TIMESTAMP, 'seed:module10', 'seed:module10');

INSERT INTO analytics_attendance_stream_events (
    id, tenant_id, device_serial_number, user_biometric_id, punch_timestamp, direction, verification_type, is_processed, created_by, updated_by
) VALUES
('5eed0010-0006-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'ZK-SN-849201', 'BIO_STU_10482', CURRENT_TIMESTAMP - INTERVAL '3 hours', 'IN', 'FACIAL_RECOGNITION', true, 'seed:module10', 'seed:module10'),
('5eed0010-0006-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'ZK-SN-849201', 'BIO_STU_10483', CURRENT_TIMESTAMP - INTERVAL '3 hours', 'IN', 'FINGERPRINT', true, 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 6. US-10002: Inbound Payment Gateway Webhooks
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_payment_webhooks (
    id, tenant_id, gateway_provider, gateway_event_id, event_type, transaction_reference, amount, currency, payload_json, signature_header, clearance_status, clearance_notes, created_by, updated_by
) VALUES
('5eed0010-0007-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'RAZORPAY', 'evt_rzp_order_99812', 'payment.captured', 'TXN-PAY-2026-881', 28500.00, 'INR', '{"order_id":"ord_881","status":"captured"}', 'sha256=d84f8812...', 'CLEARED', 'Ledger updated instantly. Financial receipt reconciled.', 'seed:module10', 'seed:module10'),
('5eed0010-0007-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'STRIPE', 'evt_stripe_ch_9941', 'charge.succeeded', 'TXN-PAY-2026-882', 12500.00, 'INR', '{"charge_id":"ch_9941","status":"succeeded"}', 'sha256=a1b2c3d4...', 'CLEARED', 'Ledger updated instantly. Financial receipt reconciled.', 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 7. US-10012: Bank Statement Reconciliation
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_bank_reconciliation (
    id, tenant_id, statement_transaction_id, erp_transaction_ref, bank_amount, erp_ledger_amount, difference_amount, transaction_date, reconciliation_status, resolution_notes, created_by, updated_by
) VALUES
('5eed0010-0008-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'STMT-HDFC-90214', 'TXN-PAY-2026-881', 28500.00, 28500.00, 0.00, CURRENT_TIMESTAMP - INTERVAL '1 day', 'MATCHED', 'Perfect match between bank settlement and ERP fee collection.', 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 8. US-10003: WhatsApp Gateway Integration Configs
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_whatsapp_configs (
    id, tenant_id, bsp_provider, phone_number_id, business_account_id, sms_failover_enabled, sms_failover_gateway, is_active, created_by, updated_by
) VALUES
('5eed0010-0009-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'META_DIRECT', '109823908492019', 'waba_acc_dps_delhi_01', true, 'GUPSHUP_SMS', true, 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 9. US-10004: Tally Prime Voucher XML Synchronization
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_tally_sync_batches (
    id, tenant_id, batch_number, sync_date, voucher_count, total_debit, total_credit, xml_payload, status, created_by, updated_by
) VALUES
('5eed0010-0010-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'TALLY-BATCH-20261023-01', CURRENT_TIMESTAMP - INTERVAL '2 hours', 15, 125000.00, 125000.00, '<ENVELOPE><HEADER><TALLYREQUEST>Import Data</TALLYREQUEST></HEADER></ENVELOPE>', 'COMPLETED', 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 10. US-10005: CBSE LOC Candidate Export Configs
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_cbse_loc_configs (
    id, tenant_id, academic_year_id, class_name, candidate_count, export_format, generated_file_url, generated_by_user_id, created_by, updated_by
) VALUES
('5eed0010-0011-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '5eed0010-0000-4000-8000-000000000001', 'Class 10', 116, 'CBSE_TEXT_FORMAT', '/vault/compliance/cbse/loc_class10_2026.txt', '5eed0010-0000-4000-8000-000000000002', 'seed:module10', 'seed:module10'),
('5eed0010-0011-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '5eed0010-0000-4000-8000-000000000001', 'Class 12', 84, 'CBSE_TEXT_FORMAT', '/vault/compliance/cbse/loc_class12_2026.txt', '5eed0010-0000-4000-8000-000000000002', 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 11. US-10013: Production Data Anonymization Jobs
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_anonymization_jobs (
    id, tenant_id, job_name, target_staging_db, tables_masked, records_masked, rules_applied_json, duration_seconds, status, created_by, updated_by
) VALUES
('5eed0010-0012-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Weekly QA Staging Seed Refresh', 'kvs_school_erp_staging', 14, 12450, '{"names":"Faker.name()","phones":"+91980000XXXX"}', 42, 'COMPLETED', 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 12. US-10017: Board Regulatory Compliance Certificates
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_compliance_certificates (
    id, tenant_id, certificate_type, certificate_number, issuing_authority, issue_date, valid_until, document_url, status, verification_notes, created_by, updated_by
) VALUES
('5eed0010-0013-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'FIRE_SAFETY_NOC', 'DFS/NOC/HQ/2026/8941', 'State Fire & Emergency Services Dept', CURRENT_TIMESTAMP - INTERVAL '90 days', CURRENT_TIMESTAMP + INTERVAL '275 days', 'https://storage.kvs.local/compliance/fire_noc_2026.pdf', 'VALID', 'Annual site inspection cleared with zero non-conformities.', 'seed:module10', 'seed:module10'),
('5eed0010-0013-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'BUILDING_SAFETY_NOC', 'PWD/BLD/NOC/2026/102', 'Public Works Department (PWD)', CURRENT_TIMESTAMP - INTERVAL '120 days', CURRENT_TIMESTAMP + INTERVAL '600 days', 'https://storage.kvs.local/compliance/pwd_building_2026.pdf', 'VALID', 'Structural stability certified for multi-storey academic wings.', 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 13. US-10006: Real-Time Server Health Telemetry
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_system_health_metrics (
    id, tenant_id, server_node_id, cpu_usage_pct, memory_usage_pct, active_db_connections, idle_db_connections, disk_usage_pct, avg_response_latency_ms, recorded_at, created_by, updated_by
) VALUES
('5eed0010-0014-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'kvs-prod-node-01', 28.4, 52.1, 22, 14, 45.0, 56, CURRENT_TIMESTAMP, 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 14. US-10007: Immutable Database Modification Forensic Audit Trail
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_data_modification_audits (
    id, tenant_id, table_name, operation, record_id, old_values_json, new_values_json, modified_by_user_id, modified_by_username, query_duration_ms, client_ip, created_by, updated_by
) VALUES
('5eed0010-0015-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'student_fee_ledgers', 'UPDATE', 'LEDGER-REC-9014', '{"waiver_amount":0}', '{"waiver_amount":5000,"approved_by":"Principal"}', '5eed0010-0000-4000-8000-000000000002', 'accounts_admin', 14, '10.14.20.101', 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 15. US-10008: Automated Cloud Database Backup Snapshots
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_cloud_backup_snapshots (
    id, tenant_id, snapshot_name, cloud_provider, bucket_location, size_bytes, point_in_time, status, checksum_sha256, created_by, updated_by
) VALUES
('5eed0010-0016-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'kvs_daily_automated_backup_0300', 'AWS_S3', 's3://kvs-db-backups-mumbai/prod/2026-10-23/', 2147483648, CURRENT_TIMESTAMP - INTERVAL '4 hours', 'COMPLETED', 'e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855', 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 16. US-10018: Database Replica Cluster Nodes
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_database_replicas (
    id, tenant_id, node_hostname, port, replica_role, replication_lag_ms, is_healthy, is_failover_eligible, last_health_check_at, created_by, updated_by
) VALUES
('5eed0010-0017-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'kvs-db-replica-01.internal', 5432, 'PRIMARY', 0, true, true, CURRENT_TIMESTAMP, 'seed:module10', 'seed:module10'),
('5eed0010-0017-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'kvs-db-replica-02.internal', 5432, 'REPLICA', 12, true, true, CURRENT_TIMESTAMP, 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 17. US-10019: System Monthly Uptime SLAs & Status Incident Notices
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_system_uptime_sla (
    id, tenant_id, period_month_year, total_minutes, downtime_minutes, uptime_percentage, sla_target_pct, sla_met, created_by, updated_by
) VALUES
('5eed0010-0018-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2026-09', 43200, 12, 99.97, 99.9, true, 'seed:module10', 'seed:module10'),
('5eed0010-0018-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '2026-10', 44640, 8, 99.98, 99.9, true, 'seed:module10', 'seed:module10');

INSERT INTO analytics_status_incidents (
    id, tenant_id, incident_title, severity, status, impacted_services, incident_message, is_public, created_by, updated_by
) VALUES
('5eed0010-0019-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'SMS Gateway Carrier Transient Delays', 'MINOR', 'MONITORING', 'SMS,NOTIFICATIONS', 'Carrier routes experiencing up to 3 minute delays; WhatsApp failover active.', true, 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 18. US-10009: Visual Drag-and-Drop SQL Report Definitions
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_custom_reports (
    id, tenant_id, report_name, category, dimensions_json, metrics_json, filters_json, chart_type, created_by_user_id, created_by, updated_by
) VALUES
('5eed0010-0020-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Term 1 Fee Realization by Grade', 'FINANCE', '["grade", "fee_head"]', '["sum(demand_amount)", "sum(collected_amount)"]', '{"academic_term":"TERM_1"}', 'BAR', '5eed0010-0000-4000-8000-000000000002', 'seed:module10', 'seed:module10'),
('5eed0010-0020-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Grade-wise Monthly Truancy Rates', 'ATTENDANCE', '["grade", "section"]', '["avg(present_pct)", "sum(truant_count)"]', '{"month":"OCTOBER"}', 'LINE', '5eed0010-0000-4000-8000-000000000002', 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 19. US-10014: Trustee Executive KPI Dashboards
-- ---------------------------------------------------------------------------------------------------
INSERT INTO analytics_trustee_kpi_dashboards (
    id, tenant_id, period_name, fee_collection_pct, average_attendance_pct, student_enrollment_count, teacher_student_ratio, board_pass_percentage, summary_notes, created_by, updated_by
) VALUES
('5eed0010-0021-4000-8000-000000000001', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Q2 FY 2026-27', 96.8, 95.2, 1280, 17.8, 99.2, 'Exceeded board academic targets and maintained 96%+ fee recovery.', 'seed:module10', 'seed:module10'),
('5eed0010-0021-4000-8000-000000000002', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'Q3 FY 2026-27', 97.4, 95.8, 1310, 17.5, 99.4, 'Board quarterly KPIs exceed targeted operational thresholds.', 'seed:module10', 'seed:module10');

-- ---------------------------------------------------------------------------------------------------
-- 20. Audit columns: Hibernate-created tables have no defaults, and a NULL @Version makes JPA treat
--     a seeded row as new on save, so stamp created_at / updated_at / version on every seed row.
-- ---------------------------------------------------------------------------------------------------
DO $$
DECLARE t text;
BEGIN
    FOR t IN SELECT table_name FROM information_schema.tables
             WHERE table_schema = current_schema() AND table_name LIKE 'analytics\_%'
    LOOP
        EXECUTE format('UPDATE %I SET created_at = COALESCE(created_at, CURRENT_TIMESTAMP), '
                       'updated_at = COALESCE(updated_at, CURRENT_TIMESTAMP), version = COALESCE(version, 0) '
                       'WHERE created_by = ''seed:module10''', t);
    END LOOP;
END $$;

COMMIT;
