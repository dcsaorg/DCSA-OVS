DO $$
DECLARE
    _json_data jsonb;
    _service jsonb;
    _vesselSchedule jsonb;
    _transportCall jsonb;
    _location_id UUID;
    _timestamp jsonb;
    _import_voyage_id UUID;
    _export_voyage_id UUID;
    _transportCallId UUID;
BEGIN
    _json_data := '{"services": [{
"carrierServiceName": "AE5",
"carrierServiceCode": "431",
"universalServiceReference": "",
"vesselSchedules": [
                {
                    "vesselOperatorSmdgLinerCode": "MSK",
                    "vesselIMONumber": "9778844",
                    "vesselName": "MARSEILLE MAERSK",
                    "vesselCallSign": "",
                    "isDummyVessel": false,
                    "transportCalls": [
                        {
                            "portVisitReference": "",
                            "transportCallReference": "14102561",
                            "carrierImportVoyageNumber": "313E",
                            "carrierExportVoyageNumber": "319W",
                            "universalImportVoyageReference": "",
                            "universalExportVoyageReference": "",
                            "location": {
                                "unLocationCode": "KRPUS",
                                "facilitySmdgCode": "PNC"
                            },
                            "statusCode": "None",
                            "timestamps": [
                                {
                                    "eventTypeCode": "ARRI",
                                    "eventClassifierCode": "PLN",
                                    "eventDateTime": "2023-05-12T17:00:00+09:00",
                                    "delayReasonCode": null,
                                    "changeRemark": null
                                },
                                {
                                    "eventTypeCode": "DEPA",
                                    "eventClassifierCode": "PLN",
                                    "eventDateTime": "2023-05-13T22:00:00+09:00",
                                    "delayReasonCode": null,
                                    "changeRemark": null
                                },
                                {
                                    "eventTypeCode": "ARRI",
                                    "eventClassifierCode": "EST",
                                    "eventDateTime": "2023-05-14T01:00:00+09:00",
                                    "delayReasonCode": null,
                                    "changeRemark": null
                                },
                                {
                                    "eventTypeCode": "DEPA",
                                    "eventClassifierCode": "EST",
                                    "eventDateTime": "2023-05-15T06:00:00+09:00",
                                    "delayReasonCode": null,
                                    "changeRemark": null
                                },
                                {
                                    "eventTypeCode": "ARRI",
                                    "eventClassifierCode": "ACT",
                                    "eventDateTime": "2023-05-14T07:45:00+09:00",
                                    "delayReasonCode": null,
                                    "changeRemark": null
                                },
                                {
                                    "eventTypeCode": "DEPA",
                                    "eventClassifierCode": "ACT",
                                    "eventDateTime": "2023-05-15T05:52:00+09:00",
                                    "delayReasonCode": null,
                                    "changeRemark": null
                                }
                            ]
                        }

                    ]
                }
            ]
        }
    ]
}';

    FOR _service IN (SELECT * FROM jsonb_array_elements(_json_data->'services')) LOOP
        FOR _vesselSchedule IN (SELECT * FROM jsonb_array_elements(_service->'vesselSchedules')) LOOP
            FOR _transportCall IN (SELECT * FROM jsonb_array_elements(_vesselSchedule->'transportCalls')) LOOP

                -- Insert into `location` table
                INSERT INTO dcsa_im_v3_0.location (
                    id,
                    location_name,
                    un_location_code
                )
                VALUES (
                    uuid_generate_v4(),
                    NULL,
                    _transportCall->'location'->>'unLocationCode'
                )  ON CONFLICT DO NOTHING RETURNING id INTO _location_id ;

                -- Insert import voyage
                INSERT INTO dcsa_im_v3_0.voyage (id, carrier_voyage_number, universal_voyage_reference)
                VALUES (
                uuid_generate_v4(), _transportCall->>'carrierImportVoyageNumber',  _transportCall->>'universalImportVoyageReference'
                 ) ON CONFLICT DO NOTHING  RETURNING id INTO _import_voyage_id;

                -- Insert export voyage
                INSERT INTO dcsa_im_v3_0.voyage (id, carrier_voyage_number, universal_voyage_reference)
                VALUES (
                uuid_generate_v4(), _transportCall->>'carrierExportVoyageNumber',  _transportCall->>'universalExportVoyageReference'
                 ) ON CONFLICT DO NOTHING  RETURNING id INTO _export_voyage_id;

             INSERT INTO dcsa_im_v3_0.transport_call (id, transport_call_reference, transport_call_sequence_number,location_id, import_voyage_id, export_voyage_id)
             SELECT uuid_generate_v4(), _transportCall->>'transportCallReference', (_transportCall->>'transportCallSequenceNumber')::int , _location_id::uuid, _import_voyage_id::uuid, _export_voyage_id::uuid        WHERE NOT EXISTS (
                 SELECT 1 FROM dcsa_im_v3_0.transport_call WHERE id = (_transportCall->>'id')::uuid
             )ON CONFLICT DO NOTHING  RETURNING id INTO _transportCallId;

            -- Iterate over each timestamp
             FOR _timestamp IN (SELECT * FROM jsonb_array_elements(_transportCall->'timestamps')) LOOP
                 -- Insert into `transport_event` table
                 INSERT INTO dcsa_im_v3_0.transport_event (
                      event_id,
                      event_classifier_code,
                      event_created_date_time,
                      event_date_time,
                     transport_event_type_code,
                     transport_call_id
                 )
                 VALUES (
                     uuid_generate_v4(),
                     _timestamp->>'eventClassifierCode',
                     CURRENT_TIMESTAMP,
                     (_timestamp->>'eventDateTime')::timestamp,
                     _timestamp->>'eventTypeCode',
                     _transportCallId
                 );
                   END LOOP;


            END LOOP;

        END LOOP;
    END LOOP;
END $$;
