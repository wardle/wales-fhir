package com.eldrix.nhs.wales.services.demography;

import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.IdType;
import org.hl7.fhir.dstu3.model.Observation;
import org.hl7.fhir.dstu3.model.Observation.ObservationStatus;
import org.hl7.fhir.dstu3.model.Quantity;
import org.hl7.fhir.dstu3.model.SimpleQuantity;

import ca.uhn.fhir.rest.annotation.IdParam;
import ca.uhn.fhir.rest.annotation.Read;
import ca.uhn.fhir.rest.server.IResourceProvider;

/**
 * All resource providers must implement IResourceProvider
 */
public class ObservationResourceProvider implements IResourceProvider {

	/**
	 * The getResourceType method comes from IResourceProvider, and must
	 * be overridden to indicate what type of resource this provider
	 * supplies.
	 */
	public Class<Observation> getResourceType() {
		return Observation.class;
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
	public Observation getResourceById(@IdParam IdType theId) {
		// Create an Observation instance
		Observation observation = new Observation();

		// Give the observation a status
		observation.setStatus(ObservationStatus.FINAL);

		// Give the observation a code (what kind of observation is this)
		Coding coding = observation.getCode().addCoding();
		coding.setCode("29463-7").setSystem("http://loinc.org").setDisplay("Body Weight");

		// Create a quantity datatype
		Quantity value = new Quantity();
		value.setValue(83.9).setSystem("http://unitsofmeasure.org").setCode("kg");
		observation.setValue(value);

		// Set the reference range
		SimpleQuantity low = new SimpleQuantity();
		low.setValue(45).setSystem("http://unitsofmeasure.org").setCode("kg");
		observation.getReferenceRangeFirstRep().setLow(low);
		SimpleQuantity high = new SimpleQuantity();
		low.setValue(90).setSystem("http://unitsofmeasure.org").setCode("kg");
		observation.getReferenceRangeFirstRep().setHigh(high);  
		return observation;
	}

}