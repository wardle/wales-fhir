package com.eldrix.nhs.wales.services.demography;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hl7.fhir.dstu3.model.Enumerations.AdministrativeGender;
import org.hl7.fhir.dstu3.model.HumanName;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Identifier.IdentifierUse;
import org.hl7.fhir.dstu3.model.Patient;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.annotation.RequiredParam;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.param.StringParam;
import ca.uhn.fhir.rest.server.IResourceProvider;

/**
 * All resource providers must implement IResourceProvider
 */
public class RestfulPatientResourceProvider implements IResourceProvider {
 
    /**
     * The getResourceType method comes from IResourceProvider, and must
     * be overridden to indicate what type of resource this provider
     * supplies.
     */
    public Class<Patient> getResourceType() {
        return Patient.class;
    }
     
    /**
     * The "@Read" annotation indicates that this method supports the
     * read operation. Read operations should return a single resource
     * instance.
     *
     * @param theId
     *    The read operation takes one parameter, which must be of type
     *    IdDt and must be annotated with the "@Read.IdParam" annotation.
     * @return
     *    Returns a resource matching this identifier, or null if none exists.
     */
    @Read()
    public Patient getResourceById(@IdParam IdType theId) {
    	String identifier = theId.getIdPart();
        Patient patient = new Patient();
        Identifier id = patient.addIdentifier();
        id.setValue(identifier);
        HumanName name = patient.addName();
        name.setFamily("Test");
        name.addGiven("PatientOne");
        patient.setGender(AdministrativeGender.FEMALE);
        patient.setBirthDate(new Date());
        return patient;
    }
 
    /**
     * The "@Search" annotation indicates that this method supports the
     * search operation. You may have many different method annotated with
     * this annotation, to support many different search criteria. This
     * example searches by family name.
     *
     * @param theFamilyName
     *    This operation takes one parameter which is the search criteria. It is
     *    annotated with the "@Required" annotation. This annotation takes one argument,
     *    a string containing the name of the search criteria. The datatype here
     *    is StringParam, but there are other possible parameter types depending on the
     *    specific search criteria.
     * @return
     *    This method returns a list of Patients. This list may contain multiple
     *    matching resources, or it may also be empty.
     */
    @Search()
    public List<Patient> getPatient(@RequiredParam(name = Patient.SP_FAMILY) StringParam theFamilyName) {
        Patient patient = new Patient();
        patient.addIdentifier();
        patient.getIdentifier().get(0).setUse(IdentifierUse.OFFICIAL);
        //patient.getIdentifier().get(0).setSystem(new UriDt("urn:hapitest:mrns"));
        patient.getIdentifier().get(0).setValue("00001");
        patient.addName();
        patient.getName().get(0).setFamily(theFamilyName.getValue());
        patient.getName().get(0).addGiven("PatientOne");
        patient.setGender(AdministrativeGender.MALE);
        return Collections.singletonList(patient);
    }
 
}