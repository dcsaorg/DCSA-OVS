
INSERT INTO service (
    carrier_id,
    carrier_service_code,
    carrier_service_name
) VALUES (
     (SELECT id FROM carrier WHERE smdg_code = 'MSK'),
     'TNT1',
     'Service name for TNT'
);


INSERT INTO voyage (
    carrier_voyage_number,
    service_id
) VALUES (
     'TNT1E',
     (SELECT id FROM service WHERE carrier_service_code = 'TNT1' LIMIT 1)
);


INSERT INTO vessel (
    vessel_imo_number,
    vessel_name,
    vessel_flag,
    vessel_call_sign,
    vessel_operator_carrier_id
) VALUES (
    '1234567',
    'King of the Seas',
    'DE',
    'NCVV',
    (SELECT id FROM carrier WHERE smdg_code = 'MSK')
);

INSERT INTO transport_call (
    id,
    transport_call_reference,
    transport_call_sequence_number,
    facility_id,
    facility_type_code,
    mode_of_transport_code,
    vessel_id,
    import_voyage_id
) VALUES (
    '8b64d20b-523b-4491-b2e5-32cfa5174eed'::uuid,
    'TC-REF-08_02-A',
    1,
    (SELECT id FROM facility WHERE un_location_code = 'SGSIN' AND facility_smdg_code = 'PSABT'),
    'POTE',
    (SELECT mode_of_transport_code FROM mode_of_transport WHERE dcsa_transport_type = 'VESSEL'),
    (SELECT id FROM vessel WHERE vessel_imo_number = '1234567'),
    (SELECT id FROM voyage WHERE carrier_voyage_number = 'TNT1E')
);


INSERT INTO transport_call (
    id,
    transport_call_reference,
    transport_call_sequence_number,
    facility_id,
    facility_type_code,
    mode_of_transport_code,
    vessel_id,
    export_voyage_id,
    import_voyage_id
) VALUES (
    '123e4567-e89b-12d3-a456-426614174000'::uuid,
    'TC-REF-08_02-B',
    1,
    (SELECT id FROM facility WHERE un_location_code = 'USNYC' AND facility_smdg_code = 'APMT'),
    'POTE',
    (SELECT mode_of_transport_code FROM mode_of_transport WHERE dcsa_transport_type = 'VESSEL'),
    (SELECT id FROM vessel WHERE vessel_imo_number = '9321483'),
    (SELECT id FROM voyage WHERE carrier_voyage_number = 'TNT1E'),
    (SELECT id FROM voyage WHERE carrier_voyage_number = 'TNT1E')
);

INSERT INTO transport (
    transport_reference,
    transport_name,
    load_transport_call_id,
    discharge_transport_call_id
) VALUES (
    'transport reference',
    'Transport name (Singapore -> NYC)',
    '8b64d20b-523b-4491-b2e5-32cfa5174eed'::uuid,
    '123e4567-e89b-12d3-a456-426614174000'::uuid
);

INSERT INTO booking (
    carrier_booking_request_reference,
    document_status,
    submission_datetime,
    receipt_type_at_origin,
    delivery_type_at_destination,
    cargo_movement_type_at_origin,
    cargo_movement_type_at_destination,
    booking_request_datetime,
    service_contract_reference,
    payment_term_code,
    is_partial_load_allowed,
    is_export_declaration_required,
    export_declaration_reference,
    is_import_license_required,
    import_license_reference,
    is_destination_filing_required,
    incoterms,
    expected_departure_date,
    transport_document_type_code,
    transport_document_reference,
    booking_channel_reference,
    communication_channel_code,
    is_equipment_substitution_allowed,
    vessel_id,
    updated_date_time
) VALUES (
    'BR1239719971',
    'PENU',
    DATE '2020-03-07',
    'CY',
    'CFS',
    'FCL',
    'LCL',
    DATE '2020-03-07',
    'SERVICE_CONTRACT_REFERENCE_01',
    'PRE',
    TRUE,
    TRUE,
    'EXPORT_DECLARATION_REFERENCE_01',
    FALSE,
    'IMPORT_LICENSE_REFERENCE_01',
    TRUE,
    'FCA',
    DATE '2020-03-07',
    'SWB',
    'TRANSPORT_DOC_REF_01',
    'BOOKING_CHA_REF_01',
    'EI',
    FALSE,
    (SELECT vessel.id FROM vessel WHERE vessel_imo_number = '9321483'),
    DATE '2021-11-04'
);

INSERT INTO booking (
    carrier_booking_request_reference,
    document_status,
    submission_datetime,
    receipt_type_at_origin,
    delivery_type_at_destination,
    cargo_movement_type_at_origin,
    cargo_movement_type_at_destination,
    booking_request_datetime,
    service_contract_reference,
    payment_term_code,
    is_partial_load_allowed,
    is_export_declaration_required,
    export_declaration_reference,
    is_import_license_required,
    import_license_reference,
    is_destination_filing_required,
    incoterms,
    expected_departure_date,
    transport_document_type_code,
    transport_document_reference,
    booking_channel_reference,
    communication_channel_code,
    is_equipment_substitution_allowed,
    vessel_id,
    updated_date_time
) VALUES (
    'BR1239719872',
    'PENU',
    DATE '2020-04-15',
    'CY',
    'CFS',
    'FCL',
    'LCL',
    DATE '2020-04-15',
    'SERVICE_CONTRACT_REFERENCE_02',
    'PRE',
    TRUE,
    TRUE,
    'EXPORT_DECLARATION_REFERENCE_02',
    FALSE,
    'IMPORT_LICENSE_REFERENCE_02',
    TRUE,
    'FCA',
    DATE '2020-04-15',
    'SWB',
    'TRANSPORT_DOC_REF_02',
    'BOOKING_CHA_REF_02',
    'EI',
    FALSE,
    (SELECT vessel.id FROM vessel WHERE vessel_imo_number = '9321483'),
    DATE '2021-01-10'
);

INSERT INTO booking (
    carrier_booking_request_reference,
    document_status,
    submission_datetime,
    receipt_type_at_origin,
    delivery_type_at_destination,
    cargo_movement_type_at_origin,
    cargo_movement_type_at_destination,
    booking_request_datetime,
    service_contract_reference,
    payment_term_code,
    is_partial_load_allowed,
    is_export_declaration_required,
    export_declaration_reference,
    is_import_license_required,
    import_license_reference,
    is_destination_filing_required,
    incoterms,
    expected_departure_date,
    transport_document_type_code,
    transport_document_reference,
    booking_channel_reference,
    communication_channel_code,
    is_equipment_substitution_allowed,
    vessel_id,
    updated_date_time
) VALUES (
    'ABC123123123',
    'RECE',
    DATE '2020-03-10',
    'CY',
    'CFS',
    'FCL',
    'LCL',
    DATE '2020-03-10',
    'SERVICE_CONTRACT_REFERENCE_03',
    'PRE',
    TRUE,
    TRUE,
    'EXPORT_DECLARATION_REFERENCE_03',
    FALSE,
    'IMPORT_LICENSE_REFERENCE_03',
    TRUE,
    'FCA',
    DATE '2020-03-10',
    'SWB',
    'TRANSPORT_DOC_REF_03',
    'BOOKING_CHA_REF_03',
    'EI',
    FALSE,
    (SELECT vessel.id FROM vessel WHERE vessel_imo_number = '9321483'),
    DATE '2021-12-16'
);

INSERT INTO shipment (
    carrier_id,
    booking_id,
    carrier_booking_reference,
    terms_and_conditions,
    confirmation_datetime,
    updated_date_time
) VALUES (
    (SELECT id FROM carrier WHERE smdg_code = 'MSK'),
    (SELECT id FROM booking WHERE carrier_booking_request_reference = 'CARRIER_BOOKING_REQUEST_REFERENCE_01'),
    'BR1239719971',
    'TERMS AND CONDITIONS!',
    DATE '2021-12-12T12:12:12',
    DATE '2021-12-12T13:13:13'
);

INSERT INTO shipment (
    carrier_id,
    booking_id,
    carrier_booking_reference,
    terms_and_conditions,
    confirmation_datetime,
    updated_date_time
) VALUES (
    (SELECT id FROM carrier WHERE smdg_code = 'MSK'),
    (SELECT id FROM booking WHERE carrier_booking_request_reference = 'CARRIER_BOOKING_REQUEST_REFERENCE_02'),
    'ABC123123123',
    'TERMS AND CONDITIONS!',
    DATE '2021-12-12T12:12:12',
    DATE '2021-12-12T13:13:13'
);

INSERT INTO shipment_transport (
    shipment_id,
    transport_id,
    transport_plan_stage_sequence_number,
    transport_plan_stage_code,
    is_under_shippers_responsibility
) VALUES (
    (SELECT id FROM shipment WHERE carrier_booking_reference = 'ABC123123123'),
    (SELECT DISTINCT transport.id FROM transport WHERE load_transport_call_id = '8b64d20b-523b-4491-b2e5-32cfa5174eed' OR discharge_transport_call_id = '8b64d20b-523b-4491-b2e5-32cfa5174eed'),
    1,
    'PRC',
    false
);

INSERT INTO reference (
    reference_type_code,
    reference_value,
    shipment_id
) VALUES (
    'FF',
    'string',
    (SELECT id FROM shipment WHERE carrier_booking_reference = 'ABC123123123')
);

INSERT INTO shipment_event (
    event_id,
    event_classifier_code,
    event_date_time,
    shipment_event_type_code,
    document_type_code,
    document_id,
    document_reference
) VALUES (
    uuid('784871e7-c9cd-4f59-8d88-2e033fa799a1'),
    'ACT',
    '2020-07-15',
    'APPR',
    'BKG',
    (SELECT id FROM booking b WHERE b.carrier_booking_request_reference = 'BR1239719971'),
    'BR1239719971'
);

INSERT INTO shipment_event (
    event_id,
    event_classifier_code,
    event_date_time,
    shipment_event_type_code,
    document_type_code,
    document_id,
    document_reference
) VALUES (
    uuid('e48f2bc0-c746-11ea-a3ff-db48243a89f4'),
    'ACT',
    TO_DATE('2020/07/15 13:14:15', 'yyyy/mm/dd hh24:mi:ss'),
    'APPR',
    'BKG',
    (SELECT id FROM booking b WHERE b.carrier_booking_request_reference = 'BR1239719971'),
    'BR1239719971'
);

INSERT INTO shipment_event (
    event_id,
    event_classifier_code,
    event_date_time,
    shipment_event_type_code,
    document_type_code,
    document_id,
    document_reference
) VALUES (
    uuid('5e51e72c-d872-11ea-811c-0f8f10a32ea1'),
    'ACT',
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'CONF',
    'BKG',
    (SELECT id FROM booking b WHERE b.carrier_booking_request_reference = 'ABC123123123'),
    'ABC123123123'
);

INSERT INTO iso_equipment_code (
    iso_equipment_code,
    iso_equipment_name,
    iso_equipment_size_code,
    iso_equipment_type_code_a
) VALUES (
    '22G2',
    'Twenty foot dry 2',
    '23',
    'G2'
);

INSERT INTO equipment(
    equipment_reference,
    iso_equipment_code,
    tare_weight,
    weight_unit
) VALUES (
    'equipref3453',
    '22G2',
    null,
    null
);

INSERT INTO equipment(
    equipment_reference,
    iso_equipment_code,
    tare_weight,
    weight_unit
) VALUES (
    'APZU4812090',
    '22G2',
    null,
    null
);

INSERT INTO utilized_transport_equipment (
    id,
    equipment_reference,
    cargo_gross_weight,
    cargo_gross_weight_unit,
    is_shipper_owned
) VALUES (
    '39e2504d-cdd6-4dbf-83e9-50412627a3b0',
    'APZU4812090',
    1424.2,
    'KGM',
    false
);

INSERT INTO seal (
    utilized_transport_equipment_id,
    seal_number,
    seal_source_code,
    seal_type_code
) VALUES (
     '39e2504d-cdd6-4dbf-83e9-50412627a3b0',
     'SN123457',
     'CUS',
     'WIR'
);

INSERT INTO equipment_event (
    event_id,
    event_classifier_code,
    event_date_time,
    equipment_event_type_code,
    transport_call_id,
    equipment_reference,
    empty_indicator_code
) VALUES (
    uuid('5e51e72c-d872-11ea-811c-0f8f10a32ea2'),
    'ACT',
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'LOAD',
    '8b64d20b-523b-4491-b2e5-32cfa5174eed'::uuid,
    'equipref3453',
    'EMPTY'
);

INSERT INTO transport_event (
    event_id,
    event_classifier_code,
    event_date_time,
    transport_event_type_code,
    transport_call_id,
    delay_reason_code,
    change_remark
) VALUES (
    uuid('5e51e72c-d872-11ea-811c-0f8f10a32ea3'),
    'ACT',
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'DEPA',
    uuid('8b64d20b-523b-4491-b2e5-32cfa5174eed'),
    'ANA',
    'Authorities not available'
);

INSERT INTO transport_event (
    event_id,
    event_classifier_code,
    event_created_date_time,
    event_date_time,
    transport_event_type_code,
    transport_call_id,
    delay_reason_code,
    change_remark
) VALUES (
    uuid('84db923d-2a19-4eb0-beb5-446c1ec57d34'),
    'ACT',
    '2021-01-09T14:12:56+01:00'::timestamptz,
    '2019-11-12T07:41:00+08:30'::timestamptz,
    'ARRI',
    '8b64d20b-523b-4491-b2e5-32cfa5174eed'::uuid,
    'WEA',
    'Bad weather'
);


INSERT INTO equipment_event (
    event_id,
    event_classifier_code,
    event_created_date_time,
    event_date_time,
    equipment_event_type_code,
    transport_call_id,
    empty_indicator_code,
    equipment_reference
) VALUES (
    uuid('84db923d-2a19-4eb0-beb5-446c1ec57d34'),
    'EST',
    '2021-01-09T14:12:56+01:00'::timestamptz,
    '2019-11-12T07:41:00+08:30'::timestamptz,
    'LOAD',
    '8b64d20b-523b-4491-b2e5-32cfa5174eed'::uuid,
    'EMPTY',
    'APZU4812090'
);

INSERT INTO transport_event (
    event_classifier_code,
    event_date_time,
    event_created_date_time,
    transport_event_type_code,
    transport_call_id,
    delay_reason_code,
    change_remark
) VALUES (
    'EST',
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2003/05/01 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'DEPA',
    '123e4567-e89b-12d3-a456-426614174000'::uuid,
    'ANA',
    'Authorities not available'
);

INSERT INTO transport_event (
    event_classifier_code,
    event_date_time,
    event_created_date_time,
    transport_event_type_code,
    transport_call_id,
    delay_reason_code,
    change_remark
) VALUES (
    'ACT',
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    TO_DATE('2003/05/03 21:02:44', 'yyyy/mm/dd hh24:mi:ss'),
    'DEPA',
    '123e4567-e89b-12d3-a456-426614174000'::uuid,
    'ANA',
    'Authorities not available'
);

